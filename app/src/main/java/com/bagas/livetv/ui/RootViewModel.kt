package com.bagas.livetv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagas.livetv.core.Constants
import com.bagas.livetv.domain.model.ThemeMode
import com.bagas.livetv.domain.repository.PlaylistRepository
import com.bagas.livetv.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    settingsRepository: SettingsRepository,
    private val playlistRepository: PlaylistRepository,
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = settingsRepository.settings
        .map { it.themeMode }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeMode.SYSTEM)

    /** One-shot auto-resume target (last watched channel), or null. */
    private val _resumeChannelId = MutableStateFlow<String?>(null)
    val resumeChannelId: StateFlow<String?> = _resumeChannelId.asStateFlow()

    init {
        // Auto-refresh playlists every 3 hours (Constants.PLAYLIST_TTL_MILLIS). On launch
        // we only hit the network when the cache is already older than the TTL, so
        // reopening the app within the window stays instant/offline; after that we refresh
        // on the interval for as long as the app keeps running. Room cache shows the UI
        // immediately while each refresh happens.
        viewModelScope.launch {
            playlistRepository.refreshStale(Constants.PLAYLIST_TTL_MILLIS, force = false)
            while (true) {
                delay(Constants.PLAYLIST_TTL_MILLIS)
                playlistRepository.refreshStale(Constants.PLAYLIST_TTL_MILLIS, force = true)
            }
        }
        // Resolve the auto-resume target once at startup.
        viewModelScope.launch {
            val settings = settingsRepository.settings.first()
            val lastId = settings.lastChannelId
            if (settings.autoResume && lastId != null && playlistRepository.getChannel(lastId) != null) {
                _resumeChannelId.value = lastId
            }
        }
    }

    fun consumeResume() {
        _resumeChannelId.value = null
    }
}

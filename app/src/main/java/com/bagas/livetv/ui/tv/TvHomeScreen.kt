package com.bagas.livetv.ui.tv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.Button
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import androidx.tv.material3.darkColorScheme
import com.bagas.livetv.domain.model.Channel
import com.bagas.livetv.ui.ChannelsViewModel
import com.bagas.livetv.ui.common.ChannelLogo
import com.bagas.livetv.ui.common.LoadingState
import com.bagas.livetv.ui.common.MessageState

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvHomeScreen(
    onChannelClick: (Channel) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenPlaylists: () -> Unit,
    viewModel: ChannelsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize().padding(horizontal = 48.dp, vertical = 28.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Live TV", style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.weight(1f))
                    Button(onClick = onOpenPlaylists) { Text("Playlist") }
                    Spacer(Modifier.width(12.dp))
                    Button(onClick = onOpenSettings) { Text("Pengaturan") }
                }
                Spacer(Modifier.height(16.dp))

                when {
                    state.loading -> LoadingState()
                    state.channels.isEmpty() && state.favorites.isEmpty() ->
                        MessageState(
                            title = "Belum ada channel",
                            subtitle = "Tambahkan playlist dari menu Playlist.",
                            actionLabel = "Buka Playlist",
                            onAction = onOpenPlaylists,
                        )
                    else -> ChannelRows(state, onChannelClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun ChannelRows(
    state: com.bagas.livetv.ui.ChannelsUiState,
    onChannelClick: (Channel) -> Unit,
) {
    val grouped = state.channels.groupBy { it.group?.takeIf { g -> g.isNotBlank() } ?: "Lainnya" }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        if (state.favorites.isNotEmpty()) {
            item { ChannelRow("Favorit", state.favorites, onChannelClick) }
        }
        if (state.history.isNotEmpty()) {
            item { ChannelRow("Baru ditonton", state.history, onChannelClick) }
        }
        grouped.forEach { (group, channels) ->
            item { ChannelRow(group, channels, onChannelClick) }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun ChannelRow(
    title: String,
    channels: List<Channel>,
    onChannelClick: (Channel) -> Unit,
) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(channels, key = { it.id }) { channel ->
                TvChannelCard(channel, onClick = { onChannelClick(channel) })
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun TvChannelCard(channel: Channel, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(168.dp),
    ) {
        Column(Modifier.width(168.dp)) {
            ChannelLogo(
                name = channel.name,
                logoUrl = channel.logoUrl,
                modifier = Modifier.fillMaxWidth().height(104.dp),
            )
            Text(
                channel.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

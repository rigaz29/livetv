package com.bagas.livetv.core

object Constants {
    /** Default JSON playlist URL placeholder shown in Settings. Replace with your repo. */
    const val DEFAULT_JSON_PLAYLIST_URL =
        "https://raw.githubusercontent.com/USERNAME/REPO/main/playlist.json"

    /** Example iptv-org grouped playlists the user can add with one tap. */
    val SAMPLE_M3U_PLAYLISTS = listOf(
        "Indonesia" to "https://iptv-org.github.io/iptv/countries/id.m3u",
        "Malaysia" to "https://iptv-org.github.io/iptv/countries/my.m3u",
        "International (News)" to "https://iptv-org.github.io/iptv/categories/news.m3u",
        "Sports" to "https://iptv-org.github.io/iptv/categories/sports.m3u",
    )

    /** Playlist cache time-to-live: refresh automatically when older than this. */
    const val PLAYLIST_TTL_MILLIS = 3 * 60 * 60 * 1000L // 3 hours

    const val HISTORY_LIMIT = 30

    /** Auto-retry tuning for playback errors. */
    const val MAX_PLAYBACK_RETRIES = 6
    const val RETRY_BASE_DELAY_MS = 1_000L
    const val RETRY_MAX_DELAY_MS = 30_000L
}

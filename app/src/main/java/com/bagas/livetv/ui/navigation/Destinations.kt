package com.bagas.livetv.ui.navigation

import android.net.Uri

/** Centralised navigation routes, shared by the mobile and TV navigation graphs. */
object Routes {
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val PLAYLISTS = "playlists"

    const val PLAYER_ARG_CHANNEL = "channelId"
    const val PLAYER_ROUTE = "player/{$PLAYER_ARG_CHANNEL}"

    fun player(channelId: String): String = "player/${Uri.encode(channelId)}"
}

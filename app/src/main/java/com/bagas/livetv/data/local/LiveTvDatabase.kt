package com.bagas.livetv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bagas.livetv.data.local.dao.ChannelDao
import com.bagas.livetv.data.local.dao.FavoriteDao
import com.bagas.livetv.data.local.dao.HistoryDao
import com.bagas.livetv.data.local.dao.PlaylistSourceDao
import com.bagas.livetv.data.local.entity.ChannelEntity
import com.bagas.livetv.data.local.entity.FavoriteEntity
import com.bagas.livetv.data.local.entity.HistoryEntity
import com.bagas.livetv.data.local.entity.PlaylistSourceEntity

@Database(
    entities = [
        PlaylistSourceEntity::class,
        ChannelEntity::class,
        FavoriteEntity::class,
        HistoryEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class LiveTvDatabase : RoomDatabase() {
    abstract fun playlistSourceDao(): PlaylistSourceDao
    abstract fun channelDao(): ChannelDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao

    companion object {
        const val NAME = "livetv.db"
    }
}

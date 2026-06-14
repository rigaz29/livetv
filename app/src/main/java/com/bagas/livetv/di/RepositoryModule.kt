package com.bagas.livetv.di

import com.bagas.livetv.data.repository.PlaylistRepositoryImpl
import com.bagas.livetv.data.repository.SettingsRepositoryImpl
import com.bagas.livetv.domain.repository.PlaylistRepository
import com.bagas.livetv.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlaylistRepository(impl: PlaylistRepositoryImpl): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}

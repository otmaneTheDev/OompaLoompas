package com.otmanethedev.oompaloompa.info.di

import com.otmanethedev.oompaloompa.info.data.datasource.RemoteDataSource
import com.otmanethedev.oompaloompa.info.data.datasource.RemoteDataSourceImpl
import com.otmanethedev.oompaloompa.info.data.repository.OompaLoompaRepositoryImpl
import com.otmanethedev.oompaloompa.info.domain.repository.OompaLoompasRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun provideOompaLoompasRepository(repository: OompaLoompaRepositoryImpl): OompaLoompasRepository

    @Binds
    @Singleton
    fun provideRemoteDataSource(dataSource: RemoteDataSourceImpl): RemoteDataSource
}

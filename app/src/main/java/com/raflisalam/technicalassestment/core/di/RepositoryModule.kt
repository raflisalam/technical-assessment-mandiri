package com.raflisalam.technicalassestment.core.di

import com.raflisalam.technicalassestment.data.repository.MoviesRepositoryImpl
import com.raflisalam.technicalassestment.domain.MoviesRepository
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
    abstract fun bindMoviesRepository(
        impl: MoviesRepositoryImpl
    ): MoviesRepository

}

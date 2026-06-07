package com.raflisalam.technicalassestment.core.di

import com.raflisalam.technicalassestment.domain.usecase.DiscoverMoviesUseCase
import com.raflisalam.technicalassestment.domain.usecase.DiscoverMoviesUseCaseImpl
import com.raflisalam.technicalassestment.domain.usecase.GetFavoriteDetailUseCaseImpl
import com.raflisalam.technicalassestment.domain.usecase.GetMovieDetailUseCase
import com.raflisalam.technicalassestment.domain.usecase.GetMovieDetailUseCaseImpl
import com.raflisalam.technicalassestment.domain.usecase.GetMovieGenresUseCase
import com.raflisalam.technicalassestment.domain.usecase.GetMovieReviewsUseCase
import com.raflisalam.technicalassestment.domain.usecase.GetMovieReviewsUseCaseImpl
import com.raflisalam.technicalassestment.domain.usecase.GetMovieVideosUseCase
import com.raflisalam.technicalassestment.domain.usecase.GetMovieVideosUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetMovieGenresUseCase(
        impl: GetFavoriteDetailUseCaseImpl
    ): GetMovieGenresUseCase

    @Binds
    abstract fun bindDiscoverMoviesUseCase(
        impl: DiscoverMoviesUseCaseImpl
    ): DiscoverMoviesUseCase

    @Binds
    abstract fun bindGetMovieDetailUseCase(
        impl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase

    @Binds
    abstract fun bindGetMovieVideosUseCase(
        impl: GetMovieVideosUseCaseImpl
    ): GetMovieVideosUseCase

    @Binds
    abstract fun bindGetMovieReviewsUseCase(
        impl: GetMovieReviewsUseCaseImpl
    ): GetMovieReviewsUseCase

}

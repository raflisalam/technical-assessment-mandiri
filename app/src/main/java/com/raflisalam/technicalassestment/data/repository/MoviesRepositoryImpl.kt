package com.raflisalam.technicalassestment.data.repository

import com.raflisalam.technicalassestment.data.dto.GenreListResponse
import com.raflisalam.technicalassestment.data.dto.MovieDetailResponse
import com.raflisalam.technicalassestment.data.dto.MovieListResponse
import com.raflisalam.technicalassestment.data.dto.ReviewListResponse
import com.raflisalam.technicalassestment.data.dto.VideoListResponse
import com.raflisalam.technicalassestment.data.remote.MoviesRemoteDataSource
import com.raflisalam.technicalassestment.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override fun getMovieGenres(): Flow<GenreListResponse> = flow {
        emit(remoteDataSource.getMovieGenres())
    }

    override fun discoverMovies(genreId: Int, page: Int, sortBy: String): Flow<MovieListResponse> = flow {
        emit(remoteDataSource.discoverMovies(genreId, page, sortBy))
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse> = flow {
        emit(remoteDataSource.getMovieDetail(movieId))
    }

    override fun getMovieVideos(movieId: Int): Flow<VideoListResponse> = flow {
        emit(remoteDataSource.getMovieVideos(movieId))
    }

    override fun getMovieReviews(movieId: Int, page: Int): Flow<ReviewListResponse> = flow {
        emit(remoteDataSource.getMovieReviews(movieId, page))
    }

}

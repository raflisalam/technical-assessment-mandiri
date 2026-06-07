package com.raflisalam.technicalassestment.data.remote

import com.raflisalam.technicalassestment.data.dto.GenreListResponse
import com.raflisalam.technicalassestment.data.dto.MovieDetailResponse
import com.raflisalam.technicalassestment.data.dto.MovieListResponse
import com.raflisalam.technicalassestment.data.dto.ReviewListResponse
import com.raflisalam.technicalassestment.data.dto.VideoListResponse
import javax.inject.Inject

interface MoviesRemoteDataSource : MoviesApiClient

class MoviesRemoteDataSourceImpl @Inject constructor(private val apiClient: MoviesApiClient) : MoviesRemoteDataSource {

    override suspend fun getMovieGenres(): GenreListResponse {
        return apiClient.getMovieGenres()
    }

    override suspend fun discoverMovies(
        genreId: Int,
        page: Int,
        sortBy: String
    ): MovieListResponse {
        return apiClient.discoverMovies(
            genreId = genreId,
            page = page,
            sortBy = sortBy
        )
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponse {
        return apiClient.getMovieDetail(movieId = movieId)
    }

    override suspend fun getMovieVideos(movieId: Int): VideoListResponse {
        return apiClient.getMovieVideos(movieId = movieId)
    }

    override suspend fun getMovieReviews(
        movieId: Int,
        page: Int
    ): ReviewListResponse {
        return apiClient.getMovieReviews(
            movieId = movieId,
            page = page
        )
    }

}
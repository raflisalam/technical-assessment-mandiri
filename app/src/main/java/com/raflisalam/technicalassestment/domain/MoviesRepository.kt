package com.raflisalam.technicalassestment.domain

import com.raflisalam.technicalassestment.data.dto.GenreListResponse
import com.raflisalam.technicalassestment.data.dto.MovieDetailResponse
import com.raflisalam.technicalassestment.data.dto.MovieListResponse
import com.raflisalam.technicalassestment.data.dto.ReviewListResponse
import com.raflisalam.technicalassestment.data.dto.VideoListResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovieGenres(): Flow<GenreListResponse>
    fun discoverMovies(genreId: Int, page: Int, sortBy: String): Flow<MovieListResponse>
    fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse>
    fun getMovieVideos(movieId: Int): Flow<VideoListResponse>
    fun getMovieReviews(movieId: Int, page: Int): Flow<ReviewListResponse>
}
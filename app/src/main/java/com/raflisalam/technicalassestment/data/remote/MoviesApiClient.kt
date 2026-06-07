package com.raflisalam.technicalassestment.data.remote

import com.raflisalam.technicalassestment.core.utils.AppConstant
import com.raflisalam.technicalassestment.data.dto.GenreListResponse
import com.raflisalam.technicalassestment.data.dto.MovieDetailResponse
import com.raflisalam.technicalassestment.data.dto.MovieListResponse
import com.raflisalam.technicalassestment.data.dto.ReviewListResponse
import com.raflisalam.technicalassestment.data.dto.VideoListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiClient {
    @GET("genre/movie/list")
    suspend fun getMovieGenres(): GenreListResponse

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = AppConstant.SORT_DISCOVER_MOVIE_KEY
    ): MovieListResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int
    ): MovieDetailResponse

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int
    ): VideoListResponse

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int
    ): ReviewListResponse
}
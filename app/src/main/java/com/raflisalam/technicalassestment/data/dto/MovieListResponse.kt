package com.raflisalam.technicalassestment.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("page") val page: Int?,
    @SerialName("results") val results: List<MovieDto>?,
    @SerialName("total_pages") val totalPages: Int?,
    @SerialName("total_results") val totalResults: Int?
)

@Serializable
data class MovieDto(
    @SerialName("id") val id: Int?,
    @SerialName("title") val title: String?,
    @SerialName("overview") val overview: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("vote_count") val voteCount: Int?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("genre_ids") val genreIds: List<Int>?,
    @SerialName("popularity") val popularity: Double?
)

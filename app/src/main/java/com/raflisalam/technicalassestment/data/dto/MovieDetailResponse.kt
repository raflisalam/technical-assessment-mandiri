package com.raflisalam.technicalassestment.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerialName("id") val id: Int?,
    @SerialName("title") val title: String?,
    @SerialName("tagline") val tagline: String?,
    @SerialName("overview") val overview: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("vote_count") val voteCount: Int?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("status") val status: String?,
    @SerialName("genres") val genres: List<GenreDto>?,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguageDto>?
)

@Serializable
data class SpokenLanguageDto(
    @SerialName("english_name") val englishName: String?,
    @SerialName("iso_639_1") val iso6391: String?,
    @SerialName("name") val name: String?
)

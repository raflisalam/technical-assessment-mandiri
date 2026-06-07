package com.raflisalam.technicalassestment.domain.model

import com.raflisalam.technicalassestment.core.utils.emptyString

data class MovieDetail(
    val id: Int = 0,
    val title: String = emptyString(),
    val tagline: String = emptyString(),
    val overview: String = emptyString(),
    val posterPath: String = emptyString(),
    val backdropPath: String = emptyString(),
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val releaseDate: String = emptyString(),
    val runtime: Int = 0,
    val status: String = emptyString(),
    val genres: List<Genre> = emptyList(),
    val spokenLanguages: List<String> = emptyList()
)

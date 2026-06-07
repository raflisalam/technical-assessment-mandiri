package com.raflisalam.technicalassestment.domain.model

import com.raflisalam.technicalassestment.core.utils.emptyString

data class Movie(
    val id: Int = 0,
    val title: String = emptyString(),
    val overview: String = emptyString(),
    val posterPath: String = emptyString(),
    val backdropPath: String = emptyString(),
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val releaseDate: String = emptyString(),
    val genreIds: List<Int> = emptyList()
)

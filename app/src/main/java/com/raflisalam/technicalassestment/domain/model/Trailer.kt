package com.raflisalam.technicalassestment.domain.model

import com.raflisalam.technicalassestment.core.utils.emptyString

data class Trailer(
    val id: String = emptyString(),
    val key: String = emptyString(),
    val name: String = emptyString(),
    val official: Boolean = false,
    val publishedAt: String = emptyString(),
)

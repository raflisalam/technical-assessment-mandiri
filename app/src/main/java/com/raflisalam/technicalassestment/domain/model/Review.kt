package com.raflisalam.technicalassestment.domain.model

import com.raflisalam.technicalassestment.core.utils.emptyString

data class Review(
    val id: String = emptyString(),
    val author: ReviewAuthor = ReviewAuthor(),
    val content: String = emptyString(),
    val createdAt: String = emptyString()
)

data class ReviewAuthor(
    val name: String = emptyString(),
    val username: String = emptyString(),
    val avatarPath: String = emptyString(),
    val rating: Double = 0.0,
)

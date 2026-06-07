package com.raflisalam.technicalassestment.domain.mapper

import com.raflisalam.technicalassestment.core.utils.AppConstant.TRAILER_TYPE_KEY
import com.raflisalam.technicalassestment.core.utils.AppConstant.YOUTUBE_SITE_KEY
import com.raflisalam.technicalassestment.core.utils.emptyString
import com.raflisalam.technicalassestment.core.utils.normalizeAvatarPath
import com.raflisalam.technicalassestment.core.utils.orDefault
import com.raflisalam.technicalassestment.core.utils.orEmpty
import com.raflisalam.technicalassestment.core.utils.orFalse
import com.raflisalam.technicalassestment.core.utils.orZero
import com.raflisalam.technicalassestment.data.dto.AuthorDetailsDto
import com.raflisalam.technicalassestment.data.dto.GenreDto
import com.raflisalam.technicalassestment.data.dto.MovieDetailResponse
import com.raflisalam.technicalassestment.data.dto.MovieDto
import com.raflisalam.technicalassestment.data.dto.ReviewDto
import com.raflisalam.technicalassestment.data.dto.VideoDto
import com.raflisalam.technicalassestment.data.dto.VideoListResponse
import com.raflisalam.technicalassestment.domain.model.Genre
import com.raflisalam.technicalassestment.domain.model.Movie
import com.raflisalam.technicalassestment.domain.model.MovieDetail
import com.raflisalam.technicalassestment.domain.model.Review
import com.raflisalam.technicalassestment.domain.model.ReviewAuthor
import com.raflisalam.technicalassestment.domain.model.Trailer

fun GenreDto.toDomain() = Genre(
    id = id.orZero(),
    name = name.orEmpty()
)

fun MovieDto.toDomain() = Movie(
    id = id.orZero(),
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    voteAverage = voteAverage.orZero(),
    voteCount = voteCount.orZero(),
    releaseDate = releaseDate.orEmpty(),
    genreIds = genreIds.orEmpty()
)

fun MovieDetailResponse.toDomain() = MovieDetail(
    id = id.orZero(),
    title = title.orEmpty(),
    tagline = tagline.orEmpty(),
    overview = overview.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    voteAverage = voteAverage.orZero(),
    voteCount = voteCount.orZero(),
    releaseDate = releaseDate.orEmpty(),
    runtime = runtime.orZero(),
    status = status.orEmpty(),
    genres = genres?.map { it.toDomain() }.orEmpty(),
    spokenLanguages = spokenLanguages?.map { it.englishName.orEmpty() }.orEmpty()
)

fun VideoListResponse.toTrailers(): List<Trailer> =
    results
        ?.filter { it.site == YOUTUBE_SITE_KEY && it.type == TRAILER_TYPE_KEY }
        ?.sortedByDescending { it.official.orFalse() }
        ?.map { it.toDomain() }
        .orEmpty()

fun VideoDto.toDomain() = Trailer(
    id = id.orEmpty(),
    key = key.orEmpty(),
    name = name.orEmpty(),
    official = official.orFalse(),
    publishedAt = publishedAt.orEmpty()
)

fun ReviewDto.toDomain() = Review(
    id = id.orEmpty(),
    content = content.orEmpty(),
    createdAt = createdAt.orEmpty(),
    author = authorDetails?.toDomain(fallbackUsername = author.orEmpty()).orDefault(ReviewAuthor())
)

fun AuthorDetailsDto.toDomain(fallbackUsername: String = emptyString()) = ReviewAuthor(
    name = (name?.takeIf { it.isNotBlank() } ?: username).orEmpty().ifEmpty { fallbackUsername },
    username = username.orEmpty().ifEmpty { fallbackUsername },
    avatarPath = avatarPath?.normalizeAvatarPath().orEmpty(),
    rating = rating.orZero(),
)
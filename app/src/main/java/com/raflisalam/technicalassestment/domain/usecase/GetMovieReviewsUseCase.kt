package com.raflisalam.technicalassestment.domain.usecase

import com.raflisalam.technicalassestment.data.dto.ReviewListResponse
import com.raflisalam.technicalassestment.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieReviewsUseCase {
    operator fun invoke(movieId: Int, page: Int): Flow<ReviewListResponse>
}

class GetMovieReviewsUseCaseImpl @Inject constructor(private val repository: MoviesRepository) : GetMovieReviewsUseCase {

    override fun invoke(movieId: Int, page: Int): Flow<ReviewListResponse> {
        return repository.getMovieReviews(movieId, page)
    }

}

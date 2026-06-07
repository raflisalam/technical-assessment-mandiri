package com.raflisalam.technicalassestment.domain.usecase

import com.raflisalam.technicalassestment.data.dto.MovieDetailResponse
import com.raflisalam.technicalassestment.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieDetailUseCase {
    operator fun invoke(movieId: Int): Flow<MovieDetailResponse>
}

class GetMovieDetailUseCaseImpl @Inject constructor(private val repository: MoviesRepository) : GetMovieDetailUseCase {

    override fun invoke(movieId: Int): Flow<MovieDetailResponse> {
        return repository.getMovieDetail(movieId)
    }

}

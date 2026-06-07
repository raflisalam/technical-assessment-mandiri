package com.raflisalam.technicalassestment.domain.usecase

import com.raflisalam.technicalassestment.data.dto.GenreListResponse
import com.raflisalam.technicalassestment.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieGenresUseCase {
    operator fun invoke(): Flow<GenreListResponse>
}

class GetFavoriteDetailUseCaseImpl @Inject constructor(private val repository: MoviesRepository) : GetMovieGenresUseCase {

    override fun invoke(): Flow<GenreListResponse> {
        return repository.getMovieGenres()
    }

}



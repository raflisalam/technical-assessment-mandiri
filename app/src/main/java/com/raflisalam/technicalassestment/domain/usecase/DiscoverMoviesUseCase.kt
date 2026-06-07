package com.raflisalam.technicalassestment.domain.usecase

import com.raflisalam.technicalassestment.data.dto.MovieListResponse
import com.raflisalam.technicalassestment.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DiscoverMoviesUseCase {
    operator fun invoke(genreId: Int, page: Int, sortBy: String): Flow<MovieListResponse>
}

class DiscoverMoviesUseCaseImpl @Inject constructor(private val repository: MoviesRepository) : DiscoverMoviesUseCase {

    override fun invoke(genreId: Int, page: Int, sortBy: String): Flow<MovieListResponse> {
        return repository.discoverMovies(genreId, page, sortBy)
    }

}

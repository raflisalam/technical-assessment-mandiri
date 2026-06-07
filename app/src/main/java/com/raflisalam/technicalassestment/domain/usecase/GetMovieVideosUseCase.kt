package com.raflisalam.technicalassestment.domain.usecase

import com.raflisalam.technicalassestment.data.dto.VideoListResponse
import com.raflisalam.technicalassestment.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieVideosUseCase {
    operator fun invoke(movieId: Int): Flow<VideoListResponse>
}

class GetMovieVideosUseCaseImpl @Inject constructor(private val repository: MoviesRepository) : GetMovieVideosUseCase {

    override fun invoke(movieId: Int): Flow<VideoListResponse> {
        return repository.getMovieVideos(movieId)
    }

}

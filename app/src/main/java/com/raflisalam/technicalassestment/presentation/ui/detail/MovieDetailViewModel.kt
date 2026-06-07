package com.raflisalam.technicalassestment.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raflisalam.technicalassestment.core.utils.ApiResult
import com.raflisalam.technicalassestment.core.utils.startProcess
import com.raflisalam.technicalassestment.domain.mapper.toDomain
import com.raflisalam.technicalassestment.domain.mapper.toTrailers
import com.raflisalam.technicalassestment.domain.model.MovieDetail
import com.raflisalam.technicalassestment.domain.model.Review
import com.raflisalam.technicalassestment.domain.model.Trailer
import com.raflisalam.technicalassestment.domain.usecase.GetMovieDetailUseCase
import com.raflisalam.technicalassestment.domain.usecase.GetMovieReviewsUseCase
import com.raflisalam.technicalassestment.domain.usecase.GetMovieVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    private val _movieDetailState = MutableStateFlow<ApiResult<MovieDetail>>(ApiResult.Default)
    val movieDetailState: StateFlow<ApiResult<MovieDetail>> = _movieDetailState.asStateFlow()

    private val _trailersState = MutableStateFlow<ApiResult<List<Trailer>>>(ApiResult.Default)
    val trailersState: StateFlow<ApiResult<List<Trailer>>> = _trailersState.asStateFlow()

    private val _reviewsState = MutableStateFlow<ApiResult<List<Review>>>(ApiResult.Default)
    val reviewsState: StateFlow<ApiResult<List<Review>>> = _reviewsState.asStateFlow()

    private val _isLoadingMoreReviews = MutableStateFlow(false)
    val isLoadingMoreReviews: StateFlow<Boolean> = _isLoadingMoreReviews.asStateFlow()

    private var reviewsCurrentPage = 1
    private var reviewsTotalPages = 1
    private val accumulatedReviews = mutableListOf<Review>()

    val hasNextReviewsPage: Boolean get() = reviewsCurrentPage < reviewsTotalPages

    fun loadAllData(movieId: Int) {
        loadMovieDetail(movieId)
        loadTrailers(movieId)
        loadReviews(movieId)
    }

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            startProcess(_movieDetailState) {
                getMovieDetailUseCase(movieId).map { it.toDomain() }
            }
        }
    }

    fun loadTrailers(movieId: Int) {
        viewModelScope.launch {
            startProcess(_trailersState) {
                getMovieVideosUseCase(movieId).map { it.toTrailers() }
            }
        }
    }

    fun loadReviews(movieId: Int) {
        reviewsCurrentPage = 1
        reviewsTotalPages = 1
        accumulatedReviews.clear()
        viewModelScope.launch {
            startProcess(_reviewsState) {
                getMovieReviewsUseCase(movieId, 1).map { response ->
                    reviewsTotalPages = response.totalPages ?: 1
                    reviewsCurrentPage = response.page ?: 1
                    response.results?.map { it.toDomain() }.orEmpty().also {
                        accumulatedReviews.clear()
                        accumulatedReviews.addAll(it)
                    }
                }
            }
        }
    }

    fun loadNextReviewsPage(movieId: Int) {
        if (!hasNextReviewsPage || _isLoadingMoreReviews.value || _reviewsState.value is ApiResult.Loading) return
        viewModelScope.launch {
            _isLoadingMoreReviews.value = true
            getMovieReviewsUseCase(movieId, reviewsCurrentPage + 1)
                .map { response ->
                    reviewsTotalPages = response.totalPages ?: 1
                    reviewsCurrentPage = response.page ?: (reviewsCurrentPage + 1)
                    response.results?.map { it.toDomain() }.orEmpty()
                }
                .catch { _isLoadingMoreReviews.value = false }
                .collect { newReviews ->
                    accumulatedReviews.addAll(newReviews)
                    _reviewsState.update { ApiResult.Success(accumulatedReviews.toList()) }
                    _isLoadingMoreReviews.value = false
                }
        }
    }
}

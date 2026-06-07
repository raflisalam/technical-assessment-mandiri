package com.raflisalam.technicalassestment.presentation.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raflisalam.technicalassestment.core.utils.ApiResult
import com.raflisalam.technicalassestment.core.utils.startProcess
import com.raflisalam.technicalassestment.domain.mapper.toDomain
import com.raflisalam.technicalassestment.domain.model.Genre
import com.raflisalam.technicalassestment.domain.model.Movie
import com.raflisalam.technicalassestment.domain.usecase.DiscoverMoviesUseCase
import com.raflisalam.technicalassestment.domain.usecase.GetMovieGenresUseCase
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
class MoviesViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val discoverMoviesUseCase: DiscoverMoviesUseCase
) : ViewModel() {

    private val _genresState = MutableStateFlow<ApiResult<List<Genre>>>(ApiResult.Default)
    val genresState: StateFlow<ApiResult<List<Genre>>> = _genresState.asStateFlow()

    private val _moviesState = MutableStateFlow<ApiResult<List<Movie>>>(ApiResult.Default)
    val moviesState: StateFlow<ApiResult<List<Movie>>> = _moviesState.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private val _selectedGenreId = MutableStateFlow(0)
    val selectedGenreId: StateFlow<Int> = _selectedGenreId.asStateFlow()

    private val _sortBy = MutableStateFlow("popularity.desc")
    val sortBy: StateFlow<String> = _sortBy.asStateFlow()

    private var currentPage = 1
    private var totalPages = 1
    private val accumulatedMovies = mutableListOf<Movie>()

    val hasNextPage: Boolean get() = currentPage < totalPages

    init {
        loadGenres()
        discoverMovies()
    }

    fun loadGenres() {
        viewModelScope.launch {
            startProcess(_genresState) {
                getMovieGenresUseCase().map { response ->
                    response.genres?.map { it.toDomain() }.orEmpty()
                }
            }
        }
    }

    fun discoverMovies(
        genreId: Int = _selectedGenreId.value,
        sortBy: String = _sortBy.value
    ) {
        _selectedGenreId.value = genreId
        _sortBy.value = sortBy
        currentPage = 1
        totalPages = 1
        accumulatedMovies.clear()
        viewModelScope.launch {
            startProcess(_moviesState) {
                discoverMoviesUseCase(genreId, 1, sortBy).map { response ->
                    totalPages = response.totalPages ?: 1
                    currentPage = response.page ?: 1
                    response.results?.map { it.toDomain() }.orEmpty().also {
                        accumulatedMovies.clear()
                        accumulatedMovies.addAll(it)
                    }
                }
            }
        }
    }

    fun onGenreSelected(genreId: Int) {
        if (_selectedGenreId.value == genreId) return
        discoverMovies(genreId = genreId)
    }

    fun onSortByChanged(sortBy: String) {
        if (_sortBy.value == sortBy) return
        discoverMovies(sortBy = sortBy)
    }

    fun loadNextPage() {
        if (!hasNextPage || _isLoadingMore.value || _moviesState.value is ApiResult.Loading) return
        viewModelScope.launch {
            _isLoadingMore.value = true
            discoverMoviesUseCase(_selectedGenreId.value, currentPage + 1, _sortBy.value)
                .map { response ->
                    totalPages = response.totalPages ?: 1
                    currentPage = response.page ?: (currentPage + 1)
                    response.results?.map { it.toDomain() }.orEmpty()
                }
                .catch { _isLoadingMore.value = false }
                .collect { newMovies ->
                    accumulatedMovies.addAll(newMovies)
                    _moviesState.update { ApiResult.Success(accumulatedMovies.toList()) }
                    _isLoadingMore.value = false
                }
        }
    }
}

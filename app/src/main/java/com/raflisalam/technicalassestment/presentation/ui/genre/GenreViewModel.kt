package com.raflisalam.technicalassestment.presentation.ui.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raflisalam.technicalassestment.core.utils.ApiResult
import com.raflisalam.technicalassestment.core.utils.startProcess
import com.raflisalam.technicalassestment.domain.mapper.toDomain
import com.raflisalam.technicalassestment.domain.model.Genre
import com.raflisalam.technicalassestment.domain.usecase.GetMovieGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase
) : ViewModel() {

    private val _genresState = MutableStateFlow<ApiResult<List<Genre>>>(ApiResult.Default)
    val genresState: StateFlow<ApiResult<List<Genre>>> = _genresState.asStateFlow()

    init {
        loadGenres()
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
}

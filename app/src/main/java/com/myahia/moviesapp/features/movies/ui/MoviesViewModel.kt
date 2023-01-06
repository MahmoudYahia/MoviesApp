package com.myahia.moviesapp.features.movies.ui

import androidx.lifecycle.viewModelScope
import com.myahia.moviesapp.R
import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import com.myahia.moviesapp.features.movies.domain.MoviesUseCase
import com.myahia.moviesapp.utils.APIResult
import com.myahia.moviesapp.utils.BaseViewModel
import com.myahia.moviesapp.utils.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) :
    BaseViewModel() {

    private val _moviesListState = MutableStateFlow<MoviesListState>(
        MoviesListState.Empty
    )
    val moviesListState: StateFlow<MoviesListState> = _moviesListState
    var selectedMovie: MovieItem? = null

    sealed class MoviesListState {
        object Empty : MoviesListState()
        object Loading : MoviesListState()
        class Success<out T : Any>(val data: T) : MoviesListState()
        class Error(val Error: UserMessage) : MoviesListState()
    }

    fun getMovieList() {
        _moviesListState.value = MoviesListState.Loading
        viewModelScope.launch {
            when (val result = moviesUseCase.getMoviesList()) {
                is APIResult.Error -> {
                    _moviesListState.value = MoviesListState.Empty
                    handleAPIResultError(result)
                }
                is APIResult.Success -> {
                    handleMoviesListResult(result)
                }
            }
        }
    }

    private fun handleMoviesListResult(result: APIResult.Success<MoviesListResponseBody>) {
        if (result.data.data.isNullOrEmpty().not()) {
            _moviesListState.value = MoviesListState.Success(result.data)
        } else {
            _moviesListState.value =
                MoviesListState.Error(UserMessage(R.string.SomethingWentWrong))
        }

    }

    fun getMovieDetails(id: Int) {
        _moviesListState.value = MoviesListState.Loading
        viewModelScope.launch {
            when (val result = moviesUseCase.getMovieDetails(id)) {
                is APIResult.Error -> {
                    handleAPIResultError(result)
                }
                is APIResult.Success -> {
                    handleMovieDetailsResult(result)
                }
            }
        }
    }

    private fun handleMovieDetailsResult(result: APIResult.Success<MovieItem>) {
        _moviesListState.value = MoviesListState.Success(result.data)
    }

}
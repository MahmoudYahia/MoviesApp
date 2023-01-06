package com.myahia.moviesapp.features.movies.domain

import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import com.myahia.moviesapp.features.movies.data.repo.IMoviesRepository
import com.myahia.moviesapp.utils.APIResult
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val moviesRepo: IMoviesRepository) {

    suspend fun getMoviesList(): APIResult<MoviesListResponseBody> {
        return moviesRepo.getMoviesList()
    }

    suspend fun getMovieDetails(id: Int): APIResult<MovieItem> {
        return moviesRepo.getMoviesDetails(id)
    }
}
package com.myahia.moviesapp.features.movies.data.repo.remote

import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import com.myahia.moviesapp.utils.APIResult

interface IMoviesRemoteDataSource {
    suspend fun getMoviesList(): APIResult<MoviesListResponseBody>
    suspend fun getMoviesDetails(movieID: Int): APIResult<MovieItem>
}
package com.myahia.moviesapp.features.movies.data.repo

import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import com.myahia.moviesapp.utils.APIResult

interface IMoviesRepository {

    suspend fun getMoviesList(): APIResult<MoviesListResponseBody>
    suspend fun getMoviesDetails(movieID: Int):APIResult<MovieItem>
}
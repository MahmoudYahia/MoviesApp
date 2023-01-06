package com.myahia.moviesapp.features.movies.data.repo.remote

import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import com.myahia.moviesapp.features.movies.data.network.MoviesApi
import com.myahia.moviesapp.utils.APIResult
import com.myahia.moviesapp.utils.BaseRemoteDataSource

class MoviesRemoteDataSource(private val api: MoviesApi, private val moviesApiKey: String) :
    IMoviesRemoteDataSource,
    BaseRemoteDataSource() {

    override suspend fun getMoviesList(): APIResult<MoviesListResponseBody> {
        return getAPIResult(safeApiCall { api.getMoviesList(moviesApiKey) })
    }
    override suspend fun getMoviesDetails(movieID: Int): APIResult<MovieItem> {
        return getAPIResult(safeApiCall { api.getMovieDetails(movieID, moviesApiKey) })
    }

}
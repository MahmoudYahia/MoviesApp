package com.myahia.moviesapp.features.movies.data.repo

import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import com.myahia.moviesapp.features.movies.data.repo.remote.IMoviesRemoteDataSource
import com.myahia.moviesapp.utils.APIResult

class MoviesRepository(private val remoteDataSource: IMoviesRemoteDataSource) : IMoviesRepository {
    override suspend fun getMoviesList(): APIResult<MoviesListResponseBody> {
      return  remoteDataSource.getMoviesList()
    }

    override suspend fun getMoviesDetails(movieID: Int): APIResult<MovieItem> {
      return  remoteDataSource.getMoviesDetails(movieID)
    }
}
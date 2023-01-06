package com.myahia.moviesapp.features.movies.data.network

import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie")
    suspend fun getMoviesList(@Query("api_key") apiKey: String): Response<MoviesListResponseBody>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieItem>
}
package com.myahia.moviesapp.features.movies.di

import com.myahia.moviesapp.features.di.DIAnnotations
import com.myahia.moviesapp.features.movies.data.network.MoviesApi
import com.myahia.moviesapp.features.movies.data.repo.IMoviesRepository
import com.myahia.moviesapp.features.movies.data.repo.MoviesRepository
import com.myahia.moviesapp.features.movies.data.repo.remote.IMoviesRemoteDataSource
import com.myahia.moviesapp.features.movies.data.repo.remote.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class MoviesModule {
    @Provides
    @ViewModelScoped
    fun providesMoviesAPI(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providesMoviesRemoteDataSrc(
        api: MoviesApi,
        @DIAnnotations.MoviesAPIKey apiKey: String
    ): IMoviesRemoteDataSource {
        return MoviesRemoteDataSource(api, apiKey)
    }

    @Provides
    @ViewModelScoped
    fun providesMoviesRepo(dataSrc: IMoviesRemoteDataSource): IMoviesRepository {
        return MoviesRepository(dataSrc)
    }
}
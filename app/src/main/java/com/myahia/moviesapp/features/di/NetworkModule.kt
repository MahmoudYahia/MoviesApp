package com.myahia.moviesapp.features.di

import com.myahia.moviesapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    @DIAnnotations.MoviesBaseURL
    fun providesBaseUrl(): String {
        return BuildConfig.MoviesBaseUrl
    }

    @Provides
    @Singleton
    @DIAnnotations.MoviesAPIKey
    fun providesAPIKey(): String {
        return BuildConfig.API_KEY
    }


    @Singleton
    @Provides
    fun providesOkHttpClient(
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        @DIAnnotations.MoviesBaseURL baseURl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseURl)
            .build();
    }


}
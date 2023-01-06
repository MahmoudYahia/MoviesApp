package com.myahia.moviesapp.features.di

import javax.inject.Qualifier

object DIAnnotations {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MoviesBaseURL

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MoviesAPIKey

}
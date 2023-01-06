package com.myahia.moviesapp.features.movies.domain

import com.myahia.moviesapp.BuildConfig

object ImageHelper {
    enum class ImageQuality(val path: String) {
        Low("w200"),
        Normal("w400"),
        High("w500")
    }

    //https://image.tmdb.org/t/p/w200/8tZYtuWezp8JbcsvHYO0O46tFbo.jpg
    fun getImageFullUrl(imageQuality: ImageQuality, imagePath: String?): String {
        return "${BuildConfig.MoviesBaseImageUrl}/${imageQuality.path}/${imagePath.orEmpty()}"
    }
}
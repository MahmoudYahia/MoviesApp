package com.myahia.moviesapp.utils

import android.content.Context
import androidx.annotation.StringRes

data class UserMessage(
    @StringRes private val resMessage: Int? = null,
    private val strMessage: String? = null
) {
    fun getMessage(context: Context): String? {
        return if (resMessage != null) context.getString(resMessage)
        else strMessage
    }
}

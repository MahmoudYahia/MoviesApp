package com.myahia.moviesapp.utils

import androidx.lifecycle.ViewModel
import com.myahia.moviesapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {

    private val _uiMessage = MutableStateFlow<UIMessageState>(UIMessageState.Default)
    val uiMessages: StateFlow<UIMessageState> = _uiMessage

    sealed class UIMessageState {
        object Default : UIMessageState()
        class Error(val error: UserMessage) : UIMessageState()
    }

    fun handleAPIResultError(result: APIResult.Error) {
        when (val error = result.error) {
            is ErrorTypes.ConnectError -> showErrorMessage(UserMessage(resMessage = R.string.NoInternetConnection))
            is ErrorTypes.GeneralError -> showErrorMessage(UserMessage(resMessage = R.string.NoInternetConnection))
            is ErrorTypes.CustomError -> showErrorMessage(error.errorMessage)
            is ErrorTypes.TokenExpiryError -> {
                //
            }
            is ErrorTypes.InActiveUserError -> {
                //
            }
            is ErrorTypes.TokenError -> {

            }
        }
    }

    private fun showErrorMessage(userMessage: UserMessage) {
        _uiMessage.value = UIMessageState.Error(userMessage)
    }
}
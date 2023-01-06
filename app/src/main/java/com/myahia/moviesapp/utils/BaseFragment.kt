package com.myahia.moviesapp.utils

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.myahia.moviesapp.R
import kotlinx.coroutines.launch


abstract class BaseFragment : Fragment() {
    private var _baseViewModel: BaseViewModel? = null
    fun setBaseViewModel(viewModel: BaseViewModel) {
        _baseViewModel = viewModel
    }


    fun showErrorMessage() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseObservables()
    }

    private fun initBaseObservables() {
        lifecycleScope.launch {
            _baseViewModel?.uiMessages?.collect {
                when (it) {
                    BaseViewModel.UIMessageState.Default -> {}
                    is BaseViewModel.UIMessageState.Error -> {
                        showSnackBar(
                            view,
                            it.error.getMessage(requireContext())
                                ?: getString(R.string.SomethingWentWrong)
                        )

                    }
                }
            }
        }
    }

    public fun handleUIMessage(message: BaseViewModel.UIMessageState) {
        when (message) {
            is BaseViewModel.UIMessageState.Error -> {
                showSnackBar(
                    view,
                    message.error.getMessage(requireContext())
                        ?: getString(R.string.NoInternetConnection), R.color.red
                )
            }
            BaseViewModel.UIMessageState.Default -> {

            }
        }
    }

    public fun showSnackBar(view: View?, msg: String, vararg colorRes: Int) {
        view?.run {
            val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
            val sbView = snackBar.view

            if (colorRes.isNotEmpty())
                sbView.setBackgroundColor(colorRes[0])
            else
                sbView.setBackgroundColor(requireContext().resources.getColor(R.color.red))

            snackBar.show()
        }
    }

    fun showLoadingDialog(loadingFrame: View?, progressBar: ProgressBar?) {
        progressBar?.isVisible = true
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        loadingFrame?.setOnTouchListener { _, _ -> true }
    }

    fun dismissProgressDialog(loadingFrame: View?, progressBar: ProgressBar?) {
        progressBar?.isVisible = false
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        loadingFrame?.setOnTouchListener { _, _ -> false }
    }


}
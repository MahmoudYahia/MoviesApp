package com.myahia.moviesapp.features.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myahia.moviesapp.R
import com.myahia.moviesapp.features.movies.domain.ImageHelper
import com.myahia.moviesapp.features.movies.ui.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details_layout.*

@AndroidEntryPoint
class MovieDetailsFragment : BottomSheetDialogFragment() {
    private val moviesViewModel: MoviesViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        isCancelable = false
        moviesViewModel.selectedMovie?.apply {
            Glide.with(requireContext())
                .load(ImageHelper.getImageFullUrl(ImageHelper.ImageQuality.High, posterPath))
                .into(detail_image_movie)
            detail_movie_title_tv.text = title
            if (releaseDate?.split("-").isNullOrEmpty().not())
                detail_movie_year_tv.text = releaseDate?.split("-")?.get(0) ?: releaseDate.orEmpty()
            detail_movie_desc_tv.text = overview
            detail_movie_rate_tv.text = voteAverage?.toString().orEmpty()
        }
        close_btn.setOnClickListener {
            dismiss()
        }
    }

}
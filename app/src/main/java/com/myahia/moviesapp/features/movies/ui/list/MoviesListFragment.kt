package com.myahia.moviesapp.features.movies.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myahia.moviesapp.R
import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.data.model.MoviesListResponseBody
import com.myahia.moviesapp.features.movies.ui.MoviesViewModel
import com.myahia.moviesapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies_list_layout.*

@AndroidEntryPoint
class MoviesListFragment : BaseFragment(), MoviesListAdapter.MovieClickListener {

    private val moviesViewModel: MoviesViewModel by viewModels({ requireActivity() })
    lateinit var movieAdapter: MoviesListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBaseViewModel(moviesViewModel)
        return inflater.inflate(R.layout.fragment_movies_list_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservables()
        moviesViewModel.getMovieList()
    }


    private fun initViews() {

        movieAdapter = MoviesListAdapter(this)
        with(movies_list_rv) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }
    }

    private fun initObservables() {
        lifecycleScope.launchWhenResumed {
            moviesViewModel.moviesListState.collect {
                when (it) {
                    MoviesViewModel.MoviesListState.Empty -> {
                        dismissProgressDialog(root_frame, loading_progress)
                    }
                    is MoviesViewModel.MoviesListState.Error -> {
                        dismissProgressDialog(root_frame, loading_progress)
                        showSnackBar(
                            view,
                            it.Error.getMessage(requireContext())
                                ?: getString(R.string.SomethingWentWrong)
                        )
                    }
                    MoviesViewModel.MoviesListState.Loading -> {
                        showLoadingDialog(root_frame, loading_progress)
                    }
                    is MoviesViewModel.MoviesListState.Success<*> -> {
                        dismissProgressDialog(root_frame, loading_progress)
                        if (it.data is MoviesListResponseBody) {
                            movieAdapter.submitList(it.data.data)
                        }
                    }
                }
            }
        }
    }

    override fun onItemClicked(movie: MovieItem) {
        moviesViewModel.selectedMovie = movie
        findNavController().navigate(R.id.action_moviesListFragment_to_movieDetailsFragment)
    }
}
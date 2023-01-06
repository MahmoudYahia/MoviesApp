package com.myahia.moviesapp.features.movies.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myahia.moviesapp.R
import com.myahia.moviesapp.features.movies.data.model.MovieItem
import com.myahia.moviesapp.features.movies.domain.ImageHelper
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MoviesListAdapter(private val listener: MovieClickListener?) :
    ListAdapter<MovieItem, MoviesListAdapter.MovieViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.movieTitle?.text = title

            if (releaseDate?.split("-").isNullOrEmpty().not())
                holder.movieYear?.text = releaseDate?.split("-")?.get(0) ?: releaseDate

            Glide.with(holder.itemView.context)
                .load(ImageHelper.getImageFullUrl(ImageHelper.ImageQuality.Low, posterPath))
                .into(holder.movieImg)
        }
        holder.itemView.setOnClickListener {
            listener?.onItemClicked(getItem(holder.adapterPosition))
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImg: ImageView = itemView.movie_item_img
        val movieTitle: TextView? = itemView.movie_item_title_tv
        val movieYear: TextView? = itemView.movie_item_desc_tv
    }

    class MoviesDiffCallback : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(
            oldItem: MovieItem,
            newItem: MovieItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieItem,
            newItem: MovieItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface MovieClickListener {
        fun onItemClicked(movie: MovieItem)
    }

}
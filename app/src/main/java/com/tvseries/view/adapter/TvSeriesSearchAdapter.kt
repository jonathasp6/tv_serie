package com.tvseries.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvseries.R
import com.tvseries.model.TvSeriesSearched

typealias eventClickTvSeriesSearched = (TvSeriesSearched) -> Unit

class TvSeriesSearchedAdapter(private val onClick: eventClickTvSeriesSearched) :
        ListAdapter<TvSeriesSearched, TvSeriesSearchedAdapter.TvSeriesSearchedViewHolder>(TvSeriesSearchedDiffCallback) {

    class TvSeriesSearchedViewHolder(itemView: View, val onClick: (TvSeriesSearched) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        private val tvSeriesNameTV: TextView = itemView.findViewById(com.tvseries.R.id.tv_series_item_tv_name)
        private val tvSeriesIV: ImageView = itemView.findViewById(com.tvseries.R.id.tv_series_iv_item)
        private var tvSeries: TvSeriesSearched? = null

        init {
            itemView.setOnClickListener {
                tvSeries?.let {
                    onClick(it)
                }
            }
        }

        fun bind(tvSeries: TvSeriesSearched) {
            this.tvSeries = tvSeries

            tvSeriesNameTV.text = tvSeries.show.name
            if (tvSeries.show.image != null) {
                Picasso.get()
                    .load(tvSeries.show.image.medium)
                    .into(tvSeriesIV)
            }
            else {
                tvSeriesIV.setImageResource(R.drawable.ic_no_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSeriesSearchedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(com.tvseries.R.layout.tv_series_item, parent, false)
        return TvSeriesSearchedViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: TvSeriesSearchedViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(tvSeries)
    }
}
object TvSeriesSearchedDiffCallback : DiffUtil.ItemCallback<TvSeriesSearched>() {
    override fun areItemsTheSame(oldItem: TvSeriesSearched, newItem: TvSeriesSearched): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TvSeriesSearched, newItem: TvSeriesSearched): Boolean {
        return oldItem.show.id == newItem.show.id
    }
}

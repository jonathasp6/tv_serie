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
import com.tvseries.model.TvSeries
import com.tvseries.R

typealias eventClickTvSeries = (TvSeries) -> Unit

class TvSeriesAdapter(private val onClick: eventClickTvSeries) :
        ListAdapter<TvSeries, TvSeriesAdapter.TvSeriesViewHolder>(TvSeriesDiffCallback) {

    class TvSeriesViewHolder(itemView: View, val onClick: (TvSeries) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        private val tvSeriesNameTV: TextView = itemView.findViewById(R.id.tv_series_item_tv_name)
        private val tvSeriesIV: ImageView = itemView.findViewById(R.id.tv_series_iv_item)
        private var tvSeries: TvSeries? = null

        init {
            itemView.setOnClickListener {
                tvSeries?.let {
                    onClick(it)
                }
            }
        }

        fun bind(tvSeries: TvSeries) {
            this.tvSeries = tvSeries

            tvSeriesNameTV.text = tvSeries.name
            tvSeries.image?.let {
                Picasso.get()
                    .load(it.medium)
                    .into(tvSeriesIV)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSeriesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.tv_series_item, parent, false)
        return TvSeriesViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: TvSeriesViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(tvSeries)
    }
}
object TvSeriesDiffCallback : DiffUtil.ItemCallback<TvSeries>() {
    override fun areItemsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
        return oldItem.id == newItem.id
    }
}

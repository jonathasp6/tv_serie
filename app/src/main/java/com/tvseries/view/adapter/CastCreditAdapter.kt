package com.tvseries.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tvseries.R
import com.tvseries.model.CastCredit

class CastCreditAdapter(private val onClick: (CastCredit) -> Unit) :
        ListAdapter<CastCredit, CastCreditAdapter.CastCreditViewHolder>(CastCreditDiffCallback) {

    class CastCreditViewHolder(itemView: View, val onClick: (CastCredit) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        private val tvSeriesNameTV: TextView = itemView.findViewById(R.id.episode_item_tv_name)
        private val tvSeriesIV: ImageView = itemView.findViewById(R.id.episode_iv_item)
        private var tvSeries: CastCredit? = null

        init {
            itemView.setOnClickListener {
                tvSeries?.let {
                    onClick(it)
                }
            }
        }

        fun bind(tvSeries: CastCredit) {
            this.tvSeries = tvSeries

            tvSeriesNameTV.text = tvSeries._embedded?.show!!.name
            tvSeriesIV.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastCreditViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.episode_item, parent, false)
        return CastCreditViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: CastCreditViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(tvSeries)

    }
}
object CastCreditDiffCallback : DiffUtil.ItemCallback<CastCredit>() {
    override fun areItemsTheSame(oldItem: CastCredit, newItem: CastCredit): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CastCredit, newItem: CastCredit): Boolean {
        return oldItem._embedded?.show!!.id == newItem._embedded?.show!!.id
    }
}

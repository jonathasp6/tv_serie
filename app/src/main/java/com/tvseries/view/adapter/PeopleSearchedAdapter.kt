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
import com.tvseries.model.PeopleSearched

typealias eventClickPeople = (PeopleSearched) -> Unit

class PeopleSearchedAdapter(private val onClick: eventClickPeople) :
        ListAdapter<PeopleSearched, PeopleSearchedAdapter.PeopleSearchedViewHolder>(PeopleSearchedDiffCallback) {

    class PeopleSearchedViewHolder(itemView: View, val onClick: eventClickPeople) :
            RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(com.tvseries.R.id.tv_series_item_tv_name)
        private val image: ImageView = itemView.findViewById(com.tvseries.R.id.tv_series_iv_item)
        private var people: PeopleSearched? = null

        init {
            itemView.setOnClickListener {
                people?.let {
                    onClick(it)
                }
            }
        }

        fun bind(people: PeopleSearched) {
            this.people = people

            name.text = people.person.name

            people.person.image?.let {
                Picasso.get()
                    .load(it.medium)
                    .into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleSearchedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(com.tvseries.R.layout.tv_series_item, parent, false)
        return PeopleSearchedViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PeopleSearchedViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(tvSeries)

    }
}
object PeopleSearchedDiffCallback : DiffUtil.ItemCallback<PeopleSearched>() {
    override fun areItemsTheSame(oldItem: PeopleSearched, newItem: PeopleSearched): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PeopleSearched, newItem: PeopleSearched): Boolean {
        return oldItem.person.id == newItem.person.id
    }
}

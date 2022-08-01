package com.tvseries.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvseries.R
import com.tvseries.model.Episode

typealias eventClickEpisode = (Episode) -> Unit

class EpisodeAdapter(private val onClick: eventClickEpisode) :
        ListAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(EpisodeDiffCallback) {

    class EpisodeViewHolder(itemView: View, val onClick: (Episode) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        private val episodeName: TextView = itemView.findViewById(com.tvseries.R.id.episode_item_tv_name)
        private val layoutItem: LinearLayout = itemView.findViewById(com.tvseries.R.id.episode_iten_ll_item)
        private val episodeImage: ImageView = itemView.findViewById(com.tvseries.R.id.episode_iv_item)


        private var episode: Episode? = null

        init {
            itemView.setOnClickListener {
                episode?.let {
                    onClick(it)
                }
            }
        }

        fun bind(episode: Episode) {
            this.episode = episode
            episodeName.text = episode.name
            if (episode.id == -1) {
                episodeName.setTextColor(ContextCompat.getColor(layoutItem.context, com.tvseries.R.color.white))
                layoutItem.setBackgroundColor(ContextCompat.getColor(layoutItem.context, com.tvseries.R.color.black))
                episodeImage.visibility = View.GONE
            }
            else {
                episodeName.setTextColor(ContextCompat.getColor(layoutItem.context, com.tvseries.R.color.black))
                layoutItem.setBackgroundColor(ContextCompat.getColor(layoutItem.context, com.tvseries.R.color.white))
                if (episode.image != null) {
                    Picasso.get()
                        .load(episode.image.medium)
                        .into(episodeImage)
                    episodeImage.visibility = View.VISIBLE
                }
                else {
                    episodeImage.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(com.tvseries.R.layout.episode_item, parent, false)
        return EpisodeViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)

    }
}
object EpisodeDiffCallback : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.id == newItem.id
    }
}

package com.tvseries.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.squareup.picasso.Picasso
import com.tvseries.R
import com.tvseries.databinding.FragmentEpisodeListBinding
import com.tvseries.model.Episode
import com.tvseries.view.adapter.EpisodeAdapter
import com.tvseries.view.adapter.eventClickEpisode
import com.tvseries.viewmodel.EpisodesViewModelList

class EpisodeListFragment : Fragment() {
    private var idTvSeries = 0
    private var tvSeriesName = ""
    private lateinit var binding:FragmentEpisodeListBinding


    private val modelFragment: EpisodesViewModelList by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idTvSeries = it.getInt("id")
            tvSeriesName = it.getString("name").toString()
        }

        modelFragment.loadEpisode(requireContext(), idTvSeries)
    }

    private val clickItem: eventClickEpisode = { episode ->
        onClickListItem(episode)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeListBinding.inflate(inflater, container, false)

        val episodeAdapter = EpisodeAdapter(clickItem)
        binding.fragmentEpisodeRvListEpisode.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        binding.fragmentEpisodeRvListEpisode.adapter = episodeAdapter
        binding.fragmentEpisodeTvTitle.text = tvSeriesName

        modelFragment.episodes.observe(viewLifecycleOwner) {
            episodeAdapter.submitList(it)
            if (it.isEmpty()) {
                Toast.makeText(
                    context,
                    getText(R.string.fragment_episode_not_found),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.fragmentEpisodeBtClose.setOnClickListener {
            binding.fragmentEpisodeLlDetail.visibility = View.GONE
        }

        return binding.root
    }

    private fun onClickListItem(episode: Episode) {
        if (episode.id != -1) {
            binding.fragmentEpisodeTvTitle.text = episode.name
            binding.fragmentEpisodeTvNumber.text = episode.number.toString()
            binding.fragmentEpisodeTvSeason.text = episode.season.toString()

            if (episode.summary != null) {
                binding.fragmentEpisodeTvSummary.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(episode.summary, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(episode.summary)
                }
            }
            else {
                binding.fragmentEpisodeTvSummary.text = ""
            }

            episode.image?.let { image ->
                Picasso.get()
                    .load(image.medium)
                    .into(binding.fragmentEpisodeIvPoster)
            }

            binding.fragmentEpisodeLlDetail.visibility = View.VISIBLE
        }
    }
}
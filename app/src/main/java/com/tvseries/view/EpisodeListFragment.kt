package com.tvseries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.tvseries.R
import com.tvseries.databinding.FragmentEpisodeListBinding
import com.tvseries.model.Episode
import com.tvseries.view.adapter.EpisodeAdapter
import com.tvseries.view.adapter.eventClickEpisode
import com.tvseries.viewmodel.EpisodeListViewModel

class EpisodeListFragment : Fragment() {
    private var idTvSeries = 0
    private var tvSeriesName = ""
    private lateinit var binding:FragmentEpisodeListBinding


    private val episodeListViewModel: EpisodeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idTvSeries = it.getInt("id")
            tvSeriesName = it.getString("name").toString()
        }

        episodeListViewModel.loadEpisode(requireContext(), idTvSeries)
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

        episodeListViewModel.episodes.observe(viewLifecycleOwner) {
            episodeAdapter.submitList(it)
            if (it.isEmpty()) {
                Toast.makeText(
                    context,
                    getText(R.string.fragment_episode_not_found),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }

    private fun onClickListItem(episode: Episode) {
        if (episode.id != -1) {
            val bundle = Bundle()
            bundle.putString("name", episode.name)
            bundle.putString("season", episode.season.toString())
            bundle.putString("number", episode.number.toString())
            bundle.putString("summary", episode.summary)
            episode.image?.let {
                bundle.putString("image", it.medium)
            }

            view?.findNavController()
                ?.navigate(R.id.action_episodeListFragment_to_episodeFragment, bundle)
        }
    }
}
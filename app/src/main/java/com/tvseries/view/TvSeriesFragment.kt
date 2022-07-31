package com.tvseries.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.tvseries.R
import com.tvseries.databinding.FragmentTvSeriesBinding
import com.tvseries.viewmodel.TvSeriesViewModel

class TvSeriesFragment : Fragment() {
    private var idTvSeries = -1
    private var name = ""
    private val tvSeriesViewModel: TvSeriesViewModel by viewModels()
    private lateinit var binding: FragmentTvSeriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idTvSeries = it.getInt("id")
            tvSeriesViewModel.loadTvSeries(requireContext(), idTvSeries)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentTvSeriesBinding.inflate(inflater, container, false)

        tvSeriesViewModel.tvSeries.observe(viewLifecycleOwner) {
            name = it.name
            binding.fragmentTvSeriesInformationTvTitle.text = name
            val scheduleTxt =
                it.schedule?.time + "\n" + it.schedule?.days.toString().replace("[", "")
                    .replace("]", "")
            binding.fragmentTvSeriesInformationTvSchedules.text = scheduleTxt
            binding.fragmentTvSeriesInformationTvRuntime.text = it.runtime.toString()
            binding.fragmentTvSeriesInformationTvGenrer.text = it.genres.toString().replace("[", "").replace("]", "")

            if (it.summary != null) {
                binding.fragmentTvSeriesInformationTvSummary.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(it.summary, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(it.summary)
                }
            } else {
                binding.fragmentTvSeriesInformationTvSummary.text = ""
            }

            it.image?.let { image ->
                Picasso.get()
                    .load(image.medium)
                    .into(binding.fragmentTvSeriesInformationIvPoster)
            }
        }

        binding.fragmentTvSeriesInformationBtSeeEpisode.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", idTvSeries)
            bundle.putString("name", name)
            view?.findNavController()?.navigate(R.id.action_tvSeriesFragment_to_episodeListFragment, bundle)
        }

        return binding.root
    }
}
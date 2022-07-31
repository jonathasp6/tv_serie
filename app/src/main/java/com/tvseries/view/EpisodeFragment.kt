package com.tvseries.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.tvseries.databinding.FragmentEpisodeBinding

class EpisodeFragment : Fragment() {
    private lateinit var binding: FragmentEpisodeBinding
    private var name = "";
    private var number = ""
    private var season = ""
    private var image = ""
    private var summary = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name","")
            number = it.getString("number","")
            season = it.getString("season","")
            image = it.getString("image","")
            summary = it.getString("summary","")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)

        binding.fragmentEpisodeTvTitle.text = name
        binding.fragmentEpisodeTvNumber.text = number
        binding.fragmentEpisodeTvSeason.text = season

        if (summary != "") {
            binding.fragmentEpisodeTvSummary.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(summary)
            }
        }
        else {
            binding.fragmentEpisodeTvSummary.text = ""
        }

        if (image != "") {
            Picasso.get()
                .load(image)
                .into(binding.fragmentEpisodeIvPoster)
        }

        binding.fragmentEpisodeLlDetail.visibility = View.VISIBLE

        return binding.root
    }
}
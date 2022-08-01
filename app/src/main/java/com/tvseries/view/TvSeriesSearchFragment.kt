package com.tvseries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tvseries.R
import com.tvseries.databinding.FragmentTvSeriesSearchBinding
import com.tvseries.model.TvSeriesSearched
import com.tvseries.view.adapter.TvSeriesSearchedAdapter
import com.tvseries.view.adapter.eventClickTvSeriesSearched
import com.tvseries.viewmodel.TvSeriesSearchViewModel
import kotlinx.coroutines.*

class TvSeriesSearchFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesSearchBinding
    private val tvSeriesSearchViewModel: TvSeriesSearchViewModel by viewModels()
    private var job: Job? = null

    private val clickItem: eventClickTvSeriesSearched = { tvSeriesSearched ->
        onClickListItem(tvSeriesSearched)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvSeriesSearchBinding.inflate(inflater, container, false)

        val tvSeriesAdapter = TvSeriesSearchedAdapter(clickItem)
        binding.fragmentTvSeriesSearchedRvList.layoutManager =
            GridLayoutManager(requireContext(), 3)
        binding.fragmentTvSeriesSearchedRvList.adapter = tvSeriesAdapter

        tvSeriesSearchViewModel.tvSeries.observe(viewLifecycleOwner) {
            it.let {
                tvSeriesAdapter.submitList(it as MutableList<TvSeriesSearched>)
                if (it.isEmpty()) {
                    binding.fragmentTvSeriesTvInstruction.visibility = View.VISIBLE
                    val name = binding.fragmentTvSeriesEtName.text.toString()
                    if (name.isEmpty()) {
                        binding.fragmentTvSeriesTvInstruction.text = getText(R.string.fragment_search_instruction)
                    }
                    else {
                        binding.fragmentTvSeriesTvInstruction.text =
                            getText(R.string.fragment_search_not_found)
                    }
                }
                else {
                    binding.fragmentTvSeriesTvInstruction.visibility = View.INVISIBLE
                }
            }
        }

        configSearchTvSeries()
        return binding.root
    }

    private fun configSearchTvSeries() {
        binding.fragmentTvSeriesEtName.addTextChangedListener {
            searchPost()
        }
    }

    private fun searchPost() {
        job?.cancel()
        job = lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                delay(250)
                val name = binding.fragmentTvSeriesEtName.text.toString()
                tvSeriesSearchViewModel.loadTvSeries(requireContext(), name)
            }
        }
    }



    private fun onClickListItem(tvSeriesSearched: TvSeriesSearched) {
        val bundle = Bundle()
        bundle.putInt("id", tvSeriesSearched.show.id)
        view?.findNavController()?.navigate(R.id.action_navigation_search_to_tvSeriesFragment, bundle)
    }
}

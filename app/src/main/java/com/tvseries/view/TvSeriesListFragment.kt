package com.tvseries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tvseries.model.TvSeries
import com.tvseries.view.adapter.TvSeriesAdapter
import com.tvseries.viewmodel.TvSeriesListViewModel
import com.tvseries.databinding.FragmentTvSerieListBinding
import com.tvseries.view.adapter.eventClickTvSeries


class TvSeriesListFragment : Fragment() {
    private val modelFragmentList: TvSeriesListViewModel by viewModels()
    private lateinit var binding : FragmentTvSerieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            modelFragmentList.loadTvSeries(it)
        }
    }

    private val clickItem: eventClickTvSeries = { tvSeries ->
        onClickListItem(tvSeries)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvSerieListBinding.inflate(inflater, container, false)

        val tvSeriesAdapter = TvSeriesAdapter(clickItem)
        binding.fragmentTvSeriesListRv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.fragmentTvSeriesListRv.adapter = tvSeriesAdapter
        binding.fragmentTvSeriesListRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) ) {
                    context?.let { modelFragmentList.loadNextPageTvSeries(it) }
                }

                if (!recyclerView.canScrollVertically(-1) && newState!=RecyclerView.SCROLL_STATE_DRAGGING ) {
                    context?.let { modelFragmentList.loadPreviousPageTvSeries(it) }
                }
            }
        })

        modelFragmentList.loadingList.observe(viewLifecycleOwner) {
            binding.fragmentTvSeriesLlLoadList.visibility = if (it) View.VISIBLE else View.GONE
        }

        modelFragmentList.tvSeries.observe(viewLifecycleOwner) {
            it.let {
                tvSeriesAdapter.submitList(it as MutableList<TvSeries>)
                binding.fragmentTvSeriesLlLoadList.visibility = View.GONE
            }
        }

        return binding.root
    }

    private fun onClickListItem(tvSeries: TvSeries) {
        Toast.makeText(requireContext(), tvSeries.name, Toast.LENGTH_LONG).show()
    }

}
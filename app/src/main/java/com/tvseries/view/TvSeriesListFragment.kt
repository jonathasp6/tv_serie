package com.tvseries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tvseries.R
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
        modelFragmentList.loadTvSeries(requireContext())
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
        binding.fragmentTvSeriesRvList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.fragmentTvSeriesRvList.adapter = tvSeriesAdapter
        binding.fragmentTvSeriesRvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

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
        val bundle = Bundle()
        bundle.putInt("id", tvSeries.id)
        view?.findNavController()?.navigate(R.id.action_navigation_home_to_tvSeriesFragment, bundle)
    }

}
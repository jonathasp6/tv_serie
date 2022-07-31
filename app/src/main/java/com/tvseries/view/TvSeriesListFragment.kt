package com.tvseries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tvseries.R
import com.tvseries.model.TvSeries
import com.tvseries.view.adapter.TvSeriesAdapter
import com.tvseries.databinding.FragmentTvSerieListBinding
import com.tvseries.model.DataFactory
import com.tvseries.view.adapter.eventClickTvSeries
import com.tvseries.viewmodel.TvSeriesListViewModel
import com.tvseries.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TvSeriesListFragment : Fragment() {
    private lateinit var  modelFragmentList: TvSeriesListViewModel
    private lateinit var binding : FragmentTvSerieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = ViewModelFactory(
            DataFactory.getInstance(requireContext())
        )
        modelFragmentList = ViewModelProvider(this, viewModelFactory)[TvSeriesListViewModel::class.java]
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                DataFactory.getInstance(requireContext()).getListTvSeries(0)
            }
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
        binding.fragmentTvSeriesRvList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.fragmentTvSeriesRvList.adapter = tvSeriesAdapter

        lifecycleScope.launch {
            modelFragmentList.flow.collectLatest { pagingData ->
                tvSeriesAdapter.submitData(pagingData)
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
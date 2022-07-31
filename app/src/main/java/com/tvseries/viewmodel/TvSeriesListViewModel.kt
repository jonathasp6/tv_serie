package com.tvseries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tvseries.model.TvSeries
import com.tvseries.model.IDataFactory
import com.tvseries.model.paging.TvSeriesPagingSource


class TvSeriesListViewModel(private val service: IDataFactory) : ViewModel() {

    val tvSeries: MutableLiveData<List<TvSeries>> by lazy {
        MutableLiveData()
    }

    val flow = Pager(
        PagingConfig(pageSize = 250)
    ) {
        TvSeriesPagingSource(service)
    }.flow
        .cachedIn(viewModelScope)

}
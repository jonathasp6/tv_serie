package com.tvseries.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvseries.model.DataFactory
import com.tvseries.model.TvSeriesSearched

class TvSeriesSearchViewModel : ViewModel () {
    val tvSeries: MutableLiveData<List<TvSeriesSearched>> by lazy {
        MutableLiveData()
    }

    fun loadTvSeries(context: Context, name: String) {
        DataFactory.getInstance(context.applicationContext).getTvSeriesByName(this, name)
    }

    fun postTvSerie(tvSeries: List<TvSeriesSearched>) {
        this.tvSeries.postValue(tvSeries)

    }
}
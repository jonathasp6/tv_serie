package com.tvseries.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvseries.model.DataFactory
import com.tvseries.model.TvSeries

class TvSeriesViewModel : ViewModel () {

    val name: MutableLiveData<String> by lazy {
        MutableLiveData()
    }


    val tvSeries: MutableLiveData<TvSeries> by lazy {
        MutableLiveData()
    }

    fun loadTvSeries(context: Context, id: Int) {
        DataFactory.getInstance(context.applicationContext).getTvShowById(this, id)
    }

    fun postInformation(tvSeries: TvSeries) {
        this.tvSeries.postValue(tvSeries)
    }
}
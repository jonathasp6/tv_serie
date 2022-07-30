package com.tvseries.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvseries.model.TvSeries
import com.tvseries.model.DataFactory

interface GenericModel {
    fun postImage(image : Bitmap)
}

class TvSeriesListViewModel : ViewModel(), GenericModel {
    private var canLoadPage = true

    val tvSeries: MutableLiveData<List<TvSeries>> by lazy {
        MutableLiveData()
    }

    private val page : MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    private val image: MutableLiveData<Bitmap> by lazy {
        MutableLiveData()
    }

    val loadingList : MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    fun loadTvSeries(context: Context) {
        page.postValue(0)
        DataFactory.getInstance(context.applicationContext).getListTvSeries(this, 0)
    }

    fun loadNextPageTvSeries(context: Context) {
        if (canLoadPage) {
            canLoadPage = false
            page.value = page.value!! + 1
            loadingList.postValue(true)
            DataFactory.getInstance(context.applicationContext).getListTvSeries(this, page.value!!)
        }
    }

    fun loadPreviousPageTvSeries(context: Context) {
        if (page.value!! > 0 && canLoadPage) {
            canLoadPage = false
            page.value = page.value!! - 1
            loadingList.postValue(true)
            DataFactory.getInstance(context.applicationContext).getListTvSeries(this, page.value!!)
        }
    }

    fun loadNewTvSeriesList(newList: List<TvSeries>) {
        tvSeries.postValue(newList)
        loadingList.postValue(false)
        canLoadPage = true
    }

    fun errorLoadList() {
        canLoadPage = false
    }

    override fun postImage(image: Bitmap) {
        this.image.postValue(image)
    }
}
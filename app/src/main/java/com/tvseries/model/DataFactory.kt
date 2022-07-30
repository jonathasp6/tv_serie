package com.tvseries.model

import android.content.Context
import com.tvseries.model.services.RESTService
import com.tvseries.viewmodel.TvSeriesViewModel
import com.tvseries.viewmodel.*

interface IDataFactory {
    fun getListTvSeries(page: Int, handler: (List<TvSeries>?) -> Unit)

    fun getTvSeriesByName(name : String, handler: (List<TvSeriesSearched>) -> Unit)

    fun getTvShowById(id : Int, handler: (TvSeries) -> Unit)
}

class DataFactory {

    companion object {
        private var instance : IDataFactory? = null

        fun getInstance(context: Context) : IDataFactory {
            if (instance == null) {
                instance = RESTService(context)
            }
            return instance as IDataFactory
        }
    }
}
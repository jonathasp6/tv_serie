package com.tvseries.model

import android.content.Context
import com.tvseries.model.services.RESTService
import com.tvseries.viewmodel.*

interface IDataFactory {
    fun getListTvSeries(model: TvSeriesListViewModel, page: Int)

    fun getTvSeriesByName(model: TvSeriesSearchViewModel, name : String)
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
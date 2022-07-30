package com.tvseries.model

import android.content.Context
import com.tvseries.model.services.RESTService
import com.tvseries.viewmodel.*

interface IDataFactory {
    fun getListTvShows(model: TvSeriesListViewModel, page: Int)
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
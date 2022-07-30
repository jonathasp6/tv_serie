package com.tvseries.model.services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tvseries.model.TvSeries
import com.tvseries.model.IDataFactory
import com.tvseries.viewmodel.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONException

class RESTService(context: Context) : IDataFactory {

    private val json = Json { ignoreUnknownKeys = true }

    private var queue : RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(context)
    }

    override fun getListTvShows(model: TvSeriesListViewModel, page : Int) {
        val url = "https://api.tvmaze.com/shows?page=$page"


        val stringRequest = StringRequest(
            Request.Method.GET, url,
                { response ->
                    try {
                        val newList = json.decodeFromString<List<TvSeries>>(response.toString())
                        model.loadNewTvSeriesList(newList)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: SerializationException) {
                        e.printStackTrace()
                    }
                },
                {
                    Log.d("TvSeriesListFragment", "That didn't work! $it")
                    model.errorLoadList()
                }
        )

        queue?.add(stringRequest)
    }


}
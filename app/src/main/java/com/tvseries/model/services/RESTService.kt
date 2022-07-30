package com.tvseries.model.services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tvseries.model.Episode
import com.tvseries.model.TvSeries
import com.tvseries.model.IDataFactory
import com.tvseries.model.TvSeriesSearched
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

    override fun getListTvSeries(page : Int, handler: (List<TvSeries>?) -> Unit) {
        val url = "https://api.tvmaze.com/shows?page=$page"


        val stringRequest = StringRequest(
            Request.Method.GET, url,
                { response ->
                    try {
                        val newList = json.decodeFromString<List<TvSeries>>(response.toString())
                        handler(newList)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: SerializationException) {
                        e.printStackTrace()
                    }
                },
                {
                    Log.d("TvSeriesListFragment", "That didn't work! $it")
                    handler(null)
                }
        )

        queue?.add(stringRequest)
    }

    override fun getTvSeriesByName(name : String, handler: (List<TvSeriesSearched>) -> Unit) {
        val url = "https://api.tvmaze.com/search/shows?q=$name"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val tvSeriesSearched = json.decodeFromString<List<TvSeriesSearched>>(response.toString())
                   handler(tvSeriesSearched)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: SerializationException) {
                    e.printStackTrace()
                }
            },
            {
                Log.d("TvSeriesListFragment", "That didn't work! $it")
            }
        )

        queue?.add(stringRequest)
    }

    override fun getTvShowById(id : Int, handler: (TvSeries) -> Unit) {
        val url = "https://api.tvmaze.com/shows/${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val tvSeries = json.decodeFromString<TvSeries>(response.toString())
                    handler(tvSeries)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: SerializationException) {
                    e.printStackTrace()
                }
            },
            {
                Log.d("TvSeriesListFragment", "That didn't work! $it")
            }
        )

        queue?.add(stringRequest)
    }

    override fun getEpisodeTvSeriesById(id: Int, handler: (ArrayList<Episode>) -> Unit) {
        val url = "https://api.tvmaze.com/shows/${id}/episodes"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val episodes = json.decodeFromString<ArrayList<Episode>>(response.toString())
                    handler(episodes)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: SerializationException) {
                    e.printStackTrace()
                }
            },
            {
                Log.d("TvSeriesListFragment", "That didn't work! $it")
            }
        )

        queue?.add(stringRequest)
    }

}
package com.tvseries.model.services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tvseries.model.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONException

class RESTService(var context: Context) : IDataFactory {

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

    override suspend fun getListTvSeries(page: Int): List<TvSeries> {
        val service = TvSeriesServiceGenerator.create()
        return service.getTvSeriesByPage(page)
    }


    override  fun getTvSeriesByName(name : String, handler: (List<TvSeriesSearched>) -> Unit) {
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

    override fun getPeopleByName(name: String, handler: (List<PeopleSearched>) -> Unit) {
        val url = "https://api.tvmaze.com/search/people?q=$name"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val peopleSearched = json.decodeFromString<List<PeopleSearched>>(response.toString())
                    handler(peopleSearched)
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

    override fun getPersonById(id : Int, handler: (Person) -> Unit) {
        val url = "https://api.tvmaze.com/people/${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val person = json.decodeFromString<Person>(response.toString())
                    handler(person)
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

    override fun getPersonCast(id : Int, handler: (List<CastCredit>) -> Unit) {
        val url = "https://api.tvmaze.com/people/${id}/castcredits?embed=show"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val castCredits = json.decodeFromString<List<CastCredit>>(response.toString())
                    handler(castCredits)
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
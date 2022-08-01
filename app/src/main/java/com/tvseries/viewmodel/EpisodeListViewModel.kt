package com.tvseries.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvseries.model.DataFactory
import com.tvseries.model.Episode

class EpisodeListViewModel : ViewModel () {
    val episodes: MutableLiveData<List<Episode>> by lazy {
        MutableLiveData()
    }

    fun loadEpisode(context: Context, id: Int) {
        DataFactory.getInstance(context.applicationContext).getEpisodeTvSeriesById(id) {
            postEpisodes(it)
        }
    }

    private fun postEpisodes(episodes: ArrayList<Episode>) {
        splitEpisodeBySeason(episodes)
        this.episodes.postValue(episodes)
    }

    private fun splitEpisodeBySeason(episodes: ArrayList<Episode>) {
        var season = 0
        if (episodes.size > 0) {
            for (i in 0..episodes.size) {
                val tempSeason = episodes[i].season
                if (tempSeason != season) {
                    episodes.add(i, Episode(-1, "Season $tempSeason", null, null, null, null))
                    season++
                }
            }
        }
    }
}
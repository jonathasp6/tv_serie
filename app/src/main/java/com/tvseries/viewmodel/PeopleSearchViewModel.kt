package com.tvseries.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvseries.model.PeopleSearched
import com.tvseries.model.DataFactory

class PeopleSearchViewModel : ViewModel () {
    val peopleList: MutableLiveData<List<PeopleSearched>> by lazy {
        MutableLiveData()
    }

    fun loadPerson(context: Context, name: String) {
        DataFactory.getInstance(context.applicationContext).getPeopleByName(name) {
            postPeople(it)
        }
    }

    private fun postPeople(people: List<PeopleSearched>) {
        this.peopleList.postValue(people)
    }
}
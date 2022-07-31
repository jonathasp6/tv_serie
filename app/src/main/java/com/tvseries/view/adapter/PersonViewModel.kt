package com.tvseries.view.adapter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvseries.model.CastCredit
import com.tvseries.model.Person
import com.tvseries.model.DataFactory

class PersonViewModel : ViewModel () {
    val person: MutableLiveData<Person> by lazy {
        MutableLiveData()
    }

    val castCredit: MutableLiveData<List<CastCredit>> by lazy {
        MutableLiveData()
    }

    fun loadPersonInformation(context: Context, id: Int) {
        DataFactory.getInstance(context.applicationContext).getPersonById(id) {
            postPersonInformation(it)
        }
        DataFactory.getInstance(context.applicationContext).getPersonCast(id) {
            postCastCreditInformation(it)
        }

    }

    private fun postPersonInformation(person: Person) {
        this.person.postValue(person)
    }

    private fun postCastCreditInformation(castCredit: List<CastCredit>) {
        this.castCredit.postValue(castCredit)
    }
}
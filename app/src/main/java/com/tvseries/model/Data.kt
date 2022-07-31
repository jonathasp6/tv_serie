package com.tvseries.model

import kotlinx.serialization.Serializable

@Serializable
data class TvSeries(
    val id: Int,
    val name: String,
    val url: String?,
    val summary: String?,
    val image : Image?,
    val genres: List<String>,
    val premiered: String?,
    val schedule: Schedule?,
    val runtime: Int?
)

@Serializable
data class Schedule(
    val time: String,
    val days: List<String>,
)

@Serializable
data class Image(
    val medium: String,
    val original: String,
)

@Serializable
data class TvSeriesSearched(
    val score: Float,
    val show: TvSeries
)

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    val season: Int?,
    val number: Int?,
    val summary: String?,
    val image: Image?
)

@Serializable
data class Person(
    val id: Int,
    val name: String,
    val url: String?,
    val image : Image?,
    val gender: String?,
)

@Serializable
data class PeopleSearched(
    val score: Float,
    val person: Person
)

@Serializable
data class CastCredit(
    val _embedded: CreditEmbedded? = null
)

@Serializable
data class CreditEmbedded(
    val show: TvSeries
)

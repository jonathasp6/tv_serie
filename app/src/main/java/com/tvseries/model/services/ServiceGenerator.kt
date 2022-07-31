package com.tvseries.model.services

import com.tvseries.model.TvSeries
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TvSeriesServiceGenerator {

    @GET("shows")
    suspend fun getTvSeriesByPage(
        @Query("page") page: Int
    ): List<TvSeries>

    companion object {
        private const val baseUrl = "https://api.tvmaze.com/"

        fun create(): TvSeriesServiceGenerator {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TvSeriesServiceGenerator::class.java)
        }
    }
}
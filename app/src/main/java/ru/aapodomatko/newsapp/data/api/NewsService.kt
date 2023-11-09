package ru.aapodomatko.newsapp.data.api

import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.GET
import ru.aapodomatko.newsapp.models.NewsResponse
import ru.aapodomatko.newsapp.utils.Constants.Companion.API_KEY


interface NewsService {

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getHeadLines(
        @Query("country") countryCode: String = "ru",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

}
package com.example.shido.apolloexample

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Shido on 26/11/2017.
 */
interface FeedApi {


    @GET("/graphql")
    fun feedDetails(@Query("query") query: String): Call<Response<FeedQuery.Data>>
}
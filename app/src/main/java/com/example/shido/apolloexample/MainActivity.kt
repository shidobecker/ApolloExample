package com.example.shido.apolloexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import type.FeedType

class MainActivity : AppCompatActivity() {


    val endpoint = "https://api.github.com/users?since=135"

    val BASE_URL = "https://api.githunt.com/graphql/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loggin = HttpLoggingInterceptor()
        loggin.level = HttpLoggingInterceptor.Level.BODY
        val okhttpClient = OkHttpClient.Builder()
                .addInterceptor(loggin).build()

        val apolloClient = ApolloClient.builder().serverUrl(BASE_URL)
                .okHttpClient(okhttpClient).build()

        val feedQuery = FeedQuery.builder().limit(10).type(FeedType.HOT)
                .build()

        val apolloCall: ApolloCall<FeedQuery.Data> = apolloClient.query(feedQuery)

        apolloCall.enqueue(object: ApolloCall.Callback<FeedQuery.Data>(){

            override fun onFailure(e: ApolloException) {
                Log.e("ERROR", "Error: ${e.message}")
            }

            override fun onResponse(response: Response<FeedQuery.Data>) {
                val data = response.data()

                val feeds = data?.feedEntries()
                feeds?.forEach {
                    Log.e("FEED", "FFED ${it.id()}  ${it.postedBy()}   ${it.repository()}")
                }
            }

        })


    }
}

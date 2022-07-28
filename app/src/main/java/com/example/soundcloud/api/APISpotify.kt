package com.example.soundcloud.api

import com.example.soundcloud.`interface`.ApiSpotifyInterface
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor

import okhttp3.OkHttpClient

import com.example.soundcloud.*
import okhttp3.Request


class APISpotify {

    companion object {
        const val BASE_URL = "https://api.spotify.com"
        fun config(token: String): ApiSpotifyInterface {

            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .addHeader("Content-Type", "application/json")
                    .method(chain.request().method(),chain.request().body())
                    .build()
                chain.proceed(newRequest)
            }.build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiSpotifyInterface::class.java)
        }
    }


}
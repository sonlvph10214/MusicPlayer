package com.example.soundcloud.api

import com.example.soundcloud.`interface`.ApiSpotifyInterface
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor

import okhttp3.OkHttpClient

import com.example.soundcloud.*
import com.example.soundcloud.`interface`.ApiZingInterface
import okhttp3.Request


class APIZing {


    companion object {
        val BASE_URL = "http://ac.mp3.zing.vn"
        fun config(): ApiZingInterface {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

            return retrofit.create(ApiZingInterface::class.java)
        }
    }



}
package com.example.soundcloud.`interface`

import com.example.soundcloud.model.*
import com.example.soundcloud.model.track.SearchTracks
import com.example.soundcloud.model.zing.Complete
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*


interface ApiZingInterface {


    @GET("/complete")
    fun getMusic(
        @Query("type") type: String,
        @Query("num") num: String,
        @Query("query") query: String
    ): Observable<Complete>


}
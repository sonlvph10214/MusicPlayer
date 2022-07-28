package com.example.soundcloud.`interface`

import com.example.soundcloud.model.*
import com.example.soundcloud.model.track.SearchTracks
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*


interface ApiSpotifyInterface {

    @GET("/v1/search")
    fun getSearchPlayList(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<SearchSound>
    @GET("/v1/search")
    fun getTrack(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<SearchTracks>

    @GET("v1/playlists/{id}/tracks")
    fun getTracks(
        @Path("id") id: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<TracksId>


    @GET("/v1/search")
    fun getArtist(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<SearchArtist>

    @GET("/v1/search")
    fun getAlbum(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
    ): Observable<SearchAlbum>

    @GET("/v1/artists/{id}/top-tracks")
    fun getArtistTracks(
        @Path("id") id: String,
        @Query("market") market: String
    ): Observable<ArtistTracks>

    @GET("/v1/me")
    fun getUser(): Observable<User>

    //
    @PUT("/v1/me/player/play")
    fun putPlayer(
        @Query("device_id") deviceId: String,
        @Query("context_uri") uri: String,
        @Query("position_ms") pos: Int,
    ): Observable<Any>


}
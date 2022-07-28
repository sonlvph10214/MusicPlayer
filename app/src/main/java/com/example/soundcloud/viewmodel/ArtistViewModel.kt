package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APISpotify
import com.example.soundcloud.model.ArtistTracks
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.SearchArtist
import com.example.soundcloud.model.Track
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ArtistViewModel : ViewModel() {
    var listItemArtist: MutableLiveData<List<ItemArtist>>? = null
    var listArtistTracksLiveData: MutableLiveData<List<Track>>? = null
    var isLoading: MutableLiveData<Boolean>? = null
    var isLoadingArtist: MutableLiveData<Boolean>? = null
    private var errorLiveData: MutableLiveData<String>? = null
    private var disposable: CompositeDisposable? = null
    val itemArtistLiveData= MutableLiveData<ItemArtist>()


    init {
        listItemArtist = MutableLiveData()
        listArtistTracksLiveData = MutableLiveData()
        isLoading = MutableLiveData()
        isLoadingArtist = MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun getArtist(token: String) {
        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token).getArtist( "t", "artist", 10,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchArtist>() {
                    override fun onNext(t: SearchArtist?) {
//                        listSongLiveData?.value = t.dataList?.get(0)?.songList!!
                        isLoading?.value = false
                        listItemArtist?.value = t?.artist?.items
                    }

                    override fun onError(e: Throwable?) {
                        isLoading?.value = false
                        Log.e("Error", e?.message.toString())
                    }

                    override fun onComplete() {

                    }
                })
        )

    }
    fun getTracksArtist(token: String,id:String) {
        isLoadingArtist?.value = true
        disposable?.add(
            APISpotify.config(token).getArtistTracks(/*"Bearer $token",*/ id, "VN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArtistTracks>() {
                    override fun onNext(t: ArtistTracks?) {
                        isLoadingArtist?.value = false
                        listArtistTracksLiveData?.value = t?.tracks
                    }


                    override fun onError(e: Throwable?) {
                        isLoadingArtist?.value = false
                        Log.e("Error", e?.message.toString())
                    }

                    override fun onComplete() {

                    }


                })
        )

    }

    fun select(item: ItemArtist) {
        itemArtistLiveData.value = item
    }


}
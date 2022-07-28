package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APISpotify
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.Track
import com.example.soundcloud.model.track.SearchTracks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class TracksViewModel : ViewModel() {
    var listTracksLD: MutableLiveData<List<Track>>? = null
    var isLoading: MutableLiveData<Boolean>? = null
    private var errorLiveData: MutableLiveData<String>? = null
    private var disposable: CompositeDisposable? = null
    val itemArtistLiveData= MutableLiveData<ItemArtist>()


    init {
        listTracksLD = MutableLiveData()
        isLoading = MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }


    fun getTrack(token: String, query:String) {
        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token).getTrack(query,"track",20,0 )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchTracks>() {
                    override fun onNext(t: SearchTracks?) {
                        isLoading?.value = false
                        listTracksLD?.value = t?.tracks?.itemTrack
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

    fun select(item: ItemArtist) {
        itemArtistLiveData.value = item
    }


}
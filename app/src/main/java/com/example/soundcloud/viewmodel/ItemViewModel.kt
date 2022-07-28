package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APISpotify
import com.example.soundcloud.model.ItemTracks
import com.example.soundcloud.model.TracksId
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ItemViewModel : ViewModel() {
    var listTracksLiveData: MutableLiveData<List<ItemTracks>>? = null
    var isLoading: MutableLiveData<Boolean>? = null
    private var errorLiveData: MutableLiveData<String>? = null
    private var disposable: CompositeDisposable? = null
    val tracksLiveData= MutableLiveData<ItemTracks>()

    init {
        listTracksLiveData = MutableLiveData()
        isLoading = MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun getTracks(token: String,id:String) {
        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token).getTracks(/*"Bearer $token",*/ id, 0,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TracksId>() {
                    override fun onNext(t: TracksId?) {
                        isLoading?.value = false
                        listTracksLiveData?.value = t?.itemList
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

    fun select(tracks: ItemTracks) {
        tracksLiveData.value = tracks
    }

}
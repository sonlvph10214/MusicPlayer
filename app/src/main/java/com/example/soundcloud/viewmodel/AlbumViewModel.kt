package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APISpotify
import com.example.soundcloud.model.ItemAlbum
import com.example.soundcloud.model.SearchAlbum
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class AlbumViewModel : ViewModel() {
    var listAlbumLiveData: MutableLiveData<List<ItemAlbum>>? = null
    var isLoading: MutableLiveData<Boolean>? = null
    private var errorLiveData: MutableLiveData<String>? = null
    private var disposable: CompositeDisposable? = null




    init {
        listAlbumLiveData = MutableLiveData()
        isLoading = MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun getAlbum(token: String) {
        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token)
                .getAlbum(/*"Bearer $token",*/ "tùng", "album", 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchAlbum>() {
                    override fun onNext(t: SearchAlbum?) {
                        isLoading?.value = false
                        listAlbumLiveData?.value = t?.album?.itemList!!
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


}
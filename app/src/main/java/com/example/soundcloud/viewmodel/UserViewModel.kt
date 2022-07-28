package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APISpotify
import com.example.soundcloud.model.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class UserViewModel : ViewModel() {
    var userLiveData: MutableLiveData<User>? = null
    var isLoading: MutableLiveData<Boolean>? = null
    private var errorLiveData: MutableLiveData<String>? = null
    private var disposable: CompositeDisposable? = null

    init {
        userLiveData = MutableLiveData()
        isLoading = MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun getUser(token: String) {
        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token).getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<User>() {
                    override fun onNext(t: User?) {
                        userLiveData?.value = t!!
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
//    fun select(tracks: ItemTracks) {
//        tracksLiveData.value = tracks
//    }

}
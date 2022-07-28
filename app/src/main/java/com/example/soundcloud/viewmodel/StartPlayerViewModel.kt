package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APISpotify
import com.example.soundcloud.model.PlayPlayer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class StartPlayerViewModel : ViewModel() {

    companion object {
        const val TAG = "StartVM"
    }


    var playPlayerLiveData: MutableLiveData<Any>? = null
    var isLoading: MutableLiveData<Boolean>? = null
    private var errorLiveData: MutableLiveData<String>? = null
    private var disposable: CompositeDisposable? = null


    init {
        playPlayerLiveData = MutableLiveData()
        isLoading = MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun putPlayer(token: String, deviceId: String, uri: String) {
//        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token)
                .putPlayer(deviceId, uri, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Any>() {
                    override fun onNext(t: Any) {
//                        isLoading?.value = false
                        playPlayerLiveData?.value = t
                    }

                    override fun onError(e: Throwable?) {
//                        isLoading?.value = false
                        Log.e(TAG, e?.message.toString())
                    }

                    override fun onComplete() {

                    }
                })
        )

    }


}
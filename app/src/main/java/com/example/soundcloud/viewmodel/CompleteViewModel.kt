package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APIZing
import com.example.soundcloud.model.zing.ArtistZing
import com.example.soundcloud.model.zing.Complete
import com.example.soundcloud.model.zing.Song
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class CompleteViewModel : ViewModel() {
    var listSongLiveData: MutableLiveData<List<Song>>? = null
    var listArtistLiveData: MutableLiveData<List<ArtistZing>>? = null
    var errorLiveData: MutableLiveData<String>? = null
    var disposable: CompositeDisposable? = null

    init {
        listSongLiveData = MutableLiveData()
        listArtistLiveData= MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun getSong() {
        disposable?.add(
            APIZing.config().getMusic("artist,song,key,code", "20", "Anh")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Complete>() {
                    override fun onNext(t: Complete?) {
                        when (t?.result) {
                            true -> {
                                listSongLiveData?.value = t.dataList?.get(0)?.songList!!
                                listArtistLiveData?.value = t.dataList?.get(1)?.artistList!!
                            }
                            false -> {

                            }
                        }
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("Error", e?.message.toString())
                    }

                    override fun onComplete() {
                    }


                })
        )

    }

}

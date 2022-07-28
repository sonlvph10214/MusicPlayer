package com.example.soundcloud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.api.APISpotify
import com.example.soundcloud.model.Item
import com.example.soundcloud.model.SearchSound
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchSoundViewModel : ViewModel() {
    var listItemLiveData: MutableLiveData<List<Item>>? = null
    var listItemChillLiveData: MutableLiveData<List<Item>>? = null
    var listItemArtist: MutableLiveData<List<Item>>? = null
    var isLoading: MutableLiveData<Boolean>? = null
    private var errorLiveData: MutableLiveData<String>? = null
    private var disposable: CompositeDisposable? = null
    val itemLiveData= MutableLiveData<Item>()

    init {
        listItemLiveData = MutableLiveData()
        listItemChillLiveData = MutableLiveData()
        listItemArtist = MutableLiveData()
        isLoading = MutableLiveData()
        errorLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun getSpotify(token: String) {
        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token).getSearchPlayList(/*"Bearer $token",*/ "tung", "playlist", 20,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchSound>() {
                    override fun onNext(t: SearchSound?) {
//                        listSongLiveData?.value = t.dataList?.get(0)?.songList!!
                        isLoading?.value = false
                        listItemLiveData?.value = t?.playList?.itemList!!
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
    fun getSpotifyChill(token: String) {
        isLoading?.value = true
        disposable?.add(
            APISpotify.config(token).getSearchPlayList(/*"Bearer $token",*/ "name", "playlist", 20,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchSound>() {
                    override fun onNext(t: SearchSound?) {
//                        listSongLiveData?.value = t.dataList?.get(0)?.songList!!
                        listItemChillLiveData?.value = t?.playList?.itemList!!
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



    fun select(item: Item) {
        itemLiveData.value = item
    }

    fun play(uri: String) {

    }

}
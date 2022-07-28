package com.example.soundcloud.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundcloud.model.person.Music

class MusicViewModel : ViewModel() {
    var musicLD: MutableLiveData<Music>? = null
    var musicListLd: MutableLiveData<ArrayList<Music>>? = null



    init {
        musicLD = MutableLiveData()
        musicListLd = MutableLiveData()

    }

    fun getData(music: Music) {
        musicLD?.value = music
    }
    fun getMusicList(musicList: ArrayList<Music>) {
        musicListLd?.value = musicList
    }
}
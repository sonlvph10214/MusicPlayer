package com.example.soundcloud.static

import com.example.soundcloud.view.playsong.PlayerMusicFragment
import com.example.soundcloud.view.playsong.PlayerMusicFragment.Companion.musicListSer
import com.example.soundcloud.view.playsong.PlayerMusicFragment.Companion.position
import kotlin.system.exitProcess

object ModelUtils {

     fun setSongPos(increment:Boolean) {
         if (!PlayerMusicFragment.repeat) {
             if (increment) {
                 if (musicListSer.size - 1 == position) {// lấy tk cuối = position
                     position = 0
                 } else ++position
             } else {
                 if (0 == position) {
                     position = musicListSer.size - 1
                 } else --position

             }
         }
     }
    fun extitMusic(){
        if(PlayerMusicFragment.playMusicLocalService != null){
            PlayerMusicFragment.playMusicLocalService?.stopForeground(true)
            PlayerMusicFragment.playMusicLocalService?.mediaPlayer?.release()
            PlayerMusicFragment.playMusicLocalService = null}
        exitProcess(1)
    }
}
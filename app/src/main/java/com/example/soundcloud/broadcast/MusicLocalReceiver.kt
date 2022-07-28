package com.example.soundcloud.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.soundcloud.MyApplication
import com.example.soundcloud.R
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.view.playsong.PlayerMusicFragment
import com.example.soundcloud.view.playsong.PlayerMusicFragment.Companion.binding
import com.example.soundcloud.view.playsong.PlayerMusicFragment.Companion.musicListSer
import com.example.soundcloud.static.CommonUtils
import com.example.soundcloud.static.ModelUtils
import com.example.soundcloud.static.ModelUtils.setSongPos

class MusicLocalReceiver : BroadcastReceiver() {




    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            MyApplication.PREVIOUS -> prevNextMusic(false,context!!)
            MyApplication.PLAY -> if (PlayerMusicFragment.isPlaying) pauseMusic() else playMusic()
            MyApplication.NEXT ->  prevNextMusic(true,context!!)
            MyApplication.EXIT -> {
                ModelUtils.extitMusic()

            }
        }
    }

    private fun playMusic() {
        PlayerMusicFragment.isPlaying = true
        PlayerMusicFragment.playMusicLocalService?.mediaPlayer!!.start()
        PlayerMusicFragment.playMusicLocalService?.showNotification(R.drawable.ic_pause_black,1F)
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
        BottomNavigationActivity.binding.btnPlayPause.setImageResource(R.drawable.ic_pause)

    }

    private fun pauseMusic() {
        PlayerMusicFragment.isPlaying = false
        PlayerMusicFragment.playMusicLocalService?.mediaPlayer!!.pause()
        PlayerMusicFragment.playMusicLocalService?.showNotification(R.drawable.ic_play_service,0F)
        binding.btnPlayPause.setImageResource(R.drawable.ic_play_white)
       BottomNavigationActivity.binding.btnPlayPause.setImageResource(R.drawable.ic_play_white)

    }

    private fun prevNextMusic(increment: Boolean, context: Context) {
        setSongPos(increment)
        PlayerMusicFragment.playMusicLocalService?.creatMediaPlayer()

        Glide.with(context)
            .load(musicListSer[PlayerMusicFragment.position].imgUri)
            .into(binding.imgMusic)
        binding.tvTitleScreen.text =
            musicListSer[PlayerMusicFragment.position].title
        binding.tvNameMusic.text =
            musicListSer[PlayerMusicFragment.position].title
        binding.tvSeekBarEnd.text = CommonUtils.formatDuration(
            musicListSer[PlayerMusicFragment.position].duration
        )
        Glide.with(context)
            .load(musicListSer[PlayerMusicFragment.position].imgUri)
            .into(BottomNavigationActivity.binding.imgMusicLocal)
        BottomNavigationActivity.binding.tvNameSongLocal.text = musicListSer[PlayerMusicFragment.position].title
        BottomNavigationActivity.binding.tvArtistLocal.text = musicListSer[PlayerMusicFragment.position].artist

        playMusic()
    }


}
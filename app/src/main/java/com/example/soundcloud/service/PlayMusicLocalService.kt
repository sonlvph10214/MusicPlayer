package com.example.soundcloud.service


import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.os.*
import android.support.v4.media.MediaMetadataCompat
import androidx.core.app.NotificationCompat
import com.example.soundcloud.MyApplication
import com.example.soundcloud.R
import com.example.soundcloud.model.person.Music
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.soundcloud.broadcast.MusicLocalReceiver
import com.example.soundcloud.view.playsong.PlayerMusicFragment
import com.example.soundcloud.static.CommonUtils
import com.example.soundcloud.static.CommonUtils.getImgArt
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity


class PlayMusicLocalService : Service() {
    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null
    var mediaSesson: MediaSessionCompat? = null
    private lateinit var runnable: Runnable
    var musicList = ArrayList<Music>()
    var pos: Int = 0

    override fun onBind(p0: Intent?): IBinder? {
        mediaSesson = MediaSessionCompat(baseContext, "My Music")
        return myBinder
    }

    inner class MyBinder : Binder() {
        fun currentService(): PlayMusicLocalService {
            return this@PlayMusicLocalService
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val bundle: Bundle? = intent?.extras
//        if (bundle != null) {
//            pos = bundle.getInt("position")
//            musicList = bundle.get("music_list") as ArrayList<Music>
//        }

        return START_NOT_STICKY
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(playPause: Int,playBackSpeed:Float) {
        var intent = Intent(this,BottomNavigationActivity::class.java)
        var contentIntent = PendingIntent.getActivity(this,0,intent,0)
        val imgArt = getImgArt(PlayerMusicFragment.musicListSer[PlayerMusicFragment.position].path)
        val image = if (imgArt != null) {
            BitmapFactory.decodeByteArray(imgArt, 0, imgArt.size)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.ic_sound_cloud)
        }

        val prevIntent =
            Intent(this, MusicLocalReceiver::class.java).setAction(MyApplication.PREVIOUS)
        val prevPendingIntent =
            PendingIntent.getBroadcast(this, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val playIntent = Intent(this, MusicLocalReceiver::class.java).setAction(MyApplication.PLAY)
        val playPendingIntent =
            PendingIntent.getBroadcast(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val nextIntent = Intent(this, MusicLocalReceiver::class.java).setAction(MyApplication.NEXT)
        val nextPendingIntent =
            PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val closeIntent = Intent(this, MusicLocalReceiver::class.java).setAction(MyApplication.EXIT)
        val closePendingIntent =
            PendingIntent.getBroadcast(this, 0, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(baseContext, MyApplication.CHANNEL_ID)
            .setContentTitle(PlayerMusicFragment.musicListSer[PlayerMusicFragment.position].title)
            .setContentText(PlayerMusicFragment.musicListSer[PlayerMusicFragment.position].artist)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_sound_cloud)
            .setLargeIcon(image)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSesson?.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(R.drawable.ic_skip_previous, "Previous", prevPendingIntent)
            .addAction(playPause, "Play", playPendingIntent)
            .addAction(R.drawable.ic_skip_next, "Next", nextPendingIntent)
            .addAction(R.drawable.ic_close_24, "Close", closePendingIntent)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            mediaSesson?.setMetadata(
                MediaMetadataCompat.Builder()
                .putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer!!.duration.toLong())
                .build())
            mediaSesson?.setPlaybackState(
                PlaybackStateCompat.Builder()
                .setState( PlaybackStateCompat.STATE_PLAYING ,mediaPlayer!!.currentPosition.toLong(),playBackSpeed)
                .setActions(PlaybackStateCompat.ACTION_SEEK_TO)
                .build())
        }

        startForeground(13, notification)
    }

    fun creatMediaPlayer() {
        try {
            if (PlayerMusicFragment.playMusicLocalService?.mediaPlayer == null) PlayerMusicFragment.playMusicLocalService?.mediaPlayer =
                MediaPlayer()
            PlayerMusicFragment.playMusicLocalService?.mediaPlayer?.reset()
            PlayerMusicFragment.playMusicLocalService?.mediaPlayer?.setDataSource(
                PlayerMusicFragment.musicListSer[PlayerMusicFragment.position].path
            )
            PlayerMusicFragment.playMusicLocalService?.mediaPlayer?.prepare()
            PlayerMusicFragment.binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
            PlayerMusicFragment.playMusicLocalService?.showNotification(R.drawable.ic_pause,0F)
            PlayerMusicFragment.binding.tvSeekBarStart.text = CommonUtils.formatDuration(
                mediaPlayer?.currentPosition!!.toLong()
            )
            PlayerMusicFragment.binding.tvSeekBarEnd.text = CommonUtils.formatDuration(
                mediaPlayer?.duration!!.toLong()
            )
            PlayerMusicFragment.binding.seekBarPA.progress = 0
            PlayerMusicFragment.binding.seekBarPA.max = mediaPlayer?.duration!!
            PlayerMusicFragment.nowPlayingId =musicList[PlayerMusicFragment.position].id

        } catch (e: Exception) {
            return
        }
    }

    fun seekBarSetup() {
        runnable = Runnable {
            PlayerMusicFragment.binding.tvSeekBarStart.text = CommonUtils.formatDuration(
                mediaPlayer?.currentPosition!!.toLong()
            )
            PlayerMusicFragment.binding.seekBarPA.progress = mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)

    }
}
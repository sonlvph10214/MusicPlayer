package com.example.soundcloud.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.soundcloud.MyApplication.Companion.CHANNEL_ID
import com.example.soundcloud.R
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.broadcast.MusicBroadCast
import com.example.soundcloud.model.Song


class PlayMusicService : Service() {


    var mediaPlayer: MediaPlayer? = null
    var isPlaying: Boolean = false
    var song: Song? = null

    companion object {
        const val ACTION_PAUSE = 1
        const val ACTION_RESUME = 2
        const val ACTION_CLOSE = 3
        const val ACTION_START = 4
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("Service", "onCreat")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle: Bundle? = intent?.extras
        if (bundle != null) {
            val songBundle: Song? = bundle.get("song") as Song?

            if (songBundle != null) {
                song = songBundle
                startMusic(songBundle)
                sendNotificationMedia(songBundle,1f)
            }

        }

        val actionMusic = intent?.getIntExtra("action_service", 0)
        if (actionMusic != null) {
            handleActionMusic(actionMusic)
        }

        return START_NOT_STICKY
    }

    private fun startMusic(song: Song) {
        if (!isPlaying) {
            mediaPlayer = MediaPlayer.create(applicationContext, song.raw)
            mediaPlayer?.start()
            isPlaying = true
            sendActionToActivity(ACTION_START)
        }
    }


    private fun handleActionMusic(action: Int) {
        when (action) {
            ACTION_PAUSE -> pauseMusic()
            ACTION_RESUME -> resumeMusic()
            ACTION_CLOSE -> {
                stopSelf()
                sendActionToActivity(ACTION_CLOSE)
            }
        }
    }

    private fun resumeMusic() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer?.start()
            isPlaying = true
            sendNotificationMedia(song!!,1f)
            sendActionToActivity(ACTION_RESUME)
        }
    }

    private fun pauseMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
            sendNotificationMedia(song!!,0f)
            sendActionToActivity(ACTION_PAUSE)
        }
    }


    private fun sendNotificationMedia(song: Song,playBackSpeed:Float) {


        val notificationIntent = Intent(this, BottomNavigationActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val bitmap = BitmapFactory.decodeResource(resources, song.img)
        val mediaSessionCompat  = MediaSessionCompat(this,"tag")
        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sound_cloud)
            .setSubText("SoundCloud")
            .setContentTitle(song.name)
            .setContentText(song.singer)
            .setLargeIcon(bitmap)
            .setContentIntent(contentIntent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(3)
                .setMediaSession(mediaSessionCompat.sessionToken))
        if (isPlaying){
            notificationBuilder
                .addAction(R.drawable.ic_skip_previous,"Previous",null)
                .addAction(R.drawable.ic_pause_black,"Pause",getPendingIntent(this, ACTION_PAUSE))
                .addAction(R.drawable.ic_skip_next,"Next",null)
                .addAction(R.drawable.ic_close_24,"Clear",getPendingIntent(this, ACTION_CLOSE))
        }else{
            notificationBuilder
                .addAction(R.drawable.ic_skip_previous,"Previous",null)
                .addAction(R.drawable.ic_play_service,"Pause",getPendingIntent(this, ACTION_RESUME))
                .addAction(R.drawable.ic_skip_next,"Next",null)
                .addAction(R.drawable.ic_close_24,"Clear",getPendingIntent(this, ACTION_CLOSE))

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            mediaSessionCompat.setMetadata(MediaMetadataCompat.Builder()
                .putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer!!.duration.toLong())
                .build())
            mediaSessionCompat.setPlaybackState(PlaybackStateCompat.Builder()
                .setState( PlaybackStateCompat.STATE_PLAYING ,mediaPlayer!!.currentPosition.toLong(),playBackSpeed)
                .setActions(PlaybackStateCompat.ACTION_SEEK_TO)
                .build())
        }


        val notification :Notification = notificationBuilder.build()
        startForeground(1, notification)

    }



    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getPendingIntent(context: Context, action: Int): PendingIntent? {
        val intent = Intent(this, MusicBroadCast::class.java)
        intent.putExtra("action", action)

        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Service", "onDestroy")
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null

        }
    }

    private fun sendActionToActivity(action: Int) {
        val intent = Intent("send_data_to_activity")
        val bundle = Bundle()
        bundle.putSerializable("obj_song", song)
        bundle.putBoolean("status_player", isPlaying)
        bundle.putInt("action_music", action)
        intent.putExtras(bundle)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

    }

}
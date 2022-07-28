package com.example.soundcloud

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class  MyApplication : Application() {

    companion object{
        val CHANNEL_ID = "channel_service"
        const val PLAY = "play"
        const val NEXT = "next"
        const val PREVIOUS = "previous"
        const val EXIT = "exit"
    }


    override fun onCreate() {
        super.onCreate()
//        SharePerferencesManager.initShared(this)
        creatChannelNotification()
    }

    private fun creatChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,getString(R.string.channel_id),NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null,null)
            val manager :NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
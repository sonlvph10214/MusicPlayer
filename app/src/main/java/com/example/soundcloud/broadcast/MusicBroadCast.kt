package com.example.soundcloud.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.soundcloud.service.PlayMusicService

class MusicBroadCast: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var actionMusic:Int = intent.getIntExtra("action",0)

        var intentService = Intent(context,PlayMusicService::class.java)
        intentService.putExtra("action_service",actionMusic)
        context.startService(intentService)
    }

}
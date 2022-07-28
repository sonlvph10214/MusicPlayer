package com.example.soundcloud.static

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.Settings.Secure.ANDROID_ID
import android.provider.Settings.Secure.getString
import java.util.concurrent.TimeUnit

object CommonUtils {

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return getString(
            context.contentResolver, ANDROID_ID
        )
    }
    fun formatDuration(duration: Long):String{
        val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) -
                minutes* TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }
    fun getImgArt(path: String): ByteArray? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        return retriever.embeddedPicture
    }
}
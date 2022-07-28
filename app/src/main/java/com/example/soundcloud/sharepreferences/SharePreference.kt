package com.example.soundcloud.sharepreferences

import android.content.Context
import android.content.SharedPreferences

object SharePreference {

    var PRIVATE_MODE = Context.MODE_PRIVATE

    const val TOKEN = "token"
    const val TIME = "timeGetToken"
    private val PREF_NAME = "soundcloud"
    private  var preferences: SharedPreferences? = null




    fun saveToken(tokenValue: String,timeGetToken:Long, context: Context) {
         preferences =
            context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        preferences?.edit()?.putString(TOKEN, tokenValue)?.apply()
        preferences?.edit()?.putLong(TIME, timeGetToken)?.apply()
    }

    fun getToken(context: Context): String {
         preferences =
            context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        return preferences?.getString(TOKEN, "").toString()
    }
    fun getTime(context: Context): Long? {
         preferences =
            context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        return preferences?.getLong(TIME, 0L)
    }



}
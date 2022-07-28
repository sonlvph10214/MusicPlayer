package com.example.soundcloud.view.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.soundcloud.R
import com.example.soundcloud.view.account.SignInFragment.Companion.CLIENT_ID
import com.example.soundcloud.view.account.SignInFragment.Companion.REDIRECT_URI
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.databinding.ActivityAccountBinding
import com.example.soundcloud.sharepreferences.SharePreference

import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import android.os.SystemClock
import com.example.soundcloud.sharepreferences.SharePreference.getTime
import com.example.soundcloud.sharepreferences.SharePreference.getToken


class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    private var token = ""
    private var time = 0L
    private var TIME_MINUTES = 60L

    companion object {
        private const val REQUEST_CODE_S = 1339
        private const val SCOPES =
            "user-read-recently-played,user-library-modify,user-read-email,user-read-private,user-modify-playback-state"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceSignIn()
        token=getToken(this)

        time = getTime(this)!!

        if (token != null){
            startActivity(Intent(this,BottomNavigationActivity::class.java))
        }


    }

    private fun replaceSignIn() {
        val signInFragment = SignInFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fameAccount, signInFragment)
            .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_S -> {
                val auth = AuthorizationClient.getResponse(resultCode, data)
                when (auth.type) {
                    AuthorizationResponse.Type.TOKEN -> {
                        token = auth.accessToken
                        val currentTime = SystemClock.elapsedRealtime()
                        SharePreference.saveToken(token, currentTime, this)
                        val intent = Intent(this, BottomNavigationActivity::class.java)
                        startActivity(intent)
                    }
                    AuthorizationResponse.Type.ERROR -> {
                        Log.e("AuthorizationResponse", auth.error)
                    }
                }
            }
        }

    }

    fun requestLoginSpotify() {
        val builder = AuthorizationRequest.Builder(
            CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        )

        builder.setScopes(arrayOf(SCOPES))
        val request = builder.build()

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE_S, request)
    }


}

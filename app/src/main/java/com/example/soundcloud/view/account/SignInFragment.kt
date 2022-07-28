package com.example.soundcloud.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.soundcloud.databinding.FragmentSignInBinding
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote


class SignInFragment : Fragment() {

    companion object {
        const val CLIENT_ID = "05596e3f30614df1a41e85db29939623"
        const val REDIRECT_URI = "http://localhost:8888/callback"


    }
    private val TAG = "SignInActivity"
    private lateinit var binding: FragmentSignInBinding

    private var spotifyRemote: SpotifyAppRemote? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.relativeSpotify.setOnClickListener {
            signInSpotify()
        }
    }


    private fun signInSpotify() {
        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()
        SpotifyAppRemote.connect(
            requireContext(),
            connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote?) {
                    spotifyRemote = spotifyAppRemote
                    Log.d(TAG, "onConnected: ${spotifyRemote?.isConnected}")
                    (activity as AccountActivity).requestLoginSpotify()
                }

                override fun onFailure(error: Throwable?) {
                    Log.d(TAG, "onFailure: ${error?.message.toString()}")
                }

            })
    }


}

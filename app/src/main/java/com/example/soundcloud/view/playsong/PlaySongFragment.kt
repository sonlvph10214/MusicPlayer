package com.example.soundcloud.view.playsong

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.databinding.FragmentPlaySongBinding
import com.example.soundcloud.model.Song
import com.example.soundcloud.service.PlayMusicService
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.static.CommonUtils
import com.example.soundcloud.viewmodel.SearchSoundViewModel
import com.example.soundcloud.viewmodel.StartPlayerViewModel


class PlaySongFragment : Fragment() {

    private lateinit var binding: FragmentPlaySongBinding
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    var isPlaying: Boolean = false
    private val soundVM: SearchSoundViewModel by activityViewModels()
    private val playVM: StartPlayerViewModel by activityViewModels()
    private var song: Song? = null
    private var action: Int = 0
    private var imgItem: String? = null


    fun newInstance(song: Song, isPlaying: Boolean, action: Int): PlaySongFragment {
        val args = Bundle()
        args.putSerializable("obj_song", song)
        args.putBoolean("status_player", isPlaying)
        args.putInt("action_music", action)
        val fragment = PlaySongFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaySongBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataBottomActivity()
//        takeDataPLayList()
//        setPlay()
        setListener()

    }


    private fun setListener() {
        binding.imgPlaySongBack.setOnClickListener {
            (activity as BottomNavigationActivity).setShow()
            (activity as BottomNavigationActivity).setShowPlaySong()
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun takeDataPLayList() {
        soundVM.itemLiveData.observe(this, { item ->
            binding.tvNamePlaySong.text = item.name
            binding.tvArtistPlaySong.text = item.description
            imgItem = item.images[0].url
//                Glide.with(requireContext())
//                    .load(item.images[0].url)
//                    .into(binding.imgPlaySong)
            val token = SharePreference.getToken(requireContext())
            val deviceId = CommonUtils.getDeviceId(requireContext())
            playVM.putPlayer(token, deviceId, item.uri)
        })
    }


    private fun handleMusic(action: Int) {
        when (action) {
            PlayMusicService.ACTION_START -> {
                setStatusPlayorPause()
                setListenerLayoutPLaySong()
            }
            PlayMusicService.ACTION_PAUSE -> {
                setStatusPlayorPause()
            }
            PlayMusicService.ACTION_RESUME -> {
                setStatusPlayorPause()
            }

        }
    }

    private fun setStatusPlayorPause() {
        if (isPlaying) {
            soundVM.itemLiveData.observe(this, { item ->
                binding.tvNamePlaySong.text = item.name
                binding.tvArtistPlaySong.text = item.description
                imgItem = item.images[0].url
                Glide.with(requireContext())
                    .load(item.images[0].url)
                    .into(binding.imgPlaySong)
            })
            binding.imgPlaySong.setImageResource(R.drawable.ic_pause_btn)
            binding.btnNextSong.visibility = View.INVISIBLE
        } else {
            binding.imgPlaySong.setImageResource(R.drawable.ic_play_button)
            binding.btnNextSong.visibility = View.VISIBLE
        }

    }

    private fun setListenerLayoutPLaySong() {
        binding.imgPlaySong.setOnClickListener {
            if (isPlaying) {
                sendActionToService(PlayMusicService.ACTION_PAUSE)

            } else {
                sendActionToService(PlayMusicService.ACTION_RESUME)
            }
        }


    }

    private fun sendActionToService(action: Int) {
        var intent = Intent(requireContext(), PlayMusicService::class.java)
        intent.putExtra("action_service", action)
        activity?.startService(intent)
    }

    private fun getDataBottomActivity() {
        if (arguments != null) {
            song = arguments?.getSerializable("obj_song") as Song?
            isPlaying = arguments?.getBoolean("status_player") == true
            action = arguments?.getInt("action_music")!!

            handleMusic(action)
        }
    }


}
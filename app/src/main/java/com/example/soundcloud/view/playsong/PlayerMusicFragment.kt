package com.example.soundcloud.view.playsong

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.databinding.FragmentPlayerMusicBinding
import com.example.soundcloud.model.person.Music
import com.example.soundcloud.service.PlayMusicLocalService
import com.example.soundcloud.static.CommonUtils
import com.example.soundcloud.static.CommonUtils.formatDuration
import com.example.soundcloud.static.ModelUtils.setSongPos
import com.example.soundcloud.view.stream.DialogTimerFragment
import com.example.soundcloud.view.stream.MyMusicFragment
import com.example.soundcloud.view.stream.PlayListDetailFragment
import com.example.soundcloud.viewmodel.MusicViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


class PlayerMusicFragment : Fragment(), ServiceConnection {

    private val musicViewModel: MusicViewModel by activityViewModels()
    var music: Music? = null
    var musicList = ArrayList<Music>()
    var fifteenMinutes: Boolean = false
    var thirtyMinutes: Boolean = false
    var fortyMinutes: Boolean = false
    var FIFTEENMIN: Long = 1
    var THIRTYMIN: Long = 30
    var FORTYMIN: Long = 40
    var classBundle: String = ""


    companion object {
        lateinit var binding: FragmentPlayerMusicBinding
        var isPlaying: Boolean = false
        var playMusicLocalService: PlayMusicLocalService? = null
        var musicListSer = ArrayList<Music>()
        var position: Int = 0
        var repeat: Boolean = false
        var nowPlayingId: String = ""

    }

    fun newInstance(music: Music = Music(), position: Int, classSend: String): PlayerMusicFragment {
        val args = Bundle()
        args.putParcelable("music", music)
        args.putInt("pos", position)
        args.putString("classSend", classSend)
        val fragment = PlayerMusicFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerMusicBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicListSer = musicList
        takeData()
        setListener()
        sendDatatoActivity()
    }

    private fun sendDatatoActivity() {
        if (music != null) {
            musicViewModel.getData(music!!)
        }

    }

    private fun startService() {
        val intent = Intent(requireContext(), PlayMusicLocalService::class.java)
        activity?.bindService(intent, this, BIND_AUTO_CREATE)
        activity?.startService(intent)
    }

    private fun setListener() {
        binding.imgBack.setOnClickListener {
            (activity as BottomNavigationActivity).setShowPlayMusicLocal()
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.btnPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseMusic()
            } else {
                playMusic()
            }
        }
        binding.btnPrevious.setOnClickListener {
            nextOrPreviousMusic(false)
        }
        binding.btnNext.setOnClickListener {
            nextOrPreviousMusic(true)
        }
        binding.seekBarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) playMusicLocalService?.mediaPlayer?.seekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.imgRepeat.setOnClickListener {
            if (!repeat) {
                repeat = true
                binding.imgRepeat.isSelected = true
            } else {
                repeat = false
                binding.imgRepeat.isSelected = false
            }
        }
        binding.imgTimer.setOnClickListener {
            var dialogTimerFragment = DialogTimerFragment()
            dialogTimerFragment.show(requireActivity().supportFragmentManager, "")
            dialogTimerFragment.onClickTimer = { hourOfDay, minute ->
                var thread = Thread {
                    try {
                        var stopThread = true
                        while (stopThread) {
                            var calendar = Calendar.getInstance()
                            val currentHour: Int = calendar.get(Calendar.HOUR)
                            val currentMinute: Int = calendar.get(Calendar.MINUTE)
                            if (currentHour == hourOfDay && currentMinute == minute) {
                                stopThread = false
//                                activity?.moveTaskToBack(true)
                                activity?.finishAffinity()
                                playMusicLocalService?.mediaPlayer?.release()
                                playMusicLocalService?.stopSelf()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                thread.start()
                dialogTimerFragment.dismiss()
                binding.imgTimer.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.purple_500
                    )
                )
            }
        }

    }

    private fun takeData() {
        if (arguments != null) {
            music = arguments?.getParcelable("music")
            position = arguments?.getInt("pos")!!
            classBundle = arguments?.getString("classSend").toString()
            when (classBundle) {
                "PlayListDetailAdapter" -> {
                    initServiceAndPlaylist(
                        MyMusicFragment.musicPlayList.ref[PlayListDetailFragment.pos].playlist!!,
                        shuffle = false
                    )
                }
                "MusicAdapter" -> {
                    initServiceAndPlaylist(musicList, shuffle = false)
                }
                "NowPlaying" -> {
                    setLayout()
                    binding.tvSeekBarStart.text =
                        formatDuration(playMusicLocalService?.mediaPlayer!!.currentPosition.toLong())
                    binding.tvSeekBarEnd.text =
                        formatDuration(playMusicLocalService?.mediaPlayer!!.duration.toLong())
                    binding.seekBarPA.progress =
                        playMusicLocalService?.mediaPlayer!!.currentPosition
                    binding.seekBarPA.max = playMusicLocalService?.mediaPlayer!!.duration
                    if (isPlaying) binding.btnPlayPause.setImageResource(R.drawable.ic_pause_white_60dp)
                    else binding.btnPlayPause.setImageResource(R.drawable.ic_play_white_60dp)
                }
                "PlayListDetail" -> {
                    initServiceAndPlaylist(
                        MyMusicFragment.musicPlayList.ref[PlayListDetailFragment.pos].playlist!!,
                        shuffle = true
                    )
                }
            }
        }
    }

    private fun initServiceAndPlaylist(
        playlist: ArrayList<Music>,
        shuffle: Boolean,
        playNext: Boolean = false
    ) {
        startService()
        musicListSer = ArrayList()
        musicListSer.addAll(playlist)
        if (shuffle) musicListSer.shuffle()
        setLayout()
    }


    private fun setLayout() {
        Glide.with(requireContext())
            .load(musicListSer[position].imgUri)
            .into(binding.imgMusic)
        binding.tvTitleScreen.text = musicListSer[position].title
        binding.tvNameMusic.text = musicListSer[position].title
        binding.tvSeekBarEnd.text = formatDuration(musicListSer[position].duration)
        if (repeat) binding.imgRepeat.isSelected = true

    }

    private fun creatMediaPlayer() {
        try {
            if (playMusicLocalService?.mediaPlayer == null) playMusicLocalService?.mediaPlayer =
                MediaPlayer()
            playMusicLocalService?.mediaPlayer?.reset()
            playMusicLocalService?.mediaPlayer?.setDataSource(musicListSer[position].path)
            playMusicLocalService?.mediaPlayer?.prepare()
            playMusicLocalService?.mediaPlayer?.start()
            isPlaying = true
            binding.btnPlayPause.setImageResource(R.drawable.ic_pause_white_60dp)
            playMusicLocalService?.showNotification(R.drawable.ic_pause, 1F)
            binding.tvSeekBarStart.text =
                CommonUtils.formatDuration(playMusicLocalService?.mediaPlayer?.currentPosition!!.toLong())
            binding.tvSeekBarEnd.text =
                CommonUtils.formatDuration(playMusicLocalService?.mediaPlayer?.duration!!.toLong())
            binding.seekBarPA.progress = 0
            binding.seekBarPA.max = playMusicLocalService?.mediaPlayer?.duration!!
            playMusicLocalService?.mediaPlayer?.setOnCompletionListener {
                setSongPos(true)
                creatMediaPlayer()
                setLayout()
            }


        } catch (e: Exception) {
            return
        }

    }

    private fun playMusic() {
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause_white_60dp)
        playMusicLocalService?.showNotification(R.drawable.ic_pause, 1F)
        isPlaying = true
        playMusicLocalService?.mediaPlayer?.start()
    }

    private fun pauseMusic() {
        binding.btnPlayPause.setImageResource(R.drawable.ic_play_white_60dp)
        playMusicLocalService?.showNotification(R.drawable.ic_play_white, 0F)
        isPlaying = false
        playMusicLocalService?.mediaPlayer?.pause()
    }

    private fun nextOrPreviousMusic(increment: Boolean) {
        if (increment) {
            setSongPos(true)
            setLayout()
            creatMediaPlayer()
        } else {
            setSongPos(false)
            setLayout()
            creatMediaPlayer()
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as PlayMusicLocalService.MyBinder
        playMusicLocalService = binder.currentService()
        creatMediaPlayer()
        playMusicLocalService?.seekBarSetup()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        playMusicLocalService = null
    }

}
package com.example.soundcloud.view.bottomnavigation

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.adapter.BottomNavigationViewPagerAdapter
import com.example.soundcloud.databinding.ActivityBottomNavigationBinding
import com.example.soundcloud.model.Song
import com.example.soundcloud.service.PlayMusicService
import com.example.soundcloud.view.home.HomeFragment
import com.example.soundcloud.view.playsong.PlaySongFragment
import com.example.soundcloud.view.playsong.PlayerMusicFragment
import com.example.soundcloud.viewmodel.MusicViewModel
import com.example.soundcloud.viewmodel.SearchSoundViewModel

class BottomNavigationActivity : AppCompatActivity() {

    var name: String? = null
    var uri: Uri? = null
    private var time = 0L
    private var song: Song? = null
    private var action: Int = 0
    private var isPlaying: Boolean = false
    private val searchSoundViewModel: SearchSoundViewModel by viewModels()
    private val musicViewModel: MusicViewModel by viewModels()
    private var currentFragment: Fragment?=null

    fun getCurrentFragment() : Fragment?{
        return currentFragment
    }



    companion object{
         lateinit var binding: ActivityBottomNavigationBinding

    }
    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val bundle: Bundle = p1?.extras ?: return
            song = bundle.getSerializable("obj_song") as Song?
            isPlaying = bundle.getBoolean("status_player")
            action = bundle.getInt("action_music")

            handleMusic(action)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver,
            IntentFilter("send_data_to_activity")
        )

        binding.imgHome.isSelected = true
        binding.tvHome.visibility = View.VISIBLE
        val viewPager = BottomNavigationViewPagerAdapter(this)
        binding.viewPagerBottom.adapter = viewPager
        binding.viewPagerBottom.isUserInputEnabled = false


        setListener()


    }

    private fun setListener() {
        //home
        binding.linearHome.setOnClickListener {
            binding.imgHome.isSelected = true
            binding.tvHome.visibility = View.VISIBLE
            binding.viewPagerBottom.currentItem = 0

            //
            binding.imgStream.isSelected = false
            binding.tvStream.visibility = View.INVISIBLE
            //
            binding.imgSearch.isSelected = false
            binding.tvSearch.visibility = View.INVISIBLE
            //
            binding.imgLibrary.isSelected = false
            binding.tvLibrary.visibility = View.INVISIBLE
        }
        binding.linearStream.setOnClickListener {
            binding.imgStream.isSelected = true
            binding.tvStream.visibility = View.VISIBLE
            binding.viewPagerBottom.currentItem = 1
            //
            binding.imgHome.isSelected = false
            binding.tvHome.visibility = View.INVISIBLE
            //
            binding.imgSearch.isSelected = false
            binding.tvSearch.visibility = View.INVISIBLE
            //
            binding.imgLibrary.isSelected = false
            binding.tvLibrary.visibility = View.INVISIBLE
        }
        binding.linearSearch.setOnClickListener {
            binding.imgSearch.isSelected = true
            binding.tvSearch.visibility = View.VISIBLE
            binding.viewPagerBottom.currentItem = 2
            //
            binding.imgHome.isSelected = false
            binding.tvHome.visibility = View.INVISIBLE
            //
            binding.imgStream.isSelected = false
            binding.tvStream.visibility = View.INVISIBLE
            //
            binding.imgLibrary.isSelected = false
            binding.tvLibrary.visibility = View.INVISIBLE
        }
        binding.linearLibrary.setOnClickListener {

            binding.imgLibrary.isSelected = true
            binding.tvLibrary.visibility = View.VISIBLE
            binding.viewPagerBottom.currentItem = 3
            //
            binding.imgHome.isSelected = false
            binding.tvHome.visibility = View.INVISIBLE
            //
            binding.imgStream.isSelected = false
            binding.tvStream.visibility = View.INVISIBLE
            //
            binding.imgSearch.isSelected = false
            binding.tvSearch.visibility = View.INVISIBLE
        }

        binding.linearPlaySong.setOnClickListener {
            val playSongFragment = PlaySongFragment().newInstance(song!!, isPlaying, action)
            supportFragmentManager.beginTransaction()
                .add(R.id.fameHome, playSongFragment).addToBackStack(null).commit()
            setHide()
            setHidePlaySong()
        }
//        binding.linearPlayLocal.setOnClickListener {
//            val playerMusicFragment = PlayerMusicFragment().newInstance(position = PlayerMusicFragment.position,
//                classSend = "NowPlaying"
//            )
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fameHome, playerMusicFragment).addToBackStack(null).commit()
//            setHidePlaySong()
//        }
       binding.btnPlayPause.setOnClickListener {
            if (PlayerMusicFragment.isPlaying) pauseMusic() else playMusic()
        }

    }

    fun setHide() {
        binding.linearBottomNavi.visibility = View.GONE
    }

    fun setShow() {
        binding.linearBottomNavi.visibility = View.VISIBLE
    }

    fun setShowPlaySong() {
        binding.linearPlaySong.visibility = View.VISIBLE
        takeData()
    }
  fun setShowPlayMusicLocal() {
        binding.linearPlayLocal.visibility = View.VISIBLE
        takeDataLocal()
    }

    fun setHidePlaySong() {
        binding.linearPlaySong.visibility = View.GONE
        binding.linearPlayLocal.visibility = View.GONE

    }


    private fun takeData() {
        searchSoundViewModel.itemLiveData.observe(this, { item ->
            binding.tvNameSong.text = item.name
            binding.tvArtist.text = item.description
        })
    }
    private fun takeDataLocal() {
        musicViewModel.musicLD?.observe(this, { item ->
            Glide.with(this)
                .load(PlayerMusicFragment.musicListSer[PlayerMusicFragment.position].imgUri)
                .into(binding.imgMusicLocal)
            binding.tvNameSongLocal.text = PlayerMusicFragment.musicListSer[PlayerMusicFragment.position].title
            binding.tvArtistLocal.text = PlayerMusicFragment.musicListSer[PlayerMusicFragment.position].artist
        })
        binding.tvNameSongLocal.isSelected = true
        binding.tvArtistLocal.isSelected = true
        if (PlayerMusicFragment.isPlaying) binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
        else binding.btnPlayPause.setImageResource(R.drawable.ic_play_white)
    }
    private fun playMusic(){
        PlayerMusicFragment.playMusicLocalService?.mediaPlayer?.start()
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
        PlayerMusicFragment.playMusicLocalService?.showNotification(R.drawable.ic_pause,1F)
        PlayerMusicFragment.isPlaying = true
    }
    private fun pauseMusic(){
        PlayerMusicFragment.playMusicLocalService?.mediaPlayer?.pause()
        binding.btnPlayPause.setImageResource(R.drawable.ic_play_white)
        PlayerMusicFragment.playMusicLocalService?.showNotification(R.drawable.ic_play_white,0F)
        PlayerMusicFragment.isPlaying = false
    }


    fun playSong() {
        val song = Song(
            "There's No One At All",
            R.raw.there_are_no_one_at_all,
            R.drawable.img_song,
            "Sơn Tùng M-TP",
            172
        )
        val intent = Intent(this, PlayMusicService::class.java)
        val bundle = Bundle()
        bundle.putSerializable("song", song)
        intent.putExtras(bundle)
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    private fun handleMusic(action: Int) {
        when (action) {
            PlayMusicService.ACTION_START -> {
                setShowPlaySong()
                setStatusPlayorPause()
                setListenerLayoutPLaySong()
            }
            PlayMusicService.ACTION_PAUSE -> {
                setStatusPlayorPause()
            }
            PlayMusicService.ACTION_RESUME -> {
                setStatusPlayorPause()
            }
            PlayMusicService.ACTION_CLOSE -> {
                setHidePlaySong()
            }

        }
    }

    private fun setStatusPlayorPause() {
        if (isPlaying) {
            binding.icPause.setImageResource(R.drawable.ic_pause)
        } else {
            binding.icPause.setImageResource(R.drawable.ic_play_white)

        }

    }

    private fun setListenerLayoutPLaySong() {
        binding.icPause.setOnClickListener {
            if (isPlaying) {
                sendActionToService(PlayMusicService.ACTION_PAUSE)
            } else {
                sendActionToService(PlayMusicService.ACTION_RESUME)
            }
        }

    }

    private fun sendActionToService(action: Int) {
        var intent = Intent(this, PlayMusicService::class.java)
        intent.putExtra("action_service", action)
        startService(intent)
    }




}
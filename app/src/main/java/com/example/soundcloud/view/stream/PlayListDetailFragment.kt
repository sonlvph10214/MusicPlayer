package com.example.soundcloud.view.stream

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.adapter.MusicAdapter
import com.example.soundcloud.adapter.PlayListPersonAdapter
import com.example.soundcloud.databinding.FragmentMyMusicBinding
import com.example.soundcloud.databinding.FragmentPlayListDetailBinding
import com.example.soundcloud.model.Item
import com.example.soundcloud.model.person.Music
import com.example.soundcloud.view.detailplaylist.DetailPlaylistFragment
import com.example.soundcloud.view.playsong.PlayerMusicFragment
import com.example.soundcloud.viewmodel.MusicViewModel
import com.google.gson.GsonBuilder

class PlayListDetailFragment : Fragment() {

    lateinit var binding: FragmentPlayListDetailBinding
    var musicAdapter: MusicAdapter? = null
    var musicList= ArrayList<Music>()
    private val musicViewModel: MusicViewModel by activityViewModels()

    var currentPlayListPos: Int = 0
    var selectionMusicFragment = SelectionMusicFragment()

    companion object {
        var pos: Int = 0
    }

    fun newInstance(musicList: ArrayList<Music> =ArrayList(), pos: Int): PlayListDetailFragment {
        val args = Bundle()
        args.putParcelableArrayList("musicList",musicList)
        args.putInt("pos", pos)
        val fragment = PlayListDetailFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListDetailBinding.inflate(inflater, container, false)
        getData()
        setLisener()
        setAdapter()
        setLayout()
        return binding.root
    }

    private fun setLisener() {
        binding.imgBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.btnPlayMusic.setOnClickListener {
            val playerMusicFragment = PlayerMusicFragment().newInstance( position = pos, classSend = "PlayListDetail")
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.side_in_right,
                    R.anim.side_in_left,
                    R.anim.side_in_right,
                    R.anim.side_in_left
                )
                ?.add(android.R.id.content, playerMusicFragment)
                ?.addToBackStack(null)?.commit()

        }
        binding.linearAddMusic.setOnClickListener {
            musicViewModel.musicListLd?.observe(viewLifecycleOwner, { item ->
             selectionMusicFragment = SelectionMusicFragment().newInstance(item)
            })
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.side_in_right,
                    0
                )
                ?.replace(android.R.id.content, selectionMusicFragment)
                ?.addToBackStack(null)?.commit()

        }
    }

    private fun setAdapter() {
        binding.rcvPlayListDetail.layoutManager = LinearLayoutManager(requireContext())
        musicAdapter = MusicAdapter(
            requireContext(),
            MyMusicFragment.musicPlayList.ref[pos].playlist,
            playlistDetails = true
        )
        binding.rcvPlayListDetail.adapter = musicAdapter
    }

    private fun setLayout() {
        binding.tvNamePlayList.text = MyMusicFragment.musicPlayList.ref[pos].name
        val editor = activity?.getSharedPreferences("FAVOURITES", MODE_PRIVATE)?.edit()
        val jsonStringPlaylist = GsonBuilder().create().toJson(MyMusicFragment.musicPlayList)
        editor?.putString("MusicPlaylist", jsonStringPlaylist)
        editor?.apply()
    }

    private fun getData() {
        if (arguments != null) {
            pos = arguments?.getInt("pos")!!
//            musicList = arguments?.getParcelableArrayList("musicList")!!
        }
    }

    override fun onResume() {
        super.onResume()
        musicAdapter?.notifyDataSetChanged()

    }

}
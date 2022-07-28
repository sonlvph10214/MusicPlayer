package com.example.soundcloud.view.stream

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.soundcloud.R
import com.example.soundcloud.adapter.MusicAdapter
import com.example.soundcloud.adapter.PlayListPersonAdapter
import com.example.soundcloud.databinding.FragmentMyMusicBinding
import com.example.soundcloud.model.person.Music
import com.example.soundcloud.model.person.MusicPlayList
import com.example.soundcloud.model.person.PlayListPerson
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.viewmodel.MusicViewModel
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.example.soundcloud.dao.PlayListDatabase
import kotlin.math.log


class MyMusicFragment : Fragment() {

    lateinit var binding: FragmentMyMusicBinding
    var playListPersonAdapter: PlayListPersonAdapter? = null
    var musicList = ArrayList<Music>()
    private  val musicViewModel: MusicViewModel by activityViewModels()

    companion object {
        var musicPlayList: MusicPlayList = MusicPlayList()
    }

    fun newInstance(musicList: ArrayList<Music> = ArrayList<Music>()): MyMusicFragment {
        val args = Bundle()
        args.putParcelableArrayList("list",musicList)
        val fragment = MyMusicFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicViewModel.musicListLd?.observe(viewLifecycleOwner, { item ->
            binding.tvCountMusic.text = item.size.toString()
        })
        getData()
        setListener()
        setAdapter()
        musicPlayList = MusicPlayList()
        val editor = activity?.getSharedPreferences("FAVOURITES", MODE_PRIVATE)
        val jsonStringPlaylist = editor?.getString("MusicPlaylist", null)
        if(jsonStringPlaylist != null){
            val dataPlaylist: MusicPlayList = GsonBuilder().create().fromJson(jsonStringPlaylist, MusicPlayList::class.java)
            musicPlayList = dataPlaylist
        }
        playListPersonAdapter?.notifyDataSetChanged()

    }
    private fun getData() {
        if (arguments != null) {
            musicList = arguments?.getParcelableArrayList("list")!!
        }

    }

    private fun setListener() {
        binding.linearPerson.setOnClickListener {
//            (activity as? BottomNavigationActivity)?.getCurrentFragment()?.let { frag ->
//                frag.view?.id?.let { id ->
//                    var songLocalDeviceFragment = SongLocalDeviceFragment()
//                    frag.childFragmentManager.beginTransaction()
//                        .replace(id, songLocalDeviceFragment).addToBackStack(null).commit()
//                }
//            }
            var songLocalDeviceFragment = SongLocalDeviceFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.side_in_right,
                    R.anim.side_in_left,
                    R.anim.side_in_right,
                    R.anim.side_in_left
                )
                ?.replace(R.id.fameHome, songLocalDeviceFragment)?.addToBackStack(null)?.commit()
        }

        binding.linearAddPlayList.setOnClickListener {
            var dialogAddPlayListFragment = DialogAddPlayListFragment()
            dialogAddPlayListFragment.show(requireActivity().supportFragmentManager, "")
            dialogAddPlayListFragment.onClose = {
                dialogAddPlayListFragment.dismiss()
            }
            dialogAddPlayListFragment.onAddPlayList = { namePlayList ->
                addPlayList(namePlayList)
                dialogAddPlayListFragment.dismiss()
            }

        }
        binding.linearArtist.setOnClickListener {
            var myArtistFragment = MyArtistFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.side_in_right,
                    R.anim.side_in_left,
                    R.anim.side_in_right,
                    R.anim.side_in_left
                )
                ?.replace(R.id.fameHome, myArtistFragment)
                ?.addToBackStack(null)?.commit()
        }
        binding.linearUpload.setOnClickListener {
            var myUploadFragment = MyUploadFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.side_in_right,
                    R.anim.side_in_left,
                    R.anim.side_in_right,
                    R.anim.side_in_left
                )
                ?.replace(R.id.fameHome, myUploadFragment)
                ?.addToBackStack(null)?.commit()
        }
    }

    private fun addPlayList(name: String) {
        val db = Room.databaseBuilder(
            requireContext(),
            PlayListDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        var playListExit = false
        for (i in musicPlayList.ref) {
            if (name == i.name) {
                playListExit = true
                break
            }
        }
        if (playListExit) Toast.makeText(requireContext(), "Đã có playlist", Toast.LENGTH_SHORT)
            .show()
        else {
            val playLists = PlayListPerson()
            playLists.name = name
            playLists.playlist = ArrayList()
            val calendar = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            playLists.createdOn = sdf.format(calendar)
//            db.playlistDao().insertAll(playLists)
            musicPlayList.ref.add(playLists)
            playListPersonAdapter?.setPlayLists(musicPlayList.ref)

        }
    }

    private fun setAdapter() {

        playListPersonAdapter = PlayListPersonAdapter(requireContext(), onClickItem = { item, pos ->
            val playListDetailFragment = PlayListDetailFragment().newInstance(musicList,pos)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(android.R.id.content, playListDetailFragment)?.addToBackStack(null)?.commit()
        })
        binding.rcvPlayList.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvPlayList.adapter = playListPersonAdapter
        playListPersonAdapter?.onDeleteItem={playListPerson, i ->
            musicPlayList.ref.removeAt(i)
            playListPersonAdapter?.notifyItemRemoved(i)
            playListPersonAdapter?.setPlayLists(musicPlayList.ref)

        }


    }

    override fun onResume() {
        super.onResume()
        val editor = activity?.getSharedPreferences("FAVOURITES", MODE_PRIVATE)?.edit()
        val jsonStringPlaylist = GsonBuilder().create().toJson(musicPlayList)
        editor?.putString("MusicPlaylist", jsonStringPlaylist)
        editor?.apply()

    }

}
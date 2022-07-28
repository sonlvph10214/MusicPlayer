package com.example.soundcloud.view.stream

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundcloud.R
import com.example.soundcloud.adapter.MusicAdapter
import com.example.soundcloud.databinding.FragmentSongLocalDevideBinding
import com.example.soundcloud.model.person.Music
import com.example.soundcloud.view.playsong.PlayerMusicFragment
import com.example.soundcloud.viewmodel.MusicViewModel
import java.io.File


class SongLocalDeviceFragment : Fragment() {


    private lateinit var binding: FragmentSongLocalDevideBinding
    private lateinit var musicAdapter: MusicAdapter
    private val musicViewModel: MusicViewModel by activityViewModels()
    var musicListGet = ArrayList<Music>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongLocalDevideBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onStart() {
        super.onStart()
        isStoragePermissionGranted()
        binding.imgBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
//            var bundle= Bundle()
//            bundle.putParcelableArrayList("list",musicListGet)
        }
    }


    private fun isStoragePermissionGranted() {
        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getAllAudio()
        } else {
        }
    }



    private fun setAdapter() {
        binding.rcvSongLocal.layoutManager = LinearLayoutManager(requireContext())
        musicAdapter = MusicAdapter(requireContext(), musicListGet)
        binding.rcvSongLocal.adapter = musicAdapter
        musicAdapter.onClickMusic = {music, i,classSend->
            val playerMusicFragment = PlayerMusicFragment().newInstance(music, i,"MusicAdapter")
            playerMusicFragment.musicList = musicListGet
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
    }

    @SuppressLint("Range")
    private fun getAllAudio() {

        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 "
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val cursor = context?.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC",
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val album =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artist =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val duration =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumId =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                            .toString()
                    val uri = Uri.parse("content://media/external/audio/albumart")
                    val artUri = Uri.withAppendedPath(uri, albumId).toString()
                    val music = Music(id, title, album, artist, duration, path, artUri)
                    val file = File(music.path)
                    if (file.exists()) {
                        musicListGet.add(music)
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        musicViewModel.getMusicList(musicListGet)
        setAdapter()

    }


}
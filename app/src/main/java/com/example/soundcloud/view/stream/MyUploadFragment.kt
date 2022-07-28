package com.example.soundcloud.view.stream

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundcloud.R
import com.example.soundcloud.adapter.ArtistAdapter
import com.example.soundcloud.adapter.MyArtistAdapter
import com.example.soundcloud.databinding.FragmentMyArtistBinding
import com.example.soundcloud.databinding.FragmentMyMusicBinding
import com.example.soundcloud.databinding.FragmentMyUploadBinding
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.viewmodel.ArtistViewModel

class MyUploadFragment : Fragment() {

    lateinit var binding:FragmentMyUploadBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }


}
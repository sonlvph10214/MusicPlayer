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
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.viewmodel.ArtistViewModel

class MyArtistFragment : Fragment() {

    lateinit var binding: FragmentMyArtistBinding
    private val artistViewModel: ArtistViewModel by viewModels()
    private var itemArtistList = mutableListOf<ItemArtist>()
    private var artistAdapter: MyArtistAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel()
        getApi()
        setAdapter()
        setListener()
    }
    private fun setListener(){
        binding.imgBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun viewModel() {
        artistViewModel.listItemArtist?.observe(viewLifecycleOwner, {
            itemArtistList = it.toMutableList()
            artistAdapter?.artistList = itemArtistList
            artistAdapter?.notifyDataSetChanged()

        })
    }

    private fun getApi() {
        var token = SharePreference.getToken(requireContext())
        artistViewModel.getArtist(token)

    }

    private fun setAdapter() {
        artistAdapter = MyArtistAdapter(itemArtistList, requireContext())
        val layoutRcvArtist =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvMyArtist.adapter = artistAdapter
        binding.rcvMyArtist.layoutManager = layoutRcvArtist
    }

}
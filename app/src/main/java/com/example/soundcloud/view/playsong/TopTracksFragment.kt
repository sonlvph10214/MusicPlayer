package com.example.soundcloud.view.playsong

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundcloud.adapter.*
import com.example.soundcloud.databinding.FragmentTopTracksBinding
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.Track
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.viewmodel.ArtistViewModel


class TopTracksFragment : Fragment() {


    private lateinit var binding: FragmentTopTracksBinding
    private val artistViewModel: ArtistViewModel by viewModels()
    var itemArtist: ItemArtist? = null
    var id: String = ""
    private var topTracksAdapter: TopTracksAdapter? = null
    private var tracksList = mutableListOf<Track>()


    fun newInstance(itemArtist: ItemArtist): TopTracksFragment {
        val args = Bundle()
        args.putSerializable("artist", itemArtist)
        val fragment = TopTracksFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopTracksBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataArtist()
        viewModel()
        callApi()
        setAdapter()
        setListener()
    }

    private fun setListener() {
        binding.imgTopTracksBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

    }

    private fun callApi() {
        artistViewModel.getTracksArtist(SharePreference.getToken(requireContext()), id)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModel() {
        artistViewModel.isLoadingArtist?.observe(this, {
            if (it) {
                binding.progressBarTopTracks.visibility = View.VISIBLE
//                binding.layoutArtist.visibility = View.GONE
            } else {
                binding.progressBarTopTracks.visibility = View.GONE
//                binding.layoutArtist.visibility = View.VISIBLE
            }
        })
        artistViewModel.listArtistTracksLiveData?.observe(viewLifecycleOwner, {
            tracksList = it.toMutableList()
            topTracksAdapter?.trackList = tracksList
            topTracksAdapter?.notifyDataSetChanged()

        })

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter() {
        topTracksAdapter = TopTracksAdapter(tracksList, requireContext())

        val layoutRcv =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvTopTracks.adapter = topTracksAdapter
        binding.rcvTopTracks.layoutManager = layoutRcv
    }

    private fun getDataArtist() {
        if (arguments != null) {
            itemArtist = arguments?.getSerializable("artist") as ItemArtist
            id = itemArtist?.id.toString()

        }
    }


}
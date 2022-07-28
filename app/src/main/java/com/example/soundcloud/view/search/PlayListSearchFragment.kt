package com.example.soundcloud.view.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundcloud.adapter.TrackAdapter
import com.example.soundcloud.databinding.FragmentPlaylistSearchBinding
import com.example.soundcloud.model.Track
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.viewmodel.TracksViewModel


class PlayListSearchFragment : Fragment() {

    private val viewModel: TracksViewModel by activityViewModels()
    var tracks: Track? = null
    private var adapter: TrackAdapter? = null
    private var itemTrack = mutableListOf<Track>()
    var name:String =""



    private lateinit var binding: FragmentPlaylistSearchBinding

    fun newInstance(track: Track): PlayListSearchFragment {
        val args = Bundle()
        args.putSerializable("track", track)
        val fragment = PlayListSearchFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setListener()
        viewModel()
        callApi()
        setAdapter()


    }

    private fun callApi() {

        viewModel.getTrack(SharePreference.getToken(requireContext()),name)

    }

    private fun setAdapter() {

        adapter = TrackAdapter(itemTrack, requireContext())

        val layoutRcv =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvPlayListSeatch.adapter = adapter
        binding.rcvPlayListSeatch.layoutManager = layoutRcv


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun viewModel() {
        viewModel.isLoading?.observe(this, {
            if (it) {
                binding.progressBarPlayListSearch.visibility = View.VISIBLE
                binding.rcvPlayListSeatch.visibility = View.INVISIBLE
            } else {
                binding.progressBarPlayListSearch.visibility = View.GONE
                binding.rcvPlayListSeatch.visibility = View.VISIBLE
            }
        })
        viewModel.listTracksLD?.observe(viewLifecycleOwner, {
            itemTrack = it.toMutableList()
            adapter?.trackList = itemTrack
            adapter?.notifyDataSetChanged()

        })
    }

    private fun setListener() {
        binding.imgPlayListSearchBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getData() {
        if (arguments != null) {
            tracks = arguments?.getSerializable("track") as Track
            name = tracks?.name.toString()

        }


    }


}
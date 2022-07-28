package com.example.soundcloud.view.artist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.adapter.*
import com.example.soundcloud.databinding.FragmentArtistBinding
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.Track
import com.example.soundcloud.view.playsong.TopTracksFragment
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.viewmodel.ArtistViewModel


class ArtistFragment : Fragment() {


    private lateinit var binding: FragmentArtistBinding
    private val artistViewModel: ArtistViewModel by viewModels()
    var itemArtist: ItemArtist? = null
    var id: String = ""
    private var artistTrackAdapter: ArtistTrackAdapter? = null
    private var tracksList = mutableListOf<Track>()


    fun newInstance(itemArtist: ItemArtist): ArtistFragment {
        val args = Bundle()
        args.putSerializable("artist", itemArtist)
        val fragment = ArtistFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistBinding.inflate(inflater, container, false)

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

    //    fun takeDataArtist(){
//        artistViewModel.itemArtistLiveData.observe(this, { itemArtist ->
//            if (itemArtist?.images?.get(0)?.url != null){
//                Glide.with(requireContext()).load(itemArtist.images[0].url).into(binding.imgArtistDetail)
//                Glide.with(requireContext()).load(itemArtist.images[0].url).into(binding.imgBgArtist)
//            }
//        })
//
//    }
    private fun setListener() {
        binding.imgArtistBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.tvSeeAllTopTracks.setOnClickListener {
            val topTracksFragment = TopTracksFragment().newInstance(itemArtist!!)
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.fameHome, topTracksFragment)?.addToBackStack(null)?.commit()

        }

    }

    private fun callApi() {
        artistViewModel.getTracksArtist(SharePreference.getToken(requireContext()), id)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter() {
        artistTrackAdapter = ArtistTrackAdapter(tracksList, requireContext())

        val layoutRcv =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvTopTracks.adapter = artistTrackAdapter
        binding.rcvTopTracks.layoutManager = layoutRcv
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun viewModel() {
        artistViewModel.isLoadingArtist?.observe(this, {
            if (it) {
                binding.progressBarArtist.visibility = View.VISIBLE
                binding.layoutArtist.visibility = View.GONE
            } else {
                binding.progressBarArtist.visibility = View.GONE
                binding.layoutArtist.visibility = View.VISIBLE
            }
        })
        artistViewModel.listArtistTracksLiveData?.observe(viewLifecycleOwner, {
            tracksList = it.toMutableList()
            artistTrackAdapter?.trackList = tracksList
            artistTrackAdapter?.notifyDataSetChanged()

        })

    }

    private fun getDataArtist() {
        if (arguments != null) {
            itemArtist = arguments?.getSerializable("artist") as ItemArtist
            Glide.with(requireContext())
                .load(itemArtist?.images?.get(0)?.url)
                .into(binding.imgBgArtist)
            Glide.with(requireContext())
                .load(itemArtist?.images?.get(0)?.url)
                .into(binding.imgArtistDetail)
            binding.tvNameArtist.text = itemArtist?.name
            binding.tvFollower.text = " ${itemArtist?.followers?.total} Follower"
            id = itemArtist?.id.toString()

        }
    }

}
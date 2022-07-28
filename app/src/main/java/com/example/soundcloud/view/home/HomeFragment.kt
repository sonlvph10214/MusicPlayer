package com.example.soundcloud.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.soundcloud.R
import com.example.soundcloud.adapter.AlbumAdapter
import com.example.soundcloud.adapter.ArtistAdapter
import com.example.soundcloud.adapter.ChillAdapter
import com.example.soundcloud.adapter.PlayListAdapter
import com.example.soundcloud.view.artist.ArtistFragment
import com.example.soundcloud.databinding.FragmentHomeBinding
import com.example.soundcloud.view.detailplaylist.DetailPlaylistFragment
import com.example.soundcloud.model.Item
import com.example.soundcloud.model.ItemAlbum
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.ItemTracks
import com.example.soundcloud.sharepreferences.SharePreference.getToken
import com.example.soundcloud.viewmodel.AlbumViewModel
import com.example.soundcloud.viewmodel.ArtistViewModel
import com.example.soundcloud.viewmodel.SearchSoundViewModel


class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var itemList = mutableListOf<Item>()
    private var itemArtistList = mutableListOf<ItemArtist>()
    private var itemAlbumList = mutableListOf<ItemAlbum>()
    private var itemRecently = mutableListOf<Item>()
    private var adapter: PlayListAdapter? = null
    private var chillAdapter: ChillAdapter? = null
    private var artistAdapter: ArtistAdapter? = null
    private var albumAdapter: AlbumAdapter? = null
    private lateinit var binding: FragmentHomeBinding
    var item: Item? = null
    var itemArtist: ItemArtist? = null
    var tracksItem: ItemTracks? = null



    private var token = ""
    //    private val viewModel: CompleteViewModel by viewModels()
    private val viewModel: SearchSoundViewModel by viewModels()
    private val albumViewModel: AlbumViewModel by viewModels()
    private val artistViewModel: ArtistViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        checkPermission()
        viewModel()
        callApi()
        setAdapter()
        binding.swipeLayout.setOnRefreshListener(this)


    }
    private fun sendDataArtisrt() {
        if (itemArtist != null) {
            artistViewModel.select(itemArtist!!)
        }
    }

    private fun callApi() {
        token = getToken(requireContext())
        viewModel.getSpotify(token)
        viewModel.getSpotifyChill(token)
        artistViewModel.getArtist(token)
        albumViewModel.getAlbum(token)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter() {

        adapter = PlayListAdapter(itemList, requireContext())

        val layoutRcv =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvParty.adapter = adapter
        binding.rcvParty.layoutManager = layoutRcv

        //
        chillAdapter = ChillAdapter(itemList, requireContext())
        val layoutRcvChill =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvChill.adapter = chillAdapter
        binding.rcvChill.layoutManager = layoutRcvChill

        //
        artistAdapter = ArtistAdapter(itemArtistList, requireContext())
        val layoutRcvArtist =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvArtist.adapter = artistAdapter
        binding.rcvArtist.layoutManager = layoutRcvArtist

        //
        albumAdapter = AlbumAdapter(itemAlbumList, requireContext())
        val layoutRcvAlbum =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvAlbum.adapter = albumAdapter
        binding.rcvAlbum.layoutManager = layoutRcvAlbum
        listenerAdapter()
    }


    override fun onRefresh() {
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            callApi()
            binding.progressBarHome.visibility = View.GONE
            binding.swipeLayout.isRefreshing = false
        }, 1000)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModel() {
        viewModel.isLoading?.observe(this, {
            if (it) {
                binding.progressBarHome.visibility = View.VISIBLE
                binding.rcvParty.visibility = View.INVISIBLE
                binding.rcvArtist.visibility = View.INVISIBLE
                binding.rcvChill.visibility = View.INVISIBLE
                binding.rcvAlbum.visibility = View.INVISIBLE
            } else {
                binding.progressBarHome.visibility = View.GONE
                binding.rcvParty.visibility = View.VISIBLE
                binding.rcvArtist.visibility = View.VISIBLE
                binding.rcvChill.visibility = View.VISIBLE
                binding.rcvAlbum.visibility = View.VISIBLE

            }
        })
        viewModel.listItemLiveData?.observe(viewLifecycleOwner, {
            itemList = it.toMutableList()
            adapter?.itemList = itemList
            adapter?.notifyDataSetChanged()

        })
        viewModel.listItemChillLiveData?.observe(viewLifecycleOwner, {
            itemList = it.toMutableList()
            chillAdapter?.itemList = itemList
            chillAdapter?.notifyDataSetChanged()

        })
        artistViewModel.listItemArtist?.observe(viewLifecycleOwner, {
            itemArtistList = it.toMutableList()
            artistAdapter?.artistList = itemArtistList
            artistAdapter?.notifyDataSetChanged()

        })
        albumViewModel.listAlbumLiveData?.observe(viewLifecycleOwner, {
            itemAlbumList = it.toMutableList()
            albumAdapter?.itemList = itemAlbumList
            albumAdapter?.notifyDataSetChanged()

        })

    }


    private fun listenerAdapter() {
        adapter?.onClickSong = { item, position ->

            val detailPlaylistFragment = DetailPlaylistFragment().newInstance(item)
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.side_in_right,R.anim.side_in_left,R.anim.side_in_right,R.anim.side_in_left)
                ?.add(R.id.fameHome, detailPlaylistFragment)
                ?.addToBackStack(null)?.commit()


        }
        chillAdapter?.onClickSong = { item, position ->

            val detailPlaylistFragment = DetailPlaylistFragment().newInstance(item)
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.side_in_right,R.anim.side_in_left,R.anim.side_in_right,R.anim.side_in_left)
                ?.add(R.id.fameHome, detailPlaylistFragment)?.addToBackStack(null)?.commit()


        }
        artistAdapter?.onClickArtist = { itemArtist, position ->

            val artistFragment = ArtistFragment().newInstance(itemArtist)
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.side_in_right,R.anim.side_in_left,R.anim.side_in_right,R.anim.side_in_left)
                ?.add(R.id.fameHome, artistFragment)?.addToBackStack(null)?.commit()


        }
    }

}


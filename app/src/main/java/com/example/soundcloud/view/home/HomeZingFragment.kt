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
import com.example.soundcloud.adapter.*
import com.example.soundcloud.view.artist.ArtistFragment
import com.example.soundcloud.databinding.FragmentHomeBinding
import com.example.soundcloud.databinding.FragmentHomeZingBinding
import com.example.soundcloud.view.detailplaylist.DetailPlaylistFragment
import com.example.soundcloud.model.Item
import com.example.soundcloud.model.ItemAlbum
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.ItemTracks
import com.example.soundcloud.model.zing.ArtistZing
import com.example.soundcloud.model.zing.Complete
import com.example.soundcloud.model.zing.Song
import com.example.soundcloud.sharepreferences.SharePreference.getToken
import com.example.soundcloud.viewmodel.AlbumViewModel
import com.example.soundcloud.viewmodel.ArtistViewModel
import com.example.soundcloud.viewmodel.CompleteViewModel
import com.example.soundcloud.viewmodel.SearchSoundViewModel


class HomeZingFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var songList = mutableListOf<Song>()
    private var listRecently = mutableListOf<Song>()
    private var completeList = mutableListOf<Complete>()
    private var adapter: SongZingAdapter? = null
    private var artistList = mutableListOf<ArtistZing>()
    private var partyAdapter: PartyZingAdapter? = null
    private var artistAdapter: ArtistZingAdapter? = null
    private var chillAdapter: ChillZingAdapter? = null
    private lateinit var binding: FragmentHomeZingBinding
    var viewModel: CompleteViewModel? = null
    private var song:Song? = null
    var complete:Complete? = null


    init {
        viewModel = CompleteViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeZingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel()
        callApi()
        setAdapter()
        binding.swipeLayout.setOnRefreshListener(this)


    }

    private fun callApi(){
        viewModel?.getSong()

    }






    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter() {
        //adapter Yourlike
//        adapter = SongAdapter(songList, requireContext())
//        val linearLayoutManager = LinearLayoutManager(requireContext())
//        binding.rcvYourLike.adapter = adapter
//        binding.rcvYourLike.layoutManager = linearLayoutManager


//        //adapter artist
//
        artistAdapter = ArtistZingAdapter(artistList, requireContext())
        val layoutRcvArtist =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvArtist.adapter = artistAdapter
        binding.rcvArtist.layoutManager = layoutRcvArtist

        //
        partyAdapter = PartyZingAdapter(songList, requireContext())
        val layoutRcv =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvParty.adapter = partyAdapter
        binding.rcvParty.layoutManager = layoutRcv
        listenerAdapter()

//        //chill
        chillAdapter = ChillZingAdapter(songList, requireContext())
        val layoutRcvChill =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvChill.adapter = partyAdapter
        binding.rcvChill.layoutManager = layoutRcvChill




    }


    override fun onRefresh() {
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            callApi()
            binding.swipeLayout.isRefreshing = false
        }, 1000)
    }

    private fun viewModel() {
        viewModel?.listSongLiveData?.observe(viewLifecycleOwner, {
            songList= it.toMutableList()
            partyAdapter?.songList = songList
            chillAdapter?.songList = songList
            partyAdapter?.notifyDataSetChanged()
            chillAdapter?.notifyDataSetChanged()

        })
        viewModel?.listArtistLiveData?.observe(viewLifecycleOwner, {
            artistList= it as MutableList<ArtistZing>
            artistAdapter?.artistList = artistList
            artistAdapter?.notifyDataSetChanged()

        })
    }
    private fun listenerAdapter(){
//        partyAdapter?.onClickSong = { song,position  ->
////            binding.tvYouLike.visibility = View.VISIBLE
//            binding.tvSuggestion.visibility = View.VISIBLE
//            binding.rcvYourLike.visibility = View.VISIBLE
//
//            listRecently.add(song)
//            val recentlyAdapter = RecentlyAdapter(listRecently,requireContext())
//            val linearRcvRecently = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
//            binding.rcvYourLike.layoutManager = linearRcvRecently
//            binding.rcvYourLike.adapter = recentlyAdapter
//            adapter = SongAdapter(listRecently, requireContext())
//            val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
//            binding.rcvYourLike.adapter = adapter
//            binding.rcvYourLike.layoutManager = linearLayoutManager

//        }
    }

}

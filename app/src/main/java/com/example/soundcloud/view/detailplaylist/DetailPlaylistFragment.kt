package com.example.soundcloud.view.detailplaylist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.soundcloud.adapter.DetailListAdapter
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.databinding.FragmentDetailPlaylistBinding
import com.example.soundcloud.model.Item
import com.example.soundcloud.model.ItemTracks
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.viewmodel.ItemViewModel
import com.example.soundcloud.viewmodel.SearchSoundViewModel


class DetailPlaylistFragment : Fragment() {

    private val viewModel: ItemViewModel by activityViewModels()
    private val searchSoundViewModel: SearchSoundViewModel by activityViewModels()
    private var detailListAdapter: DetailListAdapter? = null
    var item: Item? = null
    private var trackList = mutableListOf<ItemTracks>()
    var itemTracks: ItemTracks? = null
    var id: String = ""



    fun newInstance(item: Item): DetailPlaylistFragment {
        val args = Bundle()
        args.putParcelable("song", item)
        val fragment = DetailPlaylistFragment()
        fragment.arguments = args
        return fragment
    }

    private lateinit var binding: FragmentDetailPlaylistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPlaylistBinding.inflate(inflater, container, false)

        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        viewModel()
        callApi()
        setListener()
        setAdapter()

    }

    private fun setListener() {
        binding.imgDetailBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.btnPlayAll.setOnClickListener {
//            (activity as BottomNavigationActivity).setShowPlaySong()
            sendDatatoActivity()
            (activity as BottomNavigationActivity).playSong()
        }
        binding.scrollViewDetailList.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val scrollY = binding.scrollViewDetailList.scrollY
            if (scrollY > 600) {
                binding.tvToolBar.visibility = View.VISIBLE
            } else {
                binding.tvToolBar.visibility = View.INVISIBLE
            }
        })

    }


    private fun sendDatatoActivity() {
        if (item != null) {
            searchSoundViewModel.select(item!!)
        }

    }

    private fun callApi() {
        viewModel.getTracks(SharePreference.getToken(requireContext()), id)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModel() {
        viewModel.isLoading?.observe(this, {
            if (it) {
                binding.progressBarDetail.visibility = View.VISIBLE
                binding.relativeDetail.visibility = View.GONE
            } else {
                binding.progressBarDetail.visibility = View.GONE
                binding.relativeDetail.visibility = View.VISIBLE
            }
        })

        viewModel.listTracksLiveData?.observe(viewLifecycleOwner, {
            trackList = it.toMutableList()
            detailListAdapter?.trackList = trackList
            detailListAdapter?.notifyDataSetChanged()


        })

    }

    private fun setAdapter() {

        detailListAdapter = DetailListAdapter(trackList, requireContext())

        val layoutRcv =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvDetailPlayList.adapter = detailListAdapter
        binding.rcvDetailPlayList.layoutManager = layoutRcv

        detailListAdapter?.onClickSong = { itemTracks, i ->

        }

    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        if (arguments != null) {
            item = arguments?.getSerializable("song") as Item
            Glide.with(requireContext())
                .load(item?.images?.get(0)?.url)
                .into(binding.imgSongDetail)
            binding.tvNameSongDetail.text = item?.name
            binding.tvToolBar.text = item?.name
            binding.tvMadeBy.text = " ${item?.description}"
            id = item?.id.toString()

        }
    }


}
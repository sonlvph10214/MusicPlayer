package com.example.soundcloud.view.stream

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundcloud.R
import com.example.soundcloud.adapter.MusicAdapter
import com.example.soundcloud.databinding.FragmentMyMusicBinding
import com.example.soundcloud.databinding.FragmentSelectionMusicBinding
import com.example.soundcloud.model.person.Music
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.viewmodel.MusicViewModel


class SelectionMusicFragment : Fragment() {
    lateinit var binding: FragmentSelectionMusicBinding
    var musicAdapter: MusicAdapter? = null
    var musicList: ArrayList<Music> = ArrayList()
    var musicListSearch: ArrayList<Music> = ArrayList()
    private val musicViewModel: MusicViewModel by activityViewModels()
    var search: Boolean = false


    fun newInstance(musicList: ArrayList<Music> = ArrayList<Music>()): SelectionMusicFragment {
        val args = Bundle()
        args.putParcelableArrayList("list", musicList)
        val fragment = SelectionMusicFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectionMusicBinding.inflate(inflater, container, false)

        getData()
        setAdapter()
        setListener()
        return binding.root
    }

    private fun setListener() {
        binding.imgBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
//        binding.edtSearch.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (p0.toString().trim().isEmpty()) {
//                    musicAdapter?.setMusicList(mutableListOf())
//
//                } else {
//                    musicAdapter?.setMusicList(musicList.filter {
//                        it.title.contains(p0.toString(), true)
//                    }.toMutableList())
//                }
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//        })
//        binding.imgCloseSearch.setOnClickListener {
//            binding.edtSearch.setText("")
//
//        }
    }

    private fun getData() {
        if (arguments != null) {
            musicList = arguments?.getParcelableArrayList("list")!!
        }

    }

    private fun setAdapter() {
        binding.rcvSearch.layoutManager = LinearLayoutManager(requireContext())
        musicAdapter = MusicAdapter(requireContext(),musicList,selectionActivity = true)
        binding.rcvSearch.adapter = musicAdapter
    }


}
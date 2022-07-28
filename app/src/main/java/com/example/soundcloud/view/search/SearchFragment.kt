package com.example.soundcloud.view.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundcloud.adapter.SearchAdapter
import com.example.soundcloud.view.bottomnavigation.BottomNavigationActivity
import com.example.soundcloud.databinding.FragmentSearchBinding
import com.example.soundcloud.sharepreferences.SharePreference

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.example.soundcloud.R
import com.example.soundcloud.model.Track
import com.example.soundcloud.viewmodel.TracksViewModel


class SearchFragment : Fragment() {


    private val viewModel: TracksViewModel by activityViewModels()
    private var itemList = mutableListOf<Track>()
    private var adapter: SearchAdapter? = null
    private var searcView: SearchView? = null

    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvSearch.visibility = View.INVISIBLE
        setListener()
        viewModel()
        callApi()
        setAdapter()


    }

    private fun callApi() {

        viewModel.getTrack(SharePreference.getToken(requireContext()), "tung")

    }

    private fun setAdapter() {

        adapter = SearchAdapter(requireContext())

        val layoutRcv =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvSearch.adapter = adapter
        binding.rcvSearch.layoutManager = layoutRcv

        adapter?.onClickItemSearch = { item, i ->
            var playListSearchFragment = PlayListSearchFragment().newInstance(item)
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.side_in_right,R.anim.side_in_left,R.anim.side_in_right,R.anim.side_in_left)
                ?.add(R.id.fameHome, playListSearchFragment)?.addToBackStack(null)?.commit()
            setShowKeyboard()
        }


        //
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isEmpty()) {
                    binding.rcvSearch.visibility = View.GONE
                    binding.imgCloseSearch.visibility = View.GONE
                    adapter?.setList(mutableListOf())
                    binding.edtSearch.isCursorVisible = false
                } else {
                    binding.rcvSearch.visibility = View.VISIBLE
                    binding.imgCloseSearch.visibility = View.VISIBLE
                    binding.edtSearch.isCursorVisible = true
                    adapter?.setList(itemList.filter {
                        it.name.contains(p0.toString(), true)
                    }.toMutableList())
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    private fun viewModel() {
        viewModel.listTracksLD?.observe(viewLifecycleOwner, {
            itemList = it.toMutableList()
            adapter?.setList(itemList)
            adapter?.notifyDataSetChanged()

        })
    }

    private fun setListener() {

        binding.edtSearch.setOnClickListener{
            binding.edtSearch.isCursorVisible = true

        }
        binding.imgCloseSearch.setOnClickListener {
            binding.edtSearch.setText("")
            (activity as BottomNavigationActivity).setShow()
            setShowKeyboard()

        }

    }

    private fun setShowKeyboard() {
        val imm: InputMethodManager? =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }


}
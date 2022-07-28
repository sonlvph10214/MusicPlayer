package com.example.soundcloud.view.personal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.soundcloud.R
import com.example.soundcloud.databinding.FragmentDetailPersonalBinding


class DetailPersonalFragment : Fragment() {

    private lateinit var binding: FragmentDetailPersonalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPersonalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        binding.imgDetailPersonalBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.linearNameDetail.setOnClickListener {
//            (activity as BottomNavigationActivity).setFameVisilibi()
            var updateProfileFragment = UpdateProfileFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.fameHome, updateProfileFragment)?.addToBackStack("detailPerson")?.commit()
        }
    }

}
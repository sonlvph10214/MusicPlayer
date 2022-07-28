package com.example.soundcloud.view.personal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.view.account.AccountActivity
import com.example.soundcloud.databinding.FragmentPersonalBinding
import com.example.soundcloud.model.User
import com.example.soundcloud.sharepreferences.SharePreference
import com.example.soundcloud.viewmodel.UserViewModel
import com.spotify.sdk.android.auth.AuthorizationClient


class PersonalFragment : Fragment() {

    private lateinit var binding: FragmentPersonalBinding
    var name:String? = null
    private val viewModel: UserViewModel by viewModels()
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        getData()
        viewModel()
        callApi()

    }
    private fun callApi() {

        viewModel.getUser(SharePreference.getToken(requireContext()))

    }
    @SuppressLint("CheckResult")
    private fun viewModel() {
        viewModel.userLiveData?.observe(viewLifecycleOwner, {
            user = it

            binding.tvYourProfile.text = user?.displayName
            if (user?.images?.get(0)?.url != null){
                Glide.with(requireContext()).load(user?.images?.get(0)?.url).into(binding.imgPersonal)

            }
        })
    }
    private fun setListener(){
//       binding.relativeYourProfile.setOnClickListener {
////           (activity as BottomNavigationActivity).setFameVisilibi()
//           var detailPersonalFragment = DetailPersonalFragment()
//           activity?.supportFragmentManager?.beginTransaction()
//               ?.add(R.id.fameHome, detailPersonalFragment)?.addToBackStack(null)?.commit()
//       }
//        binding.imgUpdatePerson.setOnClickListener{
////            (activity as BottomNavigationActivity).setFameVisilibi()
//            var updateProfileFragment = UpdateProfileFragment()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.add(R.id.fameHome, updateProfileFragment)?.addToBackStack("personal")?.commit()
//        }

        binding.tvSignOut.setOnClickListener {
            val dialogSignOutFragment = DialogSignOutFragment()
            dialogSignOutFragment.show(requireActivity().supportFragmentManager, "")
            dialogSignOutFragment.onSelectYes = {
                AuthorizationClient.clearCookies(context)
                binding.tvSignOut.visibility =View.INVISIBLE
                binding.tvSignIn.visibility =View.VISIBLE
                binding.tvYourProfile.text = " "
                binding.imgPersonal.setImageResource(R.drawable.ic_person)
                dialogSignOutFragment.dismiss()
            }
            dialogSignOutFragment.onSelectNo = {
                dialogSignOutFragment.dismiss()
            }
        }
        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(requireContext(),AccountActivity::class.java))
        }
    }
    private fun getData(){
        if (arguments != null) {
            name = arguments?.getString("personName")
//            Glide.with(requireContext())
//                .load(DetailPlaylistFragment.URL_IMG + song?.thumb)
//                .into(binding.imgSongDetail)
            binding.tvNameProfile.text = name
//            binding.tvMadeBy.text = "Made by ${song?.artist}"
        }
    }


}
package com.example.soundcloud.view.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.soundcloud.R
import com.example.soundcloud.databinding.FragmentDialogSignoutBinding


class DialogSignOutFragment() : DialogFragment() {

    var onSelectYes: (() -> Unit)? = null
    var onSelectNo: (() -> Unit)? = null

    private lateinit var binding: FragmentDialogSignoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogSignoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.custom_bg_white)
        setListener()

    }
    fun setListener(){
        binding.tvYes.setOnClickListener {
            onSelectYes?.invoke()
        }
        binding.tvNo.setOnClickListener {
            onSelectNo?.invoke()
        }
    }
}
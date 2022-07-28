package com.example.soundcloud.view.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.soundcloud.R
import com.example.soundcloud.databinding.FragmentDialogAddPlaylistBinding
import com.example.soundcloud.databinding.FragmentDialogSignoutBinding


class DialogAddPlayListFragment() : DialogFragment() {

    var onAddPlayList: ((String) -> Unit)? = null
    var onClose: (() -> Unit)? = null


    private lateinit var binding: FragmentDialogAddPlaylistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddPlaylistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialog?.window?.setBackgroundDrawableResource(R.drawable.custom_bg_white)
        setListener()

    }
    fun setListener(){

        binding.tvTitleAdd.setOnClickListener {
            var namePlayList = binding.edtNamePlayList.text
            if (namePlayList!=null){
                if (namePlayList.isNotEmpty()){
                    onAddPlayList?.invoke(namePlayList.toString())

                }
            }
        }
        binding.imgCloseDialog.setOnClickListener {
            onClose?.invoke()
        }
    }
}
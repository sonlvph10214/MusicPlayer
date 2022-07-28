package com.example.soundcloud.view.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.soundcloud.databinding.FragmentBottomSheetTimerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener


class DialogTimerFragment() : DialogFragment() {


    private var is24HView: Boolean = true
    private lateinit var binding: FragmentBottomSheetTimerBinding
    var onClickTimer: ((Int, Int) -> Unit)? = null
    var hour : Int=0
    var minutes : Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetTimerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        binding.timePicker.setIs24HourView(is24HView);

    }

    fun setListener() {
        binding.timePicker.setOnTimeChangedListener(OnTimeChangedListener { view, hourOfDay, minute ->
            hour= hourOfDay
            minutes= minute
            binding.textViewTime.text = "$hourOfDay:$minute"
        })
        binding.btnSetTime.setOnClickListener {
            onClickTimer?.invoke(hour,minutes)
        }
    }
}
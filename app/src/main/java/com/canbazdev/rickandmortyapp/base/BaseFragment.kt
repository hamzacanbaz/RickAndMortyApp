package com.canbazdev.rickandmortyapp.base

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

/*
*   Created by hamzacanbaz on 19.06.2022
*/
abstract class BaseFragment<DB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {
    private var _binding: DB? = null
    val binding: DB get() = _binding!!

    open fun DB.initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = this
        binding.initialize()

        return _binding!!.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showShortToast(displayedText: String) {
        Toast.makeText(context, displayedText, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(displayedText: String) {
        Toast.makeText(context, displayedText, Toast.LENGTH_LONG).show()
    }


}
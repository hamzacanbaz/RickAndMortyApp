package com.canbazdev.rickandmortyapp.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.canbazdev.rickandmortyapp.presentation.onboarding.SliderFragment

/*
*   Created by hamzacanbaz on 19.06.2022
*/
class OnboardingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = SliderFragment()
        fragment.arguments = Bundle().apply {
            putInt("onboarding", position)
        }
        return fragment
    }

}
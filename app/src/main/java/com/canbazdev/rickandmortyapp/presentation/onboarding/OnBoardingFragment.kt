package com.canbazdev.rickandmortyapp.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.databinding.FragmentOnBoardingBinding
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var dotsIndicator: WormDotsIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        onboardingAdapter = OnboardingAdapter(this)
        viewPager = binding.vpOnboarding
        viewPager.adapter = onboardingAdapter
        dotsIndicator = binding.dotsIndicator
        dotsIndicator.setViewPager2(viewPager)
    }


}
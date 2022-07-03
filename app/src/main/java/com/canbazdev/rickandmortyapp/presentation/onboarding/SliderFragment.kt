package com.canbazdev.rickandmortyapp.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.databinding.FragmentOnboardingFirstBinding
import com.canbazdev.rickandmortyapp.util.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/*
*   Created by hamzacanbaz on 19.06.2022
*/
@AndroidEntryPoint
class SliderFragment :
    BaseFragment<FragmentOnboardingFirstBinding>(R.layout.fragment_onboarding_first) {

    private val viewModel: SliderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        arguments?.takeIf { it.containsKey("onboarding") }?.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.getOnBoardingData(getInt("onboarding"))
            }
        }
        observe()
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    Event.NavigateToMain -> {
                        findNavController().navigate(R.id.action_onBoardingFragment_to_charactersFragment)
                    }
                    else -> {

                    }
                }
            }
        }
    }

}
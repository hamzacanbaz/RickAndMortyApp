package com.canbazdev.rickandmortyapp.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.util.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashFragment : Fragment() {


    private val viewModel: SplashViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        viewModel.startSplash()

    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    Event.NavigateToOnBoardingScreen -> {
                        findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
                    }
                    Event.NavigateToMain -> {
                        findNavController().navigate(R.id.action_splashFragment_to_charactersFragment)
                    }
                    else -> {}
                }
            }
        }
    }

}

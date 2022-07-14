package com.canbazdev.rickandmortyapp.presentation.detail_character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.databinding.FragmentCharacterDetailBinding
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.util.States
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding>(R.layout.fragment_character_detail) {

    private val viewModel: CharacterDetailViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        observe()

    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (it == States.Success.state) {
                    binding.clDetail.alpha = 1f
                }
            }
        }
    }


}
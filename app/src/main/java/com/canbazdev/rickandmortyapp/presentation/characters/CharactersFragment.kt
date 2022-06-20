package com.canbazdev.rickandmortyapp.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersItemDecoration
import com.canbazdev.rickandmortyapp.base.BaseFragment
import com.canbazdev.rickandmortyapp.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CharactersFragment() : BaseFragment<FragmentCharactersBinding>(R.layout.fragment_characters) {

    private lateinit var charactersAdapter: CharactersAdapter
    private val viewModel: CharactersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersAdapter = CharactersAdapter(viewModel)
        binding.viewmodel = viewModel
        binding.adapter = charactersAdapter
        binding.itemDecoration = CharactersItemDecoration()


        lifecycleScope.launchWhenStarted {
            viewModel.characters.collect {
                if (it.isNotEmpty()) {
                    println(it[0])
                }
            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                println("ui $it")
                if (it != 0) {
                    binding.pbCharacters.visibility = View.GONE
                }
            }
        }
    }

}
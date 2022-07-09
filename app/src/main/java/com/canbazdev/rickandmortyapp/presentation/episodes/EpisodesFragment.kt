package com.canbazdev.rickandmortyapp.presentation.episodes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.databinding.FragmentEpisodesBinding
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.presentation.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.presentation.characters.CharactersItemDecoration
import com.canbazdev.rickandmortyapp.presentation.locations.LocationItemDecoration
import com.canbazdev.rickandmortyapp.util.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private lateinit var episodesAdapter: EpisodesAdapter
    private val viewModel: EpisodesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        episodesAdapter = EpisodesAdapter(viewModel)
        binding.viewmodel = viewModel
        binding.adapter = episodesAdapter
        binding.itemDecoration = LocationItemDecoration()


    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is Event.NavigateToDetail -> {

                        val bundle = Bundle()
                        bundle.putString("characterId", event.characterId.toString())

                        findNavController().navigate(
                            R.id.action_charactersFragment_to_characterDetailFragment, bundle
                        )
                    }

                    else -> {}
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (it != 0) {
                    binding.pbCharacters.visibility = View.GONE
                }
            }
        }
    }


}
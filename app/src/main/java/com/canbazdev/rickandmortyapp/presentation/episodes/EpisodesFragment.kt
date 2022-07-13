package com.canbazdev.rickandmortyapp.presentation.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.databinding.FragmentEpisodesBinding
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.presentation.locations.LocationItemDecoration
import com.canbazdev.rickandmortyapp.util.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private lateinit var episodesAdapter: EpisodesAdapter
    private val viewModel: EpisodesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodesAdapter = EpisodesAdapter(viewModel)
        binding.viewmodel = viewModel
        binding.adapter = episodesAdapter
        binding.itemDecoration = LocationItemDecoration()
        observe()


    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {
            viewModel.episodes.collectLatest {
                episodesAdapter.submitData(it)
            }


        }


        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is Event.NavigateToEpisodeDetail -> {

                        val bundle = Bundle()
                        bundle.putParcelable("clickedEpisode", event.episode)

                        findNavController().navigate(
                            R.id.action_episodesFragment_to_episodeDetailFragment, bundle
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
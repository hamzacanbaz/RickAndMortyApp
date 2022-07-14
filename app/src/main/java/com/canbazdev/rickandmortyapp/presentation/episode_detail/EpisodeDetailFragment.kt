package com.canbazdev.rickandmortyapp.presentation.episode_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.databinding.FragmentEpisodeDetailBinding
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.presentation.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.util.Event
import com.canbazdev.rickandmortyapp.util.LayoutManagers
import com.canbazdev.rickandmortyapp.util.States
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EpisodeDetailFragment :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    private val viewModel: EpisodeDetailViewModel by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        charactersAdapter = CharactersAdapter(viewModel)
        charactersAdapter.changeLayoutManager(LayoutManagers.EPISODE_LINEAR_LAYOUT_MANAGER)
        binding.itemDecoration = EpisodeCharactersItemDecoration()
        binding.viewModel = viewModel
        binding.adapter = charactersAdapter

    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is Event.NavigateToDetail -> {

                        val bundle = Bundle()
                        bundle.putString("characterId", event.characterId.toString())

                        findNavController().navigate(
                            R.id.action_global_characterDetailFragment, bundle
                        )
                    }

                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.episodeCharacters.collect {
                charactersAdapter.apply {
                    this.submitData(PagingData.from(viewModel.episodeCharacters.value))
                    this.refresh()

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (it != States.Loading.state) {
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }

    }


}
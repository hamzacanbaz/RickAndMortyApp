package com.canbazdev.rickandmortyapp.presentation.characters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.databinding.FragmentCharactersBinding
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.util.Event
import com.canbazdev.rickandmortyapp.util.LayoutManagers
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>(R.layout.fragment_characters) {

    private lateinit var charactersAdapter: CharactersAdapter
    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        charactersAdapter = CharactersAdapter(viewModel)
        binding.viewmodel = viewModel
        binding.adapter = charactersAdapter
        binding.itemDecoration = CharactersItemDecoration()


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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.characters_layout_manager, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Grid" -> {
                viewModel.changeCurrentLayoutManager(LayoutManagers.GRID_LAYOUT_MANAGER)
            }
            "Linear" -> {
                viewModel.changeCurrentLayoutManager(LayoutManagers.LINEAR_LAYOUT_MANAGER)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
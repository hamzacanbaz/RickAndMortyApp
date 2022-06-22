package com.canbazdev.rickandmortyapp.presentation.locations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersItemDecoration
import com.canbazdev.rickandmortyapp.adapters.locations.LocationItemDecoration
import com.canbazdev.rickandmortyapp.adapters.locations.LocationsAdapter
import com.canbazdev.rickandmortyapp.base.BaseFragment
import com.canbazdev.rickandmortyapp.databinding.FragmentLocationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LocationsFragment : BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {

    private lateinit var locationsAdapter: LocationsAdapter
    private val viewModel: LocationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        observe()
        locationsAdapter = LocationsAdapter()
        binding.adapter = locationsAdapter
        binding.itemDecoration = LocationItemDecoration()

    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (it == 1) {
                    println(viewModel.locations.value.size)
                }
            }
        }
    }


}
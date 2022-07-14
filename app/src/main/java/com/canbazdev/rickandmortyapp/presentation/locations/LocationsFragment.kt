package com.canbazdev.rickandmortyapp.presentation.locations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.databinding.FragmentLocationsBinding
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.util.Event
import com.canbazdev.rickandmortyapp.util.States
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LocationsFragment : BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {

    private lateinit var locationsAdapter: LocationsAdapter
    private val viewModel: LocationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        locationsAdapter = LocationsAdapter(viewModel)
        binding.adapter = locationsAdapter
        binding.itemDecoration = LocationItemDecoration()
        observe()

    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {
            viewModel.locations.collectLatest {
                locationsAdapter.submitData(it)
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is Event.OpenTheLocationDetail -> {
                        val bundle = Bundle()
                        bundle.putString("characterId", event.locationPosition.toString())
                    }
                    else -> {
                    }
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (it != States.Loading.state) {
                    binding.pbCharacters.visibility = View.GONE
                }
            }
        }


    }


}
package com.canbazdev.rickandmortyapp.presentation.characters

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.databinding.DialogFilterBinding
import com.canbazdev.rickandmortyapp.databinding.FragmentCharactersBinding
import com.canbazdev.rickandmortyapp.presentation.base.BaseFragment
import com.canbazdev.rickandmortyapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest


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

        charactersAdapter = CharactersAdapter(viewModel)
        binding.viewmodel = viewModel
        binding.adapter = charactersAdapter
        binding.itemDecoration = CharactersItemDecoration()
        observe()


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
            viewModel.uiState.collect {
                if (it != States.Loading.state) {
                    binding.pbCharacters.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.characters.collectLatest {
                charactersAdapter.submitData(it)
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.characters_layout_manager, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            LayoutManagers.GRID_LAYOUT_MANAGER.text -> {
                viewModel.changeCurrentLayoutManager(LayoutManagers.GRID_LAYOUT_MANAGER)
            }
            LayoutManagers.LINEAR_LAYOUT_MANAGER.text -> {
                viewModel.changeCurrentLayoutManager(LayoutManagers.LINEAR_LAYOUT_MANAGER)
            }
            LayoutManagers.EPISODE_LINEAR_LAYOUT_MANAGER.text -> {
                viewModel.clearFilterAreas()
                setFilterDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFilterDialog() {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.dialog_filter, null, false)
        (binding as DialogFilterBinding).viewmodel = viewModel
        val builder = AlertDialog.Builder(context).create()
        builder.apply {
            this.setView(binding.root)
            this.show()
        }
        setFilterDialogListeners(binding, builder)

    }

    private fun setFilterDialogListeners(alertDialog: ViewDataBinding, builder: AlertDialog) {
        (alertDialog as DialogFilterBinding).powerSpinnerViewStatus
            .setOnSpinnerItemSelectedListener<String> { _, _, newIndex, _ ->
                viewModel.updateFilterStatus(Status.values()[newIndex])
            }
        alertDialog.powerSpinnerViewGender
            .setOnSpinnerItemSelectedListener<String> { _, _, newIndex, _ ->
                viewModel.updateFilterGender(Gender.values()[newIndex])
            }

        alertDialog.etFilterName.addTextChangedListener {
            viewModel.updateName(it.toString())
        }

        alertDialog.btnFilter.setOnClickListener {
            viewModel.getFilterCharacters()
            builder.cancel()
        }
    }


}
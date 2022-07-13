package com.canbazdev.rickandmortyapp.presentation.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.databinding.LocationItemBinding
import com.canbazdev.rickandmortyapp.databinding.LocationWithCharactersItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Location
import com.canbazdev.rickandmortyapp.util.NestedCharacters

/*
*   Created by hamzacanbaz on 23.06.2022
*/
class LocationsAdapter(
    private val listener: OnItemClickedListener?
) : PagingDataAdapter<Location, LocationsAdapter.LocationsViewHolder>(DiffUtilCallBack()) {

    private var locationsList = listOf<Location>()
    private var nestedCharacterAdapter = NestedCharacterAdapter()
    private var nestedCharactersList: List<Character> = ArrayList()

    fun setLocationsList(list: List<Location>) {
        locationsList = list
        notifyDataSetChanged()
    }

    inner class LocationsViewHolder(private val binding: ViewDataBinding) :
        BaseViewHolder<Location>(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun bind(item: Location) {
            if (binding is LocationItemBinding) {
                binding.location = item
            } else if (binding is LocationWithCharactersItemBinding) {
                binding.location = item
                binding.chrList = nestedCharactersList
                binding.rvNestedCharacter.adapter = nestedCharacterAdapter
            }

            //  setUpNestedCharacters(layoutPosition, nestedCharacterAdapter)
//            binding.rvNestedCharacter.adapter = nestedCharacterAdapter
        }

        override fun onClick(p0: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                openOrCloseCharactersSection(position)

                nestedCharactersList =
                    getItem(position)?.name?.let {
                        listener?.onItemClicked(
                            position, getItem(position)?.residents!!,
                            it
                        )
                    }!!
                nestedCharacterAdapter.setCharacterList(nestedCharactersList.toList())
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == NestedCharacters.OPEN.isOpen) {
            val binding = LocationWithCharactersItemBinding.inflate(inflater, parent, false)
            LocationsViewHolder(binding)
        } else {
            val binding = LocationItemBinding.inflate(inflater, parent, false)
            LocationsViewHolder(binding)

        }
    }


    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Location)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    interface OnItemClickedListener {
        fun onItemClicked(
            position: Int,
            idList: List<String>,
            locationName: String
        ): List<Character>
    }

    private fun openOrCloseCharactersSection(position: Int) {
        if (getItem(position)?.isDetailsOpen == false) {
            repeat(itemCount) {
                getItem(it)?.isDetailsOpen = false
            }

            getItem(position)?.isDetailsOpen = true
        } else {
            getItem(position)?.isDetailsOpen = false

        }
        notifyItemChanged(position)

    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.isDetailsOpen == true) {
            NestedCharacters.OPEN.isOpen
        } else {
            NestedCharacters.CLOSE.isOpen
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }

    }


}

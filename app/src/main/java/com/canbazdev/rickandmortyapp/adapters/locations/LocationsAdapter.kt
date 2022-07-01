package com.canbazdev.rickandmortyapp.adapters.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.databinding.LocationItemBinding
import com.canbazdev.rickandmortyapp.databinding.LocationWithCharactersItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Location

/*
*   Created by hamzacanbaz on 23.06.2022
*/
class LocationsAdapter(
    private val listener: OnItemClickedListener?
) : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    private var locationsList = ArrayList<Location>()
    private var nestedCharacterAdapter = NestedCharacterAdapter()

    fun setLocationsList(list: List<Location>) {
        locationsList.clear()
        locationsList.addAll(list)
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
                binding.rvNestedCharacter.adapter = nestedCharacterAdapter
            }

            //  setUpNestedCharacters(layoutPosition, nestedCharacterAdapter)
//            binding.rvNestedCharacter.adapter = nestedCharacterAdapter
        }

        override fun onClick(p0: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                openOrCloseCharactersSection(position)
                val nestedCharactersList =
                    listener?.onItemClicked(position, locationsList[position].residents!!)
                if (!nestedCharactersList.isNullOrEmpty()) {
                    nestedCharacterAdapter.setCharacterList(nestedCharactersList.toList())
                }
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 0) {
            val binding = LocationWithCharactersItemBinding.inflate(inflater, parent, false)
            return LocationsViewHolder(binding)
        } else {
            val binding = LocationItemBinding.inflate(inflater, parent, false)
            return LocationsViewHolder(binding)

        }
    }


    override fun getItemCount(): Int {
        return locationsList.size
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Location)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(locationsList[position])
    }

    interface OnItemClickedListener {
        fun onItemClicked(position: Int, idList: List<String>): ArrayList<Character>
    }

    // TODO Burası doğru mu acaba
    private fun openOrCloseCharactersSection(position: Int) {
        locationsList[position].isDetailsOpen = locationsList[position].isDetailsOpen == false
        notifyItemChanged(position)

    }


    override fun getItemViewType(position: Int): Int {
        return if (locationsList[position].isDetailsOpen == true) {
            0
        } else {
            1
        }
    }

    private fun setUpNestedCharacters(
        position: Int,
        nestedCharacterAdapter: NestedCharacterAdapter
    ) {
        val nestedCharacterList: ArrayList<Character> = ArrayList()

        locationsList[position].residents?.onEach {
            nestedCharacterList.add(Character())
        }
        nestedCharacterAdapter.characterList = nestedCharacterList
    }


}

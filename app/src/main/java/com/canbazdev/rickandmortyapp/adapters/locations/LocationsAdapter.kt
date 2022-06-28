package com.canbazdev.rickandmortyapp.adapters.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.databinding.LocationItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Location

/*
*   Created by hamzacanbaz on 23.06.2022
*/
class LocationsAdapter(
    private val listener: OnItemClickedListener
) : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    private var locationsList = ArrayList<Location>()
    private var charactersAdapter = CharactersAdapter(null)

    @SuppressLint("NotifyDataSetChanged")
    fun setLocationsList(list: List<Location>) {
        locationsList.clear()
        locationsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class LocationsViewHolder(private val binding: LocationItemBinding) :
        BaseViewHolder<Location>(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun bind(item: Location) {
            binding.location = item
            binding.rvNestedCharacter.adapter = charactersAdapter
        }

        override fun onClick(p0: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                openOrCloseCharactersSection(position)
                setUpNestedCharacters(layoutPosition, charactersAdapter)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LocationItemBinding.inflate(inflater, parent, false)
        return LocationsViewHolder(binding)
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
        fun onItemClicked(position: Int, location: Location)
    }

    // TODO Burası doğru mu acaba
    private fun openOrCloseCharactersSection(position: Int) {
        locationsList[position].isDetailsOpen = locationsList[position].isDetailsOpen == false
        notifyItemChanged(position)

    }

    private fun setUpNestedCharacters(position: Int, charactersAdapter: CharactersAdapter) {
        val nestedCharacterList: ArrayList<Character> = ArrayList()

        locationsList[position].residents?.onEach {
            nestedCharacterList.add(Character())
        }
        charactersAdapter.characterList = nestedCharacterList

    }


}

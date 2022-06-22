package com.canbazdev.rickandmortyapp.adapters.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.databinding.LocationItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Location

/*
*   Created by hamzacanbaz on 23.06.2022
*/
class LocationsAdapter(
) : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    var locationsList = ArrayList<Location>()

    @SuppressLint("NotifyDataSetChanged")
    fun setLocationsList(list: List<Location>) {
        locationsList.clear()
        locationsList.addAll(list)
        notifyDataSetChanged()

    }

    inner class LocationsViewHolder(private val binding: LocationItemBinding) :
        BaseViewHolder<Location>(binding.root) {
        override fun bind(item: Location) {
            binding.location = item
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


}

package com.canbazdev.rickandmortyapp.presentation.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.databinding.NestedCharacterItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Character

/*
*   Created by hamzacanbaz on 29.06.2022
*/
class NestedCharacterAdapter :
    RecyclerView.Adapter<NestedCharacterAdapter.NestedCharactersViewHolder>() {

    private var characterList = ArrayList<Character>()

    fun setCharacterList(list: List<Character>) {
        characterList.clear()
        characterList.addAll(list)
        notifyDataSetChanged()

    }

    inner class NestedCharactersViewHolder(private val binding: NestedCharacterItemBinding) :
        BaseViewHolder<Character>(binding.root) {


        override fun bind(item: Character) {
            binding.character = item
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NestedCharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NestedCharacterItemBinding.inflate(inflater, parent, false)
        return NestedCharactersViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return characterList.size
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Character)
    }

    override fun onBindViewHolder(
        holder: NestedCharactersViewHolder,
        position: Int
    ) {
        holder.bind(characterList[position])

    }


}

package com.canbazdev.rickandmortyapp.adapters.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.databinding.NestedCharacterItemBinding
import com.canbazdev.rickandmortyapp.databinding.TestItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Character

/*
*   Created by hamzacanbaz on 29.06.2022
*/
class NestedCharacterAdapter() :
    RecyclerView.Adapter<NestedCharacterAdapter.NestedCharactersViewHolder>() {

    var characterList = ArrayList<Character>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacterList(list: List<Character>) {
        characterList.clear()
        characterList.addAll(list)
        notifyDataSetChanged()

    }

    inner class NestedCharactersViewHolder(private val binding: TestItemBinding) :
        BaseViewHolder<Character>(binding.root) {


        override fun bind(item: Character) {
//            binding.character = item
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NestedCharacterAdapter.NestedCharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TestItemBinding.inflate(inflater, parent, false)
        return NestedCharactersViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return characterList.size
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Character)
    }

    override fun onBindViewHolder(
        holder: NestedCharacterAdapter.NestedCharactersViewHolder,
        position: Int
    ) {
        holder.bind(characterList[position])

    }


}

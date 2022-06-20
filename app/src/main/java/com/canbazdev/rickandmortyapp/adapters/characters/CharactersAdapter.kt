package com.canbazdev.rickandmortyapp.adapters.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canbazdev.rickandmortyapp.databinding.CharacterItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Character

/*
*   Created by hamzacanbaz on 20.06.2022
*/
class CharactersAdapter(
    private val listener: OnItemClickedListener
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    var characterList = ArrayList<Character>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacterList(list: List<Character>) {
        characterList.clear()
        characterList.addAll(list)
        notifyDataSetChanged()

    }

    inner class CharactersViewHolder(private val binding: CharacterItemBinding) :
        BaseViewHolder<Character>(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }


        override fun bind(item: Character) {
            binding.tvCharacterName.text = item.name
            println(item.image)
            Glide.with(itemView.context).load(item.image).into(binding.ivCharacter)

        }

        override fun onClick(p0: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClicked(position, characterList[position])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        return CharactersViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return characterList.size
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Character)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characterList[position])

    }

    interface OnItemClickedListener {
        fun onItemClicked(position: Int, character: Character)
    }


}

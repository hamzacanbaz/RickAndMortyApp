package com.canbazdev.rickandmortyapp.presentation.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.databinding.CharacterForLinearLayoutItemBinding
import com.canbazdev.rickandmortyapp.databinding.CharacterItemBinding
import com.canbazdev.rickandmortyapp.databinding.EpisodeCharactersItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.util.LayoutManagers

/*
*   Created by hamzacanbaz on 20.06.2022
*/
class CharactersAdapter(
    private val listener: OnItemClickedListener?
) : PagingDataAdapter<Character, CharactersAdapter.CharactersViewHolder>(DiffUtilCallBack()) {

    private var characterList = ArrayList<Character>()
    private var selectedLayoutManager = LayoutManagers.GRID_LAYOUT_MANAGER


    @SuppressLint("NotifyDataSetChanged")
    fun setCharacterList(list: List<Character>) {
        characterList.clear()
        characterList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CharactersViewHolder(private val binding: ViewDataBinding) :
        BaseViewHolder<Character>(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }


        override fun bind(item: Character) {
            when (binding) {
                is CharacterItemBinding -> {
                    binding.character = item
                }
                is CharacterForLinearLayoutItemBinding -> {
                    binding.character = item
                }
                is EpisodeCharactersItemBinding -> {
                    binding.character = item
                }
            }

        }

        override fun onClick(p0: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                getItem(position)?.let { listener?.onItemClicked(position, it) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            when (viewType) {
                LayoutManagers.GRID_LAYOUT_MANAGER.ordinal -> {
                    CharacterItemBinding.inflate(inflater, parent, false)
                }
                LayoutManagers.LINEAR_LAYOUT_MANAGER.ordinal -> {
                    CharacterForLinearLayoutItemBinding.inflate(inflater, parent, false)
                }
                else -> {
                    EpisodeCharactersItemBinding.inflate(inflater, parent, false)
                }
            }
        return CharactersViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return when (selectedLayoutManager) {
            LayoutManagers.GRID_LAYOUT_MANAGER -> {
                LayoutManagers.GRID_LAYOUT_MANAGER.ordinal
            }
            LayoutManagers.LINEAR_LAYOUT_MANAGER -> {
                LayoutManagers.LINEAR_LAYOUT_MANAGER.ordinal
            }
            else -> {
                LayoutManagers.EPISODE_LINEAR_LAYOUT_MANAGER.ordinal
            }
        }
    }



    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Character)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }


    }

    interface OnItemClickedListener {
        fun onItemClicked(position: Int, character: Character)
    }

    fun changeLayoutManager(layoutManagers: LayoutManagers) {
        selectedLayoutManager = layoutManagers
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
}

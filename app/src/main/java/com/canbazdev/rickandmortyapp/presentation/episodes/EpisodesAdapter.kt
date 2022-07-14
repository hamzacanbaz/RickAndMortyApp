package com.canbazdev.rickandmortyapp.presentation.episodes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.databinding.EpisodeItemBinding
import com.canbazdev.rickandmortyapp.domain.model.Episode

/*
*   Created by hamzacanbaz on 20.06.2022
*/
class EpisodesAdapter(
    private val listener: OnItemClickedListener?
) : PagingDataAdapter<Episode, EpisodesAdapter.EpisodesViewHolder>(DiffUtilCallBack()) {

    private var episodesList = ArrayList<Episode>()

    @SuppressLint("NotifyDataSetChanged")
    fun setEpisodesList(list: List<Episode>) {
        episodesList.clear()
        episodesList.addAll(list)
        notifyDataSetChanged()

    }

    inner class EpisodesViewHolder(private val binding: EpisodeItemBinding) :
        BaseViewHolder<Episode>(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }


        override fun bind(item: Episode) {
            binding.episode = item
        }

        override fun onClick(p0: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                getItem(position)?.let { listener?.onItemClicked(position, it) }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EpisodeItemBinding.inflate(inflater, parent, false)

        return EpisodesViewHolder(binding)
    }


    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Episode)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    interface OnItemClickedListener {
        fun onItemClicked(position: Int, episode: Episode)
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }

    }


}

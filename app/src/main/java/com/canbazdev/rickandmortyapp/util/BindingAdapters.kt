package com.canbazdev.rickandmortyapp.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Episode
import com.canbazdev.rickandmortyapp.domain.model.Location
import com.canbazdev.rickandmortyapp.presentation.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.presentation.characters.CharactersItemDecoration
import com.canbazdev.rickandmortyapp.presentation.characters.CharactersItemForLinearLayoutDecoration
import com.canbazdev.rickandmortyapp.presentation.episodes.EpisodesAdapter
import com.canbazdev.rickandmortyapp.presentation.locations.LocationsAdapter

/*
*   Created by hamzacanbaz on 19.06.2022
*/
@BindingAdapter("android:imageUrl")
fun <T> loadImage(view: ImageView, drawable: T?) {
    if (drawable != null) {
        Glide.with(view.context).load(drawable).error(R.drawable.error).into(view)
    }
}

@BindingAdapter("android:setBackgroundColor")
fun setBackgroundColor(view: View, @ColorInt color: Int) {
    view.setBackgroundResource(color)
}

@BindingAdapter("android:isLastScreen")
fun setLastScreen(view: TextView, isLast: Boolean) {
    if (isLast) {
        view.text = "Skip"
    }
}

@BindingAdapter("android:setAdapter")
fun <T> setAdapter(recyclerView: RecyclerView, adapter: T?) {
    adapter.let { recyclerView.adapter = it as RecyclerView.Adapter<*> }
}

@BindingAdapter("android:setItemDecoration")
fun <T> setItemDecoration(recyclerView: RecyclerView, decoration: T?) {
    recyclerView.addItemDecoration(decoration as RecyclerView.ItemDecoration)
}

@BindingAdapter("android:submitList")
fun submitList(recyclerView: RecyclerView, list: List<Character>?) {
    val adapter = recyclerView.adapter as CharactersAdapter?
    adapter?.setCharacterList(list ?: listOf())
}

@BindingAdapter("android:submitLocationsList")
fun submitLocationsList(recyclerView: RecyclerView, list: List<Location>?) {
    val adapter = recyclerView.adapter as LocationsAdapter?
    adapter?.setLocationsList(list ?: listOf())
    adapter?.notifyDataSetChanged()
}

@BindingAdapter("android:submitEpisodesList")
fun submitEpisodesList(recyclerView: RecyclerView, list: List<Episode>?) {
    val adapter = recyclerView.adapter as EpisodesAdapter?
    adapter?.setEpisodesList(list ?: listOf())
    adapter?.notifyDataSetChanged()
}

@BindingAdapter("android:setAlpha")
fun setAlpha(view: View, isLoading: Boolean) {
    if (isLoading) {
        view.alpha = 0.5f
    } else {
        view.alpha = 1f
    }
}

@BindingAdapter("android:setLayoutManager")
fun setLayoutManager(recyclerView: RecyclerView, layoutManager: LayoutManagers) {
    if (layoutManager == LayoutManagers.GRID_LAYOUT_MANAGER) {
        recyclerView.apply {
            this.layoutManager = GridLayoutManager(recyclerView.context, 2)
            (this.adapter as CharactersAdapter).selectedLayoutManager =
                LayoutManagers.GRID_LAYOUT_MANAGER
            while (recyclerView.itemDecorationCount > 0) {
                recyclerView.removeItemDecorationAt(0)
            }
            this.addItemDecoration(CharactersItemDecoration())
        }
    } else {
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(recyclerView.context)
            (this.adapter as CharactersAdapter).selectedLayoutManager =
                LayoutManagers.LINEAR_LAYOUT_MANAGER
            while (recyclerView.itemDecorationCount > 0) {
                recyclerView.removeItemDecorationAt(0)
            }
            this.addItemDecoration(CharactersItemForLinearLayoutDecoration())
        }

    }

}

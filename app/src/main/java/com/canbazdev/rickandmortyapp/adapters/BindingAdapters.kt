package com.canbazdev.rickandmortyapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.adapters.locations.LocationsAdapter
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Location

/*
*   Created by hamzacanbaz on 19.06.2022
*/
@BindingAdapter("android:imageUrl")
fun <T> loadImage(view: ImageView, drawable: T?) {
    if (drawable != null) {
        Glide.with(view.context).load(drawable).into(view)
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
}
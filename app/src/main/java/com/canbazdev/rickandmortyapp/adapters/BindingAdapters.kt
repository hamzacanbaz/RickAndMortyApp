package com.canbazdev.rickandmortyapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersItemDecoration
import com.canbazdev.rickandmortyapp.domain.model.Character

/*
*   Created by hamzacanbaz on 19.06.2022
*/
@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, drawable: Int?) {
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
fun setAdapter(recyclerView: RecyclerView, adapter: CharactersAdapter?) {
    adapter.let { recyclerView.adapter = it }
}

@BindingAdapter("android:setItemDecoration")
fun submitList(recyclerView: RecyclerView, decoration: CharactersItemDecoration) {
    recyclerView.addItemDecoration(decoration)
}

@BindingAdapter("android:submitList")
fun submitList(recyclerView: RecyclerView, list: List<Character>?) {
    val adapter = recyclerView.adapter as CharactersAdapter?

    adapter?.setCharacterList(list ?: listOf())
}
package com.canbazdev.rickandmortyapp.presentation.characters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.R


/*
*   Created by hamzacanbaz on 20.06.2022
*/
class CharactersItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacing = view.resources.getDimensionPixelSize(R.dimen.margin_24dp)
        val isFirstItem = parent.getChildAdapterPosition(view) == 0
        val isSecondItem = parent.getChildAdapterPosition(view) == 1

        with(outRect) {
            if (isFirstItem || isSecondItem) top = spacing
            bottom = spacing

        }
    }

}
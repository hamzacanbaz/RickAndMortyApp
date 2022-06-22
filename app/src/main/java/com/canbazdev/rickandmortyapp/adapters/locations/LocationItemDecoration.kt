package com.canbazdev.rickandmortyapp.adapters.locations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.rickandmortyapp.R

/*
*   Created by hamzacanbaz on 23.06.2022
*/
class LocationItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacing = view.resources.getDimensionPixelSize(R.dimen.margin_24dp)
        val isFirstItem = parent.getChildAdapterPosition(view) == 0
        val isSecondItem = parent.getChildAdapterPosition(view) == 1
        val isItemEven = parent.getChildAdapterPosition(view) % 2 == 0

        with(outRect) {
            if (isFirstItem || isSecondItem) top = spacing
            bottom = spacing
            if (isItemEven) {
                left = spacing
                right = spacing / 2
            } else {
                right = spacing
                left = spacing / 2

            }

        }
    }

}
package com.example.test1.ui.base.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(
    private val top: Int,
    private val between: Int,
    private val bottom: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == -1) {
            return
        }

        val spanCount = 1

        val itemCount = parent.adapter?.itemCount ?: -1
        outRect.bottom = if (position >= itemCount - spanCount) {
            bottom
        } else {
            between / 2
        }
    }
}

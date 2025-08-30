package com.hqumath.demo.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 首尾额外添加间距
 * CardView 需要 marginTop marginBottom
 */
class VerticalSpaceItemDecoration(
    private val verticalSpaceHeight: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == 0) {
            outRect.top = verticalSpaceHeight
        } else if (position == itemCount - 1) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}
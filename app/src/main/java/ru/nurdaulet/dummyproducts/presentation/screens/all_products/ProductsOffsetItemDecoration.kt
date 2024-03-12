package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class ProductsOffsetItemDecoration(
    private val leftOffset: Int = 0,
    private val topOffset: Int = 0,
    private val rightOffset: Int = 0,
    private val bottomOffset: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val manager = parent.layoutManager as GridLayoutManager
        if (position < manager.spanCount) outRect.top = topOffset

        if (position % 2 != 0) {
            outRect.right = rightOffset
            outRect.left = leftOffset / 2
        } else {
            outRect.left = leftOffset
            outRect.right = rightOffset / 2
        }

        outRect.bottom = bottomOffset
    }
}

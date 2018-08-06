package gastonsanguinetti.tvshows.tvshowdetails.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class HorizontalItemsItemDecoration(
        private val initialSpaceInPx: Int,
        private val itemSpaceInPx: Int,
        private val topSpaceInPx: Int,
        private val bottomSpaceInPx: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = itemSpaceInPx
        outRect.right = itemSpaceInPx
        outRect.top = topSpaceInPx
        outRect.bottom = bottomSpaceInPx

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = initialSpaceInPx
        }

        if (parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1) {
            outRect.right = initialSpaceInPx
        }
    }

}
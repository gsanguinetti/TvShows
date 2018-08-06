package gastonsanguinetti.tvshows.tvshowdetails.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


class HorizontalItemsLayoutManager(context: Context, private val visibleItems: Float)
    : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean =
            super.checkLayoutParams(lp) && lp!!.width == getItemSize()

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
            setProperItemSize(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): RecyclerView.LayoutParams =
            setProperItemSize(super.generateLayoutParams(lp))

    private fun setProperItemSize(layoutParams: RecyclerView.LayoutParams): RecyclerView.LayoutParams {
        layoutParams.width = getItemSize()
        return layoutParams
    }

    private fun getItemSize(): Int {
        return Math.round(width.toFloat() / visibleItems)
    }
}
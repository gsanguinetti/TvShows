package gastonsanguinetti.tvshows.tvshowdetails.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import gastonsanguinetti.tvshows.R
import kotlinx.android.synthetic.main.layout_tvshow_section.view.*

class TvShowDetailsSectionLayout : FrameLayout {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        addView(LayoutInflater.from(context).inflate(R.layout.layout_tvshow_section, this,
                false))

        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TvShowDetailsSectionLayout,
                0, 0)

        try {
            sectionTitleTextView.text = typedArray.getString(R.styleable.TvShowDetailsSectionLayout_section_title)
            typedArray.getBoolean(R.styleable.TvShowDetailsSectionLayout_section_padding_enabled, true).let {
                if(it){
                    val layoutParams = contentLayout.layoutParams as LinearLayout.LayoutParams
                    context.resources.getDimensionPixelSize(R.dimen.tvshow_details_section_content_margin).let {
                        layoutParams.setMargins(it, 0, it, 0)
                        contentLayout.layoutParams = layoutParams
                    }
                }
            }
        } finally {
            typedArray.recycle()
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams) {
        child?.let {
            if (child.id == R.id.rootSectionView) super.addView(child, index, params)
            else contentLayout.addView(it, index, params)
        }
    }
}
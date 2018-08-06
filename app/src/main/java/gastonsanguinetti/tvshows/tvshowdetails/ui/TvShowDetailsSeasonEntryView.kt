package gastonsanguinetti.tvshows.tvshowdetails.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import gastonsanguinetti.tvshows.R
import gastonsanguinetti.tvshows.ext.loadImage
import gastonsanguinetti.tvshows.network.ImageUrlBuilder
import kotlinx.android.synthetic.main.layout_tvshow_season_entry.view.*

class TvShowDetailsSeasonEntryView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context,
                seasonNumber: Int,
                episodesNumber: Int,
                airDate :String?,
                poster: String?) : super(context) {

        addView(LayoutInflater.from(context).inflate(R.layout.layout_tvshow_season_entry, this, false))
        seasonNumberTextView.text =
                if(seasonNumber > 0) context.getString(R.string.seasons_entry, seasonNumber)
                else context.getString(R.string.season_zero)
        airDate?.let { seasonAirDateTextView.text = it }
        seasonEpisodesTextView.text = context.getString(R.string.episodes, episodesNumber)
        poster?.let { seasonPosterImageView.loadImage(ImageUrlBuilder().build(poster)) }
    }

}
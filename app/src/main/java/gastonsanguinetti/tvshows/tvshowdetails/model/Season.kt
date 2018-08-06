package gastonsanguinetti.tvshows.tvshowdetails.model

import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class Season(
        @SerializedName("air_date") private val rawAirDate: String?,
        @SerializedName("episode_count") override val episodeCount: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("poster_path") override val posterPath: String?,
        @SerializedName("season_number") override val seasonNumber: Int
) : TvShowSeason {
    override val airDate: String?
        get() {
            rawAirDate?.let {
                return DateFormat.getDateInstance()
                        .format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(rawAirDate))
                        .toString()
            }
            return null
        }
}
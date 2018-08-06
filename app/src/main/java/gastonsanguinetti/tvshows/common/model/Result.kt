package gastonsanguinetti.tvshows.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Result(
        @SerializedName("poster_path") override val posterPath: String?,
        @SerializedName("popularity") override val popularity: Double,
        @SerializedName("id") override val id: Int,
        @SerializedName("backdrop_path") override val backdropPath: String?,
        @SerializedName("vote_average") override val voteAverage: Double,
        @SerializedName("overview") override val overview: String,
        @SerializedName("first_air_date") private val rawFirstAirDate: String,
        @SerializedName("origin_country") override val originCountry: List<String>,
        @SerializedName("genre_ids") val genreIds: List<Int>,
        @SerializedName("original_language") override val originalLanguage: String,
        @SerializedName("vote_count") override val voteCount: Int,
        @SerializedName("name") override val name: String,
        @SerializedName("original_name") override val originalName: String
) : TvShow {
    override val firstAirDate: String
        get() =
            DateFormat.getDateInstance()
                    .format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(rawFirstAirDate))
                    .toString()
}
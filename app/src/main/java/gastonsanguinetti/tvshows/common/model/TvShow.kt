package gastonsanguinetti.tvshows.common.model

import android.os.Parcelable

interface TvShow : Parcelable {
    val id: Int
    val posterPath: String?
    val backdropPath: String?
    val popularity: Double
    val voteAverage: Double
    val overview: String
    val firstAirDate: String
    val originCountry: List<String>
    val originalLanguage: String
    val voteCount: Int
    val name: String
    val originalName: String
}
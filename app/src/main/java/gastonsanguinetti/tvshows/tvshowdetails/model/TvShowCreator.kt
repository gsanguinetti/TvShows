package gastonsanguinetti.tvshows.tvshowdetails.model

interface TvShowCreator {
    val id: Int
    val name: String
    val gender: Int?
    val profilePath: String
}
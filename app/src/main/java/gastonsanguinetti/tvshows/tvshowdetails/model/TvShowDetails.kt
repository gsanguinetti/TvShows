package gastonsanguinetti.tvshows.tvshowdetails.model

interface TvShowDetails {
    val createdBy: List<TvShowCreator>
    val genres: List<TvShowGenre>
    val homepage: String
    val numberOfEpisodes: Int
    val numberOfSeasons: Int
    val seasons: List<TvShowSeason>
    val status: String
}
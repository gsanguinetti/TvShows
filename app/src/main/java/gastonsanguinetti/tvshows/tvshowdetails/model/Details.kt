package gastonsanguinetti.tvshows.tvshowdetails.model

import com.google.gson.annotations.SerializedName
import gastonsanguinetti.tvshows.common.model.TvShow
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Details(
        @SerializedName("backdrop_path") override val backdropPath: String?,
        @SerializedName("created_by") override val createdBy: @RawValue List<CreatedBy>,
        @SerializedName("episode_run_time") val episodeRunTime: List<Int>,
        @SerializedName("first_air_date") override val firstAirDate: String,
        @SerializedName("genres") override val genres: @RawValue List<Genre>,
        @SerializedName("homepage") override val homepage: String,
        @SerializedName("id") override val id: Int,
        @SerializedName("in_production") val inProduction: Boolean,
        @SerializedName("languages") val languages: List<String>,
        @SerializedName("last_air_date") val lastAirDate: String,
        @SerializedName("name") override val name: String,
        @SerializedName("networks") val networks: @RawValue List<Network>,
        @SerializedName("number_of_episodes") override val numberOfEpisodes: Int,
        @SerializedName("number_of_seasons") override val numberOfSeasons: Int,
        @SerializedName("origin_country") override val originCountry: List<String>,
        @SerializedName("original_language") override val originalLanguage: String,
        @SerializedName("original_name") override val originalName: String,
        @SerializedName("overview") override val overview: String,
        @SerializedName("popularity") override val popularity: Double,
        @SerializedName("poster_path") override val posterPath: String?,
        @SerializedName("production_companies") val productionCompanies: @RawValue List<ProductionCompany>,
        @SerializedName("seasons") override val seasons: @RawValue List<Season>,
        @SerializedName("status") override val status: String,
        @SerializedName("type") val type: String,
        @SerializedName("vote_average") override val voteAverage: Double,
        @SerializedName("vote_count") override val voteCount: Int
) : TvShowDetails, TvShow
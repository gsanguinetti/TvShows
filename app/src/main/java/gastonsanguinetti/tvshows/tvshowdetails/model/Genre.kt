package gastonsanguinetti.tvshows.tvshowdetails.model

import com.google.gson.annotations.SerializedName

data class Genre(
        @SerializedName("id") override val id: Int,
        @SerializedName("name") override val name: String
) : TvShowGenre
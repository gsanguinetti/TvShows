package gastonsanguinetti.tvshows.tvshowdetails.model

import com.google.gson.annotations.SerializedName

data class CreatedBy(
        @SerializedName("id") override val id: Int,
        @SerializedName("name") override val name: String,
        @SerializedName("gender") override val gender: Int?,
        @SerializedName("profile_path") override val profilePath: String
) : TvShowCreator
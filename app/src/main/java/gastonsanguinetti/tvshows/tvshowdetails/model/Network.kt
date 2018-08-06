package gastonsanguinetti.tvshows.tvshowdetails.model

import com.google.gson.annotations.SerializedName

data class Network(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String
)
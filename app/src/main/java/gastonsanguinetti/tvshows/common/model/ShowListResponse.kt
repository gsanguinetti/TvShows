package gastonsanguinetti.tvshows.common.model

import com.google.gson.annotations.SerializedName

data class ShowListResponse(
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: List<Result>,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int
)
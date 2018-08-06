package gastonsanguinetti.tvshows.common.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Configuration(
        @SerializedName("images") val images: Images,
        @SerializedName("change_keys") val changeKeys: List<String>
) : Serializable
package gastonsanguinetti.tvshows.network

import gastonsanguinetti.tvshows.common.model.Configuration
import gastonsanguinetti.tvshows.common.model.ShowListResponse
import gastonsanguinetti.tvshows.tvshowdetails.model.Details
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("tv/popular")
    fun getPopularShows(@Query("page") page: Int): Call<ShowListResponse>

    @GET("tv/{tv_id}/similar")
    fun getSimilarShows(@Path("tv_id") id: Int, @Query("page") page: Int): Call<ShowListResponse>

    @GET("configuration")
    fun getConfiguration(): Call<Configuration>

    @GET("tv/{tv_id}")
    fun getShowDetails(@Path("tv_id") id: Int,
                       @Query("append_to_response") options: String = "videos,images,reviews"): Call<Details>
}
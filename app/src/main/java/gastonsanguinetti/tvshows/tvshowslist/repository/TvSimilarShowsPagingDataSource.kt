package gastonsanguinetti.tvshows.tvshowslist.repository

import android.arch.paging.DataSource
import gastonsanguinetti.tvshows.common.model.Result
import gastonsanguinetti.tvshows.common.model.ShowListResponse
import gastonsanguinetti.tvshows.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvSimilarShowsPagingDataSource(override val api: Api,
                                     val tvShowId: Int) : TvShowsPagingDataSource(api) {

    override fun loadData(onDataLoaded: (results: List<Result?>) -> Unit, onDataError: () -> Unit) {
        api.getSimilarShows(tvShowId, pageIndex).enqueue(object : Callback<ShowListResponse> {
            override fun onFailure(call: Call<ShowListResponse>?, t: Throwable?) {
                onDataError()
            }

            override fun onResponse(call: Call<ShowListResponse>?,
                                    response: Response<ShowListResponse>?) {
                if (response != null && response.isSuccessful) {
                    pageIndex++
                    response.body()?.let { onDataLoaded(it.results) }
                } else {
                    onDataError()
                }
            }
        })
    }
}

class TvSimilarShowsPagingDataSourceFactory(override val api: Api, private val tvShowId: Int)
    : TvShowsPagingDataSourceFactory(api) {

    override fun create(): DataSource<Int, Result> {
        val dataSource = TvSimilarShowsPagingDataSource(api, tvShowId)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

}
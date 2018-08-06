package gastonsanguinetti.tvshows.tvshowslist.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.ItemKeyedDataSource
import gastonsanguinetti.tvshows.common.model.NetworkStatus
import gastonsanguinetti.tvshows.common.model.Result
import gastonsanguinetti.tvshows.common.model.ShowListResponse
import gastonsanguinetti.tvshows.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class TvShowsPagingDataSource(open val api: Api) : ItemKeyedDataSource<Int, Result>() {

    val networkStatus = MutableLiveData<NetworkStatus>()
    val initialLoading = MutableLiveData<NetworkStatus>()
    var pageIndex = 1

    var onRetryAction: (() -> Unit)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Result>) {
        initialLoading.postValue(NetworkStatus.LOADING)
        loadData(
                onDataLoaded = {
                    initialLoading.postValue(NetworkStatus.DONE)
                    callback.onResult(it.filterNotNull())
                },
                onDataError = { initialLoading.postValue(NetworkStatus.ERROR) }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Result>) {
        networkStatus.postValue(NetworkStatus.LOADING)
        loadData(
                onDataLoaded = {
                    networkStatus.postValue(NetworkStatus.DONE)
                    callback.onResult(it.filterNotNull())
                },
                onDataError = {
                    onRetryAction = { loadAfter(params, callback) }
                    networkStatus.postValue(NetworkStatus.ERROR)
                }
        )
    }

    fun retry() = onRetryAction?.invoke()

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Result>) = Unit

    override fun getKey(item: Result): Int = item.id

    protected open fun loadData(onDataLoaded: (results: List<Result?>) -> Unit, onDataError: () -> Unit) =
            api.getPopularShows(pageIndex).enqueue(object : Callback<ShowListResponse> {
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

open class TvShowsPagingDataSourceFactory(open val api: Api) : DataSource.Factory<Int, Result>() {

    var sourceLiveData = MutableLiveData<TvShowsPagingDataSource>()

    override fun create(): DataSource<Int, Result> {
        val dataSource = TvShowsPagingDataSource(api)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

}
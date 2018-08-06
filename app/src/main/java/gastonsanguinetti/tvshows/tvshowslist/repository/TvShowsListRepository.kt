package gastonsanguinetti.tvshows.tvshowslist.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import gastonsanguinetti.tvshows.TvShowsApplication
import gastonsanguinetti.tvshows.network.NetworkRepository
import gastonsanguinetti.tvshows.tvshowslist.model.TvShowsResult


class TvShowsListRepository : NetworkRepository() {

    var sourceLiveData: LiveData<TvShowsPagingDataSource>? = null

    init {
        TvShowsApplication.networkComponent.inject(this)
    }

    fun getPopularShows(): TvShowsResult = getShows(TvShowsPagingDataSourceFactory(api))

    fun getSimilarShows(tvShowId :Int): TvShowsResult = getShows(TvSimilarShowsPagingDataSourceFactory(api, tvShowId))

    private fun getShows(dataSourceFactory: TvShowsPagingDataSourceFactory): TvShowsResult {
        sourceLiveData = dataSourceFactory.sourceLiveData

        val networkStatus = Transformations.switchMap(dataSourceFactory.sourceLiveData, { it.networkStatus })
        val initialLoading = Transformations.switchMap(dataSourceFactory.sourceLiveData, { it.initialLoading })

        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
                .setPageSize(TV_SHOWS_PAGE_SIZE).build()
        val data = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()

        return TvShowsResult(data, networkStatus, initialLoading)
    }

    fun retryGettingPagedShows() = sourceLiveData?.value?.retry()

    companion object {
        private const val TV_SHOWS_PAGE_SIZE = 20
    }
}
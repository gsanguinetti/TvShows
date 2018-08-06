package gastonsanguinetti.tvshows.tvshowslist.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import gastonsanguinetti.tvshows.common.model.NetworkStatus
import gastonsanguinetti.tvshows.common.model.Result
import gastonsanguinetti.tvshows.tvshowslist.model.TvShowsResult
import gastonsanguinetti.tvshows.tvshowslist.repository.TvShowsListRepository

class TvShowsListViewModel : ViewModel() {

    private val tvShowListResponse = MutableLiveData<TvShowsResult>()
    private val tvShowListRepository = TvShowsListRepository()

    val tvShowList: LiveData<PagedList<Result>> = Transformations.switchMap(tvShowListResponse,
            { it -> it.data })
    val networkStatus: LiveData<NetworkStatus> = Transformations.switchMap(tvShowListResponse,
            { it -> it.networkStatus })
    val initialLoading: LiveData<NetworkStatus> = Transformations.switchMap(tvShowListResponse,
            { it -> it.initialStatus })

    fun onTvShowsRequested() {
        if(tvShowListResponse.value == null
                || tvShowListResponse.value!!.initialStatus.value == NetworkStatus.ERROR)
            tvShowListResponse.postValue(tvShowListRepository.getPopularShows())
    }

    fun onRetryGettingPagedShows() = tvShowListRepository.retryGettingPagedShows()
}
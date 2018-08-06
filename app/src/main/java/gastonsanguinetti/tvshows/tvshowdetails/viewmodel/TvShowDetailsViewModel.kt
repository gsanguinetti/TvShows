package gastonsanguinetti.tvshows.tvshowdetails.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import gastonsanguinetti.tvshows.common.model.NetworkStatus
import gastonsanguinetti.tvshows.common.model.Result
import gastonsanguinetti.tvshows.tvshowdetails.model.TvShowDetails
import gastonsanguinetti.tvshows.tvshowdetails.model.TvShowDetailsResult
import gastonsanguinetti.tvshows.tvshowdetails.repository.TvShowDetailsRepository
import gastonsanguinetti.tvshows.tvshowslist.model.TvShowsResult
import gastonsanguinetti.tvshows.tvshowslist.repository.TvShowsListRepository

class TvShowDetailsViewModel : ViewModel() {

    private val tvShowDetailsResponse = MutableLiveData<TvShowDetailsResult>()
    private val tvSimilarShowsResponse = MutableLiveData<TvShowsResult>()

    private val tvShowDetailsRepository = TvShowDetailsRepository()
    private val tvShowListRepository = TvShowsListRepository()
    private var tvShowId: Int? = null

    val tvShowDetailsNetworkStatus: LiveData<NetworkStatus> =
            Transformations.switchMap(tvShowDetailsResponse) { it -> it.status }
    val tvShowDetailsData: LiveData<TvShowDetails> = Transformations.switchMap(tvShowDetailsResponse) { it -> it.data }
    val tvSimilarShowsInitialNetworkStatus: LiveData<NetworkStatus> =
            Transformations.switchMap(tvSimilarShowsResponse) { it -> it.initialStatus }
    val tvSimilarShowsNetworkStatus: LiveData<NetworkStatus> = Transformations.switchMap(tvSimilarShowsResponse) { it -> it.networkStatus }
    val tvSimilarShowsData: LiveData<PagedList<Result>> = Transformations.switchMap(tvSimilarShowsResponse) { it -> it.data }

    fun onTvShowDetailsRequested(tvShowId: Int) {
        this.tvShowId = tvShowId
        if (tvShowDetailsResponse.value == null ||
                tvShowDetailsResponse.value!!.status.value == NetworkStatus.ERROR)
            tvShowDetailsResponse.postValue(tvShowDetailsRepository.getTvShowDetails(tvShowId))
    }

    fun onTvSimilarShowsRequested() {
        tvShowId?.let {
            if (tvSimilarShowsResponse.value == null ||
                    tvSimilarShowsResponse.value!!.initialStatus.value == NetworkStatus.ERROR)
                tvSimilarShowsResponse.postValue(tvShowListRepository.getSimilarShows(it))
        }
    }

    fun onRetryTvShowDetailsRequest() {
        tvShowId?.let {
            tvShowDetailsResponse.postValue(tvShowDetailsRepository.getTvShowDetails(it))
        }
    }

    fun onRetrySimilarShowsRequest() {
        tvShowId?.let {
            tvSimilarShowsResponse.postValue(tvShowListRepository.getSimilarShows(it))
        }
    }

    fun onRetryGettingPagedSimilarShows() = tvShowListRepository.retryGettingPagedShows()
}
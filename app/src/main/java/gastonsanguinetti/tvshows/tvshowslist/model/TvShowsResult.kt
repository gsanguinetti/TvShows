package gastonsanguinetti.tvshows.tvshowslist.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import gastonsanguinetti.tvshows.common.model.NetworkStatus
import gastonsanguinetti.tvshows.common.model.Result

data class TvShowsResult(
        val data: LiveData<PagedList<Result>>,
        val networkStatus: LiveData<NetworkStatus>,
        val initialStatus: LiveData<NetworkStatus>
)
package gastonsanguinetti.tvshows.tvshowdetails.model

import android.arch.lifecycle.MutableLiveData
import gastonsanguinetti.tvshows.common.model.NetworkStatus

data class TvShowDetailsResult(
        val data :MutableLiveData<TvShowDetails>,
        val status :MutableLiveData<NetworkStatus>
)
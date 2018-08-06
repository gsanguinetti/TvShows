package gastonsanguinetti.tvshows.tvshowdetails.repository

import android.arch.lifecycle.MutableLiveData
import gastonsanguinetti.tvshows.TvShowsApplication
import gastonsanguinetti.tvshows.common.model.NetworkStatus
import gastonsanguinetti.tvshows.network.NetworkRepository
import gastonsanguinetti.tvshows.tvshowdetails.model.Details
import gastonsanguinetti.tvshows.tvshowdetails.model.TvShowDetails
import gastonsanguinetti.tvshows.tvshowdetails.model.TvShowDetailsResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDetailsRepository : NetworkRepository() {

    init { TvShowsApplication.networkComponent.inject(this) }

    fun getTvShowDetails(tvShowId: Int): TvShowDetailsResult {

        val data = MutableLiveData<TvShowDetails>()
        val networkStatus = MutableLiveData<NetworkStatus>()

        networkStatus.postValue(NetworkStatus.LOADING)
        api.getShowDetails(tvShowId).enqueue(object : Callback<Details> {
            override fun onFailure(call: Call<Details>?, t: Throwable?) {
                networkStatus.postValue(NetworkStatus.ERROR)
            }

            override fun onResponse(call: Call<Details>?, response: Response<Details>?) {
                networkStatus.postValue(
                        if (response != null && response.isSuccessful) NetworkStatus.DONE
                        else NetworkStatus.ERROR
                )
                response?.body()?.let { data.postValue(it) }
            }
        })
        return TvShowDetailsResult(data, networkStatus)
    }
}
package gastonsanguinetti.tvshows.common.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import gastonsanguinetti.tvshows.TvShowsApplication
import gastonsanguinetti.tvshows.common.model.Configuration
import gastonsanguinetti.tvshows.network.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ConfigurationRepository : NetworkRepository() {

    @Inject
    lateinit var context: Context

    init { TvShowsApplication.networkComponent.inject(this) }

    fun fetchConfigurationFromServer(onLoadComplete: () -> Unit, onLoadingError: () -> Unit) {
        api.getConfiguration().enqueue(object : Callback<Configuration> {
            override fun onFailure(call: Call<Configuration>?, t: Throwable?) {
                onLoadingError()
            }

            override fun onResponse(call: Call<Configuration>?, response: Response<Configuration>?) {
                response?.body()?.let {
                    getSharedPreferences().edit { putString(CONFIGURATION_SPREFS_ENTRY, Gson().toJson(it)) }
                }
                onLoadComplete()
            }

        })
    }

    fun getConfiguration(): Configuration? =
            Gson().fromJson(getSharedPreferences().getString(CONFIGURATION_SPREFS_ENTRY, null),
                    Configuration::class.java)

    private fun getSharedPreferences(): SharedPreferences =
            context.getSharedPreferences(CONFIGURATION_SPREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val CONFIGURATION_SPREFS_NAME = "configuration"
        private const val CONFIGURATION_SPREFS_ENTRY = "configuration"
    }
}
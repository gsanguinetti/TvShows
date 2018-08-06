package gastonsanguinetti.tvshows

import android.app.Application
import com.facebook.stetho.Stetho
import gastonsanguinetti.tvshows.network.DaggerNetworkComponent
import gastonsanguinetti.tvshows.network.NetworkComponent
import gastonsanguinetti.tvshows.network.NetworkModule

class TvShowsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = initAppComponent()
        networkComponent = initNetworkComponent()

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    private fun initAppComponent(): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()

    private fun initNetworkComponent(): NetworkComponent =
            DaggerNetworkComponent.builder()
                    .networkModule(NetworkModule())
                    .build()

    companion object {
        lateinit var networkComponent: NetworkComponent
        lateinit var appComponent: AppComponent
    }
}
package gastonsanguinetti.tvshows.splash.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import gastonsanguinetti.tvshows.common.repository.ConfigurationRepository
import gastonsanguinetti.tvshows.ext.showConnectionErrorView
import gastonsanguinetti.tvshows.tvshowslist.ui.TvShowsListActivity

class SplashActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        fetchConfiguration()
    }

    private fun fetchConfiguration() {
        ConfigurationRepository().fetchConfigurationFromServer(
                { onConfigurationLoaded() },
                { showConnectionErrorView({ fetchConfiguration() }) }
        )
    }

    private fun onConfigurationLoaded() {
        startActivity(Intent(this, TvShowsListActivity::class.java))
        finish()
    }

}
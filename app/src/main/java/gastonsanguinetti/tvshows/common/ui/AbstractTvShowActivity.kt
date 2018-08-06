package gastonsanguinetti.tvshows.common.ui

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import gastonsanguinetti.tvshows.common.model.TvShow
import gastonsanguinetti.tvshows.tvshowdetails.ui.TvShowDetailsActivity
import gastonsanguinetti.tvshows.tvshowslist.ui.TvShowsListAdapter

abstract class AbstractTvShowActivity : AppCompatActivity() {

    protected val adapter: TvShowsListAdapter = TvShowsListAdapter({ tvShow: TvShow, imageView: ImageView ->
        onItemSelected(tvShow, imageView)
    })

    private fun onItemSelected(tvShow: TvShow, imageView: ImageView) {
        TvShowDetailsActivity.startActivity(tvShow, this, imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = finish()
}
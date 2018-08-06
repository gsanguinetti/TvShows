package gastonsanguinetti.tvshows.tvshowslist.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import androidx.core.view.forEach
import gastonsanguinetti.tvshows.R
import gastonsanguinetti.tvshows.common.model.NetworkStatus
import gastonsanguinetti.tvshows.common.ui.AbstractTvShowActivity
import gastonsanguinetti.tvshows.ext.hide
import gastonsanguinetti.tvshows.ext.show
import gastonsanguinetti.tvshows.ext.showConnectionErrorView
import gastonsanguinetti.tvshows.tvshowslist.ui.TvShowsListAdapter.Companion.NETWORK_STATE_VIEW_TYPE
import gastonsanguinetti.tvshows.tvshowslist.viewmodel.TvShowsListViewModel
import kotlinx.android.synthetic.main.activity_show_list.*

class TvShowsListActivity : AbstractTvShowActivity() {

    private lateinit var viewModel: TvShowsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        viewModel = ViewModelProviders.of(this).get(TvShowsListViewModel::class.java)
        setupShowListView()
        handleNetworkState()
        handleTvShowListAdapter()
        viewModel.onTvShowsRequested()
    }

    private fun setupShowListView() {
        val columns = resources.getInteger(R.integer.tvshow_list_columns)
        val layoutManager = GridLayoutManager(this, columns)
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)) {
                    NETWORK_STATE_VIEW_TYPE -> columns
                    else -> 1
                }
            }
        }
        showListRecyclerView.layoutManager = layoutManager

        showListRecyclerView.addItemDecoration(GridSpacingItemDecoration(columns,
                resources.getDimensionPixelSize(R.dimen.tvshows_item_spacing)))
    }

    private fun handleNetworkState() {
        viewModel.initialLoading.observe(this, Observer {
            showListRootLayout.forEach { it.hide() }
            when (it) {
                NetworkStatus.LOADING -> loadingProgressBar.show()
                NetworkStatus.DONE -> showListRecyclerView.show()
                NetworkStatus.ERROR -> showConnectionErrorView({ viewModel.onTvShowsRequested() })
            }
        })
        viewModel.networkStatus.observe(this, Observer {
            adapter.networkStatus = it
            when (it) {
                NetworkStatus.ERROR -> showConnectionErrorView({ viewModel.onRetryGettingPagedShows() })
                else -> Unit
            }
        })
    }

    private fun handleTvShowListAdapter() {
        if (showListRecyclerView.adapter == null)
            showListRecyclerView.adapter = adapter

        viewModel.tvShowList.observe(this, Observer { adapter.submitList(it) })
    }
}

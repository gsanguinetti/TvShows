package gastonsanguinetti.tvshows.tvshowdetails.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import fisk.chipcloud.ChipCloud
import fisk.chipcloud.ChipCloudConfig
import gastonsanguinetti.tvshows.R
import gastonsanguinetti.tvshows.common.model.NetworkStatus
import gastonsanguinetti.tvshows.common.model.TvShow
import gastonsanguinetti.tvshows.common.ui.AbstractTvShowActivity
import gastonsanguinetti.tvshows.ext.animatedShow
import gastonsanguinetti.tvshows.ext.hide
import gastonsanguinetti.tvshows.ext.loadImage
import gastonsanguinetti.tvshows.ext.showConnectionErrorView
import gastonsanguinetti.tvshows.network.ImageUrlBuilder
import gastonsanguinetti.tvshows.tvshowdetails.viewmodel.TvShowDetailsViewModel
import kotlinx.android.synthetic.main.activity_show_details.*
import java.text.DecimalFormat


class TvShowDetailsActivity : AbstractTvShowActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(transitionsIsAllowed(savedInstanceState)) { postponeEnterTransition() }
        setContentView(R.layout.activity_show_details)
        check(intent.hasExtra(TV_SHOW_EXTRA))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val tvShow: TvShow = intent.getParcelableExtra(TV_SHOW_EXTRA)

        initCollapsingToolbar(tvShow.backdropPath)
        populateKnownDetails(tvShow)

        if(transitionsIsAllowed(savedInstanceState)) {
            window.enterTransition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(p0: Transition?) {
                    titleTextView.animatedShow()
                    contentScrollView.animatedShow()
                    contentScrollView.doOnPreDraw { it.scrollTo(0, 0) }
                    showPosterImageView.animatedShow()
                    shadowView.animatedShow()
                    initFillingDetails(tvShow)
                }

                override fun onTransitionResume(p0: Transition?) = Unit
                override fun onTransitionPause(p0: Transition?) = Unit
                override fun onTransitionCancel(p0: Transition?) = Unit

                override fun onTransitionStart(p0: Transition?) {
                    contentScrollView.hide()
                    showPosterImageView.hide()
                    titleTextView.hide()
                    shadowView.hide()
                }
            })
        } else { initFillingDetails(tvShow) }

        if (relatedShowsRecyclerView.adapter == null)
            relatedShowsRecyclerView.adapter = adapter
        relatedShowsRecyclerView.layoutManager =
                HorizontalItemsLayoutManager(this, RELATED_ITEMS_VISIBLE)
        relatedShowsRecyclerView.addItemDecoration(
                HorizontalItemsItemDecoration(
                        resources.getDimensionPixelSize(R.dimen.tvshow_details_section_content_margin),
                        resources.getDimensionPixelSize(R.dimen.tvshow_details_section_related_items_margin),
                        resources.getDimensionPixelSize(R.dimen.tvshow_details_title_top_margin),
                        resources.getDimensionPixelSize(R.dimen.tvshow_details_title_bottom_margin)
                )
        )

        initToolbar()

        ViewModelProviders.of(this).get(TvShowDetailsViewModel::class.java).let { viewmodel ->

            viewmodel.tvShowDetailsNetworkStatus.observe(this, Observer {
                when (it) {
                    NetworkStatus.ERROR -> showConnectionErrorView({ viewmodel.onRetryTvShowDetailsRequest() })
                    else -> Unit
                }
            })

            viewmodel.tvSimilarShowsInitialNetworkStatus.observe(this, Observer {
                when (it) {
                    NetworkStatus.ERROR -> showConnectionErrorView({ viewmodel.onRetrySimilarShowsRequest() })
                    NetworkStatus.DONE -> relatedShowsSectionView.animatedShow()
                    else -> Unit
                }
            })

            viewmodel.tvSimilarShowsNetworkStatus.observe(this, Observer {
                when (it) {
                    NetworkStatus.ERROR -> showConnectionErrorView({ viewmodel.onRetryGettingPagedSimilarShows() })
                    else -> Unit
                }
            })

            viewmodel.tvShowDetailsData.observe(this, Observer {
                it?.let {
                    detailsContainer.animatedShow()

                    // Genres
                    val config = ChipCloudConfig()
                            .uncheckedChipColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
                            .selectMode(ChipCloud.SelectMode.none)
                            .useInsetPadding(true)
                    val chipCloud = ChipCloud(this, genresChipGroup, config)

                    if (it.genres.isNotEmpty()) it.genres.forEach { chipCloud.addChip(it.name) }
                    else genresChipGroup.hide()

                    // Info
                    episodesTextView.text = getString(R.string.episodes, it.numberOfEpisodes)
                    seasonsTextView.text = getString(R.string.seasons, it.numberOfSeasons)
                    if (it.homepage.isNotBlank()) websiteTextView.text = getString(R.string.website, it.homepage)
                    else websiteTextView.hide()

                    // Seasons
                    if (it.seasons.isEmpty()) seasonsSectionView.hide()
                    else it.seasons.forEach {
                        seasonsContainerLayout.addView(
                                TvShowDetailsSeasonEntryView(this, it.seasonNumber,
                                        it.episodeCount, it.airDate, it.posterPath)
                        )
                    }

                    // Similar Shows
                    viewmodel.onTvSimilarShowsRequested()
                }
            })

            viewmodel.tvSimilarShowsData.observe(this, Observer {
                adapter.submitList(it)
            })
        }

        if(transitionsIsAllowed(savedInstanceState)) { startPostponedEnterTransition() }
    }

    fun initFillingDetails(tvShow: TvShow) {
        ViewModelProviders.of(this).get(TvShowDetailsViewModel::class.java)
                .onTvShowDetailsRequested(tvShow.id)
    }

    private fun initCollapsingToolbar(poster: String?) {
        poster?.let { backdropImageView.loadImage(ImageUrlBuilder().build(it)) }
        detailsCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)
        detailsCollapsingToolbarLayout.title = ""
    }

    private fun populateKnownDetails(tvShow: TvShow) {
        tvShow.posterPath?.let { showPosterImageView.loadImage(ImageUrlBuilder().build(it)) }

        ratingTextView.text = DecimalFormat("#.#").format(tvShow.voteAverage / 2)
        voteCountTextView.text = getString(R.string.vote_count, tvShow.voteCount)
        titleTextView.text = tvShow.name

        if (!tvShow.overview.isBlank()) overviewTextView.text = tvShow.overview
        else overviewTextView.hide()

        firstAirDateTextView.text = getString(R.string.first_air_date, tvShow.firstAirDate)

        detailsAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0).let { collapsed ->
                titleTextView.setTextColor(
                        if (collapsed) getTextPrimaryColor()
                        else ResourcesCompat.getColor(resources, android.R.color.white, null)
                )
                detailsCollapsingToolbarLayout.title = if (collapsed) tvShow.name else ""
            }
        }

        titleTextView.doOnLayout {
            val params = contentScrollView.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior as AppBarLayout.ScrollingViewBehavior?
            behavior!!.overlayTop =
                    resources.getDimensionPixelSize(R.dimen.tvshow_details_content_top_margin) +
                    resources.getDimensionPixelSize(R.dimen.tvshow_details_title_top_margin) +
                    resources.getDimensionPixelSize(R.dimen.tvshow_details_title_bottom_margin) +
                    it.height
        }
    }

    private fun transitionsIsAllowed(savedInstanceState: Bundle?): Boolean =
            savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    private fun initToolbar() {
        setSupportActionBar(detailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun getTextPrimaryColor(): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
        val typedArray = obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.textColorPrimary))
        val primaryColor = typedArray.getColor(0, -1)
        typedArray.recycle()
        return primaryColor
    }

    companion object {
        private const val TV_SHOW_EXTRA = "tvShowExtra"
        private const val RELATED_ITEMS_VISIBLE = 2.75f

        fun startActivity(tvShow: TvShow, activity: AppCompatActivity, imageView: ImageView) {
            val intent = Intent(activity, TvShowDetailsActivity::class.java)
            intent.putExtra(TV_SHOW_EXTRA, tvShow)
            val pairArray = ArrayList<android.support.v4.util.Pair<View, String>>()
            pairArray.add(android.support.v4.util.Pair(imageView, activity.getString(R.string.imagePoster_transition_name)))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                    *pairArray.toTypedArray())
            activity.startActivity(intent, options.toBundle())
        }
    }
}
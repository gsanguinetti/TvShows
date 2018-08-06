package gastonsanguinetti.tvshows.network

import dagger.Component
import gastonsanguinetti.tvshows.common.repository.ConfigurationRepository
import gastonsanguinetti.tvshows.tvshowdetails.repository.TvShowDetailsRepository
import gastonsanguinetti.tvshows.tvshowslist.repository.TvShowsListRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(showsListRepository: TvShowsListRepository)
    fun inject(tvShowDetailsRepository: TvShowDetailsRepository)
    fun inject(configurationRepository: ConfigurationRepository)
}
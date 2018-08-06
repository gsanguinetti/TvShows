package gastonsanguinetti.tvshows.network

import javax.inject.Inject

abstract class NetworkRepository {
    @Inject
    open lateinit var api: Api
}
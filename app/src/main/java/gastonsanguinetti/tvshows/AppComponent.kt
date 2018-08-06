package gastonsanguinetti.tvshows

import dagger.Component
import gastonsanguinetti.tvshows.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(networkModule: NetworkModule)
}
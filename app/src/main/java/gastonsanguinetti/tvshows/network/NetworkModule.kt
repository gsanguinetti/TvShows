package gastonsanguinetti.tvshows.network

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import gastonsanguinetti.tvshows.TvShowsApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NetworkModule {

    @Inject
    lateinit var context: Context

    init { TvShowsApplication.appComponent.inject(this) }

    @Provides
    @Singleton
    fun provideApi(): Api =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .baseUrl(BASE_URL)
                    .client(OkHttpClient.Builder()
                            .addInterceptor(NetworkConfigurator(API_KEY))
                            .addNetworkInterceptor(StethoInterceptor())
                            .build())
                    .build()
                    .create(Api::class.java)

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "43e66aea250baab7a7ea77f1a4c675bc"
    }
}
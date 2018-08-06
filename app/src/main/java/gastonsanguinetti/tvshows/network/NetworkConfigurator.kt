package gastonsanguinetti.tvshows.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class NetworkConfigurator(private val apiKey : String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .addQueryParameter("language", Locale.getDefault().language)
                .addQueryParameter("region", Locale.getDefault().country)
                .build()

        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
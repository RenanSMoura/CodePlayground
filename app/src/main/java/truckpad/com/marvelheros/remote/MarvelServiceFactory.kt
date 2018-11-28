package truckpad.com.marvelheros.remote

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.cli.Digest
import org.apache.commons.codec.digest.DigestUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import truckpad.com.marvelheros.BuildConfig
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

object MarvelServiceFactory {

    private const val APIKEY = "9e55ad45f00c0574be5056ba03fc9b44"
    private const val SECRETKEY = "2b6f0eaed6b9b4706a478385a5d5cd25e61f5ab9"

    fun makeMarvelService(isDebug: Boolean): MarvelService {
        val okHttpClient = makeOkHttpClient(makeLogginInterceptor(isDebug),makeApiKeyInterceptor())
        return makeGithubTrendingService(okHttpClient, Gson())
    }

    private fun makeGithubTrendingService(okHttpClient: OkHttpClient,gson : Gson) : MarvelService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(MarvelService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, apiInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).addInterceptor(apiInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).build()
    }


    private fun makeApiKeyInterceptor() : Interceptor{
            return Interceptor {
                chain ->
                val timeStamp = System.currentTimeMillis().toString()
                val stringToHash = timeStamp + SECRETKEY + APIKEY
                val md5Hash = String(Hex.encodeHex(DigestUtils.md5(stringToHash)))

                val original = chain.request()
                val originalHttpUrl = original.url()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("ts",timeStamp)
                    .addQueryParameter("apikey",APIKEY)
                    .addQueryParameter("hash",md5Hash)
                    .build()
                chain.proceed(original.newBuilder().url(url).build())
            }
    }

    private fun makeLogginInterceptor(isDebug: Boolean) : HttpLoggingInterceptor {
        val loggin = HttpLoggingInterceptor()
        loggin.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggin
    }
}
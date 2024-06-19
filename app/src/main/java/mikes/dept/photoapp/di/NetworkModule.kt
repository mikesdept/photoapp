package mikes.dept.photoapp.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import mikes.dept.data.network.NetworkService
import mikes.dept.data.network.interceptor.ExceptionsInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    private companion object {
        private const val CONNECT_TIMEOUT_SECONDS = 30L
    }

    @Singleton
    @Provides
    fun getNetworkService(retrofit: Retrofit): NetworkService = retrofit.create<NetworkService>()

    @Provides
    fun getClient(okHttpClient: OkHttpClient, converterFactory: Factory): Retrofit = Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .baseUrl("base_url") // TODO: setup api
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()

    @Provides
    fun getConverterFactory(json: Json): Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    fun getJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    fun getOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        exceptionsInterceptor: ExceptionsInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(exceptionsInterceptor)
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS).build()

    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(BODY)

    @Provides
    fun getExceptionsInterceptor(json: Json): ExceptionsInterceptor = ExceptionsInterceptor(json = json)

    @Provides
    fun gson() = Gson()
}

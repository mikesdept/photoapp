package mikes.dept.data.network.interceptor

import kotlinx.serialization.json.Json
import mikes.dept.domain.exceptions.ConnectionTimeoutException
import mikes.dept.domain.exceptions.NoInternetConnectionException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionsInterceptor(
    private val json: Json
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = runCatching { chain.proceed(request) }.getOrElse { throwable ->
            throw when (throwable) {
                is UnknownHostException,
                is NoRouteToHostException,
                is ConnectException -> NoInternetConnectionException()
                is SocketTimeoutException -> ConnectionTimeoutException()
                else -> throwable
            }
        }
        return response
    }

    private fun errorFromJson(errorJson: String?): CustomError? = errorJson?.let { jsonString ->
        runCatching {
            json.decodeFromString(CustomError.serializer(), jsonString)
        }.getOrNull()
    }

}

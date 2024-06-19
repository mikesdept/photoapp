package mikes.dept.data.network.interceptor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomError(
    @SerialName("errorCode") val errorCode: String? = null,
    @SerialName("errorMessage") val errorMessage: String? = null
)

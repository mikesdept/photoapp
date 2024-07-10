package mikes.dept.domain.entities

sealed class PhotoContentEntity {

    data class Url(val smallUrl: String, val regularUrl: String) : PhotoContentEntity()

    data class Base64(val base64: String): PhotoContentEntity()

}

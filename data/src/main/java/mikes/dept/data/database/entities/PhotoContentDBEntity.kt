package mikes.dept.data.database.entities

sealed class PhotoContentDBEntity {
    
    data class Url(val smallUrl: String, val regularUrl: String) : PhotoContentDBEntity()
    
    data class Base64(val base64: String): PhotoContentDBEntity()
    
}

package mikes.dept.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mikes.dept.domain.entities.PhotoEntity

@Entity
data class PhotoDBEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idLocal") val idLocal: Long = 0L,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "regularUrl") val regularUrl: String,
    @ColumnInfo(name = "smallUrl") val smallUrl: String
) {

    fun toDomain(): PhotoEntity = PhotoEntity(
        id = id,
        regularUrl = regularUrl,
        smallUrl = smallUrl
    )

}

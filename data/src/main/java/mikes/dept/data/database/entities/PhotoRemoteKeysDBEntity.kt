package mikes.dept.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoRemoteKeysDBEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "previousKey") val previousKey: Int?,
    @ColumnInfo(name = "nextKey") val nextKey: Int?
)

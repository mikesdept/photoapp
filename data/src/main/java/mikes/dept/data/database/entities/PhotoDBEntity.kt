package mikes.dept.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoDBEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idLocal") val idLocal: Long = 0L
)

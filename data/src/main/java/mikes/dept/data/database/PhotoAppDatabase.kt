package mikes.dept.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mikes.dept.data.database.dao.PhotoDao
import mikes.dept.data.database.entities.PhotoDBEntity

@Database(
    entities = [
        PhotoDBEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PhotoAppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}

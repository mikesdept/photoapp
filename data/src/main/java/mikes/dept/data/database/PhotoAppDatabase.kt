package mikes.dept.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mikes.dept.data.database.dao.PhotoDao
import mikes.dept.data.database.dao.PhotoRemoteKeysDao
import mikes.dept.data.database.entities.PhotoDBEntity
import mikes.dept.data.database.entities.PhotoRemoteKeysDBEntity

@Database(
    entities = [
        PhotoDBEntity::class,
        PhotoRemoteKeysDBEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PhotoAppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    abstract fun photoRemoteKeysDao(): PhotoRemoteKeysDao

}

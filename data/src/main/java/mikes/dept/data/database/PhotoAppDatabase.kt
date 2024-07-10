package mikes.dept.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mikes.dept.data.database.converters.BitmapConverter
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
@TypeConverters(BitmapConverter::class)
abstract class PhotoAppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    abstract fun photoRemoteKeysDao(): PhotoRemoteKeysDao

}

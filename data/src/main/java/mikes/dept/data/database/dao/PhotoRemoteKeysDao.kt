package mikes.dept.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mikes.dept.data.database.entities.PhotoRemoteKeysDBEntity

@Dao
interface PhotoRemoteKeysDao {

    @Query("SELECT * FROM PhotoRemoteKeysDBEntity WHERE id = :id LIMIT 1")
    suspend fun getRemoteKeyById(id: String): PhotoRemoteKeysDBEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photoRemoteKeysList: List<PhotoRemoteKeysDBEntity>)

    @Query("DELETE FROM PhotoRemoteKeysDBEntity")
    suspend fun clear()

}

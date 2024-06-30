package mikes.dept.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mikes.dept.data.database.entities.PhotoDBEntity

@Dao
interface PhotoDao {

    @Query("SELECT * FROM PhotoDBEntity")
    fun getAll(): PagingSource<Int, PhotoDBEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photoList: List<PhotoDBEntity>)

    @Query("DELETE FROM PhotoDBEntity")
    suspend fun clear()

}

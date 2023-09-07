package com.sanmer.geomag.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanmer.geomag.database.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM records")
    fun getAllAsFlow(): Flow<List<RecordEntity>>

    @Query(
        "SELECT * FROM records " +
        "WHERE model = :model " +
        "AND time = :time " +
        "AND altitude = :altitude " +
        "AND latitude = :latitude " +
        "AND longitude = :longitude " +
        "LIMIT 1"
    )
    fun getByKey(model: String, time: String, altitude: Double, latitude: Double, longitude: Double): RecordEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: RecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<RecordEntity>)

    @Delete
    suspend fun delete(value: RecordEntity)

    @Delete
    suspend fun delete(list: List<RecordEntity>)

    @Query("DELETE FROM records")
    suspend fun deleteAll()
}
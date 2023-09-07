package com.sanmer.geomag.repository

import com.sanmer.geomag.database.dao.RecordDao
import com.sanmer.geomag.database.entity.RecordKey
import com.sanmer.geomag.database.entity.toEntity
import com.sanmer.geomag.database.entity.toRecord
import com.sanmer.geomag.model.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val recordDao: RecordDao
) {
    fun getAllAsFlow() = recordDao.getAllAsFlow().map { list ->
        list.map { it.toRecord() }
    }

    suspend fun getByKey(key: RecordKey) = withContext(Dispatchers.IO) {
        recordDao.getByKey(
            model = key.model,
            time = key.time,
            altitude = key.altitude,
            latitude = key.latitude,
            longitude = key.longitude
        ).toRecord()
    }

    suspend fun insert(value: Record) = withContext(Dispatchers.IO) {
        recordDao.insert(value.toEntity())
    }

    suspend fun insert(list: List<Record>) = withContext(Dispatchers.IO) {
        recordDao.insert(list.map { it.toEntity() })
    }

    suspend fun delete(value: Record) = withContext(Dispatchers.IO) {
        recordDao.delete(value.toEntity())
    }

    suspend fun delete(list: List<Record>) = withContext(Dispatchers.IO) {
        recordDao.delete(list.map { it.toEntity() })
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        recordDao.deleteAll()
    }
}
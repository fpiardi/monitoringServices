package com.fpiardi.monitoringservices.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fpiardi.monitoringservices.model.SourceDetailError

@Dao
interface SourceDetailErrorDao {
    @Query("SELECT * FROM source_detail_error")
    suspend fun all(): List<SourceDetailError>

    @Query("""
      SELECT * FROM source_detail_error
      WHERE name LIKE :query
    """)
    suspend fun search(query: String): List<SourceDetailError>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SourceDetailError>)

    @Query("DELETE FROM source_detail_error")
    suspend fun deleteAll()
}
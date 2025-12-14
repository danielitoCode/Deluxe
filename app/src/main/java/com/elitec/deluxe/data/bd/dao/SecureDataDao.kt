package com.elitec.deluxe.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elitec.deluxe.domain.entities.SecureData

@Dao
interface SecureDataDao {
    @Query("SELECT * FROM securedata LIMIT 1")
    suspend fun getOne(): SecureData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(secureData: SecureData)

    @Query("DELETE FROM securedata")
    suspend fun clear()
}
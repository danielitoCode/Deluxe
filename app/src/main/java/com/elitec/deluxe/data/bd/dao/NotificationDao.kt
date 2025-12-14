package com.elitec.deluxe.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.elitec.deluxe.domain.entities.Categoria
import com.elitec.deluxe.domain.entities.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification")
    fun getAllFlow(): Flow<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Notification>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: Notification)

    @Query("SELECT * FROM notification WHERE id = :id")
    suspend fun getById(id: String): Notification

    @Query("DELETE FROM notification WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM notification")
    suspend fun deleteAll()
}
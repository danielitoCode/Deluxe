package com.elitec.deluxe.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.elitec.deluxe.data.bd.DeluxeDao
import com.elitec.deluxe.domain.entities.Categoria
import com.elitec.deluxe.domain.entities.Joya
import kotlinx.coroutines.flow.Flow

@Dao
interface JoyasDao {
    @Query("SELECT * FROM joya")
    fun getAllFlow(): Flow<List<Joya>>

    @Query("SELECT * FROM joya")
    fun getAll(): List<Joya>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Joya>)

    @Transaction
    suspend fun replaceAll(joyas: List<Joya>) {
        deleteAll()
        insertAll(joyas)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: Joya)

    @Query("SELECT * FROM joya WHERE id = :id")
    suspend fun getById(id: String): Joya

    @Query("DELETE FROM joya WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM joya")
    suspend fun deleteAll()
}
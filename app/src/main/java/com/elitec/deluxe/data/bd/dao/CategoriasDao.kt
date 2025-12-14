package com.elitec.deluxe.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.elitec.deluxe.data.bd.DeluxeDao
import com.elitec.deluxe.domain.entities.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriasDao {
    @Query("SELECT * FROM categoria")
    fun getAllFlow(): Flow<List<Categoria>>

    @Query("SELECT * FROM categoria")
    fun getAll(): List<Categoria>

    @Transaction
    suspend fun replaceAll(categorias: List<Categoria>) {
        deleteAll()
        insertAll(categorias)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Categoria>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: Categoria)

    @Query("SELECT * FROM categoria WHERE id = :id")
    suspend fun getById(id: String): Categoria

    @Query("DELETE FROM categoria WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM categoria")
    suspend fun deleteAll()
}

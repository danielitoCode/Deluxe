package com.elitec.deluxe.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.elitec.deluxe.domain.entities.Categoria
import com.elitec.deluxe.domain.entities.TestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Query("SELECT * FROM testentity")
    fun getAllFlow(): Flow<List<TestEntity>>

    @Query("SELECT * FROM testentity")
    fun getAll(): List<TestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TestEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: TestEntity)

    @Upsert
    suspend fun save(item: TestEntity)

    @Query("SELECT * FROM testentity WHERE id = :id")
    suspend fun getById(id: String): TestEntity

    @Query("DELETE FROM testentity WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM testentity")
    suspend fun deleteAll()
}
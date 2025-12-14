package com.elitec.deluxe.application.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elitec.deluxe.data.bd.dao.CategoriasDao
import com.elitec.deluxe.data.bd.dao.JoyasDao
import com.elitec.deluxe.data.bd.dao.NotificationDao
import com.elitec.deluxe.data.bd.dao.SecureDataDao
import com.elitec.deluxe.data.bd.dao.TestDao
import com.elitec.deluxe.domain.entities.Categoria
import com.elitec.deluxe.domain.entities.converters.DateTimeConverter
import com.elitec.deluxe.domain.entities.Joya
import com.elitec.deluxe.domain.entities.Notification
import com.elitec.deluxe.domain.entities.SecureData
import com.elitec.deluxe.domain.entities.TestEntity

@Database(
    entities = [
        Categoria::class,
        Joya::class,
        SecureData::class,
        Notification::class,
        TestEntity::class
    ],
    version = 4,
)
@TypeConverters(
    DateTimeConverter::class
)
abstract class DeluxeBD: RoomDatabase() {
    abstract fun categoriaDao(): CategoriasDao
    abstract fun joyaDao(): JoyasDao
    abstract fun securDataDao(): SecureDataDao
    abstract fun getTestDao(): TestDao
    abstract fun getNotificationsDao(): NotificationDao
}


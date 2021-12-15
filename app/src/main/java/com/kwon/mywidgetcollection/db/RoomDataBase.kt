package com.kwon.mywidgetcollection.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kwon.mywidgetcollection.api.*
import com.kwon.mywidgetcollection.entity.*

@Database(entities = [LifeRecord::class, ScheduleRecord::class, MockExamRecord::class], version = 3, exportSchema = true)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun lifeRecordService(): LifeRecordService
    abstract fun scheduleRecordService(): ScheduleRecordService
    abstract fun mockExamRecordService(): MockExamRecordService

    companion object {
        var instance: RoomDataBase? = null
        private const val DATABASE_NAME = "room_db"

        @Synchronized
        fun getInstance(context: Context): RoomDataBase? {
            instance?.let {
                return it
            }
            instance = Room.databaseBuilder(
                context.applicationContext,
                RoomDataBase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            return instance
        }
    }
}
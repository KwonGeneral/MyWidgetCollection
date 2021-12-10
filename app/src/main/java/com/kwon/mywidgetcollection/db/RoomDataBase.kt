package com.kwon.mywidgetcollection.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kwon.mywidgetcollection.api.*
import com.kwon.mywidgetcollection.entity.*
import java.io.File

@Database(entities = [LifeRecord::class, ScheduleRecord::class], version = 3, exportSchema = true)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun lifeRecordService(): LifeRecordService
    abstract fun scheduleRecordService(): ScheduleRecordService

    companion object {
        var instance: RoomDataBase? = null
        private const val DATABASE_NAME = "room_db"
        private const val DATABASE_DIR = "com.kwon.mywidgetcollection.db.RoomDataBase/2.json"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                        "PRIMARY KEY(`id`))")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE life_record ADD COLUMN updated_at INTEGER")
            }
        }


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
//                .createFromAsset(DATABASE_DIR)
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            return instance
        }
    }
}
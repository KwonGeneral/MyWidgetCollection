package com.kwon.mywidgetcollection.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kwon.mywidgetcollection.api.AwardsRecordService
import com.kwon.mywidgetcollection.api.CertificationRecordService
import com.kwon.mywidgetcollection.api.RankRecordService
import com.kwon.mywidgetcollection.api.VolunteerWorkRecordService
import com.kwon.mywidgetcollection.data.AwardsRecord
import com.kwon.mywidgetcollection.data.CertificationRecord
import com.kwon.mywidgetcollection.data.RankRecord
import com.kwon.mywidgetcollection.data.VolunteerWorkRecord

@Database(entities = [AwardsRecord::class, CertificationRecord::class, VolunteerWorkRecord::class, RankRecord::class], version = 2, exportSchema = false)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun awardsRecordService(): AwardsRecordService
    abstract fun certificationRecordService(): CertificationRecordService
    abstract fun volunteerWorkRecordService(): VolunteerWorkRecordService
    abstract fun rankRecordService(): RankRecordService

    companion object {
        var instance: RoomDataBase? = null

        @Synchronized
        fun getInstance(context: Context): RoomDataBase? {
            instance?.let {
                return it
            }
            instance = Room.databaseBuilder(
                context.applicationContext,
                RoomDataBase::class.java,
                "room_db"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            return instance
        }
    }
}
package com.kwon.mywidgetcollection.pager

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.RoomDatabase
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.entity.LifeRecord
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import kotlinx.coroutines.flow.Flow

class LifeRecordPaging(val context: Context) {
    fun getPage() {
        fun result(): Flow<PagingData<LifeRecord>> {
            return Pager(
                config = PagingConfig(pageSize = 100, enablePlaceholders = true),
                pagingSourceFactory = {RoomDataBase.getInstance(context)!!.lifeRecordService().readByPage()}
            ).flow
        }
    }

    fun getPage(type: String) {
        fun result(): Flow<PagingData<LifeRecord>> {
            return Pager(
                config = PagingConfig(pageSize = 100, enablePlaceholders = true),
                pagingSourceFactory = {RoomDataBase.getInstance(context)!!.lifeRecordService().readByPage()}
            ).flow
        }
    }

    fun getPage(type: String, sub_type: String) {
        fun result(): Flow<PagingData<LifeRecord>> {
            return Pager(
                config = PagingConfig(pageSize = 100, enablePlaceholders = true),
                pagingSourceFactory = {RoomDataBase.getInstance(context)!!.lifeRecordService().readByPage()}
            ).flow
        }
    }

    fun getSearchPage(type: String, sub_type: String) {

    }
}

class SchedulePaging {
    class Read(val context: Context, val count: Int) {
        fun result(): Flow<PagingData<ScheduleRecord>> {
            return Pager(
                config = PagingConfig(pageSize = 100, enablePlaceholders = true),
                pagingSourceFactory = {RoomDataBase.getInstance(context)!!.scheduleRecordService().readByPage()}
            ).flow
        }
    }

    class Search(val context: Context, private val type: String, private val sub_type: String, private val from_date: Long, private val to_date: Long, private val sort_name: String = "id", private val count: Int = 100) {
        fun result(sortDESC: Boolean = false): Flow<PagingData<ScheduleRecord>> {
            if(sortDESC) {
                return Pager(
                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
                    pagingSourceFactory = {RoomDataBase.getInstance(context)!!.scheduleRecordService().searchByPageDESC(type, sub_type, from_date, to_date, sort_name, count)}
                ).flow
            }
            return Pager(
                config = PagingConfig(pageSize = 100, enablePlaceholders = true),
                pagingSourceFactory = {RoomDataBase.getInstance(context)!!.scheduleRecordService().searchByPage(type, sub_type, from_date, to_date, sort_name, count)}
            ).flow
        }
    }
}

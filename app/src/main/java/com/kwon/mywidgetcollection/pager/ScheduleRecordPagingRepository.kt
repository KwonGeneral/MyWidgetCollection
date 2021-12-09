package com.kwon.mywidgetcollection.pager

import androidx.paging.*
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import kotlinx.coroutines.flow.Flow


class ScheduleRecordPagingRepository(private val database: RoomDataBase) {
    fun getScheduleRecordSearchPageASC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): Flow<PagingData<ScheduleRecord>> {
        val getScheduleRecordPageSourceFactory = {
            database.scheduleRecordService().searchByPageASC(type, sub_type, from_date, to_date, count)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getScheduleRecordPageSourceFactory
        ).flow
    }
    fun getScheduleRecordSearchPageDESC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): Flow<PagingData<ScheduleRecord>> {
        val getScheduleRecordPageSourceFactory = {
            database.scheduleRecordService().searchByPageDESC(type, sub_type, from_date, to_date, count)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getScheduleRecordPageSourceFactory
        ).flow
    }

    fun getScheduleRecordPage(): Flow<PagingData<ScheduleRecord>> {
        val getScheduleRecordPageSourceFactory = {
            database.scheduleRecordService().readByPage()
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getScheduleRecordPageSourceFactory
        ).flow
    }

    fun getTodayRecordPage(): Flow<PagingData<ScheduleRecord>> {
        val getScheduleRecordPageSourceFactory = {
            database.scheduleRecordService().searchByType(ScheduleDefine.TODAY)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getScheduleRecordPageSourceFactory
        ).flow
    }

    fun getTodoRecordPage(): Flow<PagingData<ScheduleRecord>> {
        val getScheduleRecordPageSourceFactory = {
            database.scheduleRecordService().searchByType(ScheduleDefine.TODO)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getScheduleRecordPageSourceFactory
        ).flow
    }
}
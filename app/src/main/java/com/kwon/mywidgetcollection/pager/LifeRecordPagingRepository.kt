package com.kwon.mywidgetcollection.pager

import androidx.paging.*
import com.kwon.mywidgetcollection.contains.LifeRecordDefine
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException


class LifeRecordPagingRepository(private val database: RoomDataBase) {
    fun getLifeRecordSearchPageASC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): Flow<PagingData<LifeRecord>> {
        val getLifeRecordSearchPageSourceFactory = {
            database.lifeRecordService().searchByPageASC(type, sub_type, from_date, to_date, count)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getLifeRecordSearchPageSourceFactory
        ).flow
    }
    fun getLifeRecordSearchPageDESC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): Flow<PagingData<LifeRecord>> {
        val getLifeRecordSearchPageSourceFactory = {
            database.lifeRecordService().searchByPageDESC(type, sub_type, from_date, to_date, count)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getLifeRecordSearchPageSourceFactory
        ).flow
    }

    fun getLifeRecordPage(): Flow<PagingData<LifeRecord>> {
        val getLifeRecordPageSourceFactory = {
            database.lifeRecordService().readByPage()
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getLifeRecordPageSourceFactory
        ).flow
    }

    fun getAwardsRecordPage(): Flow<PagingData<LifeRecord>> {
        val getAwardsRecordPageSourceFactory = {
            database.lifeRecordService().searchByType(LifeRecordDefine.AWARD)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getAwardsRecordPageSourceFactory
        ).flow
    }

    fun getCertificationRecordPage(): Flow<PagingData<LifeRecord>> {
        val getCertificationRecordPageSourceFactory = {
            database.lifeRecordService().searchByType(LifeRecordDefine.CERTIFICATION)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getCertificationRecordPageSourceFactory
        ).flow
    }

    fun getVolunteerRecordPage(): Flow<PagingData<LifeRecord>> {
        val getVolunteerRecordPageSourceFactory = {
            database.lifeRecordService().searchByType(LifeRecordDefine.VOLUNTEER)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getVolunteerRecordPageSourceFactory
        ).flow
    }

    fun getGradeRecordPage(): Flow<PagingData<LifeRecord>> {
        val getRankRecordPageSourceFactory = {
            database.lifeRecordService().searchByType(LifeRecordDefine.GRADE)
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getRankRecordPageSourceFactory
        ).flow
    }
}
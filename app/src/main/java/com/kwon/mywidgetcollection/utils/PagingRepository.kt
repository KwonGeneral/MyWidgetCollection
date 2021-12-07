package com.kwon.mywidgetcollection.utils

import androidx.paging.*
import com.kwon.mywidgetcollection.api.AwardsRecordService
import com.kwon.mywidgetcollection.data.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import kotlinx.coroutines.flow.Flow


class PagedRepository(private val database: RoomDataBase) {
    fun getAwardsRecordPage(): Flow<PagingData<AwardsRecord>> {
        val getAwardsRecordPageSourceFactory = {
            database.awardsRecordService().readByPage()
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getAwardsRecordPageSourceFactory
        ).flow
    }

    fun getCertificationRecordPage(): Flow<PagingData<CertificationRecord>> {
        val getCertificationRecordPageSourceFactory = {
            database.certificationRecordService().readByPage()
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getCertificationRecordPageSourceFactory
        ).flow
    }

    fun getVolunteerWorkRecordPage(): Flow<PagingData<VolunteerWorkRecord>> {
        val getVolunteerWorkRecordPageSourceFactory = {
            database.volunteerWorkRecordService().readByPage()
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getVolunteerWorkRecordPageSourceFactory
        ).flow
    }

    fun getRankRecordPage(): Flow<PagingData<RankRecord>> {
        val getRankRecordPageSourceFactory = {
            database.rankRecordService().readByPage()
        }
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
            pagingSourceFactory = getRankRecordPageSourceFactory
        ).flow
    }
}
package com.kwon.mywidgetcollection.viewmodel

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kwon.mywidgetcollection.data.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.utils.PagedRepository
import kotlinx.coroutines.flow.Flow
import navigation.LinearTapContainer
import navigation.LinearTapItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel(val context: Context): ViewModel() {
    var awardsRecordData = MutableLiveData<Flow<PagingData<AwardsRecord>>>()
    var certificationRecordData = MutableLiveData<Flow<PagingData<CertificationRecord>>>()
    var volunteerWorkRecordData = MutableLiveData<Flow<PagingData<VolunteerWorkRecord>>>()
    var rankRecordData = MutableLiveData<Flow<PagingData<RankRecord>>>()

    fun getLinearTapItemList(context: Context): List<LinearTapItem> {
        val list = mutableListOf<LinearTapItem>()
        list.add(LinearTapItem(context, LinearTapItemData("수상", "3회", "수상")))
        list.add(LinearTapItem(context, LinearTapItemData("자격증", "1개", "자격증")))
        list.add(LinearTapItem(context, LinearTapItemData("봉사활동", "22시간", "봉사활동")))
        list.add(LinearTapItem(context, LinearTapItemData("석차등급", "2등급", "석차등급")))

        return list
    }

    private fun dateTimeFormat(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    fun getAwardsRecord(award_name: String? = "", award_rank: String? = "", award_organization_name: String? = "", award_period: String? = ""): AwardsRecord {
        return AwardsRecord(
            id = null,
            award_name = award_name,
            award_rank = award_rank,
            award_organization_name = award_organization_name,
            award_period = award_period,
            created_at = dateTimeFormat(LocalDateTime.now())
        )
    }

    fun getCertificationRecord(certification_name: String? = "", certification_organization_name: String? = "", certification_period: String? = ""): CertificationRecord {
        return CertificationRecord(
            id = null,
            certification_name = certification_name,
            certification_organization_name = certification_organization_name,
            certification_period = certification_period,
            created_at = dateTimeFormat(LocalDateTime.now())
        )
    }

    fun getVolunteerWorkRecord(volunteer_work_name: String? = "", volunteer_work_organization_name: String? = "", volunteer_work_time: String? = "", volunteer_work_period: String? = "",): VolunteerWorkRecord {
        return VolunteerWorkRecord(
            id = null,
            volunteer_work_name = volunteer_work_name,
            volunteer_work_organization_name = volunteer_work_organization_name,
            volunteer_work_time = volunteer_work_time,
            volunteer_work_period = volunteer_work_period,
            created_at = dateTimeFormat(LocalDateTime.now())
        )
    }

    fun getRankRecord(rank_name: String? = "", rank_semester: String? = "", rank_period: String? = ""): RankRecord {
        return RankRecord(
            id = null,
            rank_name = rank_name,
            rank_semester = rank_semester,
            rank_period = rank_period,
            created_at = dateTimeFormat(LocalDateTime.now())
        )
    }

    fun getAwardsRecordByPage(){
        awardsRecordData.postValue(PagedRepository(RoomDataBase.getInstance(context)!!).getAwardsRecordPage().cachedIn(viewModelScope))
    }

    fun getCertificationRecordByPage() {
        certificationRecordData.postValue(PagedRepository(RoomDataBase.getInstance(context)!!).getCertificationRecordPage().cachedIn(viewModelScope))
    }

    fun getVolunteerWorkRecordByPage() {
        volunteerWorkRecordData.postValue(PagedRepository(RoomDataBase.getInstance(context)!!).getVolunteerWorkRecordPage().cachedIn(viewModelScope))
    }

    fun getRankRecordByPage() {
        rankRecordData.postValue(PagedRepository(RoomDataBase.getInstance(context)!!).getRankRecordPage().cachedIn(viewModelScope))
    }

    fun createAwardsRecord(awardsRecord: AwardsRecord) {
        RoomDataBase.getInstance(context)?.awardsRecordService()?.create(awardsRecord)
    }

    fun createCertificationRecord(certificationRecord: CertificationRecord) {
        RoomDataBase.getInstance(context)?.certificationRecordService()?.create(certificationRecord)
    }

    fun createVolunteerWorkRecord(volunteerWorkRecord: VolunteerWorkRecord) {
        RoomDataBase.getInstance(context)?.volunteerWorkRecordService()?.create(volunteerWorkRecord)
    }

    fun createRankRecord(rankRecord: RankRecord) {
        RoomDataBase.getInstance(context)?.rankRecordService()?.create(rankRecord)
    }
}

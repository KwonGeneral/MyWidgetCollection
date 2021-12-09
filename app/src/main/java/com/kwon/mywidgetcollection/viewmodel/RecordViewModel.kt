package com.kwon.mywidgetcollection.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kwon.mywidgetcollection.adapter.LifeRecordAdapter
import com.kwon.mywidgetcollection.contains.LifeRecordDefine
import com.kwon.mywidgetcollection.data.LinearTapItemData
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.pager.LifeRecordPagingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import navigation.LinearTapItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel(val context: Context): ViewModel() {
    var recordData = MutableLiveData<Flow<PagingData<LifeRecord>>>()
    var awardData = MutableLiveData<Flow<PagingData<LifeRecord>>>()
    var certificationData = MutableLiveData<Flow<PagingData<LifeRecord>>>()
    var volunteerData = MutableLiveData<Flow<PagingData<LifeRecord>>>()
    var gradeData = MutableLiveData<Flow<PagingData<LifeRecord>>>()

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: RecordViewModel? = null
        fun getInstance(context: Context): RecordViewModel {
            instance?.let {
                return it
            }
            instance = RecordViewModel(context)
            return instance!!
        }
    }

    fun getLinearTapItemList(context: Context): List<LinearTapItem> {
        val list = mutableListOf<LinearTapItem>()
        list.add(LinearTapItem(context, LinearTapItemData("수상", "3회", "수상")))
        list.add(LinearTapItem(context, LinearTapItemData("자격증", "1개", "자격증")))
        list.add(LinearTapItem(context, LinearTapItemData("봉사활동", "22시간", "봉사활동")))
        list.add(LinearTapItem(context, LinearTapItemData("석차등급", "2등급", "석차등급")))

        return list
    }

    fun getLifeRecordSearchPage(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String) {
        if(sort.uppercase() == "ASC") {
            awardData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
        } else {
            awardData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
        }

    }

    fun getLifeRecordSearch(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String): List<LifeRecord>? {
        return if(sort.uppercase() == "ASC") {
            RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(type, sub_type, from_date, to_date, count)
        } else {
            RoomDataBase.getInstance(context)?.lifeRecordService()?.searchDESC(type, sub_type, from_date, to_date, count)
        }
    }

    fun getLifeRecordByPage(type: String?) {
        when(type) {
            LifeRecordDefine.AWARD -> { awardData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getAwardsRecordPage().cachedIn(viewModelScope)) }
            LifeRecordDefine.CERTIFICATION -> { certificationData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getCertificationRecordPage().cachedIn(viewModelScope)) }
            LifeRecordDefine.VOLUNTEER -> { volunteerData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getVolunteerRecordPage().cachedIn(viewModelScope)) }
            LifeRecordDefine.GRADE -> { gradeData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getGradeRecordPage().cachedIn(viewModelScope)) }
            else -> {
                recordData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordPage().cachedIn(viewModelScope))
                awardData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getAwardsRecordPage().cachedIn(viewModelScope))
                certificationData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getCertificationRecordPage().cachedIn(viewModelScope))
                volunteerData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getVolunteerRecordPage().cachedIn(viewModelScope))
                gradeData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getGradeRecordPage().cachedIn(viewModelScope))
            }
        }
    }

    fun createLifeRecord(lifeRecord: LifeRecord) {
        Log.d("TEST", "생성완료! : $lifeRecord")
        RoomDataBase.getInstance(context)?.lifeRecordService()?.create(lifeRecord)
    }

    fun deleteLifeRecord(id: Long?) {
        id?.let {
            RoomDataBase.getInstance(context)?.lifeRecordService()?.delete(id)
        }
    }
}

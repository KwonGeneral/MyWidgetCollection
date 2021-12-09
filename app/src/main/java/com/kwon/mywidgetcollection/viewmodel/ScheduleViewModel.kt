package com.kwon.mywidgetcollection.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.pager.ScheduleRecordPagingRepository
import kotlinx.coroutines.flow.Flow

class ScheduleViewModel(val context: Context): ViewModel() {
    var scheduleData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()
    var todayData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 일정 : today
    var todoData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 할일 : todo

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: ScheduleViewModel? = null
        fun getInstance(context: Context): ScheduleViewModel {
            instance?.let {
                return it
            }
            instance = ScheduleViewModel(context)
            return instance!!
        }
    }

    fun getScheduleRecordSearchPage(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String) {
        if(sort.uppercase() == "ASC") {
            when(type) {
                ScheduleDefine.TODAY -> {
                    todayData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
                }
                ScheduleDefine.TODO -> {
                    todoData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
                }
            }
        } else {
            when(type) {
                ScheduleDefine.TODAY -> {
                    todayData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
                }
                ScheduleDefine.TODO -> {
                    todoData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
                }
            }
        }
    }

    fun getScheduleRecordSearch(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String): List<ScheduleRecord>? {
        return if(sort.uppercase() == "ASC") {
            RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchASC(type, sub_type, from_date, to_date, count)
        } else {
            RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(type, sub_type, from_date, to_date, count)
        }
    }

    fun getScheduleRecordByPage(type: String? = "") {
        when(type) {
            ScheduleDefine.TODAY -> { todayData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getTodayRecordPage().cachedIn(viewModelScope)) }
            ScheduleDefine.TODO -> { todoData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getTodoRecordPage().cachedIn(viewModelScope)) }
            else -> {
                scheduleData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordPage().cachedIn(viewModelScope))
                todayData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getTodayRecordPage().cachedIn(viewModelScope))
                todoData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getTodoRecordPage().cachedIn(viewModelScope))
            }
        }
    }

    fun createScheduleRecord(scheduleRecord: ScheduleRecord) {
        Log.d("TEST", "생성완료! : $scheduleRecord")
        RoomDataBase.getInstance(context)?.scheduleRecordService()?.create(scheduleRecord)
    }

    fun deleteScheduleRecord(id: Long?) {
        id?.let {
            RoomDataBase.getInstance(context)?.scheduleRecordService()?.delete(id)
        }
    }
}

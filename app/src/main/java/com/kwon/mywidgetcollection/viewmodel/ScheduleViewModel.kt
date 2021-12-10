package com.kwon.mywidgetcollection.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.Define
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.pager.SchedulePaging
import com.kwon.mywidgetcollection.pager.SchedulePagingRepository
import kotlinx.coroutines.flow.Flow

class ScheduleViewModel(val context: Context): ViewModel() {
    // PagingData
    var todayPagingData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 일정
    var todoPagingData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 할일

    // NormalData
    var todayData = MutableLiveData<List<ScheduleRecord>>()  // 일정
    var todoData = MutableLiveData<List<ScheduleRecord>>()  // 할일

    /**
     * 스케쥴 DB Record 생성
     */
    fun createScheduleRecord(scheduleRecord: ScheduleRecord) {
        Log.d("TEST", "생성완료! : $scheduleRecord")
        RoomDataBase.getInstance(context)?.scheduleRecordService()?.create(scheduleRecord)
    }

    /**
     * 스케쥴 DB Record 삭제
     */
    fun deleteScheduleRecord(id: Long?) {
        id?.let {
            RoomDataBase.getInstance(context)?.scheduleRecordService()?.delete(id)
        }
    }

    // Todo =================================  Room Query  =================================
    // Todo ================================= 일정 ( Today ) =================================
    /**
     * 일정 조건 검색 페이징
     */
    private fun setTodayPaging(count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result())
    }
    private fun setTodayPaging(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result())
    }
    private fun setTodayPaging(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count).result())
    }
    private fun setTodayPaging(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count).result())
    }

    /**
     * 일정 조건 검색 페이징 역정렬
     */
    private fun setTodayPagingDESC(count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result(true))
    }
    private fun setTodayPagingDESC(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result(true))
    }
    private fun setTodayPagingDESC(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count).result(true))
    }
    private fun setTodayPagingDESC(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count).result(true))
    }

    /**
     * 일정 조건 검색
     */
    private fun setTodayData(count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodayData(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodayData(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count))
    }
    private fun setTodayData(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count))
    }

    /**
     * 일정 조건 검색 역정렬
     */
    private fun setTodayDataDESC(count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodayDataDESC(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodayDataDESC(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count))
    }
    private fun setTodayDataDESC(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count))
    }

    // Todo ================================= 할일 ( Todo ) =================================
    /**
     * 할일 조건 검색 페이징
     */
    private fun setTodoPaging(count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result())
    }
    private fun setTodoPaging(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result())
    }
    private fun setTodoPaging(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count).result())
    }
    private fun setTodoPaging(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count).result())
    }

    /**
     * 할일 검색 페이징 역정렬
     */
    private fun setTodoPagingDESC(count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result(true))
    }
    private fun setTodoPagingDESC(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count).result(true))
    }
    private fun setTodoPagingDESC(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count).result(true))
    }
    private fun setTodoPagingDESC(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoPagingData.postValue(SchedulePaging.Search(context, ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count).result(true))
    }

    /**
     * 할일 조건 검색
     */
    private fun setTodoData(count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodoData(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodoData(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count))
    }
    private fun setTodoData(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.search(ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count))
    }

    /**
     * 할일 조건 검색 역정렬
     */
    private fun setTodoDataDESC(count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, Default.SUB_TYPE, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodoDataDESC(sub_type: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, sub_type, Default.FROM_DATE, Default.TO_DATE, Default.SORT_NAME, count=count))
    }
    private fun setTodoDataDESC(sub_type: String, from_date: Long, to_date: Long, count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, sub_type, from_date, to_date, Default.SORT_NAME, count=count))
    }
    private fun setTodoDataDESC(sub_type: String, from_date: Long, to_date: Long, sort_name: String, count: Int = Default.MAX_PAGE_COUNT) {
        todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, sub_type, from_date, to_date, sort_name, count=count))
    }
}

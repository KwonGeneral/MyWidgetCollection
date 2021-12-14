package com.kwon.mywidgetcollection.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.contains.SharedDefine
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.db.SharedDataBase
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class ScheduleViewModel(val context: Context, var option: SchSearchOption = SchSearchOption()): ViewModel() {
    // PagingData
    var todayPagingData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 일정
    var todoPagingData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 할일

    // NormalData
    var todayData = MutableLiveData<List<ScheduleRecord>>()  // 일정
    var todoData = MutableLiveData<List<ScheduleRecord>>()  // 할일

    init {
        //초기화
        option.matchTime(getDate())
    }

    data class SchSearchOption(
        var sub_type: String = Default.SUB_TYPE,
        var from_date: Long = Default.FROM_DATE,
        var to_date: Long = Default.TO_DATE,
        var sort_name: String = Default.SORT_NAME,
        var count: Int = Default.PAGE_COUNT,
        var sortDESC: Boolean = false
    ) {
        fun matchTime(date: String) {
            Calendar.getInstance()?.let { cal ->
                cal.time = SimpleDateFormat(Default.DATE_FORMAT, Locale.KOREAN).parse(date)!!
                cal.set(Calendar.HOUR, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                from_date = cal.timeInMillis
                cal.set(Calendar.HOUR, 23)
                cal.set(Calendar.MINUTE, 59)
                cal.set(Calendar.SECOND, 59)
                to_date = cal.timeInMillis
            }
        }
    }

    /**
     * Shared Db : Date 가져오기
     */
    private fun getDate(): String {
        return SharedDataBase.getInstance(context).getCalendarDate()
    }

    /**
     * Shared Db : Date 저장하기
     */
    fun setDate(date: String) {
        SharedDataBase.getInstance(context).setCalendarDate(date)
        option.matchTime(getDate())
        update()
    }

    fun setDESC(desc: Boolean) {
        option.apply {
            sortDESC = desc
        }
        update()
    }

    fun insertScheduleRecord(scheduleRecord: ScheduleRecord) {
        RoomDataBase.getInstance(context)?.scheduleRecordService()?.create(scheduleRecord)
        Log.d("TEST", "생성완료 : $scheduleRecord")
    }

    fun deleteScheduleRecord(scheduleRecord: ScheduleRecord) {
        RoomDataBase.getInstance(context)?.scheduleRecordService()?.delete(scheduleRecord.id!!)
        Log.d("TEST", "생성완료 : $scheduleRecord")
    }


    fun updateToday(t_option: SchSearchOption = option) {
        t_option.run {
            updatePaging(this, ScheduleDefine.TODAY)?.let {
                todayPagingData.postValue(it)
            }
            updateList(this, ScheduleDefine.TODAY)?.let {
                todayData.postValue(it)
            }
        }
    }

    fun updateTodo(t_option: SchSearchOption = option) {
        t_option.run {
            updateList(this, ScheduleDefine.TODO)?.let {
                todoData.postValue(it)
            }
            updatePaging(this, ScheduleDefine.TODO)?.let {
                todoPagingData.postValue(it)
            }
        }
    }

    fun update(t_option: SchSearchOption = option) {
        t_option.run {
            updateToday(this)
            updateTodo(this)
        }
    }

    private fun updateList(option: SchSearchOption, type: String): List<ScheduleRecord>? {
        option.run {
            RoomDataBase.getInstance(context)?.scheduleRecordService()
                ?.let { scheduleRecordService ->
                    if(sortDESC) {
                        return scheduleRecordService.searchDESC(
                            type,
                            sub_type,
                            from_date,
                            to_date,
                            sort_name,
                            count
                        )
                    }
                    return scheduleRecordService.search(
                        type,
                        sub_type,
                        from_date,
                        to_date,
                        sort_name,
                        count
                    )
                }
        }
        return null
    }

    private fun updatePaging(
        option: SchSearchOption,
        type: String
    ): Flow<PagingData<ScheduleRecord>>? {
        option.run {
            RoomDataBase.getInstance(context)?.scheduleRecordService()
                ?.let { scheduleRecordService ->
                    PagingConfig(pageSize = count, enablePlaceholders = true)?.let { pagingConfig ->
                        var pagingSourceFactory = {
                            scheduleRecordService.searchByPage(
                                type,
                                sub_type,
                                from_date,
                                to_date,
                                sort_name,
                                count
                            )
                        }
                        if (sortDESC) {
                            pagingSourceFactory = {
                                scheduleRecordService.searchByPageDESC(
                                    type,
                                    sub_type,
                                    from_date,
                                    to_date,
                                    sort_name,
                                    count
                                )
                            }
                        }

                        return Pager(
                            config = pagingConfig,
                            pagingSourceFactory = pagingSourceFactory
                        ).flow
                    }
                }
            return null
        }
    }
}

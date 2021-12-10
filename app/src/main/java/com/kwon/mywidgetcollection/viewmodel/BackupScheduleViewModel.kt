//package com.kwon.mywidgetcollection.viewmodel
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.paging.PagingData
//import com.kwon.mywidgetcollection.contains.ScheduleDefine
//import com.kwon.mywidgetcollection.entity.*
//import com.kwon.mywidgetcollection.db.RoomDataBase
//import com.kwon.mywidgetcollection.pager.SchedulePaging
//import com.kwon.mywidgetcollection.pager.SchedulePagingRepository
//import kotlinx.coroutines.flow.Flow
//
//class BackupScheduleViewModel(val context: Context): ViewModel() {
//    // PagingData
//    var todayPagingData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 일정
//    var todoPagingData = MutableLiveData<Flow<PagingData<ScheduleRecord>>>()  // 할일
//
//    // NormalData
//    var todayData = MutableLiveData<List<ScheduleRecord>>()  // 일정
//    var todoData = MutableLiveData<List<ScheduleRecord>>()  // 할일
//
//    /**
//     * 스케쥴 DB Paging3 검색 정렬 & 역정렬 조회
//     *
//     * < parameter >
//     * type : 종류
//     * sub_type : 서브 종류
//     * from_date : 시작 일자
//     * to_date : 종료 일자
//     * count : 검색 개수
//     * sort : 정렬 방식 ( ASC & DESC )
//     */
////    fun getScheduleRecordSearchPage(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String) {
////        if(sort.uppercase() == "ASC") {
////            when(type) {
////                ScheduleDefine.TODAY -> {
////                    todayPagingData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
////                }
////                ScheduleDefine.TODO -> {
////                    todoPagingData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
////                }
////            }
////        } else {
////            when(type) {
////                ScheduleDefine.TODAY -> {
////                    todayPagingData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
////                }
////                ScheduleDefine.TODO -> {
////                    todoPagingData.postValue(ScheduleRecordPagingRepository(RoomDataBase.getInstance(context)!!).getScheduleRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
////                }
////            }
////        }
////    }
//
//    fun getScheduleRecordSearchPage(type: String) {
//        todayPagingData.postValue(SchedulePaging(context)?.getSearchPage(type))
//    }
//
//    /**
//     * 스케쥴 DB 검색 정렬 & 역정렬 조회
//     *
//     * < parameter >
//     * type : 종류
//     * sub_type : 서브 종류
//     * from_date : 시작 일자
//     * to_date : 종료 일자
//     * count : 검색 개수
//     * sort : 정렬 방식 ( ASC & DESC )
//     */
//    fun getScheduleRecordSearch(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String){
//        if(sort.uppercase() == "ASC") {
//            when(type) {
//                ScheduleDefine.TODAY -> {
//                    todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchASC(type, sub_type, from_date, to_date, count))
//                }
//                ScheduleDefine.TODO -> {
//                    todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchASC(type, sub_type, from_date, to_date, count))
//                }
//                else -> {
//                    todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchASC(ScheduleDefine.TODAY, sub_type, from_date, to_date, count))
//                    todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchASC(ScheduleDefine.TODO, sub_type, from_date, to_date, count))
//                }
//            }
//        } else {
//            when(type) {
//                ScheduleDefine.TODAY -> {
//                    todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(type, sub_type, from_date, to_date, count))
//                }
//                ScheduleDefine.TODO -> {
//                    todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(type, sub_type, from_date, to_date, count))
//                }
//                else -> {
//                    todayData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODAY, sub_type, from_date, to_date, count))
//                    todoData.postValue(RoomDataBase.getInstance(context)?.scheduleRecordService()?.searchDESC(ScheduleDefine.TODO, sub_type, from_date, to_date, count))
//                }
//            }
//        }
//    }
//
//    /**
//     * 스케쥴 DB Record 생성
//     */
//    fun createScheduleRecord(scheduleRecord: ScheduleRecord) {
//        Log.d("TEST", "생성완료! : $scheduleRecord")
//        RoomDataBase.getInstance(context)?.scheduleRecordService()?.create(scheduleRecord)
//    }
//
//    /**
//     * 스케쥴 DB Record 삭제
//     */
//    fun deleteScheduleRecord(id: Long?) {
//        id?.let {
//            RoomDataBase.getInstance(context)?.scheduleRecordService()?.delete(id)
//        }
//    }
//}

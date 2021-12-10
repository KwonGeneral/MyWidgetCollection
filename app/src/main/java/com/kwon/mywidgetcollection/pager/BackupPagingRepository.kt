//package com.kwon.mywidgetcollection.pager
//
//import android.util.Log
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import com.kwon.mywidgetcollection.contains.Define
//import com.kwon.mywidgetcollection.contains.LifeRecordDefine
//import com.kwon.mywidgetcollection.contains.ScheduleDefine
//import com.kwon.mywidgetcollection.db.RoomDataBase
//import com.kwon.mywidgetcollection.entity.LifeRecord
//import com.kwon.mywidgetcollection.entity.ScheduleRecord
//import kotlinx.coroutines.flow.Flow
//import java.lang.NullPointerException
//
//class BackupPagingRepository(private val database: RoomDataBase) {
//
//    // TODO ===================================== 생활 기록부 =====================================
//    /**
//     * 생활기록부 DB 정렬 검색 조회
//     */
//    fun getLifeRecordSearch(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort: String): Flow<PagingData<LifeRecord>> {
//        when(sort.uppercase()) {
//            Define.SORT_ASC -> {
//                val pageSourceFactory = {
//                    database.lifeRecordService().searchByPageASC(type, sub_type, from_date, to_date, count)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//            Define.SORT_DESC -> {
//                val pageSourceFactory = {
//                    database.lifeRecordService().searchByPageDESC(type, sub_type, from_date, to_date, count)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//            else -> {
//                val pageSourceFactory = {
//                    database.lifeRecordService().searchByPageASC(type, sub_type, from_date, to_date, count)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//        }
//    }
//
//    /**
//     * 생활기록부 DB 조회
//     */
//    fun getLifeRecord(type: String, sort: String): Flow<PagingData<LifeRecord>> {
//        when(sort.uppercase()) {
//            Define.SORT_ASC -> {
//                when(type) {
//                    LifeRecordDefine.AWARD -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByTypeASC(LifeRecordDefine.AWARD)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    LifeRecordDefine.CERTIFICATION -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByTypeASC(LifeRecordDefine.CERTIFICATION)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    LifeRecordDefine.VOLUNTEER -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByTypeASC(LifeRecordDefine.VOLUNTEER)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    LifeRecordDefine.GRADE -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByTypeASC(LifeRecordDefine.GRADE)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    else -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().readByPage()
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                }
//            }
//
//            Define.SORT_DESC -> {
//                when(type) {
//                    LifeRecordDefine.AWARD -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByPageDESC(LifeRecordDefine.AWARD)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    LifeRecordDefine.CERTIFICATION -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByPageDESC(LifeRecordDefine.CERTIFICATION)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    LifeRecordDefine.VOLUNTEER -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByPageDESC(LifeRecordDefine.VOLUNTEER)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    LifeRecordDefine.GRADE -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().searchByPageDESC(LifeRecordDefine.GRADE)
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                    else -> {
//                        val pageSourceFactory = {
//                            database.lifeRecordService().readByPage()
//                        }
//                        return Pager(
//                            config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                            pagingSourceFactory = pageSourceFactory
//                        ).flow
//                    }
//                }
//            }
//            else -> {
//                throw NullPointerException("정렬 값을 넣어주세요.")
//            }
//        }
//    }
//
//    // TODO ===================================== 스케쥴 =====================================
//    /**
//     * 스케쥴 DB 정렬 검색 조회
//     */
//    fun getScheduleSearch(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort: String): Flow<PagingData<ScheduleRecord>> {
//        when(sort.uppercase()) {
//            Define.SORT_ASC -> {
//                val pageSourceFactory = {
//                    database.scheduleRecordService().searchByPageASC(type, sub_type, from_date, to_date, count)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//            Define.SORT_DESC -> {
//                val pageSourceFactory = {
//                    database.scheduleRecordService().searchByPageDESC(type, sub_type, from_date, to_date, count)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//            else -> {
//                val pageSourceFactory = {
//                    database.scheduleRecordService().searchByPageASC(type, sub_type, from_date, to_date, count)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//        }
//    }
//
//    /**
//     * 스케쥴 DB 조회
//     */
//    fun getSchedule(type: String): Flow<PagingData<ScheduleRecord>> {
//        when(type) {
//            ScheduleDefine.TODAY -> {
//                val pageSourceFactory = {
//                    database.scheduleRecordService().searchByType(ScheduleDefine.TODAY)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//            ScheduleDefine.TODO -> {
//                val pageSourceFactory = {
//                    database.scheduleRecordService().searchByType(ScheduleDefine.TODO)
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//            else -> {
//                val pageSourceFactory = {
//                    database.scheduleRecordService().readByPage()
//                }
//                return Pager(
//                    config = PagingConfig(pageSize = 100, enablePlaceholders = true),
//                    pagingSourceFactory = pageSourceFactory
//                ).flow
//            }
//        }
//    }
//}
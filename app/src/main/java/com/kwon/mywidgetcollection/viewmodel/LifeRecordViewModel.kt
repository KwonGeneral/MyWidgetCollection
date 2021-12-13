package com.kwon.mywidgetcollection.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.LifeDefine
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.utils.DateUtil
import kotlinx.coroutines.flow.Flow
import java.util.*

class LifeRecordViewModel(val context: Context, var option: LifeSearchOption = LifeSearchOption()) : ViewModel() {
    // PagingData
    var awardPagingData = MutableLiveData<Flow<PagingData<LifeRecord>>>()  // 수상
    var certificationPagingData = MutableLiveData<Flow<PagingData<LifeRecord>>>()  // 자격증
    var volunteerPagingData = MutableLiveData<Flow<PagingData<LifeRecord>>>()  // 봉사활동
    var gradePagingData = MutableLiveData<Flow<PagingData<LifeRecord>>>()  // 석차등급

    // NormalData
    var awardData = MutableLiveData<List<LifeRecord>>()  // 수상
    var certificationData = MutableLiveData<List<LifeRecord>>()  // 자격증
    var volunteerData = MutableLiveData<List<LifeRecord>>()  // 봉사활동
    var gradeData = MutableLiveData<List<LifeRecord>>()  // 석차등급

    data class LifeSearchOption(
        var sub_type: String = Default.SUB_TYPE,
        var title: String = Default.EMPTY_STR,
        var content: String = Default.EMPTY_STR,
        var area: String = Default.EMPTY_STR,
        var rank: String = Default.EMPTY_STR,
        var semester: String = Default.EMPTY_STR,
        var sort_name: String = Default.SORT_NAME,
        var count: Int = Default.PAGE_COUNT,
        var time_at: Long = Default.EMPTY_LONG,
        var from_date: Long = Default.FROM_DATE,
        var to_date: Long = Default.TO_DATE,
        var sortDESC: Boolean = false
    )

    fun insertLifeRecord(lifeRecord: LifeRecord) {
        RoomDataBase.getInstance(context)?.lifeRecordService()?.create(lifeRecord)
        Log.d("TEST", "생성완료 : $lifeRecord")
    }

    fun deleteLifeRecord(lifeRecord: LifeRecord) {
        RoomDataBase.getInstance(context)?.lifeRecordService()?.delete(lifeRecord.id!!)
        Log.d("TEST", "생성완료 : $lifeRecord")
    }

    fun updateAward(t_option: LifeSearchOption = option) {
        t_option.run {
            updatePaging(this, LifeDefine.AWARD)?.let {
                awardPagingData.postValue(it)
            }
            updateList(this, LifeDefine.AWARD)?.let {
                awardData.postValue(it)
            }
        }
    }

    fun updateCertification(t_option: LifeSearchOption = option) {
        t_option.run {
            updatePaging(this, LifeDefine.CERTIFICATION)?.let {
                certificationPagingData.postValue(it)
            }
            updateList(this, LifeDefine.CERTIFICATION)?.let {
                certificationData.postValue(it)
            }
        }
    }

    fun updateVolunteer(t_option: LifeSearchOption = option) {
        t_option.run {
            updatePaging(this, LifeDefine.VOLUNTEER)?.let {
                volunteerPagingData.postValue(it)
            }
            updateList(this, LifeDefine.VOLUNTEER)?.let {
                volunteerData.postValue(it)
            }
        }
    }

    fun updateGrade(t_option: LifeSearchOption = option) {
        t_option.run {
            updatePaging(this, LifeDefine.GRADE)?.let {
                gradePagingData.postValue(it)
            }
            updateList(this, LifeDefine.GRADE)?.let {
                gradeData.postValue(it)
            }
        }
    }

    fun update(t_option: LifeSearchOption = option) {
        t_option.run {
            updateAward(this)
            updateCertification(this)
            updateVolunteer(this)
            updateGrade(this)
        }
    }

    private fun updateList(option: LifeSearchOption, type: String): List<LifeRecord>? {
        option.run {
            RoomDataBase.getInstance(context)?.lifeRecordService()
                ?.let { lifeRecordService ->
                    if (!sortDESC) {
                        return lifeRecordService.search(
                            type,
                            sub_type = sub_type,
                            from_date = from_date,
                            to_date = to_date,
                            sort_name = sort_name,
                            count = count
                        )
                    } else {
                        return lifeRecordService.searchDESC(
                            type,
                            sub_type = sub_type,
                            from_date = from_date,
                            to_date = to_date,
                            sort_name = sort_name,
                            count = count
                        )
                    }
                }
        }
        return null
    }

    private fun updatePaging(
        option: LifeSearchOption,
        type: String
    ): Flow<PagingData<LifeRecord>>? {
        option.run {
            RoomDataBase.getInstance(context)?.lifeRecordService()
                ?.let { lifeRecordService ->
                    PagingConfig(pageSize = count, enablePlaceholders = true)?.let { pagingConfig ->
                        var pagingSourceFactory = {
                            lifeRecordService.searchByPage(
                                type,
                                sub_type = sub_type,
                                from_date = from_date,
                                to_date = to_date,
                                sort_name = sort_name,
                                count = count
                            )
                        }
                        if (sortDESC) {
                            pagingSourceFactory = {
                                lifeRecordService.searchByPageDESC(
                                    type,
                                    sub_type = sub_type,
                                    from_date = from_date,
                                    to_date = to_date,
                                    sort_name = sort_name,
                                    count = count
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

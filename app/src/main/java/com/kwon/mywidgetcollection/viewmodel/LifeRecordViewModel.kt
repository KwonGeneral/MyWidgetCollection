package com.kwon.mywidgetcollection.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kwon.mywidgetcollection.contains.Define
import com.kwon.mywidgetcollection.contains.LifeRecordDefine
import com.kwon.mywidgetcollection.entity.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import kotlinx.coroutines.flow.Flow

class LifeRecordViewModel(val context: Context) : ViewModel() {
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

    private var currentFocus = MutableLiveData<Int>(0)
    private var sort: String = ""
    private var type: String = ""
    private var subType: String = ""
    private var fromDate: Long = 0
    private var toDate: Long = 0
    private var count: Int = 0

    init {
        currentFocus.value = 0
    }

    // 쉐어드 필요
    private fun getCurrentFocus(): Int {
        currentFocus.value?.let {
            return it
        }
        return 0
    }

    // 생활기록부에서 사용자의 액션은
    // 현재 페이지를 전환하는 액션 뿐이다.
    // 뷰에서는 현재 페이지가 바뀔때마다 한번의 호출로
    // 다양한 작업을 진행한다.
    fun setCurrentFocus(currentFocus: Int) {
        this.currentFocus.value = currentFocus
        when(currentFocus) {
//            0 -> {
//                setType(LifeRecordDefine.AWARD)
//            }
//            1 -> {
//                setType(LifeRecordDefine.CERTIFICATION)
//            }
//            2 -> {
//                setType(LifeRecordDefine.VOLUNTEER)
//            }
//            3 -> {
//                setType(LifeRecordDefine.GRADE)
//            }
        }
    }

    fun getAllRecordList() {
//        setAwardPagingData()
//        setCertificationPagingData()
//        setVolunteerPagingData()
//        setGradePagingData()
    }

    private fun getSort(): String {
        return sort
    }

    private fun setSort(sort: String) {
        this.sort = sort
        when(getType()) {
            LifeRecordDefine.AWARD -> {

            }
            LifeRecordDefine.CERTIFICATION -> {

            }
            LifeRecordDefine.VOLUNTEER -> {

            }
            LifeRecordDefine.GRADE -> {

            }
        }
    }

    private fun getType(): String {
        return type
    }

//    private fun setType(type: String) {
//        this.type = type
//        when(type) {
//            LifeRecordDefine.AWARD -> { setAwardPagingData() }
//            LifeRecordDefine.CERTIFICATION -> { setCertificationPagingData() }
//            LifeRecordDefine.VOLUNTEER -> { setVolunteerPagingData() }
//            LifeRecordDefine.GRADE -> { setGradePagingData() }
//        }
//    }

    private fun getSubType(): String {
        return subType
    }

    private fun setSubType(subType: String) {
        this.subType = subType
    }

    private fun getFromDate(): Long {
        return fromDate
    }

    private fun setFromDate(fromDate: Long) {
        this.fromDate = fromDate
    }

    private fun getToDate(): Long {
        return toDate
    }

    private fun setToDate(toDate: Long) {
        this.toDate = toDate
    }

    private fun getCount(): Int {
        return count
    }

    private fun setCount(count: Int) {
        this.count = count
    }

//    private fun setAwardPagingData() {
//        awardPagingData.postValue(PagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecord(LifeRecordDefine.AWARD, Define.SORT_ASC).cachedIn(viewModelScope))
//    }
//
//    private fun setAwardPagingData(sort: String) {
//
//    }
//
//    private fun setCertificationPagingData() {
//        certificationPagingData.postValue(PagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecord(LifeRecordDefine.CERTIFICATION, Define.SORT_ASC).cachedIn(viewModelScope))
//    }
//
//    private fun setVolunteerPagingData() {
//        volunteerPagingData.postValue(PagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecord(LifeRecordDefine.VOLUNTEER, Define.SORT_ASC).cachedIn(viewModelScope))
//    }
//
//    private fun setGradePagingData() {
//        gradePagingData.postValue(PagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecord(LifeRecordDefine.GRADE, Define.SORT_ASC).cachedIn(viewModelScope))
//    }

    // Todo ===================================================================================

//    private var currentMenu = MutableLiveData<Int>()
//    init {
//        currentMenu.value = //쉐어드로드
//    }

//    fun getCurrentMenuLive():MutableLiveData<Int>
//    {
//        return currentMenu
//    }
//
//    fun setCurrentMenu(menu: Int) {
//        currentMenu.value = menu
//        //쉐어드 저장
//    }

//    /**
//     * 생활기록부 DB Paging3 검색 정렬 & 역정렬 조회
//     *
//     * < parameter >
//     * type : 종류
//     * sub_type : 서브 종류
//     * from_date : 시작 일자
//     * to_date : 종료 일자
//     * count : 검색 개수
//     * sort : 정렬 방식 ( ASC & DESC )
//     */
//    fun getSearchPage(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String) {
//        if(sort.uppercase() == "ASC") {
//            when(type) {
//                LifeRecordDefine.AWARD -> {
//                    awardPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                LifeRecordDefine.CERTIFICATION -> {
//                    certificationPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                LifeRecordDefine.VOLUNTEER -> {
//                    volunteerPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                LifeRecordDefine.GRADE -> {
//                    gradePagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                else -> {
//                    awardPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(LifeRecordDefine.AWARD, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                    certificationPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(LifeRecordDefine.CERTIFICATION, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                    volunteerPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(LifeRecordDefine.VOLUNTEER, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                    gradePagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageASC(LifeRecordDefine.GRADE, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//            }
//        } else {
//            when(type) {
//                LifeRecordDefine.AWARD -> {
//                    awardPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                LifeRecordDefine.CERTIFICATION -> {
//                    certificationPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                LifeRecordDefine.VOLUNTEER -> {
//                    volunteerPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                LifeRecordDefine.GRADE -> {
//                    gradePagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(type, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//                else -> {
//                    awardPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(LifeRecordDefine.AWARD, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                    certificationPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(LifeRecordDefine.CERTIFICATION, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                    volunteerPagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(LifeRecordDefine.VOLUNTEER, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                    gradePagingData.postValue(LifeRecordPagingRepository(RoomDataBase.getInstance(context)!!).getLifeRecordSearchPageDESC(LifeRecordDefine.GRADE, sub_type, from_date, to_date, count).cachedIn(viewModelScope))
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 생활기록부 DB 검색 정렬 & 역정렬 조회
//     *
//     * < parameter >
//     * type : 종류
//     * sub_type : 서브 종류
//     * from_date : 시작 일자
//     * to_date : 종료 일자
//     * count : 검색 개수
//     * sort : 정렬 방식 ( ASC & DESC )
//     */
//    fun getLifeRecordSearch(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int, sort:String) {
//        if(sort.uppercase() == "ASC") {
//            when(type) {
//                LifeRecordDefine.AWARD -> {
//                    awardData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(type, sub_type, from_date, to_date, count))
//                }
//                LifeRecordDefine.CERTIFICATION -> {
//                    certificationData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(type, sub_type, from_date, to_date, count))
//                }
//                LifeRecordDefine.VOLUNTEER -> {
//                    volunteerData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(type, sub_type, from_date, to_date, count))
//                }
//                LifeRecordDefine.GRADE -> {
//                    gradeData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(type, sub_type, from_date, to_date, count))
//                }
//                else -> {
//                    awardData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(LifeRecordDefine.AWARD, sub_type, from_date, to_date, count))
//                    certificationData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(LifeRecordDefine.CERTIFICATION, sub_type, from_date, to_date, count))
//                    volunteerData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(LifeRecordDefine.VOLUNTEER, sub_type, from_date, to_date, count))
//                    gradeData.postValue(RoomDataBase.getInstance(context)?.lifeRecordService()?.searchASC(LifeRecordDefine.GRADE, sub_type, from_date, to_date, count))
//                }
//            }
//        }
//    }

//    /**
//     * 생활기록부 DB Record 생성
//     */
//    fun createLifeRecord(lifeRecord: LifeRecord) {
//        Log.d("TEST", "생성완료! : $lifeRecord")
//        RoomDataBase.getInstance(context)?.lifeRecordService()?.create(lifeRecord)
//    }
//
//    /**
//     * 생활기록부 DB Record 삭제
//     */
//    fun deleteLifeRecord(id: Long?) {
//        id?.let {
//            RoomDataBase.getInstance(context)?.lifeRecordService()?.delete(id)
//        }
//    }
}

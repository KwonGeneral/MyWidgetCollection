package com.kwon.mywidgetcollection.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Room
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.TimerStatus
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.entity.MockExamRecord
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import kotlinx.coroutines.flow.Flow
import java.lang.IndexOutOfBoundsException
import java.sql.Time
import java.util.*
import kotlin.math.floor

class MockExamViewModel(val context: Context) {
    /**
     * examData: DB 레코드 데이터
     * tempMockExamRecord: 임시 레코드 데이터
     * timerData: 타이머 데이터
     * sortDESC: examData 정렬 방식 ( false : 정순 / true : 역순 )
     */
    var examData = MutableLiveData<List<MockExamRecord>>()
    var tempMockExamRecord = MutableLiveData<MockExamRecord>()
    var timerData = MutableLiveData<TimerData>()
    var detail_data = MutableLiveData<MockExamRecord>()
    private var sortDESC: Boolean = false

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: MockExamViewModel? = null
        fun getInstance(context: Context): MockExamViewModel {
            instance?.let {
                return it
            }
            instance = MockExamViewModel(context)
            return instance!!
        }

        fun releaseInstace() {
            instance = null
        }
    }

    init {
        update()
        timerDataCheck()
    }

    /**
     * DB 조회 후 examData 업데이트
     * count: 읽어올 데이터 갯수
     */
    fun update(count: Int = Default.PAGE_COUNT) {
        getRecordList(count)?.let {
            examData.postValue(it)
        }
    }

    /**
     * DB 레코드 수정
     * data: subjects 수정
     */
    fun modify(record: MockExamRecord) {
        modifyMockExamRecord(record)
    }

    fun modify(record: MockExamRecord, data: MockExamRecord.MockExamData) {
        record?.let {
            it.modify(data)
        }
        modifyMockExamRecord(record)
        update()
    }

    /**
     * DB 레코드 삭제
     */
    fun delete(record: MockExamRecord) {
        record?.id?.let {
            deleteMockExamRecord(it)
            update()
        }
    }

    /**
     * 정렬
     */
    private fun getDESC(): Boolean {
        return sortDESC
    }

    private fun setDESC(desc: Boolean) {
        sortDESC = desc
        update()
    }

    /**
     * DB Insert & Modify & Delete & Read
     */
    private fun insertMockExamRecord(mockExamRecord: MockExamRecord) {
        RoomDataBase.getInstance(context)?.mockExamRecordService()?.create(mockExamRecord)
    }

    private fun modifyMockExamRecord(mockExamRecord: MockExamRecord) {
        RoomDataBase.getInstance(context)?.mockExamRecordService()?.update(mockExamRecord)
    }

    private fun deleteMockExamRecord(id: Long) {
        RoomDataBase.getInstance(context)?.mockExamRecordService()?.delete(id)
    }

    private fun getRecordList(count: Int = Default.PAGE_COUNT): List<MockExamRecord>? {
        RoomDataBase.getInstance(context)?.mockExamRecordService()?.let { mockExamRecordService ->
            if (sortDESC) return mockExamRecordService.readAllDESC(count)
            return mockExamRecordService.readAll(count)
        }
        return null
    }

    // Todo =====================================================================================
    /**
     * 데이터 체크
     */
    private fun tempDataCheck(): MutableLiveData<MockExamRecord>? {
        if (tempMockExamRecord.value == null) return null
        return tempMockExamRecord
    }
    private fun timerDataCheck(): MutableLiveData<TimerData>? {
        if (timerData.value == null) {
            tempDataCheck()?.value?.let { record ->
                TimerData(TimerStatus.STOP, Default.EMPTY_STR, record)?.let {
                    timerData.value = it
                    it.remain_time = it.getCurrentSubject()?.range!!
                }
            }
        }
        timerData.value?.let { timer ->
            if (timer.timerStatus == TimerStatus.START || timer.timerStatus == TimerStatus.END) return null
            return timerData
        }
        return null
    }

    fun timerRefresh() {
        timerStart()
        timerPause()
    }

    /**
     * 임시 데이터 - 타이틀 설정
     */
    fun setMockTitle(title: String) {
        if (tempMockExamRecord.value == null) {
            tempMockExamRecord.value = MockExamRecord()
        }
        tempMockExamRecord.value?.let {
            it.title = title
            tempMockExamRecord.postValue(it)
        }
    }

    /**
     * 임시 데이터 - Subjects Get & Add & Remove & Modify
     */
    fun getMockExam(): String? {
        tempDataCheck()?.value?.let {
            return it.subjects
        }
        return null
    }

    fun addMockExam(name: String, range: Long): Boolean {
        tempDataCheck()?.value?.let {
            it.add(MockExamRecord.MockExamData(name, range, 0))
            tempMockExamRecord.value = it
            timerDataCheck()
            return true
        }
        return false
    }

    fun removeMockExam(mc_data: MockExamRecord.MockExamData): Boolean {
        tempDataCheck()?.value?.let {
            it.remove(mc_data)
            tempMockExamRecord.value = it
            return true
        }
        return false
    }

    fun modifyMockExam(mc_data: MockExamRecord.MockExamData): Boolean {
        tempDataCheck()?.value?.let {
            mc_data?.let { mc_data ->
                it.modify(mc_data)
                modifyMockExamRecord(it)
                tempMockExamRecord.value = it
                return true
            }
        }
        return false
    }

    /**
     * 임시 데이터 DB에 저장 & 초기화
     */
    private fun insertTempData(): Boolean? {
        tempMockExamRecord.value?.let {
            insertMockExamRecord(it)
            return true
        }
        return null
    }

    private fun resetTempData() {
        tempMockExamRecord.value = null
    }


    fun checkExamStart(): Boolean {
        tempMockExamRecord.value?.let {

            if (it.getSubject()?.subjects?.size!! > 0) {
                return true
            }
        }
        return false
    }


    /**
     * 타이머 ( Timer ) 시작
     */
    fun timerStart(): Boolean {
        tempDataCheck()?.value?.let { record ->
            timerDataCheck()?.value?.let { timer ->

                timer.timerStatus = TimerStatus.START
                timer.timer = Timer()
                timer.startTime = System.currentTimeMillis()
                timer.prev_time = timer.startTime
                val timerTask: TimerTask = object : TimerTask() {
                    override fun run() {
                        timer.time = System.currentTimeMillis() - timer.prev_time
                        timer.prev_time = System.currentTimeMillis()
                        timer.remain_time = timer.remain_time - timer.time

                        Log.d("SHONZ", "timer.remain_time = " + timer.remain_time)
                        timer.getCurrentSubject()?.let { subject ->
                            if (timer.remain_time < 0) {
                                timer.time = 0
                                timer.timer.cancel()
                                timer.startTime = System.currentTimeMillis()
                                nextExam()
                            } else {
                                timerData.postValue(timer)
                            }
                        }
                    }
                }
                timer.timer.schedule(timerTask, 0, 300)
                return true
            }
        }
        return false
    }

    /**
     * 타이머 ( Timer ) 일시정지
     */
    fun timerPause(): Boolean {
        tempDataCheck()?.value?.let {
            timerData.value?.let { timer ->
                timer.timer?.let {
                    if (timer.timerStatus == TimerStatus.END) return false
                    timer.timerStatus = TimerStatus.PAUSE
                    it.cancel()
                    return true
                }
            }
        }
        return false
    }

    /**
     * 타이머 ( Timer ) 정지
     */
    fun timerStop(): Boolean {
        insertTempData()?.let {
            timerPause()
            timerData.postValue(null)
            update()
            return true
        }
        return false
    }

    /**
     * 다음 시험 ( Exam ) 시작
     */
    fun nextExam(): Boolean {
        timerData.value?.let { timer ->

            timer.timerStatus = TimerStatus.PAUSE
            timer.next()
            timerData.postValue(timer)
            return true
        }
        return false
    }

    fun invalidate() {

    }

    fun examInit() {
        timerDataCheck()
    }

    /**
     * 타이머 ( Timer ) 데이터
     *
     * timerStatus: 타이머 상태 값
     * timerSubject: 과목명
     * timerRecord: MockExamRecord Data
     * time: 타이머 시간 값
     * timer: 타이머 할당
     * startTime: 시작 시간 값
     */
    class TimerData(
        var timerStatus: TimerStatus = TimerStatus.STOP,
        var timerSubject: String = Default.EMPTY_STR,
        val timerRecord: MockExamRecord,
        var remain_time: Long = 0,
        var time: Long = Default.EMPTY_LONG,
        var timer: Timer = Timer(),
        var startTime: Long = System.currentTimeMillis(),
        var prev_time: Long = 0
    ) {
        init {
            /**
             * 최초 과목명 설정 -> timerRecord 내부 아이템의 Name 기준으로 과목명 설정
             */
            if (timerSubject == Default.EMPTY_STR) {
                timerRecord.getSubject()?.subjects?.let { list ->
                    timerSubject = list[0].name
                }
            }
        }

        /**
         * 다음 과목으로 이동 -> 현재 Record Subject Name 과 Timer Subject Name 이 같을 경우 다음 과목으로 이동 && IndexError 발생 시, 다음 과목이 없는 상태이므로 Timer 종료
         *
         * now_subject: 현재 시험 Subjects 데이터
         * next_subject: 다음 시험 Subjects 데이터
         */
        fun next(): Boolean {
            if (timerStatus == TimerStatus.START || timerStatus == TimerStatus.END) return false
            timerRecord.getSubject()?.let { list ->
                for (i in 0 until list.subjects.size) {
                    list.subjects[i]?.let { now_subject ->
                        time = 0
                        timer.cancel()
                        startTime = System.currentTimeMillis()
                        try {
                            list.subjects[i + 1]?.let { next_subject ->
                                if (now_subject.name == timerSubject) {
                                    timerSubject = next_subject.name
                                    timerStatus = TimerStatus.STOP
                                    remain_time = next_subject.range
                                    return true
                                }
                            }
                        } catch (e: IndexOutOfBoundsException) {
                            e.printStackTrace()
                            timerStatus = TimerStatus.PAUSE
                            time = 0
                            return false
                        }
                    }
                }
            }
            return false
        }

        /**
         * 현재 TimerRecord Subject Data 가져오기
         */
        fun getCurrentSubject(): MockExamRecord.MockExamData? {
            timerRecord.getSubject()?.let { timer_mock_record ->
                for (item in timer_mock_record.subjects) {
                    if (item.name == timerSubject) {
                        return item
                    }
                }
            }
            return null
        }

        fun getCurrentIndex(): Int {
            timerRecord.getSubject()?.let {
                for (k in it.subjects) {
                    if (k.name == timerSubject) {
                        return (it.subjects.indexOf(k) + 1)
                    }
                }
            }
            return 1
        }
    }
}
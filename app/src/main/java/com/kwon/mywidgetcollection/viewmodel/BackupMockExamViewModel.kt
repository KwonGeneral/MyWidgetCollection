//package com.kwon.mywidgetcollection.viewmodel
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import androidx.room.Room
//import com.kwon.mywidgetcollection.contains.Default
//import com.kwon.mywidgetcollection.db.RoomDataBase
//import com.kwon.mywidgetcollection.entity.MockExamRecord
//import com.kwon.mywidgetcollection.entity.ScheduleRecord
//import kotlinx.coroutines.flow.Flow
//import java.lang.IndexOutOfBoundsException
//import java.sql.Time
//import java.util.*
//
//class BackupMockExamViewModel(val context: Context) {
//    var examData = MutableLiveData<List<MockExamRecord>>()
//    var tempMockExamRecord = MutableLiveData<MockExamRecord>()
//    var timerData = MutableLiveData<TimerData>()
//
//    companion object {
//        @SuppressLint("StaticFieldLeak")
//        var instance: BackupMockExamViewModel? = null
//        fun getInstance(context: Context): BackupMockExamViewModel {
//            instance?.let {
//                return it
//            }
//            instance = BackupMockExamViewModel(context)
//            return instance!!
//        }
//    }
//
//    private var sortDESC: Boolean = false
//
//    fun update() {
//        updateList()?.let {
//            examData.postValue(it)
//        }
//    }
//
//    fun modify(record: MockExamRecord, data: MockExamRecord.MockExamData) {
//        record?.let {
//            it.modify(data)
//        }
//        modifyMockExamRecord(record)
//    }
//
//    fun delete(record: MockExamRecord) {
//        record?.id?.let {
//            deleteMockExamRecord(it)
//        }
//    }
//
//    private fun setDESC(desc: Boolean) {
//        sortDESC = desc
//        update()
//    }
//
//    private fun insertMockExamRecord(mockExamRecord: MockExamRecord) {
//        RoomDataBase.getInstance(context)?.mockExamRecordService()?.create(mockExamRecord)
//    }
//
//    private fun modifyMockExamRecord(mockExamRecord: MockExamRecord) {
//        RoomDataBase.getInstance(context)?.mockExamRecordService()?.update(mockExamRecord)
//    }
//
//    private fun deleteMockExamRecord(id: Long) {
//        RoomDataBase.getInstance(context)?.mockExamRecordService()?.delete(id)
//    }
//
//    private fun updateList(count: Int = Default.PAGE_COUNT): List<MockExamRecord>? {
//        RoomDataBase.getInstance(context)?.mockExamRecordService()?.let { mockExamRecordService ->
//            if (sortDESC) {
//                return mockExamRecordService.readAllDESC(
//                    count
//                )
//            }
//            return mockExamRecordService.readAll(
//                count
//            )
//        }
//        return null
//    }
//
//    // Todo =====================================================================================
//    fun checkData() {
//        tempMockExamRecord.value?.apply {
//            Log.d("TEST", "tempMockExamRecord -> title: $title / id: $id / description: $description / create_at: $create_at /\n subjects: $subjects")
//        }
//    }
//
//    fun checkTimer() {
//        timerData.value?.apply {
//            Log.d("TEST", "timerData -> timerStatus: $timerStatus / time: $time / timer: $timer / startTime: $startTime /\n timerMockExamRecord: $timerMockExamRecord /\n timerSubjectName: $timerSubjectName")
//        }
//    }
//
//    //?????????????????? ????????? ??????
//    fun setMockTitle(title: String) {
//        if (tempMockExamRecord.value == null) {
//            Log.d("TEST", "setMockTitle - tempMockExamRecord ?????? null ?????????.")
//            tempMockExamRecord.value = MockExamRecord()
//        }
//        tempMockExamRecord.value?.let {
//            it.title = title
//        }
////        tempMockExamRecord.postValue(tempMockExamRecord.value)
//        Log.d("TEST", "setMockTitle ????????? : ${tempMockExamRecord.value?.title}")
//        checkData()
//    }
//
//    fun addMockExam(name: String, range: Long): Boolean {
//        if (tempMockExamRecord.value == null) {
//            Log.d("TEST", "addMockExam - tempMockExamRecord ?????? null ?????????.")
//            return false
//        }
//        tempMockExamRecord.value?.let {
//            it.add(MockExamRecord.MockExamData(name, range, 0))
//        }
//        tempMockExamRecord.postValue(tempMockExamRecord.value)
//
//        Log.d("TEST", "addMockExam name: $name / range: $range")
//        checkData()
//        return true
//    }
//
//    fun removeMockExam(mc_data: MockExamRecord.MockExamData): Boolean {
//        if (tempMockExamRecord.value == null) {
//            Log.d("TEST", "removeMockExam - tempMockExamRecord ?????? null ?????????.")
//            return false
//        }
//        tempMockExamRecord.value?.let {
//            it.remove(mc_data)
//        }
//        tempMockExamRecord.postValue(tempMockExamRecord.value)
//
//        Log.d("TEST", "removeMockExam -> name: ${mc_data.name} / range: ${mc_data.range} / point: ${mc_data.point}")
//        checkData()
//        return true
//    }
//
//    fun modifyMockExam(mc_data: MockExamRecord.MockExamData): Boolean {
//        if (tempMockExamRecord.value == null) {
//            Log.d("TEST", "modifyMockExam - tempMockExamRecord ?????? null ?????????.")
//            return false
//        }
//        tempMockExamRecord.value?.let {
//            it.modify(mc_data)
//        }
//        modifyMockExamRecord(tempMockExamRecord.value!!)
//
//        tempMockExamRecord.postValue(tempMockExamRecord.value)
//
//        Log.d("TEST", "modifyMockExam -> name: ${mc_data.name} / range: ${mc_data.range} / point: ${mc_data.point}")
//        checkData()
//        return true
//    }
//
//    fun saveMockExam() {
//        //db??? ?????????
//        tempMockExamRecord.value?.let {
//            insertMockExamRecord(it)
//            Log.d("TEST", "????????? ?????? ??????!")
//            checkData()
//        }
//    }
//
//    fun startMockExam(): Boolean {
//        if (tempMockExamRecord.value == null) {
//            Log.d("TEST", "startMockExam - tempMockExamRecord ?????? null ?????????.")
//            return false
//        }
//
//        if (timerData.value == null) {
//            Log.d("TEST", "startMockExam - timerData ?????? null ?????????. -> ?????? ??????")
//            tempMockExamRecord.value?.let {
//                timerData.value = TimerData(MockStatus.STOP, "", it)
//            }
//            checkTimer()
//        }
//
//        timerData.value?.let {
//            if(it.timerStatus == MockStatus.START || it.timerStatus == MockStatus.END) {
//                return false
//            }
//            val firstTime = it.time
//            it.timerStatus = MockStatus.START
//            it.timer = Timer()
//            it.startTime = System.currentTimeMillis()
//            val timerTask: TimerTask = object : TimerTask() {
//                override fun run() {
//                    it.time = System.currentTimeMillis() - it.startTime + firstTime
//
//                    Log.d("TEST", "????????? ???????????? -> \n startTime: ${it.startTime} /\n time: ${it.time}")
//                    it.getCurrentSubject()?.let { subject ->
//                        if (subject.range < it.time) {
//                            it.time = 0
//                            it.timer.cancel()
//                            it.startTime = System.currentTimeMillis()
//                            nextExam()
//                        }
//                    }
//                    timerData.postValue(it)
//                }
//            }
//            it.timer.schedule(timerTask, 0, 1000)
////            it.timer.schedule(object : TimerTask() {
////                override fun run() {
////                    it.time = System.currentTimeMillis() - it.startTime
////                    Log.d("TEST", "????????? ???????????? -> startTime: ${it.startTime} /\n time: ${it.time}")
////                    it.getCurrentSubject()?.let { subject ->
////                        if (subject.range < it.time) {
////                            it.timer.cancel()
////                            it.startTime = System.currentTimeMillis()
////                            nextExam()
////                        }
////                    }
////                    timerData.postValue(it)
////                }
////            }, 1000)
//        }
//
//        return true
//    }
//
//    fun pauseExam(): Boolean {
//        if (tempMockExamRecord.value == null) {
//            Log.d("TEST", "pauseExam - tempMockExamRecord ?????? null ?????????.")
//            return false
//        }
//
//        timerData.value?.let {
//            if(it.timerStatus == MockStatus.END) {
//                return false
//            }
//            it.timer?.let { timer ->
//                it.timerStatus = MockStatus.PAUSE
//                timer.cancel()
//            }
//        }
//        return true
//    }
//
//    fun examFinish() {
//        tempMockExamRecord.value?.let {
//            insertMockExamRecord(it)
//        }
//        timerData.postValue(null)
//    }
//
//    fun nextExam() {
//        Log.d("TEST", "-- nextExam ?????? --")
//        checkTimer()
//        timerData.value?.let {
//            it.timerStatus = MockStatus.PAUSE
//            it.next()
//            timerData.postValue(it)
//        }
//        Log.d("TEST", "-- nextExam ?????? --")
//        checkTimer()
//    }
//
//    enum class MockStatus {
//        START, PAUSE, STOP, END
//    }
//
//    class TimerData(
//        var timerStatus: MockStatus = MockStatus.STOP,
//        var timerSubjectName: String = Default.EMPTY_STR,
//        val timerMockExamRecord: MockExamRecord,
//        var time: Long = Default.EMPTY_LONG,
//        var timer: Timer = Timer()
//    ) {
//        var startTime: Long = System.currentTimeMillis()
//
//        init {
//            // ?????? ????????? ??????
//            if (timerSubjectName == Default.EMPTY_STR) {
//                timerMockExamRecord.getSubject()?.subjects?.let {
//                    timerSubjectName = it[0].name
//                }
//            }
//            Log.d("TEST", "?????? ????????? ?????? : $timerSubjectName")
//        }
//
//        // ?????? ???????????? ?????? ( ?????? ????????? ?????? ?????? false ?????? )
//        fun next(): Boolean {
//            if(timerStatus == MockStatus.START || timerStatus == MockStatus.END) {
//                return false
//            }
//            timerMockExamRecord.getSubject()?.let {
//                for (i in 0 until it.subjects.size) {
//                    it.subjects[i]?.let { now_subject ->
//                        try{
//                            it.subjects[i + 1]?.let { next_subject ->
//                                // ?????? mockExamRecord??? ??????????????? subject name??? TimerData??? ??????????????? timerSubjectName??? ?????? ??????,
//                                // timerSubjectName??? ?????? next_subject.name?????? ????????????.
//                                if (now_subject.name == timerSubjectName) {
//                                    Log.d("TEST", "! next -> now_subject.name == timerSubjectName !")
//                                    timerSubjectName = next_subject.name
//                                    timerStatus = MockStatus.STOP
//                                    return true
//                                }
//                                Log.d("TEST", "?????? ???????????? ?????? -> now_subject: $now_subject / next_subject: $next_subject")
//                            }
//                        } catch (e: IndexOutOfBoundsException) {
//                            timerStatus = MockStatus.END
//                            Log.d("TEST", "????????? ???????????????.")
//                            return true
//                        }
//                    }
//                }
//            }
//            return false
//        }
//
//        // ?????? current item data ????????????
//        fun getCurrentSubject(): MockExamRecord.MockExamData? {
//            timerMockExamRecord.getSubject()?.let { timer_mock_record ->
//                for (item in timer_mock_record.subjects) {
//                    if (item.name == timerSubjectName) {
//                        Log.d("TEST", "! getCurrentSubject -> item.name == timerSubjectName !")
//                        return item
//                    }
//                }
//            }
//            return null
//        }
//    }
//}
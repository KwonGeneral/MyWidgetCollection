package com.kwon.mywidgetcollection.entity

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.kwon.mywidgetcollection.contains.Default


// 모의고사
@Entity(tableName = "mock_exam_record")
data class MockExamRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var title: String = Default.EMPTY_STR,
    var description: String = Default.EMPTY_STR,
    var create_at: Long = Default.EMPTY_LONG,
    var subjects: String = Default.EMPTY_STR
) {
    fun check(str: String) {
        Log.d("TEST", "! MockExamRecord - $str ! -> id: ${this.id} / title: ${this.title} / description: ${this.description} / create_at: ${this.create_at} /\n subjects: ${this.subjects}")
    }

    fun add(mockExamData: MockExamData):Boolean {
        val fromDataJson:MockExamDataList
        var tempMockList = MockExamDataList()

        Log.d("TEST", "ADD mockExamData -> name : ${mockExamData.name} / range : ${mockExamData.range} / point : ${mockExamData.point}")
        if(this.subjects == Default.EMPTY_STR) {
            tempMockList.subjects.add(mockExamData)
            this.subjects = Gson().toJson(tempMockList)
            Log.d("TEST", "ADD Null - toDataJson : ${Gson().toJson(tempMockList)}")
            fromDataJson = Gson().fromJson(this.subjects, MockExamDataList::class.java)
            Log.d("TEST", "ADD Null - fromDataJson : $fromDataJson")
        } else {
            fromDataJson = Gson().fromJson(this.subjects, MockExamDataList::class.java)
            Log.d("TEST", "ADD NOT Null - fromDataJson : $fromDataJson")
        }

        fromDataJson?.let {
            for(mc_data in it.subjects) {
                if(mockExamData.toString() == mc_data.toString()) {
                    return false
                }
            }
            Log.d("TEST", "ADD - mockExamData : $mockExamData")
            it.subjects.add(mockExamData)
            this.subjects = Gson().toJson(it)
            Log.d("TEST", "ADD - it.subjects : ${it.subjects}")
            Log.d("TEST", "ADD - this.subjects : ${this.subjects}")
        }

//        this.subjects = Gson().toJson(mockExamData)

//        Log.d("TEST", "fromJson -> ${Gson().fromJson(mockExamData, MockExamDataList::class.java)}")
//        Gson().fromJson(subjects, MockExamDataList::class.java)?.let {
//            for(mc_data in it.subjects) {
//                if(mockExamData.toString() == mc_data.toString()) {
//                    return false
//                }
//            }
//            Log.d("TEST", "ADD - mockExamData : $mockExamData")
//            it.subjects.add(mockExamData)
//            this.subjects = Gson().toJson(it)
//            Log.d("TEST", "ADD - it.subjects : ${it.subjects}")
//            Log.d("TEST", "ADD - this.subjects : ${this.subjects}")
//        }

        check("add")
        return true
    }

    fun remove(mockExamData: MockExamData):Boolean {
        Gson().fromJson(subjects, MockExamDataList::class.java)?.let {
            for(mc_data in it.subjects)
            {
                if(mockExamData.toString() == mc_data.toString()) {
                    it.subjects.remove(mc_data)
                    this.subjects = Gson().toJson(it)
                    return true
                }
            }
        }

        check("remove")
        return false
    }

    fun modify(mockExamData: MockExamData):Boolean {
        Gson().fromJson(subjects, MockExamDataList::class.java)?.let {
            for (mc_data in it.subjects) {
                if(mockExamData.toString() == mc_data.toString()) {
                    mc_data.point = mockExamData.point
                    mc_data.range = mockExamData.range
                    this.subjects = Gson().toJson(it)
                    return true
                }
            }
        }

        check("modify")
        return false
    }

    fun getSubject():MockExamDataList? {
        Gson().fromJson(subjects, MockExamDataList::class.java)?.let {
            return it
        }

        check("getSubject")
        return null
    }

    data class MockExamDataList(var subjects: ArrayList<MockExamData> = ArrayList<MockExamData>())
    data class MockExamData(val name: String, var range: Long, var point: Int) {
        override fun toString(): String {
            return name
        }
    }
}
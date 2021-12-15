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
    var create_at: Long = System.currentTimeMillis(),
    var subjects: String = Default.EMPTY_STR
) {
    data class MockExamDataList(var subjects: ArrayList<MockExamData> = ArrayList<MockExamData>())
    data class MockExamData(val name: String, var range: Long, var point: Int) {
        override fun toString(): String {
            return name
        }
    }

    fun add(mockExamData: MockExamData):Boolean {
        val fromDataJson:MockExamDataList
        val tempMockList = MockExamDataList()

        if(this.subjects == Default.EMPTY_STR) {
            tempMockList.subjects.add(mockExamData)
            this.subjects = Gson().toJson(tempMockList)
            fromDataJson = Gson().fromJson(this.subjects, MockExamDataList::class.java)
        } else {
            fromDataJson = Gson().fromJson(this.subjects, MockExamDataList::class.java)
        }

        fromDataJson?.let {
            for(mc_data in it.subjects) {
                if(mockExamData.toString() == mc_data.toString()) {
                    return false
                }
            }
            it.subjects.add(mockExamData)
            this.subjects = Gson().toJson(it)
        }
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
        return false
    }

    fun getSubject():MockExamDataList? {
        Gson().fromJson(subjects, MockExamDataList::class.java)?.let {
            return it
        }
        return null
    }
}
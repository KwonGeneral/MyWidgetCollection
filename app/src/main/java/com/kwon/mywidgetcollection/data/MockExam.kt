package com.kwon.mywidgetcollection.data

import androidx.room.Entity
import com.google.gson.Gson


@Entity
data class MockExam(val id:Int,val tile:String,val description:String,val create_at:Long,val subjects:String) {
    init {
        val sim = SimSubjects().apply {
            subjects.add(SimSubject("국어",40,100))
            subjects.add(SimSubject("수학",80,0))
            subjects.add(SimSubject("영어",10,0))
        }
        val reSim:SimSubject = Gson().fromJson(Gson().toJson(sim),SimSubject::class.java)
    }

    data class SimSubjects(var subjects: ArrayList<SimSubject> = ArrayList<SimSubject>())
    data class SimSubject(val name:String,val range:Long,val point:Int)
}

//3월모의고사
//국어 100점 time range _ 40분
//수학 80점 30분
//사회 20점 10분
//
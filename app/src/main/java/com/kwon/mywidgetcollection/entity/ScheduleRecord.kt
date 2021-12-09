package com.kwon.mywidgetcollection.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

// 일정, 할일
@Entity(tableName = "schedule_record")
class ScheduleRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val type: String? = "",
    val sub_type: String? = "",
    val title: String? = "",
    val content: String? = "",
    val from_date: Long? = 0,
    val to_date:Long? = 0,
    val status:Int? = 0,  // 0 : 미진행, 1 : 진행중, 2 : 완료, 400 : 실패
    val create_at: Long? = 0,
)


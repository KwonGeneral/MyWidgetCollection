package com.kwon.mywidgetcollection.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kwon.mywidgetcollection.contains.Default
import java.time.LocalDateTime

// 일정, 할일
@Entity(tableName = "schedule_record")
class ScheduleRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = Default.EMPTY_LONG,
    val type: String? = Default.EMPTY_STR,
    val sub_type: String? = Default.EMPTY_STR,
    val title: String? = Default.EMPTY_STR,
    val content: String? = Default.EMPTY_STR,
    val status:Int? = Default.EMPTY_INT,  // 0 : 미진행, 1 : 진행중, 2 : 완료, 400 : 실패
    val from_date: Long? = Default.EMPTY_LONG,
    val to_date:Long? = Default.EMPTY_LONG,
    val create_at: Long? = Default.EMPTY_LONG,
)


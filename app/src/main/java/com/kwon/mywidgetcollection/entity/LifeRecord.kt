package com.kwon.mywidgetcollection.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

// 생활기록부
@Entity(tableName = "life_record")
class LifeRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val type: String? = "",
    val sub_type: String? = "",
    val title: String? = "",
    val content: String? = "",
    val area: String? = "",
    val from_date: Long? = 0,
    val to_date:Long? = 0,
    val rank:String? = "",
    val time_at:String? = "",
    val semester: String? = "",
    val create_at: Long? = 0,
)


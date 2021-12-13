package com.kwon.mywidgetcollection.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.kwon.mywidgetcollection.utils.DateUtil
import java.time.LocalDateTime
import java.util.*

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
    val rank: String? = "",
    val semester: String? = "",
    val from_date: Long? = 0,
    val to_date: Long? = 0,
    val time_at: Long? = 0,
    val create_at: Long? = 0,
)
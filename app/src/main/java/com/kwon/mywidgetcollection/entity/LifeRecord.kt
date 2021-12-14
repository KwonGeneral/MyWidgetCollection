package com.kwon.mywidgetcollection.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.utils.DateUtil
import java.time.LocalDateTime
import java.util.*

// 생활기록부
@Entity(tableName = "life_record")
class LifeRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = Default.EMPTY_LONG,
    val type: String? = Default.EMPTY_STR,
    val sub_type: String? = Default.EMPTY_STR,
    val title: String? = Default.EMPTY_STR,
    val content: String? = Default.EMPTY_STR,
    val area: String? = Default.EMPTY_STR,
    val rank: String? = Default.EMPTY_STR,
    val semester: String? = Default.EMPTY_STR,
    val from_date: Long? = Default.EMPTY_LONG,
    val to_date: Long? = Default.EMPTY_LONG,
    val time_at: Long? = Default.EMPTY_LONG,
    val create_at: Long? = Default.EMPTY_LONG,
)
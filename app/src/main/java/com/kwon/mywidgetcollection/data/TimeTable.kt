package com.kwon.mywidgetcollection.data

import androidx.room.Entity

data class TimeTable (
    val id: Long,
    val from_time: Long,
    val to_time: Long,
    val subject: String,
    val created_at: Long,
    val point_color: String,
    val description:String,
    val dayOfWeek:Int
)
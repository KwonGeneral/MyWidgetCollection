package com.kwon.mywidgetcollection.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// 봉사활동 이력
@Entity(tableName = "volunteer_work_record")
data class VolunteerWorkRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val volunteer_work_name: String? = "",
    val volunteer_work_organization_name: String? = "",
    val volunteer_work_time: String? = "",
    val volunteer_work_period: String? = "",
    val created_at: String? = "",
)
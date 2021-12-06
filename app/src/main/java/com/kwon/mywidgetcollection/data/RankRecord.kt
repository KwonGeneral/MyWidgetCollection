package com.kwon.mywidgetcollection.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// 수상 이력
@Entity(tableName = "rank_record")
data class RankRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val rank_name: String? = "",
    val rank_semester: String? = "",
    val rank_period: String? = "",
    val created_at: String? = "",
)
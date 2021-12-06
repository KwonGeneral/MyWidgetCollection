package com.kwon.mywidgetcollection.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// 수상 이력
@Entity(tableName = "awards_record")
data class AwardsRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val award_name: String? = "",
    val award_rank: String? = "",
    val award_organization_name: String? = "",
    val award_period: String? = "",
    val created_at: String? = "",
)
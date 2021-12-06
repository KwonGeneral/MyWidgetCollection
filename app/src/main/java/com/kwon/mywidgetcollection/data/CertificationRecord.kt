package com.kwon.mywidgetcollection.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// 자격증 이력
@Entity(tableName = "certification_record")
data class CertificationRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val certification_name: String? = "",
    val certification_organization_name: String? = "",
    val certification_period: String? = "",
    val created_at: String? = "",
)
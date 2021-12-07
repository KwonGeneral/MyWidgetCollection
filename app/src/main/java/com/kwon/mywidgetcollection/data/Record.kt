package com.kwon.mywidgetcollection.data

import androidx.room.PrimaryKey

class Record (
    val id: Long? = 0,
    val name: String? = "",
    val rank: String? = "",
    val organization_name: String? = "",
    val period: String? = "",
    val created_at: String? = "",
)
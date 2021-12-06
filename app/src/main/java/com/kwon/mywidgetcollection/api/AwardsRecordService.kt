package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.data.AwardsRecord


@Dao
interface AwardsRecordService {
    @Query("SELECT * FROM awards_record")
    fun readAll(): List<AwardsRecord>

    @Query("SELECT * FROM awards_record")
    fun readByPage() : PagingSource<Int, AwardsRecord>

    @Query("SELECT * FROM awards_record WHERE award_name = :award_name")
    fun searchByTitle(award_name: String): PagingSource<Int, AwardsRecord>

    @Update
    fun update(vararg record: AwardsRecord)

    @Query("DELETE FROM awards_record WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun create(vararg record: AwardsRecord)

    @Query("DELETE FROM awards_record")
    fun reset()
}
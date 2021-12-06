package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.data.VolunteerWorkRecord


@Dao
interface VolunteerWorkRecordService {
    @Query("SELECT * FROM volunteer_work_record")
    fun readAll(): List<VolunteerWorkRecord>

    @Query("SELECT * FROM volunteer_work_record")
    fun readByPage() : PagingSource<Int, VolunteerWorkRecord>

    @Query("SELECT * FROM volunteer_work_record WHERE volunteer_work_name = :volunteer_work_name")
    fun searchByTitle(volunteer_work_name: String): PagingSource<Int, VolunteerWorkRecord>

    @Update
    fun update(vararg record: VolunteerWorkRecord)

    @Query("DELETE FROM volunteer_work_record WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun create(vararg record: VolunteerWorkRecord)

    @Query("DELETE FROM volunteer_work_record")
    fun reset()
}
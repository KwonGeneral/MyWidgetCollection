package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.data.CertificationRecord


@Dao
interface CertificationRecordService {
    @Query("SELECT * FROM certification_record")
    fun readAll(): List<CertificationRecord>

    @Query("SELECT * FROM certification_record")
    fun readByPage() : PagingSource<Int, CertificationRecord>

    @Query("SELECT * FROM certification_record WHERE certification_name = :certification_name")
    fun searchByTitle(certification_name: String): PagingSource<Int, CertificationRecord>

    @Update
    fun update(vararg record: CertificationRecord)

    @Query("DELETE FROM certification_record WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun create(vararg record: CertificationRecord)

    @Query("DELETE FROM certification_record")
    fun reset()
}
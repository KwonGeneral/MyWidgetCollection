package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.data.RankRecord


@Dao
interface RankRecordService {
    @Query("SELECT * FROM rank_record")
    fun readAll(): List<RankRecord>

    @Query("SELECT * FROM rank_record")
    fun readByPage() : PagingSource<Int, RankRecord>

    @Query("SELECT * FROM rank_record WHERE rank_name = :rank_name")
    fun searchByTitle(rank_name: String): PagingSource<Int, RankRecord>

    @Update
    fun update(vararg record: RankRecord)

    @Query("DELETE FROM rank_record WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun create(vararg record: RankRecord)

    @Query("DELETE FROM rank_record")
    fun reset()
}
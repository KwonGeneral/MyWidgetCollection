package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.entity.LifeRecord
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import java.util.*

@Dao
interface LifeRecordService {

    //전체가져오기
    @Query("SELECT * FROM life_record")
    fun readAll(): List<LifeRecord>

    //전체가져오기 페이징
    @Query("SELECT * FROM life_record")
    fun readByPage(): PagingSource<Int, LifeRecord>


    //from - to 0 -2999년
    //type 별
    //정순-역순
    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name ASC LIMIT :count")
    fun search(
        type: String,
        sub_type: String,
        from_date: Long,
        to_date: Long,
        sort_name: String,
        count: Int
    ): List<LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name DESC LIMIT :count")
    fun searchDESC(
        type: String,
        sub_type: String,
        from_date: Long,
        to_date: Long,
        sort_name: String,
        count: Int
    ): List<LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name ASC LIMIT :count")
    fun searchByPage(
        type: String,
        sub_type: String,
        from_date: Long,
        to_date: Long,
        sort_name: String,
        count: Int
    ): PagingSource<Int, LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name DESC LIMIT :count")
    fun searchByPageDESC(
        type: String,
        sub_type: String,
        from_date: Long,
        to_date: Long,
        sort_name: String,
        count: Int
    ): PagingSource<Int, LifeRecord>

    @Update
    fun update(vararg record: LifeRecord)

    @Query("DELETE FROM life_record WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun create(vararg record: LifeRecord)

    @Query("DELETE FROM life_record")
    fun reset()
}
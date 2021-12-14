package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import java.util.*

@Dao
interface ScheduleRecordService {

    //전체
    @Query("SELECT * FROM schedule_record")
    fun readAll(): List<ScheduleRecord>

    //전체 페이징
    @Query("SELECT * FROM schedule_record")
    fun readByPage() : PagingSource<Int, ScheduleRecord>


    //해당 type의 데이터를 프롬데이트 기준으로 정순/역순 form-to 안의 값을 반환
    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name ASC LIMIT :count")
    fun search(type:String, sub_type:String, from_date: Long, to_date:Long, sort_name:String, count:Int): List<ScheduleRecord>

    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name DESC LIMIT :count")
    fun searchDESC(type:String, sub_type:String, from_date:Long, to_date:Long, sort_name:String, count:Int): List<ScheduleRecord>


    //페이징
    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name ASC LIMIT :count")
    fun searchByPage(type:String, sub_type:String, from_date:Long, to_date:Long, sort_name:String, count:Int): PagingSource<Int, ScheduleRecord>

    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name DESC LIMIT :count")
    fun searchByPageDESC(type:String, sub_type:String, from_date:Long, to_date:Long, sort_name:String, count:Int): PagingSource<Int, ScheduleRecord>


    //수정
    @Update
    fun update(vararg record: ScheduleRecord)

    //삭제
    @Query("DELETE FROM schedule_record WHERE id = :id")
    fun delete(id: Long)

    //추가
    @Insert
    fun create(vararg record: ScheduleRecord)

    @Query("DELETE FROM schedule_record")
    fun reset()
}
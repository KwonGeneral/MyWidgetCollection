package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.entity.ScheduleRecord

@Dao
interface ScheduleRecordService {
    @Query("SELECT * FROM schedule_record")
    fun readAll(): List<ScheduleRecord>

    @Query("SELECT * FROM schedule_record")
    fun readByPage() : PagingSource<Int, ScheduleRecord>

    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name ASC LIMIT :count")
    fun search(type:String, sub_type:String, from_date:Long, to_date:Long, sort_name:String, count:Int): List<ScheduleRecord>

    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name DESC LIMIT :count")
    fun searchDESC(type:String, sub_type:String, from_date:Long, to_date:Long, sort_name:String, count:Int): List<ScheduleRecord>

    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name ASC LIMIT :count")
    fun searchByPage(type:String, sub_type:String, from_date:Long, to_date:Long, sort_name:String, count:Int): PagingSource<Int, ScheduleRecord>

    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY :sort_name DESC LIMIT :count")
    fun searchByPageDESC(type:String, sub_type:String, from_date:Long, to_date:Long, sort_name:String, count:Int): PagingSource<Int, ScheduleRecord>

//    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id ASC LIMIT :count")
//    fun searchASC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): List<ScheduleRecord>
//
//    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id DESC LIMIT :count")
//    fun searchDESC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): List<ScheduleRecord>
//
//    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id ASC LIMIT :count")
//    fun searchByPageASC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): PagingSource<Int, ScheduleRecord>
//
//    @Query("SELECT * FROM schedule_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id DESC LIMIT :count")
//    fun searchByPageDESC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): PagingSource<Int, ScheduleRecord>

    @Update
    fun update(vararg record: ScheduleRecord)

    @Query("DELETE FROM schedule_record WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun create(vararg record: ScheduleRecord)

    @Query("DELETE FROM schedule_record")
    fun reset()
}
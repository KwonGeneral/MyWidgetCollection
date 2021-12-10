package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.entity.LifeRecord

@Dao
interface LifeRecordService {
    @Query("SELECT * FROM life_record")
    fun readAll(): List<LifeRecord>

    @Query("SELECT * FROM life_record")
    fun readByPage() : PagingSource<Int, LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type ORDER BY id ASC")
    fun searchByTypeASC(type: String): PagingSource<Int, LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type ORDER BY id desc")
    fun searchByTypeDESC(type: String): PagingSource<Int, LifeRecord>

    @Query("SELECT * FROM life_record WHERE title = :title")
    fun searchByTitle(title: String): PagingSource<Int, LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id ASC LIMIT :count")
    fun searchASC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): List<LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id DESC LIMIT :count")
    fun searchDESC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): List<LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id ASC LIMIT :count")
    fun searchByPageASC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): PagingSource<Int, LifeRecord>

    @Query("SELECT * FROM life_record WHERE type = :type AND sub_type = :sub_type AND from_date >= :from_date AND to_date <= :to_date ORDER BY id DESC LIMIT :count")
    fun searchByPageDESC(type:String, sub_type:String, from_date:Long, to_date:Long, count:Int): PagingSource<Int, LifeRecord>

    @Update
    fun update(vararg record: LifeRecord)

    @Query("DELETE FROM life_record WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun create(vararg record: LifeRecord)

    @Query("DELETE FROM life_record")
    fun reset()
}
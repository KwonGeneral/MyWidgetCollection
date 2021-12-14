package com.kwon.mywidgetcollection.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kwon.mywidgetcollection.entity.MockExamRecord
import java.util.*

@Dao
interface MockExamRecordService {

    // 전체
    @Query("SELECT * FROM mock_exam_record ORDER BY id ASC LIMIT :count")
    fun readAll(count:Int): List<MockExamRecord>

    // 전체 역정렬
    @Query("SELECT * FROM mock_exam_record ORDER BY id DESC LIMIT :count")
    fun readAllDESC(count:Int): List<MockExamRecord>

    // 전체 페이징
    @Query("SELECT * FROM mock_exam_record ORDER BY id ASC LIMIT :count")
    fun readByPage(count:Int) : PagingSource<Int, MockExamRecord>

    // 전체 페이징 역정렬
    @Query("SELECT * FROM mock_exam_record ORDER BY id DESC LIMIT :count")
    fun readByPageDESC(count:Int) : PagingSource<Int, MockExamRecord>

    // 수정
    @Update
    fun update(vararg record: MockExamRecord)

    // 삭제
    @Query("DELETE FROM mock_exam_record WHERE id = :id")
    fun delete(id: Long)

    // 추가
    @Insert
    fun create(vararg record: MockExamRecord)

    // 초기화
    @Query("DELETE FROM mock_exam_record")
    fun reset()
}
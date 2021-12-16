package com.kwon.mywidgetcollection.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.ScreenDefine
import com.kwon.mywidgetcollection.data.MockExamItemData
import com.kwon.mywidgetcollection.entity.MockExamRecord
import com.kwon.mywidgetcollection.viewmodel.MockExamViewModel
import com.kwon.mywidgetcollection.viewmodel.ScreenViewModel
import kotlinx.android.synthetic.main.list_item_exam_schedule.view.*
import kotlinx.android.synthetic.main.list_item_record_pager.view.*
import java.util.*

class MockExamScheduleAdapter constructor(var context: Context, var items:List<MockExamItemData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun notifyChanges(oldList: List<MockExamRecord>, newList: List<MockExamRecord>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(LayoutInflater.from(context).inflate(R.layout.list_item_exam_schedule, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position]?.let { item ->
            with(holder.itemView) {
                exam_schedule_period.text = item.me_period
                if(item.me_subject != null) {
                    exam_schedule_subject.setText(item.me_subject)
                    exam_schedule_subject.background = null
                    exam_schedule_subject.isClickable = false
                    exam_schedule_subject.isFocusable = false
                    exam_schedule_subject.isFocusableInTouchMode = false
                } else {
                    exam_schedule_subject.setText(Default.EMPTY_STR)
                }
                if(item.me_point != null) {
                    exam_schedule_time_point.setText(item.me_point)
                } else {
                    exam_schedule_time_point.setText(Default.EMPTY_STR)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

}
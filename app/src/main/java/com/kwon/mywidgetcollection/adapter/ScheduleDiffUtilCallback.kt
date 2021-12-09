package com.kwon.mywidgetcollection.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kwon.mywidgetcollection.entity.ScheduleRecord

class ScheduleDiffUtilCallback(private val oldItem: List<ScheduleRecord>, private val newItem: List<ScheduleRecord>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition] == newItem[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldItem.size
    }

    override fun getNewListSize(): Int {
        return newItem.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition].id == newItem[newItemPosition].id
    }
}
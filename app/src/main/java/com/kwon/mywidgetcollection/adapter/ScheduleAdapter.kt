package com.kwon.mywidgetcollection.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.service.quicksettings.Tile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import kotlinx.android.synthetic.main.list_item_schedule.view.*

class ScheduleAdapter constructor(val context: Context, val type: String, val schedule: List<ScheduleRecord>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items:MutableList<ScheduleRecord> = schedule as MutableList<ScheduleRecord>

    private fun calDiff(newItems: List<ScheduleRecord>) {
        val tileDiffUtilCallback = ScheduleDiffUtilCallback(items, newItems)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(tileDiffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun setNewTiles(newItems: List<ScheduleRecord>) {
        items.run {
            clear()
            addAll(newItems)
        }
    }

    fun shuffle() {
        val newItems = mutableListOf<ScheduleRecord>().apply {
            addAll(items)
            shuffle()
        }
        calDiff(newItems)
        setNewTiles(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(LayoutInflater.from(context).inflate(R.layout.list_item_schedule, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position]?.let { item ->
            with(holder.itemView) {
                when(type) {
                    ScheduleDefine.TODAY -> {
                        lt_title.text = item.title
                        lt_sub_title.text = "${item.from_date} ~ ${item.to_date}"
                    }
                    ScheduleDefine.TODO -> {
                        lt_title.text = item.title
                        when(item.status) {
                            0 -> {
                                lt_sub_title.text = "미진행"
                            }
                            1 -> {
                                lt_sub_title.text = "진행중"
                            }
                            2 -> {
                                lt_sub_title.text = "완료"
                            }
                            400 -> {
                                lt_sub_title.text = "실패"
                            }
                        }
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}
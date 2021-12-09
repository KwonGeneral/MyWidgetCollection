package com.kwon.mywidgetcollection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import kotlinx.android.synthetic.main.list_item_schedule.view.*

class SchedulePageAdapter(val type: String? = "") : PagingDataAdapter<ScheduleRecord, SchedulePageAdapter.ScheduleViewHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_schedule, parent, false))
    }

    companion object {
        private val Diff = object : DiffUtil.ItemCallback<ScheduleRecord>() {
            override fun areItemsTheSame(oldItem: ScheduleRecord, newItem: ScheduleRecord): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ScheduleRecord, newItem: ScheduleRecord): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("TEST", "getItemCount : ${super.getItemCount()}")
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        getItem(position).let { it?.let { holder.bind(type, it) } }
    }

    class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.lt_title
        private val subTitle = view.lt_sub_title
        private lateinit var item: ScheduleRecord

        fun bind(type: String? = "", item: ScheduleRecord) {
            this.item = item

            when(type) {
                ScheduleDefine.TODAY -> {
                    title.text = item.title
                    subTitle.text = "${item.from_date} ~ ${item.to_date}"
                }
                ScheduleDefine.TODO -> {
                    title.text = item.title
                    when(item.status) {
                        0 -> {
                            subTitle.text = "미진행"
                        }
                        1 -> {
                            subTitle.text = "진행중"
                        }
                        2 -> {
                            subTitle.text = "완료"
                        }
                        400 -> {
                            subTitle.text = "실패"
                        }
                    }
                }
            }
        }
    }
}
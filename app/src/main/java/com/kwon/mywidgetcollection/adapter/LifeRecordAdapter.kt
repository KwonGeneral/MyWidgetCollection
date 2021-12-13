package com.kwon.mywidgetcollection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.contains.LifeDefine
import com.kwon.mywidgetcollection.entity.LifeRecord
import kotlinx.android.synthetic.main.list_item_record.view.*
import kotlinx.android.synthetic.main.list_item_record_pager.view.*

class LifeRecordAdapter(val type: String? = "") : PagingDataAdapter<LifeRecord, LifeRecordAdapter.LifeViewHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeViewHolder {
        return LifeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_record, parent, false))
    }

    companion object {
        private val Diff = object : DiffUtil.ItemCallback<LifeRecord>() {
            override fun areItemsTheSame(oldItem: LifeRecord, newItem: LifeRecord): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LifeRecord, newItem: LifeRecord): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("TEST", "getItemCount : ${super.getItemCount()}")
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: LifeViewHolder, position: Int) {
        getItem(position).let { it?.let { holder.bind(type, it) } }
    }

    class LifeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.lr_title
        private val subTitle = view.lr_sub_title
        private val body = view.lr_body
        private lateinit var item: LifeRecord

        fun bind(type: String? = "", item: LifeRecord) {
            this.item = item

            when(type) {
                LifeDefine.AWARD -> {
                    title.text = item.title
                    subTitle.text = item.rank
                    body.text = item.area
                }
                LifeDefine.CERTIFICATION -> {
                    title.text = item.title
                    subTitle.text = item.time_at.toString()
                    body.text = item.area
                }
                LifeDefine.VOLUNTEER -> {
                    title.text = item.title
                    subTitle.text = item.from_date.toString()
                    body.text = item.area
                }
                LifeDefine.GRADE -> {
                    title.text = item.title
                    subTitle.text = item.rank
                    body.text = item.semester
                }
            }
            Log.d("TEST", "Item -> title: ${item.title} / content: ${item.content} / organization: ${item.area}")
        }
    }
}
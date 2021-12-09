package com.kwon.mywidgetcollection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.contains.LifeRecordDefine
import com.kwon.mywidgetcollection.entity.LifeRecord
import kotlinx.android.synthetic.main.list_item_record.view.*
import kotlinx.android.synthetic.main.list_item_record_pager.view.*

class LifeRecordAdapter(val type: String? = "") : PagingDataAdapter<LifeRecord, LifeRecordAdapter.LifeViewHolder>(Diff) {
    var checkBoxVisibility: Boolean = false
    var checkBoxIsChecked: Boolean = false
    var itemList = hashMapOf<Long, String>()
    var itemIdList = mutableListOf<Long>()

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
//        Log.d("TEST", "getItemCount : ${super.getItemCount()}")
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: LifeViewHolder, position: Int) {
        getItem(position).let { it?.let { holder.bind(type, checkBoxVisibility, it) } }
    }

    fun setItemVisibility() {
        checkBoxVisibility = !checkBoxVisibility
        notifyDataSetChanged()
    }

    class LifeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.lr_title
        private val subTitle = view.lr_sub_title
        private val body = view.lr_body
        private val checkBox = view.lr_checkbox
        private lateinit var item: LifeRecord
        var itemList = hashMapOf<Long, String>()

        fun bind(type: String? = "", checkBoxVisibility: Boolean, item: LifeRecord) {
            this.item = item

            when(type) {
                LifeRecordDefine.AWARD -> {
                    title.text = item.title
                    subTitle.text = item.rank
                    body.text = item.area
                }
                LifeRecordDefine.CERTIFICATION -> {
                    title.text = item.title
                    subTitle.text = item.time_at
                    body.text = item.area
                }
                LifeRecordDefine.VOLUNTEER -> {
                    title.text = item.title
                    subTitle.text = item.from_date.toString()
                    body.text = item.area
                }
                LifeRecordDefine.GRADE -> {
                    title.text = item.title
                    subTitle.text = item.rank
                    body.text = item.semester
                }
            }
            if(checkBoxVisibility) {
                checkBox.visibility = View.VISIBLE
            } else {
                checkBox.visibility = View.GONE
            }
            Log.d("TEST", "Item -> title: ${item.title} / content: ${item.content} / organization: ${item.area}")
        }
    }
}
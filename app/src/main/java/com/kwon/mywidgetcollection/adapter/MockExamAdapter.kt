package com.kwon.mywidgetcollection.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.contains.ScreenDefine
import com.kwon.mywidgetcollection.entity.MockExamRecord
import com.kwon.mywidgetcollection.viewmodel.MockExamViewModel
import com.kwon.mywidgetcollection.viewmodel.ScreenViewModel
import kotlinx.android.synthetic.main.list_item_exam.view.*
import kotlinx.android.synthetic.main.list_item_record_pager.view.*
import java.text.SimpleDateFormat
import java.util.*

class MockExamAdapter constructor(var context: Context, var items:List<MockExamRecord>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        return VH(LayoutInflater.from(context).inflate(R.layout.list_item_exam, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position]?.let { item ->
            with(holder.itemView) {
                exam_title.text = item.title
                exam_create_at.text = SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.KOREAN).format(item.create_at)

                list_item_exam_layout.setOnClickListener {
                    MockExamViewModel.getInstance(context)?.let { vm ->
                        ScreenViewModel.getInstance(context)?.let { sc ->
                            vm.detail_data.postValue(item)
                            sc.screenStatus.postValue(ScreenDefine.MODE_DETAIL_FRAGMENT)
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
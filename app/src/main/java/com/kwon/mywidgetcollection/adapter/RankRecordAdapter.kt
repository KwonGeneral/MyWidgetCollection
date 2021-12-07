package com.kwon.mywidgetcollection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.data.RankRecord
import kotlinx.android.synthetic.main.list_item_record.view.*

class RankRecordAdapter : PagingDataAdapter<RankRecord, RankRecordAdapter.AwardsViewHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwardsViewHolder {
        return AwardsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_record, parent, false)
        )
    }

    companion object {
        private val Diff = object : DiffUtil.ItemCallback<RankRecord>() {
            override fun areItemsTheSame(oldItem: RankRecord, newItem: RankRecord): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RankRecord, newItem: RankRecord): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: AwardsViewHolder, position: Int) {
        getItem(position).let {
            Log.d("TEST", "Item -> $it")
            if (it != null) {
                holder.bind(it)
            }
        }
    }

    class AwardsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.name
        private val rank = view.rank
        private val organizationName = view.organization_name
        private val period = view.period
        private lateinit var item: RankRecord

        fun bind(item: RankRecord) {
            this.item = item
            name.text = item.rank_name
            rank.text = item.rank_period
            organizationName.text = item.rank_semester
            period.text = ""
        }
    }
}
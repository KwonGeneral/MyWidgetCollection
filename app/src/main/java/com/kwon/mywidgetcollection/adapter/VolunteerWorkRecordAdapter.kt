package com.kwon.mywidgetcollection.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.data.VolunteerWorkRecord
import kotlinx.android.synthetic.main.list_item_record.view.*

class VolunteerWorkRecordAdapter : PagingDataAdapter<VolunteerWorkRecord, VolunteerWorkRecordAdapter.VolunteerWorkViewHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerWorkViewHolder {
        return VolunteerWorkViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_record, parent, false)
        )
    }

    companion object {
        private val Diff = object : DiffUtil.ItemCallback<VolunteerWorkRecord>() {
            override fun areItemsTheSame(oldItem: VolunteerWorkRecord, newItem: VolunteerWorkRecord): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VolunteerWorkRecord, newItem: VolunteerWorkRecord): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: VolunteerWorkViewHolder, position: Int) {
        getItem(position).let {
            if (it != null) {
                holder.bind(it)
            }
        }
    }

    class VolunteerWorkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.name
        private val time = view.rank
        private val organizationName = view.organization_name
        private val period = view.period
        private lateinit var item: VolunteerWorkRecord

        fun bind(item: VolunteerWorkRecord) {
            this.item = item
            name.text = item.volunteer_work_name
            time.text = item.volunteer_work_time
            organizationName.text = item.volunteer_work_organization_name
            period.text = item.volunteer_work_period
        }
    }
}
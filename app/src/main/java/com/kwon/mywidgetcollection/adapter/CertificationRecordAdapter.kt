package com.kwon.mywidgetcollection.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.data.CertificationRecord
import kotlinx.android.synthetic.main.list_item_record.view.*

class CertificationRecordAdapter : PagingDataAdapter<CertificationRecord, CertificationRecordAdapter.CertificationViewHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificationViewHolder {
        return CertificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_record, parent, false)
        )
    }

    companion object {
        private val Diff = object : DiffUtil.ItemCallback<CertificationRecord>() {
            override fun areItemsTheSame(
                oldItem: CertificationRecord,
                newItem: CertificationRecord
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CertificationRecord,
                newItem: CertificationRecord
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CertificationViewHolder, position: Int) {
        getItem(position).let {
            if (it != null) {
                holder.bind(it)
            }
        }
    }

    class CertificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.name
        private val rank = view.rank
        private val organizationName = view.organization_name
        private val period = view.period
        private lateinit var item: CertificationRecord

        fun bind(item: CertificationRecord) {
            this.item = item
            name.text = item.certification_name
            rank.text = item.certification_period
            organizationName.text = item.certification_organization_name
            period.text = ""
        }
    }
}
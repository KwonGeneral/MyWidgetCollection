package com.kwon.mywidgetcollection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwon.mywidgetcollection.R
import kotlinx.android.synthetic.main.list_item_record_pager.view.*

class ViewPagerAdapter(adapterArrayList: ArrayList<LifeRecordAdapter>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = adapterArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.pagerRecycler.adapter = item[position]
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.list_item_record_pager, parent, false)){

        val pagerRecycler: RecyclerView = itemView.record_pager_recycler
    }
}
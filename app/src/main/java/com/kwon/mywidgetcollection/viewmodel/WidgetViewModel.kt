package com.kwon.mywidgetcollection.viewmodel

import android.content.Context
import com.kwon.mywidgetcollection.data.LinearTapItemData
import com.kwon.mywidgetcollection.data.MockExamItemData
import navigation.LinearTapItem

class WidgetViewModel(val context: Context) {

    /**
     * Custom LinearTap Widget 아이템 넣기
     */
    fun getLinearTapItemList(context: Context): List<LinearTapItem> {
        val list = mutableListOf<LinearTapItem>()
        list.add(LinearTapItem(context, LinearTapItemData("수상", "3회", "수상")))
        list.add(LinearTapItem(context, LinearTapItemData("자격증", "1개", "자격증")))
        list.add(LinearTapItem(context, LinearTapItemData("봉사활동", "22시간", "봉사활동")))
        list.add(LinearTapItem(context, LinearTapItemData("석차등급", "2등급", "석차등급")))

        return list
    }
}
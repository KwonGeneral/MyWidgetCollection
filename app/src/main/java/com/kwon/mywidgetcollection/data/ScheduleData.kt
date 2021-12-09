package com.kwon.mywidgetcollection.data

import com.kwon.mywidgetcollection.entity.ScheduleRecord

data class ScheduleData(var type: String? = "", var sub_type: String? = "", var title: String? = "", var content: String? = "", var from_date: Long? = 0, var to_date: Long? = 0) {
    companion object {
        fun parse(scheduleRecord: ScheduleRecord): ScheduleData {
            return ScheduleData().apply {
                type = scheduleRecord.type
                sub_type = scheduleRecord.sub_type
                title = scheduleRecord.title
                content = scheduleRecord.content
                from_date = scheduleRecord.from_date
                to_date = scheduleRecord.to_date
            }
        }

        fun build(scheduleData: ScheduleData): ScheduleRecord {
            return ScheduleRecord(
                id = null,
                type = scheduleData.type,
                sub_type = scheduleData.sub_type,
                title = scheduleData.title,
                content = scheduleData.content,
                from_date = scheduleData.from_date,
                to_date = scheduleData.to_date,
                create_at = System.currentTimeMillis()
            )
        }
    }
}
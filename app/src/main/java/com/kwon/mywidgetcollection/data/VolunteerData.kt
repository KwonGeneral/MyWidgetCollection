package com.kwon.mywidgetcollection.data

import com.kwon.mywidgetcollection.entity.LifeRecord
import java.util.*

data class VolunteerData(var type: String? = "", var sub_type: String? = "", var title: String? = "", var area: String? = "", var from_date: Long? = 0, var to_date: Long? = 0) {
    companion object {
        fun parse(lifeRecord: LifeRecord): VolunteerData {
            return VolunteerData().apply {
                type = lifeRecord.type
                sub_type = lifeRecord.sub_type
                title = lifeRecord.title
                area = lifeRecord.area
                from_date = lifeRecord.from_date
                to_date = lifeRecord.to_date
            }
        }

        fun build(volunteerData: VolunteerData):LifeRecord
        {
            return LifeRecord(
                id = null,
                type = volunteerData.type,
                sub_type = volunteerData.sub_type,
                title = volunteerData.title,
                area = volunteerData.area,
                from_date = volunteerData.from_date,
                to_date = volunteerData.to_date,
                create_at = 0
            )
        }
    }
}
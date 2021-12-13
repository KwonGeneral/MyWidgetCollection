package com.kwon.mywidgetcollection.data

import com.kwon.mywidgetcollection.entity.LifeRecord
import com.kwon.mywidgetcollection.utils.DateUtil
import java.time.LocalDateTime

data class AwardData(var type: String? = "", var sub_type: String? = "", var title: String? = "", var area: String? = "", var rank: String? = "") {
    companion object {
        fun parse(lifeRecord: LifeRecord): AwardData {
            return AwardData().apply {
                type = lifeRecord.type
                sub_type = lifeRecord.sub_type
                title = lifeRecord.title
                area = lifeRecord.area
                rank = lifeRecord.rank
            }
        }

        fun build(awardData: AwardData): LifeRecord {
            return LifeRecord(
                id = null,
                type = awardData.type,
                sub_type = awardData.sub_type,
                title = awardData.title,
                area = awardData.area,
                rank = awardData.rank,
                create_at = 0
            )
        }
    }
}
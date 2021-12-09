package com.kwon.mywidgetcollection.data

import com.kwon.mywidgetcollection.entity.LifeRecord
import com.kwon.mywidgetcollection.utils.DateUtil
import java.time.LocalDateTime

data class GradeData(var type: String? = "", var sub_type: String? = "", var title: String? = "", var rank: String? = "", var semester: String? = "") {
    companion object {
        fun parse(lifeRecord: LifeRecord): GradeData {
            return GradeData().apply {
                type = lifeRecord.type
                sub_type = lifeRecord.sub_type
                title = lifeRecord.title
                rank = lifeRecord.rank
                semester = lifeRecord.semester
            }
        }

        fun build(certificationData: GradeData):LifeRecord {
            return LifeRecord(
                id = null,
                type = certificationData.type,
                sub_type = certificationData.sub_type,
                title = certificationData.title,
                rank = certificationData.rank,
                semester = certificationData.semester,
                create_at = System.currentTimeMillis()
            )
        }
    }
}
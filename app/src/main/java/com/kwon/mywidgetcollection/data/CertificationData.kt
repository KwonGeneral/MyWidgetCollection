package com.kwon.mywidgetcollection.data

import com.kwon.mywidgetcollection.entity.LifeRecord
import java.util.*

data class CertificationData(var type: String? = "", var sub_type: String? = "", var title: String? = "", var area: String? = "", var time_at: Long? = 0, var from_date: Long? = 0, var to_date: Long? = 0) {
    companion object {
        fun parse(lifeRecord: LifeRecord): CertificationData {
            return CertificationData().apply {
                type = lifeRecord.type
                sub_type = lifeRecord.sub_type
                title = lifeRecord.title
                area = lifeRecord.area
                time_at = lifeRecord.time_at
                from_date = lifeRecord.from_date
                to_date = lifeRecord.to_date
            }
        }

        fun build(certificationData: CertificationData):LifeRecord {
            return LifeRecord(
                id = null,
                type = certificationData.type,
                sub_type = certificationData.sub_type,
                title = certificationData.title,
                area = certificationData.area,
                time_at = certificationData.time_at,
                from_date = certificationData.from_date,
                to_date = certificationData.to_date,
                create_at = 0
            )
        }
    }
}
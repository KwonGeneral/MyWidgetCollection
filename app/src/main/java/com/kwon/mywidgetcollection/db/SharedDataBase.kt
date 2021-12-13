package com.kwon.mywidgetcollection.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.SharedDefine
import com.kwon.mywidgetcollection.viewmodel.ScreenViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class SharedDataBase(context: Context, prefAppName: String?) :
    SharedPreferencesPropertyBackend(context, prefAppName) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: SharedDataBase? = null
        val prefAppName = "shared_db"
        fun getInstance(context: Context): SharedDataBase {
            instance?.let {
                return it
            }
            instance = SharedDataBase(context, prefAppName)
            return instance!!
        }
    }


    fun setCalendarDate(date: String) {
        setString(SharedDefine.Schedule.DATE, date)
    }

    fun getCalendarDate(): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        return getString(
            SharedDefine.Schedule.DATE,
            SimpleDateFormat(Default.DATE_FORMAT, Locale.KOREAN).format(cal.time)
        )
    }

}
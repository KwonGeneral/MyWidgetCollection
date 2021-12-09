package com.kwon.mywidgetcollection.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kwon.mywidgetcollection.activity.MainActivity
import com.kwon.mywidgetcollection.contains.ScreenDefine
import kotlinx.android.synthetic.main.activity_main.*

class ScreenViewModel(val context: Context) {
    var screenStatus = MutableLiveData<String>()

    init {
        screenStatus.postValue(ScreenDefine.SCHEDULE_FRAGMENT)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: ScreenViewModel? = null
        fun getInstance(context: Context): ScreenViewModel {
            instance?.let {
                return it
            }
            instance = ScreenViewModel(context)
            return instance!!
        }
    }
}

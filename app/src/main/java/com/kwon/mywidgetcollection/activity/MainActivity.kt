package com.kwon.mywidgetcollection.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kwon.mywidgetcollection.contains.ScreenDefine
import com.kwon.mywidgetcollection.viewmodel.ScreenViewModel
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ScreenViewModel.getInstance(this).screenStatus.observe(this, { fragment_name ->
            changeFragment(fragment_name)
        })
        main_nav.onItemClick = { action ->
            changeFragment(action)
        }
    }

    private fun changeFragment(fragment_type:String) {
        supportFragmentManager.beginTransaction().let { ft ->
            fragment_type.let { ty ->
                when (ty) {
                    ScreenDefine.SCHEDULE_FRAGMENT -> ScheduleFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                    ScreenDefine.LIFE_RECORD_FRAGMENT -> LifeRecordFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                    ScreenDefine.CALENDAR_FRAGMENT -> CalendarFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                    ScreenDefine.MODE_FRAGMENT -> ModeFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                    ScreenDefine.SETTING_FRAGMENT -> SettingFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                    else -> ScheduleFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                }
            }
        }
    }

}
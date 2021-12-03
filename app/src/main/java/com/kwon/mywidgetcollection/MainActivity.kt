package com.kwon.mywidgetcollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kwon.mywidgetcollection.contains.ScreenDefine
import com.kwon.mywidgetcollection.viewmodel.ScreenViewModel
import com.kwon.mywidgetcollection.R
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
                    ScreenDefine.HOME_FRAGMENT -> HomeFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                    ScreenDefine.TEST_FRAGMENT -> TestFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                    else -> HomeFragment().apply { ft.replace(R.id.main_frag, this).commit() }
                }
            }
        }
    }

}
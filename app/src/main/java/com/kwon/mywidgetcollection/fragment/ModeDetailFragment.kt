package com.kwon.mywidgetcollection.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.adapter.MockExamAdapter
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.ScreenDefine
import com.kwon.mywidgetcollection.viewmodel.MockExamViewModel
import com.kwon.mywidgetcollection.viewmodel.ScreenViewModel
import kotlinx.android.synthetic.main.fragment_mode.*


class ModeDetailFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mode_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MockExamViewModel.releaseInstace()
        MockExamViewModel.getInstance(requireContext())?.let { vm ->

        }
    }
}
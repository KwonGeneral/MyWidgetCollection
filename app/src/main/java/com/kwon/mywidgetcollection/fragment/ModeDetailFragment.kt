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
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.viewmodel.MockExamViewModel
import kotlinx.android.synthetic.main.fragment_mode_detail.*
import java.lang.IndexOutOfBoundsException
import java.text.SimpleDateFormat
import java.util.*


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

        MockExamViewModel.getInstance(requireContext())?.let { vm ->
            vm.detail_data.observe(viewLifecycleOwner, { data ->
                mode_detail_title.text = data.title
                mode_detail_create_at.text = SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.KOREAN).format(data.create_at)

                mode_detail_modify_btn.setOnClickListener {
                    for(k in 0 until data.getSubjectCount()) {
                        data.getSubject()?.subjects?.let {
                            when(k) {
                                0 -> {
                                    if(mode_detail_point_edit_1.text.toString() != Default.EMPTY_STR) {
                                        it[k].point = mode_detail_point_edit_1.text.toString().toInt()
                                        vm.modify(data, it[k])
                                    }
                                }
                                1 -> {
                                    if(mode_detail_point_edit_2.text.toString() != Default.EMPTY_STR) {
                                        it[k].point = mode_detail_point_edit_2.text.toString().toInt()
                                        vm.modify(data, it[k])
                                    }
                                }
                                2 -> {
                                    if(mode_detail_point_edit_3.text.toString() != Default.EMPTY_STR) {
                                        it[k].point = mode_detail_point_edit_3.text.toString().toInt()
                                        vm.modify(data, it[k])
                                    }
                                }
                                3 -> {
                                    if(mode_detail_point_edit_4.text.toString() != Default.EMPTY_STR) {
                                        it[k].point = mode_detail_point_edit_4.text.toString().toInt()
                                        vm.modify(data, it[k])
                                    }
                                }
                                4 -> {
                                    if(mode_detail_point_edit_5.text.toString() != Default.EMPTY_STR) {
                                        it[k].point = mode_detail_point_edit_5.text.toString().toInt()
                                        vm.modify(data, it[k])
                                    }
                                }
                            }
                        }
                    }
                }

                mode_detail_delete_btn.setOnClickListener {
                    vm.delete(data)
                    dismiss()
                }

                try {
                    mode_detail_subject_edit_1.text = data.getSubject()?.subjects?.get(0)?.name
                    mode_detail_point_edit_1.setText(data.getSubject()?.subjects?.get(0)?.point.toString())
                    mode_detail_subject_edit_2.text = data.getSubject()?.subjects?.get(1)?.name
                    mode_detail_point_edit_2.setText(data.getSubject()?.subjects?.get(1)?.point.toString())
                    mode_detail_subject_edit_3.text = data.getSubject()?.subjects?.get(2)?.name
                    mode_detail_point_edit_3.setText(data.getSubject()?.subjects?.get(2)?.point.toString())
                    mode_detail_subject_edit_4.text = data.getSubject()?.subjects?.get(3)?.name
                    mode_detail_point_edit_4.setText(data.getSubject()?.subjects?.get(3)?.point.toString())
                    mode_detail_subject_edit_5.text = data.getSubject()?.subjects?.get(4)?.name
                    mode_detail_point_edit_5.setText(data.getSubject()?.subjects?.get(4)?.point.toString())
                } catch (e: IndexOutOfBoundsException) {

                }

                when(data.getSubjectCount()) {
                    1 -> {
                        mode_detail_schedule_1.visibility = View.VISIBLE
                    }
                    2 -> {
                        mode_detail_schedule_1.visibility = View.VISIBLE
                        mode_detail_schedule_2.visibility = View.VISIBLE
                    }
                    3 -> {
                        mode_detail_schedule_1.visibility = View.VISIBLE
                        mode_detail_schedule_2.visibility = View.VISIBLE
                        mode_detail_schedule_3.visibility = View.VISIBLE
                    }
                    4 -> {
                        mode_detail_schedule_1.visibility = View.VISIBLE
                        mode_detail_schedule_2.visibility = View.VISIBLE
                        mode_detail_schedule_3.visibility = View.VISIBLE
                        mode_detail_schedule_4.visibility = View.VISIBLE
                    }
                    5 -> {
                        mode_detail_schedule_1.visibility = View.VISIBLE
                        mode_detail_schedule_2.visibility = View.VISIBLE
                        mode_detail_schedule_3.visibility = View.VISIBLE
                        mode_detail_schedule_4.visibility = View.VISIBLE
                        mode_detail_schedule_5.visibility = View.VISIBLE
                    }
                }

                Log.d("TEST", "???? 1 : ${data.subjects}")
                Log.d("TEST", "???? 2 : ${data.getSubjectCount()}")
            })

            mode_detail_title
        }
    }
}
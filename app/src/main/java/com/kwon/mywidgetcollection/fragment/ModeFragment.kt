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
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.adapter.MockExamAdapter
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.ScreenDefine
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.entity.MockExamRecord
import com.kwon.mywidgetcollection.viewmodel.MockExamViewModel
import com.kwon.mywidgetcollection.viewmodel.ScreenViewModel
import kotlinx.android.synthetic.main.fragment_mode.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.math.roundToLong


class ModeFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mode, container, false)
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
            vm.examData.observe(viewLifecycleOwner, {
                for(k in it) {
                    Log.d(
                        "TEST",
                        "전체 데이터 -> id: ${k.id} / title: ${k.title} / subjects: ${k.subjects} / create_at: ${k.create_at}"
                    )
                }
            })

            mode_setting_start_btn.setOnClickListener {
                vm.setMockTitle(mode_setting_layout_title_edit.text.toString())

                if(exam_subject_edit_1.text.toString() != Default.EMPTY_STR && exam_time_edit_1.text.toString() != Default.EMPTY_STR) {
                    vm.addMockExam(exam_subject_edit_1.text.toString(), exam_time_edit_1.text.toString().toLong())
                }
                if(exam_subject_edit_2.text.toString() != Default.EMPTY_STR && exam_time_edit_2.text.toString() != Default.EMPTY_STR) {
                    vm.addMockExam(exam_subject_edit_2.text.toString(), exam_time_edit_2.text.toString().toLong())
                }
                if(exam_subject_edit_3.text.toString() != Default.EMPTY_STR && exam_time_edit_3.text.toString() != Default.EMPTY_STR) {
                    vm.addMockExam(exam_subject_edit_3.text.toString(), exam_time_edit_3.text.toString().toLong())
                }
                if(exam_subject_edit_4.text.toString() != Default.EMPTY_STR && exam_time_edit_4.text.toString() != Default.EMPTY_STR) {
                    vm.addMockExam(exam_subject_edit_4.text.toString(), exam_time_edit_4.text.toString().toLong())
                }
                if(exam_subject_edit_5.text.toString() != Default.EMPTY_STR && exam_time_edit_5.text.toString() != Default.EMPTY_STR) {
                    vm.addMockExam(exam_subject_edit_5.text.toString(), exam_time_edit_5.text.toString().toLong())
                }

//                vm.timerRefresh()
                ScreenViewModel.getInstance(requireContext())?.screenStatus.value = ScreenDefine.MODE_START_FRAGMENT
            }
        }



//        MockExamViewModel(requireContext())?.let { vm ->
//            vm.timerData.observe(viewLifecycleOwner, { ob ->
//                ob?.let {  timerData ->
//
//                    now_subject_text.text = ob.timerSubject
//                    var sec:Long = ob.remain_time/1000
//                    var min:Long = Math.floor((sec/60).toDouble()).roundToLong()
//                    sec = sec - min*60
//
//                    timer_hour_text.text = ""+min+":"+sec
//                    Log.d("SHONZ",ob.timerSubject)
//                }
//            })
//
//            vm.tempMockExamRecord.observe(viewLifecycleOwner,{
//
//            })
//
//            vm.examData.observe(viewLifecycleOwner,{
//                for(k in it) {
//                    Log.d(
//                        "TEST",
//                        "전체 데이터 -> id: ${k.id} / title: ${k.title} / subjects: ${k.subjects} / create_at: ${k.create_at}"
//                    )
//                }
//            })
//
//            // 시험명 입력
//            vm.setMockTitle("3월 모의고사")
//
//            vm.addMockExam("국어", 10000)
//            vm.addMockExam("영어", 10000)
//            vm.addMockExam("수학", 10000)
//            vm.addMockExam("사회", 10000)
//            vm.addMockExam("과학", 10000)
//
//            mock_start_btn.setOnClickListener {
//                vm.timerStart()
//            }
//
//            mock_pause_btn.setOnClickListener {
//                vm.timerPause()
//            }
//
//            mock_next_btn.setOnClickListener {
//                vm.nextExam()
//            }
//            mock_modify_btn.setOnClickListener {
//                vm.examData.value?.get(0)?.let {
//                    it.getSubject()?.subjects?.get(0)?.let { item->
//                        item.point = 60
//                        Log.d("TEST","Modify data")
//                        vm.modify(it, item)  // 수정
//                    }
//                }
//            }
//            mock_stop_btn.setOnClickListener {
//                if(!vm.timerStop())
//                {
//                    Log.d("Shonz","실패")
//                }
//            }
//        }
    }
}
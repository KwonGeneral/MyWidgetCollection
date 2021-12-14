package com.kwon.mywidgetcollection.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.entity.MockExamRecord
import com.kwon.mywidgetcollection.viewmodel.MockExamViewModel
import kotlinx.android.synthetic.main.fragment_mode.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class ModeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST", "- 모드 프레그먼트 -")

        MockExamViewModel.getInstance(requireContext())?.let { vm ->
            vm.tempMockExamRecord.observe(viewLifecycleOwner, { ob ->
                Log.d("TEST", " 값 변동 : $ob")
            })

            vm.timerData.observe(viewLifecycleOwner, { ob ->
                ob?.let {  timerData ->
                    Log.d("Timer", "타이머 값 변동 : ${timerData.time}")
                    if(timerData.time == 0L) {
                        return@observe
                    }

                    var timerHour = timer_hour_text.text.toString().toInt()
                    var timerMin = timer_min_text.text.toString().toInt()
                    var timerSecond = timer_second_text.text.toString().toInt()

                    if (timerSecond != 0) {
                        timerSecond--
                    } else if (timerMin != 0) {
                        timerSecond = 60
                        timerSecond--
                        timerMin--
                    } else if (timerHour != 0) {
                        timerSecond = 60
                        timerMin = 60
                        timerSecond--
                        timerMin--
                        timerHour--
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        if (timerSecond <= 9) {
                            timer_second_text.text = "0$timerSecond"
                        } else {
                            timer_second_text.text = timerSecond.toString()
                        }
                        if (timerMin <= 9) {
                            timer_min_text.text = "0$timerMin"
                        } else {
                            timer_min_text.text = timerMin.toString()
                        }
                        if (timerHour <= 9) {
                            timer_hour_text.text = "0$timerHour"
                        } else {
                            timer_hour_text.text = timerHour.toString()
                        }
                    }
                }
            })

            vm.setMockTitle("3월 모의고사")
            vm.addMockExam("수학", 6000)
            vm.addMockExam("영어", 3000)
            vm.addMockExam("사회", 4000)

            mock_start_btn.setOnClickListener {
                vm.startMockExam()
            }

            mock_pause_btn.setOnClickListener {
                vm.pauseExam()
            }

            mock_stop_btn.setOnClickListener {
                for(k in RoomDataBase.getInstance(requireContext())?.mockExamRecordService()?.readAll(100)!!) {
//                    if(k.id == 0L) {  // 삭제
//                        vm.delete(k)
//                    }
//                    vm.modify(k, MockExamRecord.MockExamData("영어", 3000, 60))  // 수정
                    Log.d("TEST", "전체 데이터 -> id: ${k.id} / title: ${k.title} / subjects: ${k.subjects}")
                }

            }
        }

    }
}
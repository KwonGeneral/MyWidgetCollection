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

            // 시험명 입력
            vm.setMockTitle("3월 모의고사")
            Log.d("MOCK", "시험명 입력: 3월 모의고사")

            vm.addMockExam("국어", 1000)
            vm.addMockExam("영어", 2000)
            vm.addMockExam("수학", 3000)
            vm.addMockExam("사회", 1000)
            vm.addMockExam("과학", 2000)
            Log.d("MOCK", "시험 스케쥴 입력: 국어 1초 / 영어 2초 / 수학 3초 / 사회 1초 / 과학 2초")

            mock_start_btn.setOnClickListener {
                vm.startMockExam()
                Log.d("MOCK", "모의고사 시작하기")
            }

            mock_pause_btn.setOnClickListener {
                vm.pauseExam()
                Log.d("MOCK", "모의고사 일시정지")
            }

            mock_stop_btn.setOnClickListener {
                vm.examFinish()
                Log.d("MOCK", "모의고사 중지하기")
                for(k in RoomDataBase.getInstance(requireContext())?.mockExamRecordService()?.readAll(100)!!) {
//                    if(k.id == 0L) {  // 삭제
//                        vm.delete(k)
//                    }
//                    vm.modify(k, MockExamRecord.MockExamData("영어", 3000, 60))  // 수정
                    Log.d("TEST", "전체 데이터 -> id: ${k.id} / title: ${k.title} / subjects: ${k.subjects}")
                }
            }

            vm.tempMockExamRecord.observe(viewLifecycleOwner, { ob ->
                Log.d("TEST", "tempMockExamRecord 값 변동 : $ob")
            })

            vm.timerData.observe(viewLifecycleOwner, { ob ->
                if(now_subject_text.text.toString() != ob.timerSubjectName) {
                    now_subject_text.text = ob.timerSubjectName
                }

                ob?.let {  timerData ->
                    Log.d("Timer", "timerData 값 변동 : ${timerData.time}")
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
        }

    }
}
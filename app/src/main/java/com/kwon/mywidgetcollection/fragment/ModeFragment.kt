package com.kwon.mywidgetcollection.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kwon.mywidgetcollection.R
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

        var timerHour = timer_hour_text.text.toString().toInt()
        var timerMin = timer_min_text.text.toString().toInt()
        var timerSecond = timer_second_text.text.toString().toInt()

        var timer = Timer()

        mock_start_btn.setOnClickListener {
            //타이머를 실행
            Log.d("TEST", "타이머 시작!!")
            timer = Timer()
            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    // 반복실행할 구문

                    // 0초 이상이면
                    if (timerSecond != 0) {
                        //1초씩 감소
                        timerSecond--

                        // 0분 이상이면
                    } else if (timerMin != 0) {
                        // 1분 = 60초
                        timerSecond = 60
                        timerSecond--
                        timerMin--

                        // 0시간 이상이면
                    } else if (timerHour != 0) {
                        // 1시간 = 60분
                        timerSecond = 60
                        timerMin = 60
                        timerSecond--
                        timerMin--
                        timerHour--
                    }

                    //시, 분, 초가 10이하(한자리수) 라면
                    // 숫자 앞에 0을 붙인다 ( 8 -> 08 )
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

                    // 타이머를 종료
                    if (timerHour == 0 && timerMin == 0 && timerSecond == 0) {
                        timer.cancel() //타이머 종료
                        Log.d("TEST", "타이머 종료!!")
                    }
                }
            }
            timer.schedule(timerTask, 0, 1000); //Timer 실행
        }

        mock_pause_btn.setOnClickListener {
            timer.cancel()
            Log.d("TEST", "타이머 일시중지!!")
        }

        mock_stop_btn.setOnClickListener {
            timer.cancel()
            Log.d("TEST", "타이머 종료!!")
        }
    }
}
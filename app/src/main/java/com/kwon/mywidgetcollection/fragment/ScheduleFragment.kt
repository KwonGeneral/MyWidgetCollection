package com.kwon.mywidgetcollection.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.activity.MainActivity
import com.kwon.mywidgetcollection.adapter.SchedulePageAdapter
import com.kwon.mywidgetcollection.contains.Default
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.data.ScheduleData
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import com.kwon.mywidgetcollection.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment : Fragment() {
    var todayAdapter = SchedulePageAdapter(ScheduleDefine.TODAY)
    var todoAdapter = SchedulePageAdapter(ScheduleDefine.TODO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST", "- 스케쥴 프레그먼트 -")
        (context as MainActivity).test_schedule.isChecked = true
        var todayFrom:Long = SimpleDateFormat(Default.DATE_FORMAT, Locale.KOREAN).parse("2021-12-13 16:43")!!.time
        var todayTo:Long = SimpleDateFormat(Default.DATE_FORMAT, Locale.KOREAN).parse("2021-12-13 18:43")!!.time
        var todoFrom:Long = SimpleDateFormat(Default.DATE_FORMAT, Locale.KOREAN).parse("2021-12-12 10:43")!!.time
        var todoTo:Long = SimpleDateFormat(Default.DATE_FORMAT, Locale.KOREAN).parse("2021-12-12 13:43")!!.time

        calendar_btn_1.setOnClickListener {
            ScheduleViewModel(requireContext())?.option
        }
        calendar_btn_2.setOnClickListener {

        }
        calendar_btn_3.setOnClickListener {

        }


        ScheduleViewModel(requireContext())?.let { vm ->
            vm.update()
            vm.todayPagingData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { todayAdapter.submitData(it) }
                }
            })
            vm.todoPagingData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { todoAdapter.submitData(it) }
                }
            })
            today_recycler.adapter = todayAdapter
            todo_recycler.adapter = todoAdapter

            calendar_btn_1.setOnClickListener {
                vm.setDate(calendar_btn_1.text.toString() + " 00:00:00")
                vm.update()
            }
            calendar_btn_2.setOnClickListener {
                vm.setDate(calendar_btn_2.text.toString() + " 00:00:00")
                vm.update()
            }
            calendar_btn_3.setOnClickListener {
                vm.setDate(calendar_btn_3.text.toString() + " 00:00:00")
                vm.update()
            }

//            ScheduleViewModel(requireContext())?.let { vm ->
//                for(k in 1..300) {
//                    vm.insertScheduleRecord(
//                        ScheduleData.build(
//                            ScheduleData.parse(
//                                ScheduleRecord(
//                                    type = ScheduleDefine.TODAY,
//                                    title = "$k 테스트 일정",
//                                    content = "",
//                                    from_date = todayFrom,
//                                    to_date = todayTo,
//                                    status = 0
//                                )
//                            )
//                        )
//                    )
//                    vm.insertScheduleRecord(
//                        ScheduleData.build(
//                            ScheduleData.parse(
//                                ScheduleRecord(
//                                    type = ScheduleDefine.TODO,
//                                    title = "$k 테스트 할일",
//                                    content = "",
//                                    from_date = todoFrom,
//                                    to_date = todoTo,
//                                    status = 0
//                                )
//                            )
//                        )
//                    )
//                    todayFrom++
//                    todayTo++
//                    todoFrom++
//                    todoTo++
//                }
//            }
        }
    }
}
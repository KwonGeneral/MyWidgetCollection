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
import com.kwon.mywidgetcollection.adapter.ScheduleAdapter
import com.kwon.mywidgetcollection.adapter.SchedulePageAdapter
import com.kwon.mywidgetcollection.contains.ScheduleDefine
import com.kwon.mywidgetcollection.data.ScheduleData
import com.kwon.mywidgetcollection.entity.ScheduleRecord
import com.kwon.mywidgetcollection.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        var todayFrom:Long = 202112090000
        var todayTo:Long = 202112120000
        var todoFrom:Long = 202102050000
        var todoTo:Long = 202107080000

        ScheduleViewModel.getInstance(requireContext())?.let { vm ->
            today_normal_search_btn.setOnClickListener {
                todo_shuffle.visibility = View.VISIBLE
                today_shuffle.visibility = View.VISIBLE
                var todayItem: List<ScheduleRecord>
                var todayNormalAdapter: ScheduleAdapter? = null
                var todoItem: List<ScheduleRecord>
                var todoNormalAdapter: ScheduleAdapter? = null

                CoroutineScope(Dispatchers.IO).launch {
                    todayItem = vm.getScheduleRecordSearch(ScheduleDefine.TODAY, "", 202112090080, 202112120200, 60, "asc")!!
                    todayNormalAdapter = ScheduleAdapter(requireContext(), ScheduleDefine.TODAY, todayItem)
                    CoroutineScope(Dispatchers.Main).launch {
                        today_recycler.adapter = todayNormalAdapter
                    }
                }
                CoroutineScope(Dispatchers.IO).launch {
                    todoItem = vm.getScheduleRecordSearch(ScheduleDefine.TODO, "", 202102050010, 202107080090, 70, "desc")!!
                    todoNormalAdapter = ScheduleAdapter(requireContext(), ScheduleDefine.TODAY, todoItem)
                    CoroutineScope(Dispatchers.Main).launch {
                        todo_recycler.adapter = todoNormalAdapter
                    }
                }
                today_shuffle.setOnClickListener {
                    todayNormalAdapter?.let {
                        it.shuffle()
                    }
                }
                todo_shuffle.setOnClickListener {
                    todoNormalAdapter?.let {
                        it.shuffle()
                    }
                }
            }
            today_page_search_btn.setOnClickListener {
                todo_shuffle.visibility = View.GONE
                today_shuffle.visibility = View.GONE
                today_recycler.adapter = todayAdapter
                todo_recycler.adapter = todoAdapter
                vm.getScheduleRecordSearchPage(ScheduleDefine.TODAY, "", 202112090030, 202112120150, 40, "asc")
                vm.getScheduleRecordSearchPage(ScheduleDefine.TODO, "", 202102050070, 202107080210, 60, "desc")

                vm.todayData.observe(viewLifecycleOwner, { data ->
                    lifecycleScope.launch {
                        data.collectLatest { todayAdapter.submitData(it) }
                    }
                })
                vm.todoData.observe(viewLifecycleOwner, { data ->
                    lifecycleScope.launch {
                        data.collectLatest { todoAdapter.submitData(it) }
                    }
                })
            }
        }

//        ScheduleViewModel.getInstance(requireContext())?.let { vm ->
//            for(k in 1..300) {
//                vm.createScheduleRecord(
//                    ScheduleData.build(
//                        ScheduleData.parse(
//                            ScheduleRecord(
//                                type = ScheduleDefine.TODAY,
//                                title = "$k 테스트 일정",
//                                content = "",
//                                from_date = todayFrom,
//                                to_date = todayTo,
//                                status = 0
//                            )
//                        )
//                    )
//                )
//                vm.createScheduleRecord(
//                    ScheduleData.build(
//                        ScheduleData.parse(
//                            ScheduleRecord(
//                                type = ScheduleDefine.TODO,
//                                title = "$k 테스트 할일",
//                                content = "",
//                                from_date = todoFrom,
//                                to_date = todoTo,
//                                status = 0
//                            )
//                        )
//                    )
//                )
//                todayFrom++
//                todayTo++
//                todoFrom++
//                todoTo++
//            }
//        }
    }
}
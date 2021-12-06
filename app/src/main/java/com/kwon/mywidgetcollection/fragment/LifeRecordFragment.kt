package com.kwon.mywidgetcollection.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.adapter.AwardsRecordAdapter
import com.kwon.mywidgetcollection.adapter.CertificationRecordAdapter
import com.kwon.mywidgetcollection.adapter.RankRecordAdapter
import com.kwon.mywidgetcollection.adapter.VolunteerWorkRecordAdapter
import com.kwon.mywidgetcollection.data.LinearTapItemData
import com.kwon.mywidgetcollection.viewmodel.RecordViewModel
import kotlinx.android.synthetic.main.fragment_life_record.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import navigation.LinearTapItem

class LifeRecordFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_life_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST", "- 생활기록부 프레그먼트 -")

        val awardsAdapter = AwardsRecordAdapter()
        val certificationAdapter = CertificationRecordAdapter()
        val volunteerWorkAdapter = VolunteerWorkRecordAdapter()
        val rankAdapter = RankRecordAdapter()

        RecordViewModel(requireContext())?.let { vm ->
            for(v in vm.getLinearTapItemList(requireContext())) {
                title_container.addView(v)
            }

            title_container.onItemClick = {
                when(it) {
                    "수상" -> {
                        life_record_recycler.adapter = awardsAdapter
                        vm.getAwardsRecordByPage()

                        item_create_btn.setOnClickListener {
                            vm.createAwardsRecord(vm.getAwardsRecord(
                                "정보올림피아드대회(경시부문)",
                                "동상(4위)",
                                "대원고등학교",
                                "2020.04.07"
                            ))
                        }

                        vm.awardsRecordData.observe(viewLifecycleOwner, { ob ->
                            lifecycleScope.launch {
                                ob?.collectLatest { data ->
                                    awardsAdapter.submitData(data)
                                }
                            }
                        })
                    }

                    "자격증" -> {
                        life_record_recycler.adapter = certificationAdapter
                        vm.getCertificationRecordByPage()

                        item_create_btn.setOnClickListener {
                            vm.createCertificationRecord(vm.getCertificationRecord(
                                "정보처리기능사",
                                "한국산업인력공단",
                                "2020.08.12",
                            ))
                        }

                        vm.certificationRecordData.observe(viewLifecycleOwner, { ob ->
                            lifecycleScope.launch {
                                ob?.collectLatest { data ->
                                    certificationAdapter.submitData(data)
                                }
                            }
                        })
                    }

                    "봉사활동" -> {
                        life_record_recycler.adapter = volunteerWorkAdapter
                        vm.getVolunteerWorkRecordByPage()

                        item_create_btn.setOnClickListener {
                            vm.createVolunteerWorkRecord(vm.getVolunteerWorkRecord(
                                "쓰레기 줍기 및 주변 정리",
                                "롯데월드",
                                "4시간",
                                "2019.03.02"
                            ))
                        }

                        vm.volunteerWorkRecordData.observe(viewLifecycleOwner, { ob ->
                            lifecycleScope.launch {
                                ob?.collectLatest { data ->
                                    volunteerWorkAdapter.submitData(data)
                                }
                            }
                        })
                    }

                    "석차등급" -> {
                        life_record_recycler.adapter = rankAdapter
                        vm.getRankRecordByPage()

                        item_create_btn.setOnClickListener {
                            vm.createRankRecord(vm.getRankRecord(
                                "2등급",
                                "1학년 2학기 기말고사",
                                "2021.11.10",
                            ))
                        }

                        vm.rankRecordData.observe(viewLifecycleOwner, { ob ->
                            lifecycleScope.launch {
                                ob?.collectLatest { data ->
                                    rankAdapter.submitData(data)
                                }
                            }
                        })
                    }
                }
            }


        }

//        val adapter = ScheduleAdapter()
//        sc_recycler.adapter = adapter
//        ScheduleViewModel(requireContext())?.let { vm ->
//            vm.getScheduleByPage()?.let { ob ->
//                lifecycleScope.launch {
//                    ob?.collectLatest {
//                        adapter.submitData(it)
//                    }
//                }
//            }
//        }
    }
}
package com.kwon.mywidgetcollection.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.adapter.*
import com.kwon.mywidgetcollection.data.LinearTapItemData
import com.kwon.mywidgetcollection.viewmodel.RecordViewModel
import kotlinx.android.synthetic.main.fragment_life_record.*
import kotlinx.android.synthetic.main.list_item_record_pager.*
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

        var firstCheck = true
        for(k in title_container.children) {
            if(firstCheck) {
                (k as LinearTapItem).isChecked = true
            }
            firstCheck = false
        }

        RecordViewModel(requireContext())?.let { vm ->
            vm.getAwardsRecordByPage()
            vm.getCertificationRecordByPage()
            vm.getVolunteerWorkRecordByPage()
            vm.getRankRecordByPage()

            vm.awardsRecordData.observe(viewLifecycleOwner, { ob ->
                lifecycleScope.launch {
                    ob?.collectLatest { data ->
                        awardsAdapter.submitData(data)
                    }
                }
            })
            vm.certificationRecordData.observe(viewLifecycleOwner, { ob ->
                lifecycleScope.launch {
                    ob?.collectLatest { data ->
                        certificationAdapter.submitData(data)
                    }
                }
            })
            vm.volunteerWorkRecordData.observe(viewLifecycleOwner, { ob ->
                lifecycleScope.launch {
                    ob?.collectLatest { data ->
                        volunteerWorkAdapter.submitData(data)
                    }
                }
            })
            vm.rankRecordData.observe(viewLifecycleOwner, { ob ->
                lifecycleScope.launch {
                    ob?.collectLatest { data ->
                        rankAdapter.submitData(data)
                    }
                }
            })

            life_record_viewpager.offscreenPageLimit = 1
            val k = arrayListOf(awardsAdapter, certificationAdapter, volunteerWorkAdapter, rankAdapter)
            life_record_viewpager.adapter = ViewPagerAdapter(k)
            life_record_viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            spring_dots_indicator.setViewPager2(life_record_viewpager)
            spring_dots_indicator.setDotIndicatorColor(Color.parseColor("#333333"))
            spring_dots_indicator.setStrokeDotsIndicatorColor(Color.parseColor("#999999"))

            life_record_viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
//                    Log.d("TEST", "1onPageScrollStateChanged state : $state")
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                    Log.d("TEST", "2onPageScrolled position : $position")
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
//                    Log.d("TEST", "3onPageSelected position : $position")
                    var count = 0
                    for(v in title_container.children) {
                        (v as LinearTapItem).itemInit(v)

                        if(count == position) {
                            (v as LinearTapItem).isChecked = true
                        }
                        count++
                    }
                }
            })



            for(v in vm.getLinearTapItemList(requireContext())) {
                title_container.addView(v)
            }

            title_container.onItemClick = {
            when(it) {
                "수상" -> {
//                        life_record_recycler.adapter = awardsAdapter
                    vm.getAwardsRecordByPage()
                    life_record_viewpager.setCurrentItem(0, false)

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
//                        life_record_recycler.adapter = certificationAdapter
                    vm.getCertificationRecordByPage()
                    life_record_viewpager.setCurrentItem(1, false)

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
//                        life_record_recycler.adapter = volunteerWorkAdapter
                    vm.getVolunteerWorkRecordByPage()
                    life_record_viewpager.setCurrentItem(2, false)

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
//                        life_record_recycler.adapter = rankAdapter
                    vm.getRankRecordByPage()
                    life_record_viewpager.setCurrentItem(3, false)

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
                } }
            }
        }
    }
}
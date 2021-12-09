package com.kwon.mywidgetcollection.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.kwon.mywidgetcollection.R
import com.kwon.mywidgetcollection.adapter.*
import com.kwon.mywidgetcollection.contains.LifeRecordDefine
import com.kwon.mywidgetcollection.data.AwardData
import com.kwon.mywidgetcollection.data.CertificationData
import com.kwon.mywidgetcollection.data.GradeData
import com.kwon.mywidgetcollection.data.VolunteerData
import com.kwon.mywidgetcollection.entity.LifeRecord
import com.kwon.mywidgetcollection.viewmodel.RecordViewModel
import kotlinx.android.synthetic.main.fragment_life_record.*
import kotlinx.android.synthetic.main.list_item_record.view.*
import kotlinx.android.synthetic.main.list_item_record_pager.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import navigation.LinearTapItem

class LifeRecordFragment : Fragment() {
    private var awardAdapter = LifeRecordAdapter(LifeRecordDefine.AWARD)
    private var certificationAdapter = LifeRecordAdapter(LifeRecordDefine.CERTIFICATION)
    private var volunteerAdapter = LifeRecordAdapter(LifeRecordDefine.VOLUNTEER)
    private var gradeAdapter = LifeRecordAdapter(LifeRecordDefine.GRADE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_life_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST", "- 생활기록부 프레그먼트 -")

        var firstCheck = true
        for(k in title_container.children) {
            if(firstCheck) {
                (k as LinearTapItem).isChecked = true
            }
            firstCheck = false
        }

        RecordViewModel?.getInstance(requireContext())?.let { vm ->
            for(v in vm.getLinearTapItemList(requireContext())) {
                title_container.addView(v)
            }
//            vm.getLifeRecordByPage(LifeRecordDefine.ALL)
            for(k in vm.getLifeRecordSearch(LifeRecordDefine.VOLUNTEER, "", 202112090000, 202112120050, 10, "asc")!!) {
                Log.d("TEST", "일반 검색 -> from : ${k.from_date} / to : ${k.to_date}")
            }
            vm.getLifeRecordSearchPage(LifeRecordDefine.VOLUNTEER, "", 202112090000, 202112120050, 10, "desc")

            vm.awardData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { awardAdapter.submitData(it) }
                }
            })
            vm.certificationData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { certificationAdapter.submitData(it) }
                }
            })
            vm.volunteerData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { volunteerAdapter.submitData(it) }
                }
            })
            vm.gradeData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { gradeAdapter.submitData(it) }
                }
            })

            life_record_viewpager.offscreenPageLimit = 1
            val k = arrayListOf(awardAdapter, certificationAdapter, volunteerAdapter, gradeAdapter)
            life_record_viewpager.adapter = ViewPagerAdapter(k)
            life_record_viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            spring_dots_indicator.setViewPager2(life_record_viewpager)
            spring_dots_indicator.setDotIndicatorColor(R.color.normal_black)
            spring_dots_indicator.setStrokeDotsIndicatorColor(R.color.pink)

//            var from:Long = 202112090000
//            var to:Long = 202112120000
//            for(k in 1..300) {
//                vm.createLifeRecord(VolunteerData.build(
//                    VolunteerData.parse(LifeRecord(
//                        type = LifeRecordDefine.VOLUNTEER,
//                        title = "$k 테스트 제목",
//                        area = "$k 테스트 장소",
//                        from_date = from,
//                        to_date = to
//                    ))
//                ))
//                from++
//                to++
//            }

            item_select_btn.setOnClickListener {
                awardAdapter.setItemVisibility()
                certificationAdapter.setItemVisibility()
                volunteerAdapter.setItemVisibility()
                gradeAdapter.setItemVisibility()
            }

            life_record_viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    var count = 0
                    for(v in title_container.children) {
                        (v as LinearTapItem).itemInit(v)
                        if(count == position) {
                            (v as LinearTapItem).isChecked = true
                        }
                        count++
                    }
                    when(position) {
                        0 -> {
                            item_create_btn.setOnClickListener {
                                vm.createLifeRecord(AwardData.build(
                                    AwardData.parse(LifeRecord(
                                        type = LifeRecordDefine.AWARD,
                                        title = "2_정보올림피아드대회(경시부문)",
                                        rank = "2_동상(4위)",
                                        area = "2_대원고등학교",
                                    ))
                                ))
                            }
                        }
                        1 -> {
                            item_create_btn.setOnClickListener {
                                vm.createLifeRecord(CertificationData.build(
                                    CertificationData.parse(LifeRecord(
                                        type = LifeRecordDefine.CERTIFICATION,
                                        title = "2_정보처리기능사",
                                        time_at = "2020.08.12",
                                        area = "2_한국산업인력공단",
                                    ))
                                ))
                            }
                        }
                        2 -> {
                            item_create_btn.setOnClickListener {
                                vm.createLifeRecord(VolunteerData.build(
                                    VolunteerData.parse(LifeRecord(
                                        type = LifeRecordDefine.VOLUNTEER,
                                        title = "2_쓰레기 줍기 및 주변 정리",
                                        area = "2_롯데월드",
//                                        from_date = from,
//                                        to_date = to
                                    ))
                                ))
                            }
                        }
                        3 -> {
                            item_create_btn.setOnClickListener {
                                vm.createLifeRecord(GradeData.build(
                                    GradeData.parse(LifeRecord(
                                        type = LifeRecordDefine.GRADE,
                                        title = "2_석차등급 입력",
                                        rank = "2_2등급",
                                        semester = "2_1학년 2학기 기말고사",
                                    ))
                                ))
                            }
                        }
                    }
                }
            })



            title_container.onItemClick = {
                when(it) {
                    "수상" -> {
                        life_record_viewpager.setCurrentItem(0, false)
                    }

                    "자격증" -> {
                        life_record_viewpager.setCurrentItem(1, false)
                    }

                    "봉사활동" -> {
                        life_record_viewpager.setCurrentItem(2, false)
                    }

                    "석차등급" -> {
                        life_record_viewpager.setCurrentItem(3, false)
                    }
                }
            }

        }
    }
}
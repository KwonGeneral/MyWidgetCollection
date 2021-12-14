package com.kwon.mywidgetcollection.fragment

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
import com.kwon.mywidgetcollection.contains.LifeDefine
import com.kwon.mywidgetcollection.data.*
import com.kwon.mywidgetcollection.db.RoomDataBase
import com.kwon.mywidgetcollection.entity.LifeRecord
import com.kwon.mywidgetcollection.viewmodel.LifeRecordViewModel
import com.kwon.mywidgetcollection.viewmodel.WidgetViewModel
import kotlinx.android.synthetic.main.fragment_life_record.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import navigation.LinearTapItem
import java.text.SimpleDateFormat
import java.util.*

class LifeRecordFragment : Fragment() {
    private var awardAdapter = LifeRecordAdapter(LifeDefine.AWARD)
    private var certificationAdapter = LifeRecordAdapter(LifeDefine.CERTIFICATION)
    private var volunteerAdapter = LifeRecordAdapter(LifeDefine.VOLUNTEER)
    private var gradeAdapter = LifeRecordAdapter(LifeDefine.GRADE)

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
        for(tap_item in title_container.children) {
            if(firstCheck) {
                (tap_item as LinearTapItem).isChecked = true
            }
            firstCheck = false
        }

        for(tap_view in WidgetViewModel(requireContext()).getLinearTapItemList(requireContext())) {
            title_container.addView(tap_view)
        }

        LifeRecordViewModel(requireContext())?.let { vm ->
            vm.update()
            vm.awardPagingData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { awardAdapter.submitData(it) }
                }
            })
            vm.certificationPagingData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { certificationAdapter.submitData(it) }
                }
            })
            vm.volunteerPagingData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { volunteerAdapter.submitData(it) }
                }
            })
            vm.gradePagingData.observe(viewLifecycleOwner, { data ->
                lifecycleScope.launch {
                    data.collectLatest { gradeAdapter.submitData(it) }
                }
            })

            vm.awardData.observe(viewLifecycleOwner, { data ->
                Log.d("TEST", "awardData : $data")
            })

            life_record_viewpager.offscreenPageLimit = 1
            val adapterList = arrayListOf(awardAdapter, certificationAdapter, volunteerAdapter, gradeAdapter)
            life_record_viewpager.adapter = ViewPagerAdapter(adapterList)
            life_record_viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            spring_dots_indicator.setViewPager2(life_record_viewpager)
            spring_dots_indicator.setDotIndicatorColor(R.color.normal_black)
            spring_dots_indicator.setStrokeDotsIndicatorColor(R.color.pink)

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
                                vm.insertLifeRecord(AwardData.build(
                                    AwardData.parse(LifeRecord(
                                        type = LifeDefine.AWARD,
                                        title = "2_정보올림피아드대회(경시부문)",
                                        rank = "2_동상(4위)",
                                        area = "2_대원고등학교",
                                    ))
                                ))
                            }
                        }
                        1 -> {
                            item_create_btn.setOnClickListener {
                                vm.insertLifeRecord(CertificationData.build(
                                    CertificationData.parse(LifeRecord(
                                        type = LifeDefine.CERTIFICATION,
                                        title = "2_정보처리기능사",
                                        time_at = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).parse("2020-08-12")!!.time,
                                        area = "2_한국산업인력공단",
                                    ))
                                ))
                            }
                        }
                        2 -> {
                            item_create_btn.setOnClickListener {
                                vm.insertLifeRecord(VolunteerData.build(
                                    VolunteerData.parse(LifeRecord(
                                        type = LifeDefine.VOLUNTEER,
                                        title = "2_쓰레기 줍기 및 주변 정리",
                                        area = "2_롯데월드",
                                        from_date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN).parse("2020-07-16 13:33")!!.time,
                                        to_date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN).parse("2020-07-16 18:40")!!.time
                                    ))
                                ))
                            }
                        }
                        3 -> {
                            item_create_btn.setOnClickListener {
                                vm.insertLifeRecord(GradeData.build(
                                    GradeData.parse(LifeRecord(
                                        type = LifeDefine.GRADE,
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
        }
    }
}
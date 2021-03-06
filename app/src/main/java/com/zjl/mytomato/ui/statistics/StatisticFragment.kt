package com.zjl.mytomato.ui.statistics

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentStatisticBinding
import com.zjl.mytomato.setGone
import com.zjl.mytomato.setVisiable
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticVm>() {

    private val calendar = Calendar.getInstance()
    private val mColors = mutableListOf<Int>()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun initUi(): FragmentStatisticBinding {
        vm.getDayAppUsedTime(context!!)
        return FragmentStatisticBinding.inflate(layoutInflater).apply {
            calendar.add(Calendar.DAY_OF_MONTH, 0)
            val sdf = SimpleDateFormat("yyyy年MM月dd日")
            var date = sdf.format(calendar.time)
            val today = date
            tvDate.text = date
            nestScrollView.isNestedScrollingEnabled = false
            ivPreviousDay.setOnClickListener {
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                date = sdf.format(calendar.time)
                tvDate.text = date
                vm.run {
                    getNumByDate(date)
                    getTimeByDate(date)
                    getPieChartData(date)
                }
            }
            ivNextDay.setOnClickListener {
                if (today != date) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    date = sdf.format(calendar.time)
                    tvDate.text = date
                    with(vm) {
                        getNumByDate(date)
                        getTimeByDate(date)
                        getPieChartData(date)
                    }
                } else {
                    Toast.makeText(context, "无法查看未来的记录哦", Toast.LENGTH_SHORT).show()
                }
            }
            pieChart.apply {
                setUsePercentValues(true)
                description.isEnabled = false
                setExtraOffsets(5f, 10f, 5f, 5f)
                dragDecelerationFrictionCoef = 0.95f
                isDrawHoleEnabled = false
                setHoleColor(Color.WHITE)
                setTransparentCircleColor(Color.WHITE)
                setTransparentCircleAlpha(110)
                holeRadius = 58f
                transparentCircleRadius = 61f
                setDrawCenterText(true)
                rotationAngle = 0f
                isRotationEnabled = true
                isHighlightPerTapEnabled = true
                animateY(1400, Easing.EaseInOutQuad)
                legend.apply {
                    verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                    orientation = Legend.LegendOrientation.VERTICAL
                    setDrawInside(false)
                    xEntrySpace = 7f
                    yEntrySpace = 0f
                    yOffset = 0f
                }
                setEntryLabelColor(Color.WHITE)
                setEntryLabelTextSize(12f)
            }
            radioDayWeek.setOnRadioCheckedListener { index ->
                when (index) {
                    0 -> {
                        vm.getWeekAppUsedTime(context!!)
                    }
                    1 -> {
                        vm.getDayAppUsedTime(context!!)
                    }
                }
            }
        }
    }

    override fun initViewModel(): StatisticVm {
        return ViewModelProvider(this).get(StatisticVm::class.java)
    }

    override fun subscribe() {
        vm.finishTodoNum.observe(this, {
            ui.apply {
                tvCount.text = it.toString()
            }
        })
        vm.totalTime.observe(this, {
            if (it != null) {
                ui.tvTimeLength.text = it.toString()
            } else {
                ui.tvTimeLength.text = "0"
            }
        })
        vm.averageTime.observe(this, {
            if (it != null) {
                ui.tvAvgTimeLength.text = it.toString()
            } else {
                ui.tvAvgTimeLength.text = "0"
            }

        })
        vm.dayNum.observe(this, {
            ui.tvTodayCount.text = it.toString()
        })
        vm.dayTime.observe(this, {
            if (it != null) {
                ui.tvTodayTimeLength.text = it.toString()
            } else {
                ui.tvTodayTimeLength.text = "0"
            }

        })
        vm.pieChartDate.observe(this, {
            if (it.isNotEmpty()) {
                ui.apply {
                    tvEmpty.setGone()
                    pieChart.setVisiable()
                    pieChart.apply {
                        data = getPieChartData(it)
                        highlightValues(null)
                        invalidate()
                    }
                }
            } else {
                ui.apply {
                    tvEmpty.setVisiable()
                    pieChart.setGone()
                }
            }


        })
        vm.barChartData.observe(this, {
            ui.apply {
                if (it.isEmpty()) {
                    tvEmptyBar.setVisiable()
                    barChart.setGone()
                } else {
                    tvEmptyBar.setGone()
                    barChart.setVisiable()
                    var totalTime = 0L
                    val usedTime = mutableMapOf<String, Float>()
                    it.map { kv ->
                        totalTime += kv.value
                    }
                    for (item in it) {
                        usedTime[item.key] = item.value / totalTime.toFloat()
                    }
                    barChart.setTotalTime(totalTime)
                    barChart.setData(usedTime)
                }
            }

        })
        vm.phoneWeekUsedTime.observe(this, {
            ui.lineGraph.setData(it)
        })
        vm.focusWeekTime.observe(this, {
            ui.lineGraphFocus.setData(it)
        })
    }

    private fun getPieChartData(datas: Map<String, Int>?): PieData {
        val entries = mutableListOf<PieEntry>()
        if (datas != null) {
            for (item in datas) {
                entries.add(PieEntry(item.value.toFloat(), item.key))
            }
        }
        val pieDataSet = PieDataSet(entries, "").apply {
            setDrawIcons(false)
            sliceSpace = 3f
            iconsOffset = MPPointF(0f, 40f)
            selectionShift = 5f
            colors = mColors
        }
        return PieData(pieDataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(11f)
            setValueTextColor(Color.WHITE)
        }

    }

    @SuppressLint("SimpleDateFormat")
    override fun init() {
        calendar.add(Calendar.DAY_OF_MONTH, 0)
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        val date = sdf.format(calendar.time)
        for (c in ColorTemplate.COLORFUL_COLORS) mColors.add(c)
        mColors.add(ColorTemplate.getHoloBlue())
        vm.getFinishTodoNum()
        vm.getTotalTime()
        vm.getTotalAverageTime()
        vm.getNumByDate(date)
        vm.getTimeByDate(date)
        vm.getPieChartData(date)
        vm.getWeekPhoneUsedTime(context!!)
        vm.getWeekFocusTime(context!!)
    }


}
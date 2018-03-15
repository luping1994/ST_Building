package net.suntrans.building.analysis

import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import net.suntrans.building.R
import net.suntrans.building.api.Api
import net.suntrans.building.api.RetrofitHelper
import net.suntrans.building.chart.MyMarkerView
import net.suntrans.building.databinding.FragmentAnalysisBinding
import java.util.ArrayList

/**
 * Created by Looney on 2018/3/13.
 * Des:
 */

object AnalysisFragmentHeper {
    fun initChart(binding: FragmentAnalysisBinding?, mTfLight: Typeface?) {

        initchart4(binding, mTfLight!!)
        initchart3(binding, mTfLight!!)
        initchart5(binding, mTfLight!!)
        initchart6(binding, mTfLight!!)

//        getChart1Data()
    }

    private fun initchart3(binding: FragmentAnalysisBinding?, mTfLight: Typeface) {

        binding!!.chart3.setDrawBarShadow(false)
        binding!!.chart3.setDrawValueAboveBar(true)
        binding!!.chart3.getAxisRight().setEnabled(false)
        binding!!.chart3.setTouchEnabled(true)
        binding!!.chart3.getDescription().setEnabled(false)
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        binding!!.chart3.setMaxVisibleValueCount(60)
        binding!!.chart3.setBackgroundColor(Color.LTGRAY)

        // scaling can now only be done on x- and y-axis separately
        binding!!.chart3.setPinchZoom(true)
        binding!!.chart3.setDrawGridBackground(false)
        // binding!!.chart3.setDrawYLabels(false);

//        val xAxisFormatter = DayAxisValueFormatter(binding!!.chart3, DayAxisValueFormatter.DAYS)

        val xAxis = binding!!.chart3.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setTypeface(mTfLight)
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f) // only intervals of 1 day
        xAxis.setLabelCount(12)
        xAxis.textSize = 5f


        val leftAxis = binding!!.chart3.getAxisLeft()
        leftAxis.setTypeface(mTfLight)
        leftAxis.setLabelCount(8, false)
        leftAxis.textSize = 5f
//        leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(15f)
        leftAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)
//
//        YAxis rightAxis = binding!!.chart3.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        val l = binding!!.chart3.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        l.setDrawInside(false)
        l.setForm(Legend.LegendForm.SQUARE)
        l.setFormSize(9f)
        l.setTextSize(5f)
        l.setXEntrySpace(4f)

//        setData3(binding,mTfLight)

    }

    private fun initchart7(binding: FragmentAnalysisBinding?, mTfLight: Typeface) {

        binding!!.chart7.setDrawBarShadow(false)
        binding!!.chart3.setDrawValueAboveBar(true)
        binding!!.chart3.getAxisRight().setEnabled(false)
        binding!!.chart3.setTouchEnabled(true)
        binding!!.chart3.getDescription().setEnabled(false)
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        binding!!.chart3.setMaxVisibleValueCount(60)
        binding!!.chart3.setBackgroundColor(Color.LTGRAY)

        // scaling can now only be done on x- and y-axis separately
        binding!!.chart3.setPinchZoom(true)
        binding!!.chart3.setDrawGridBackground(false)
        // binding!!.chart3.setDrawYLabels(false);

//        val xAxisFormatter = DayAxisValueFormatter(binding!!.chart3, DayAxisValueFormatter.DAYS)

        val xAxis = binding!!.chart3.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setTypeface(mTfLight)
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f) // only intervals of 1 day
        xAxis.setLabelCount(12)
        xAxis.textSize = 5f


        val leftAxis = binding!!.chart3.getAxisLeft()
        leftAxis.setTypeface(mTfLight)
        leftAxis.setLabelCount(8, false)
        leftAxis.textSize = 5f
//        leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(15f)
        leftAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)
//
//        YAxis rightAxis = binding!!.chart3.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        val l = binding!!.chart3.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        l.setDrawInside(false)
        l.setForm(Legend.LegendForm.SQUARE)
        l.setFormSize(9f)
        l.setTextSize(5f)
        l.setXEntrySpace(4f)

//        setData3(binding,mTfLight)

    }


    private fun initchart4(binding: FragmentAnalysisBinding?, mTfLight: Typeface) {

//        binding!!.chart4.setOnChartValueSelectedListener(this)

        // no description text
        binding!!.chart4.getDescription().setEnabled(false)

        // enable touch gestures
        binding!!.chart4.setTouchEnabled(true)

        binding!!.chart4.setDragDecelerationFrictionCoef(0.9f)

        // enable scaling and dragging
        binding!!.chart4.setDragEnabled(true)
        binding!!.chart4.setScaleEnabled(true)
        binding!!.chart4.setDrawGridBackground(false)
        binding!!.chart4.setHighlightPerDragEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        binding!!.chart4.setPinchZoom(true)

        // set an alternative background color
        binding!!.chart4.setBackgroundColor(Color.LTGRAY)

        // add data

        binding!!.chart4.animateX(2500)

        // get the legend (only possible after setting data)
        val l = binding!!.chart4.getLegend()

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = mTfLight
        l.textSize = 5f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
//        l.setYOffset(5f);

        val xAxis = binding!!.chart4.getXAxis()
        xAxis.typeface = mTfLight
        xAxis.textSize = 5f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = binding!!.chart4.getAxisLeft()
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.textSize = 5f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        binding!!.chart4.axisRight.isEnabled = false

    }

    private fun initchart5(binding: FragmentAnalysisBinding?, mTfLight: Typeface) {

//        binding!!.chart4.setOnChartValueSelectedListener(this)

        // no description text
        binding!!.chart5.getDescription().setEnabled(false)

        // enable touch gestures
        binding!!.chart5.setTouchEnabled(true)

        binding!!.chart5.setDragDecelerationFrictionCoef(0.9f)

        // enable scaling and dragging
        binding!!.chart5.setDragEnabled(true)
        binding!!.chart5.setScaleEnabled(true)
        binding!!.chart5.setDrawGridBackground(false)
        binding!!.chart5.setHighlightPerDragEnabled(true)


        binding!!.chart5.axisRight.isEnabled = false

        // if disabled, scaling can be done on x- and y-axis separately
        binding!!.chart5.setPinchZoom(true)

        // set an alternative background color
        binding!!.chart5.setBackgroundColor(Color.LTGRAY)

        // add data

        binding!!.chart5.animateX(2500)

        // get the legend (only possible after setting data)
        val l = binding!!.chart5.getLegend()

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = mTfLight
        l.textSize = 5f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
//        l.setYOffset(5f);

        val xAxis = binding!!.chart5.getXAxis()
        xAxis.typeface = mTfLight
        xAxis.textSize = 5f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = binding!!.chart5.getAxisLeft()
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.textSize = 5f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true


    }

    private fun initchart6(binding: FragmentAnalysisBinding?, mTfLight: Typeface) {

//        binding!!.chart4.setOnChartValueSelectedListener(this)

        // no description text
        binding!!.chart6.getDescription().setEnabled(false)

        // enable touch gestures
        binding!!.chart6.setTouchEnabled(true)

        binding!!.chart6.setDragDecelerationFrictionCoef(0.9f)

        // enable scaling and dragging
        binding!!.chart6.setDragEnabled(true)
        binding!!.chart6.setScaleEnabled(true)
        binding!!.chart6.setDrawGridBackground(false)
        binding!!.chart6.setHighlightPerDragEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        binding!!.chart6.setPinchZoom(true)

        // set an alternative background color
        binding!!.chart6.setBackgroundColor(Color.LTGRAY)

        // add data

        binding!!.chart6.animateX(2500)

        // get the legend (only possible after setting data)
        val l = binding!!.chart6.getLegend()

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = mTfLight
        l.textSize = 5f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
//        l.setYOffset(5f);

        val xAxis = binding!!.chart6.getXAxis()
        xAxis.typeface = mTfLight
        xAxis.textSize = 5f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = binding!!.chart6.getAxisLeft()
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.textSize = 5f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

//        val rightAxis = binding!!.chart6.axisRight
//        rightAxis.typeface = mTfLight
//        rightAxis.textColor = Color.RED
//        rightAxis.textSize = 5f
//
//        rightAxis.axisMaximum = 40f
//        rightAxis.axisMinimum = -2f
//        rightAxis.setDrawGridLines(false)
//        rightAxis.setDrawZeroLine(false)
//        rightAxis.isGranularityEnabled = false
        binding!!.chart4.axisRight.isEnabled = false


    }


    val MATERIAL_COLORS = intArrayOf(rgb("#cc7832"))


}

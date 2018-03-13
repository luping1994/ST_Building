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
    }

    private fun initchart3(binding: FragmentAnalysisBinding?, mTfLight: Typeface) {

        binding!!.chart3.setDrawBarShadow(false)
        binding!!.chart3.setDrawValueAboveBar(true)
        binding!!.chart3.getAxisRight().setEnabled(false)
        binding!!.chart3.setTouchEnabled(false)
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
        xAxis.textSize = 30f

//        xAxis.setValueFormatter(xAxisFormatter);
//        val custom = MyAxisValueFormatter()

        val leftAxis = binding!!.chart3.getAxisLeft()
        leftAxis.setTypeface(mTfLight)
        leftAxis.setLabelCount(8, false)
        leftAxis.textSize = 30f
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
        l.setTextSize(30f)
        l.setXEntrySpace(4f)

        setData3(binding,mTfLight)

    }

    private fun setData3(binding: FragmentAnalysisBinding?, mTfLight: Typeface) {
        val start = 1f

        val yVals1 = ArrayList<BarEntry>()
        for (i in 1..7) {
            var `val` = 0f

            `val` = (20+i).toFloat()
            yVals1.add(BarEntry(i.toFloat(), `val`))
        }


        val set1: BarDataSet

        if (binding!!.chart3.getData() != null && binding!!.chart3.getData().getDataSetCount() > 0) {
            set1 = binding!!.chart3.getData().getDataSetByIndex(0) as BarDataSet
            set1.values = yVals1
            binding!!.chart3.getData().notifyDataChanged()
            binding!!.chart3.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(yVals1, "")
            set1.setColors(*MATERIAL_COLORS)
            set1.setDrawValues(false)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTypeface(mTfLight)
            data.barWidth = 0.8f

            binding!!.chart3.setData(data)
        }
        binding!!.chart3.invalidate()
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
        setData(24, 30f, binding)

        binding!!.chart4.animateX(2500)

        // get the legend (only possible after setting data)
        val l = binding!!.chart4.getLegend()

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = mTfLight
        l.textSize = 30f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
//        l.setYOffset(11f);

        val xAxis = binding!!.chart4.getXAxis()
        xAxis.typeface = mTfLight
        xAxis.textSize = 30f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = binding!!.chart4.getAxisLeft()
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = 0f
        leftAxis.textSize = 30f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        val rightAxis = binding!!.chart4.axisRight
        rightAxis.typeface = mTfLight
        rightAxis.textColor = Color.RED
        rightAxis.textSize = 30f

        rightAxis.axisMaximum = 40f
        rightAxis.axisMinimum = -10f
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.isGranularityEnabled = false

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

        // if disabled, scaling can be done on x- and y-axis separately
        binding!!.chart5.setPinchZoom(true)

        // set an alternative background color
        binding!!.chart5.setBackgroundColor(Color.LTGRAY)

        // add data
        setData5(24, 30f, binding)

        binding!!.chart5.animateX(2500)

        // get the legend (only possible after setting data)
        val l = binding!!.chart5.getLegend()

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = mTfLight
        l.textSize = 30f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
//        l.setYOffset(11f);

        val xAxis = binding!!.chart5.getXAxis()
        xAxis.typeface = mTfLight
        xAxis.textSize = 30f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = binding!!.chart5.getAxisLeft()
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = 0f
        leftAxis.textSize = 30f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        val rightAxis = binding!!.chart5.axisRight
        rightAxis.typeface = mTfLight
        rightAxis.textColor = Color.RED
        rightAxis.textSize = 30f

        rightAxis.axisMaximum = 40f
        rightAxis.axisMinimum = -10f
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.isGranularityEnabled = false

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
        setData6(24, 30f, binding)

        binding!!.chart6.animateX(2500)

        // get the legend (only possible after setting data)
        val l = binding!!.chart6.getLegend()

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = mTfLight
        l.textSize = 30f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
//        l.setYOffset(11f);

        val xAxis = binding!!.chart6.getXAxis()
        xAxis.typeface = mTfLight
        xAxis.textSize = 30f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = binding!!.chart6.getAxisLeft()
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = 0f
        leftAxis.textSize = 30f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        val rightAxis = binding!!.chart6.axisRight
        rightAxis.typeface = mTfLight
        rightAxis.textColor = Color.RED
        rightAxis.textSize = 30f

        rightAxis.axisMaximum = 40f
        rightAxis.axisMinimum = -10f
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.isGranularityEnabled = false

    }

    private fun setData(count: Int, range: Float, binding: FragmentAnalysisBinding?) {

        val yVals1 = ArrayList<Entry>()

        for (i in 0 until count) {
            val mult = range / 2f
            val `val` = (Math.random() * mult).toFloat() + 50
            yVals1.add(Entry(i.toFloat(), `val`))
        }

        val yVals2 = ArrayList<Entry>()

        for (i in 0 until count - 1) {
            val `val` = (Math.random() * range).toFloat() + 70
            yVals2.add(Entry(i.toFloat(), `val`))
            //            if(i == 10) {
            //                yVals2.add(new Entry(i, val + 50));
            //            }
        }

        val yVals3 = ArrayList<Entry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            yVals3.add(Entry(i.toFloat(), `val`))
        }

        val set1: LineDataSet
        val set2: LineDataSet
        val set3: LineDataSet

        if (binding!!.chart4.getData() != null && binding!!.chart4.getData().getDataSetCount() > 0) {
            set1 = binding!!.chart4.data.getDataSetByIndex(0) as LineDataSet
            set2 = binding!!.chart4.getData().getDataSetByIndex(1) as LineDataSet
            set3 = binding!!.chart4.getData().getDataSetByIndex(2) as LineDataSet
            set1.values = yVals1
            set2.values = yVals2
            set3.values = yVals3
            binding!!.chart4.getData().notifyDataChanged()
            binding!!.chart4.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals1, "A相电压")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            set1.setDrawValues(false)
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = LineDataSet(yVals2, "B相电压")
            set2.axisDependency = YAxis.AxisDependency.LEFT
            set2.color = Color.RED
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.setDrawValues(false)

            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = LineDataSet(yVals3, "C相电压")
            set3.axisDependency = YAxis.AxisDependency.LEFT
            set3.color = Color.YELLOW
            set3.setCircleColor(Color.WHITE)
            set3.lineWidth = 2f
            set3.circleRadius = 3f
            set3.fillAlpha = 65
            set3.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            set3.setDrawCircleHole(false)
            set3.highLightColor = Color.rgb(244, 117, 117)
            set3.setDrawValues(false)

            // create a data object with the datasets
            val data = LineData(set1, set2, set3)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)

            // set data
            binding!!.chart4.setData(data)
        }
    }

    private fun setData6(count: Int, range: Float, binding: FragmentAnalysisBinding?) {

        val yVals1 = ArrayList<Entry>()

        for (i in 0 until count) {
            val mult = range / 2f
            val `val` = (Math.random() * mult).toFloat() + 50
            yVals1.add(Entry(i.toFloat(), `val`))
        }

        val yVals2 = ArrayList<Entry>()

        for (i in 0 until count - 1) {
            val `val` = (Math.random() * range).toFloat() + 70
            yVals2.add(Entry(i.toFloat(), `val`))
            //            if(i == 10) {
            //                yVals2.add(new Entry(i, val + 50));
            //            }
        }

        val yVals3 = ArrayList<Entry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            yVals3.add(Entry(i.toFloat(), `val`))
        }

        val set1: LineDataSet
        val set2: LineDataSet
        val set3: LineDataSet

        if (binding!!.chart6.getData() != null && binding!!.chart6.getData().getDataSetCount() > 0) {
            set1 = binding!!.chart6.data.getDataSetByIndex(0) as LineDataSet
            set2 = binding!!.chart6.getData().getDataSetByIndex(1) as LineDataSet
            set3 = binding!!.chart6.getData().getDataSetByIndex(2) as LineDataSet
            set1.values = yVals1
            set2.values = yVals2
            set3.values = yVals3
            binding!!.chart6.getData().notifyDataChanged()
            binding!!.chart6.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals1, "A相功率")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            set1.setDrawValues(false)
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = LineDataSet(yVals2, "B相功率")
            set2.axisDependency = YAxis.AxisDependency.LEFT
            set2.color = Color.RED
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.setDrawValues(false)

            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = LineDataSet(yVals3, "C相功率")
            set3.axisDependency = YAxis.AxisDependency.LEFT
            set3.color = Color.YELLOW
            set3.setCircleColor(Color.WHITE)
            set3.lineWidth = 2f
            set3.circleRadius = 3f
            set3.fillAlpha = 65
            set3.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            set3.setDrawCircleHole(false)
            set3.highLightColor = Color.rgb(244, 117, 117)
            set3.setDrawValues(false)

            // create a data object with the datasets
            val data = LineData(set1, set2, set3)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)

            // set data
            binding!!.chart6.setData(data)
        }
    }

    private fun setData5(count: Int, range: Float, binding: FragmentAnalysisBinding?) {

        val yVals1 = ArrayList<Entry>()

        for (i in 0 until count) {
            val mult = range / 2f
            val `val` = (Math.random() * mult).toFloat() + 50
            yVals1.add(Entry(i.toFloat(), `val`))
        }

        val yVals2 = ArrayList<Entry>()

        for (i in 0 until count - 1) {
            val `val` = (Math.random() * range).toFloat() + 70
            yVals2.add(Entry(i.toFloat(), `val`))
            //            if(i == 10) {
            //                yVals2.add(new Entry(i, val + 50));
            //            }
        }

        val yVals3 = ArrayList<Entry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            yVals3.add(Entry(i.toFloat(), `val`))
        }

        val set1: LineDataSet
        val set2: LineDataSet
        val set3: LineDataSet

        if (binding!!.chart5.getData() != null && binding!!.chart5.getData().getDataSetCount() > 0) {
            set1 = binding!!.chart5.data.getDataSetByIndex(0) as LineDataSet
            set2 = binding!!.chart5.getData().getDataSetByIndex(1) as LineDataSet
            set3 = binding!!.chart5.getData().getDataSetByIndex(2) as LineDataSet
            set1.values = yVals1
            set2.values = yVals2
            set3.values = yVals3
            binding!!.chart5.getData().notifyDataChanged()
            binding!!.chart5.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals1, "A相电流")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            set1.setDrawValues(false)
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = LineDataSet(yVals2, "B相电流")
            set2.axisDependency = YAxis.AxisDependency.LEFT
            set2.color = Color.RED
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.setDrawValues(false)

            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = LineDataSet(yVals3, "C相电流")
            set3.axisDependency = YAxis.AxisDependency.LEFT
            set3.color = Color.YELLOW
            set3.setCircleColor(Color.WHITE)
            set3.lineWidth = 2f
            set3.circleRadius = 3f
            set3.fillAlpha = 65
            set3.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            set3.setDrawCircleHole(false)
            set3.highLightColor = Color.rgb(244, 117, 117)
            set3.setDrawValues(false)

            // create a data object with the datasets
            val data = LineData(set1, set2, set3)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)

            // set data
            binding!!.chart5.setData(data)
        }
    }

    val MATERIAL_COLORS = intArrayOf(rgb("#cc7832"))

}

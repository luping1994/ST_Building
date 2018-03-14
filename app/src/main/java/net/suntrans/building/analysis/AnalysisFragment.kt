package net.suntrans.building.analysis

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import net.suntrans.building.BasedFragment
import net.suntrans.building.R
import net.suntrans.building.databinding.FragmentAnalysisBinding
import java.util.ArrayList

/**
 * Created by Looney on 2018/2/6.
 * Des:分析片段
 */

class AnalysisFragment : BasedFragment(){

    protected var mTfRegular: Typeface?=null
    protected var mTfLight: Typeface?=null
    var binding:FragmentAnalysisBinding? = null

    private var itemHeight: Int = 0
    private var itemWidth: Int = 0
    private var itemPadding: Int = 0
    private var itemPaddingTop: Int = 0


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentAnalysisBinding>(inflater, R.layout.fragment_analysis, container, false)

        return binding!!.root

    }

    protected var mMonths = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")

    protected var mParties = arrayOf("员工区", "生产部", "会议室", "机房", "Party E", "Party F", "Party G", "Party H", "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z")

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mTfRegular = Typeface.createFromAsset(context.assets, "OpenSans-Regular.ttf")
        mTfLight = Typeface.createFromAsset(context.assets, "OpenSans-Light.ttf")
        getItemHeightByPercent()

        initChart1()
        initChart2()

        AnalysisFragmentHeper.initChart(binding,mTfLight)
        //

//        setUpWebview(binding!!.webView)
//        binding!!.webView.loadUrl("http://172.16.1.187:8080/SuntransDemo/energy.html?_ijt=5eqbvnlek33ol4erguuj758a1i")

    }

    private fun setUpWebview(webview: WebView) {
        val settings = webview.settings

        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setGeolocationEnabled(true)
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = true
        settings.displayZoomControls = false

        webview.setInitialScale(0)
        webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webview.webViewClient = WebViewClient()
        webview.isVerticalScrollBarEnabled = false

        val localWebSettings = webview.settings
        localWebSettings.javaScriptEnabled = true
        localWebSettings.javaScriptCanOpenWindowsAutomatically = true
        localWebSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

        webview.isHorizontalScrollBarEnabled = false//水平不显示


    }
    override fun onDestroy() {
        super.onDestroy()
    }


    //通过屏幕尺寸的百分比设置
    private fun getItemHeightByPercent() {
        //获取屏幕宽度
        val metric = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metric)

        itemWidth = metric.widthPixels
        itemHeight = (metric.widthPixels.toFloat() * 9 / 16).toInt()
        itemPadding = itemWidth / 25
        itemPaddingTop = itemWidth / 15
//        println("item高度为$itemHeight,屏幕宽度为${metric.widthPixels}")
        //给recyclerview动态设置一个paddingbottom
        binding!!.chart1.setPadding(0, 0, 0, itemPaddingTop)
        binding!!.chart2.setPadding(0, 0, 0, itemPaddingTop)
        binding!!.chart1.layoutParams.height = itemHeight
        binding!!.chart2.layoutParams.height = itemHeight
    }


    private fun initChart2() {

        binding!!.chart2.setUsePercentValues(true)
        binding!!.chart2.description.isEnabled = false
        binding!!.chart2.setExtraOffsets(5f, 10f, 5f, 5f)

        binding!!.chart2.dragDecelerationFrictionCoef = 0.95f

        binding!!.chart2.setCenterTextTypeface(mTfLight)
        binding!!.chart2.centerText = "能耗分布图"

        binding!!.chart2.isDrawHoleEnabled = true
        binding!!.chart2.setHoleColor(Color.WHITE)

        binding!!.chart2.setTransparentCircleColor(Color.WHITE)
        binding!!.chart2.setTransparentCircleAlpha(110)

        binding!!.chart2.holeRadius = 58f
        binding!!.chart2.transparentCircleRadius = 61f

        binding!!.chart2.setDrawCenterText(true)

        binding!!.chart2.rotationAngle = 0f
        // enable rotation of the chart by touch
        binding!!.chart2.isRotationEnabled = true
        binding!!.chart2.isHighlightPerTapEnabled = true

        // binding!!.chart2.setUnit(" €");
        // binding!!.chart2.setDrawUnitsInChart(true);

        // add a selection listener
//        binding!!.chart2.setOnChartValueSelectedListener(this)

        setData2(4, 100f)

        binding!!.chart2.animateY(1400, Easing.EasingOption.EaseInOutQuad)
        // binding!!.chart2.spin(2000, 0, 360);

        val l = binding!!.chart2.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(20f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        binding!!.chart2.setEntryLabelColor(Color.WHITE)
        binding!!.chart2.setEntryLabelTypeface(mTfRegular)
        binding!!.chart2.setEntryLabelTextSize(5f)
    }

    private fun initChart1() {

//        binding!!.chart1.setOnChartValueSelectedListener(this)

        // no description text
        binding!!.chart1.getDescription().setEnabled(false)

        // enable touch gestures
        binding!!.chart1.setTouchEnabled(true)

        binding!!.chart1.setDragDecelerationFrictionCoef(0.9f)

        // enable scaling and dragging
        binding!!.chart1.setDragEnabled(true)
        binding!!.chart1.setScaleEnabled(true)
        binding!!.chart1.setDrawGridBackground(false)
        binding!!.chart1.setHighlightPerDragEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        binding!!.chart1.setPinchZoom(true)

        // set an alternative background color
        binding!!.chart1.setBackgroundColor(Color.LTGRAY)

        // add data
        setData(24, 5f)

        binding!!.chart1.animateX(2500)

        // get the legend (only possible after setting data)
        val l = binding!!.chart1.getLegend()

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

        val xAxis = binding!!.chart1.getXAxis()
        xAxis.typeface = mTfLight
        xAxis.textSize = 5f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = binding!!.chart1.getAxisLeft()
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = 0f
        leftAxis.textSize = 5f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        val rightAxis = binding!!.chart1.axisRight
        rightAxis.typeface = mTfLight
        rightAxis.textColor = Color.RED
        rightAxis.textSize = 5f

        rightAxis.axisMaximum = 40f
        rightAxis.axisMinimum = -10f
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.isGranularityEnabled = false

    }

    private fun setData2(count: Int, range: Float) {

        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(PieEntry((Math.random() * range + range / 5).toFloat(), mParties[i % mParties.size]))
        }

        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.sliceSpace = 1f
        dataSet.selectionShift = 1f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(5f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(mTfLight)
        binding!!.chart2.setData(data)

        // undo all highlights
        binding!!.chart2.highlightValues(null)

        binding!!.chart2.invalidate()
    }
    private fun setData(count: Int, range: Float) {

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

        if (binding!!.chart1.getData() != null && binding!!.chart1.getData().getDataSetCount() > 0) {
            set1 = binding!!.chart1.data.getDataSetByIndex(0) as LineDataSet
            set2 = binding!!.chart1.getData().getDataSetByIndex(1) as LineDataSet
            set3 = binding!!.chart1.getData().getDataSetByIndex(2) as LineDataSet
            set1.values = yVals1
            set2.values = yVals2
            set3.values = yVals3
            binding!!.chart1.getData().notifyDataChanged()
            binding!!.chart1.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals1, "今日用电量")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 1f
            set1.circleRadius = 1f
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
            set2 = LineDataSet(yVals2, "昨日用电量")
            set2.axisDependency = YAxis.AxisDependency.LEFT
            set2.color = Color.RED
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 1f
            set2.circleRadius = 1f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.setDrawValues(false)

            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = LineDataSet(yVals3, "电表温度")
            set3.axisDependency = YAxis.AxisDependency.LEFT
            set3.color = Color.YELLOW
            set3.setCircleColor(Color.WHITE)
            set3.lineWidth = 1f
            set3.circleRadius = 1f
            set3.fillAlpha = 65
            set3.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            set3.setDrawCircleHole(false)
            set3.highLightColor = Color.rgb(244, 117, 117)
            set3.setDrawValues(false)

            // create a data object with the datasets
            val data = LineData(set1, set2, set3)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(2f)

            // set data
            binding!!.chart1.setData(data)
        }
    }

    private fun generateCenterSpannableText(): SpannableString {

        val s = SpannableString("昨日能耗分布图(kW·h)")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 7, 0)
//        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
//        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
//        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
//        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 7, s.length, 0)
        return s
    }

}

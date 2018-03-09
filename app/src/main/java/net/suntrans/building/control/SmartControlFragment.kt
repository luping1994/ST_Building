package net.suntrans.building.control

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import net.suntrans.building.App
import net.suntrans.building.BasedFragment
import net.suntrans.building.R
import net.suntrans.building.databinding.FragmentControlBinding
import net.suntrans.building.domin.MyArea
import net.suntrans.building.vedio.camhi.HiDataValue
import net.suntrans.building.widget.PercentTextView

/**
 * Created by Looney on 2018/2/6.
 * Des:智能控制片段
 */

class SmartControlFragment : BasedFragment() {

    private var webview: WebView? = null
    private var house_id: String? = null
    private var token: String? = null

    private var binding: FragmentControlBinding? = null


    //recyclerView Item的高度
    private var itemHeight: Int = 0
    private var itemWidth: Int = 0
    private var itemPadding: Int = 0
    private var itemPaddingTop: Int = 0

    //区域数据集合
    private val datas: MutableList<MyArea> = ArrayList()

    //recyclerview Adapter

    private var adapter: AreaAdapter1? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentControlBinding>(inflater, R.layout.fragment_control, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        house_id = "1"
        token = "Bearer " + App.getSharedPreferences().getString("access_token", "-1")
        webview = view!!.findViewById(R.id.webView) as WebView

        getItemHeightByPercent()

        getAreaData()
        adapter = AreaAdapter1(R.layout.item_area, datas)
        binding!!.recyclerView.adapter =adapter

    }

    private fun getAreaData() {

        var area1  = MyArea()
        area1.id="3"
        area1.name="展厅"
        area1.imgResId =R.drawable.zhanting

        datas.add(area1)

        var area2  = MyArea()
        area2.id="3"
        area2.name="会议室"
        area2.imgResId =R.drawable.huiyishi

        datas.add(area2)

        var area3  = MyArea()
        area3.id="3"
        area3.name="员工区"
        area3.imgResId =R.drawable.yuangong

        datas.add(area3)

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
        binding!!.recyclerView.setPadding(0, 0, 0, itemPaddingTop)
    }


    override fun onDestroy() {
        webview!!.destroy()
        super.onDestroy()
    }


    //RecyclerView adapter
    private inner class AreaAdapter1(layoutResId: Int, data: MutableList<MyArea>) : RecyclerView.Adapter<VH>() {
        private val resId = layoutResId
        private val cameraLists = data

        init {

        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
            val view = LayoutInflater.from(activity.applicationContext)
                    .inflate(resId, parent, false)
            view.layoutParams.height = itemHeight
            view.layoutParams.width = itemWidth
            view.setPadding(itemPadding, itemPaddingTop, itemPadding, 0)

           var name1 =  view.findViewById<PercentTextView>(R.id.name1)
            name1.layoutParams.height = itemHeight/3
            name1.layoutParams.width = itemWidth/2
            return VH(view)
        }

        override fun getItemCount(): Int {

            return datas!!.size
        }

        override fun onBindViewHolder(holder: VH?, position: Int) {

            holder!!.setData(position)

        }

    }

    //ViewHolder
    private inner class VH(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var image: ImageView? = null

        init {
            name = itemView!!.findViewById(R.id.name1)
            image = itemView!!.findViewById(R.id.snapshot)
            itemView.setOnClickListener {

                var intent = Intent(activity,AreaPlanActivity::class.java)
                intent.putExtra("title",datas[adapterPosition].name)
                intent.putExtra("house_id",datas[adapterPosition].id)
                startActivity(intent)
            }
        }

        fun setData(position: Int) {
            name!!.text = datas[position].name
            image!!.setImageResource(datas[position].imgResId)
        }
    }
}

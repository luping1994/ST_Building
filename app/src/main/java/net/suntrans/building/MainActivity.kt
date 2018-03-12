package net.suntrans.building

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.TextView
import com.hichip.base.HiLog
import com.hichip.sdk.HiChipSDK
import com.pgyersdk.Pgy
import com.pgyersdk.update.PgyUpdateManager
import net.suntrans.building.BuildConfig.DEBUG
import net.suntrans.building.adapter.FragmentAdapter
import net.suntrans.building.databinding.ActivityMainBinding
import net.suntrans.building.analysis.AnalysisFragment
import net.suntrans.building.control.SmartControlFragment
import net.suntrans.building.vedio.VedioFragment
import net.suntrans.building.vedio.camhi.HiDataValue
import net.suntrans.building.widget.PercentTextView


class MainActivity : BasedActivity() {
    private var binding: ActivityMainBinding? = null
    private val TAB_TITLES = intArrayOf(R.string.nav_vedio, R.string.nav_control, R.string.nav_analysis)
    private val TAB_IMGS = intArrayOf(R.drawable.select_home, R.drawable.select_home, R.drawable.select_home)

    private val TAG_VEDIO = "vedio"
    private val TAG_CONTROL = "control"
    private val TAG_ANALYSIS = "analysis"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setTabAndViewpager()
        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metric)
        val logoPadding = metric.widthPixels / 35
        binding!!.logo.setPadding(logoPadding, logoPadding, logoPadding, logoPadding)

//        var s: Long = Runtime.getRuntime().maxMemory()
//        s = s / 1024 / 1024
//        println("$s mb")

        if (!DEBUG)
            PgyUpdateManager.register(this, Config.FILE_PROVIDER)
    }

    private fun setTabAndViewpager() {

        initSDK()

        for (i in TAB_TITLES.indices) {
            val tab = binding!!.tabLayout.newTab()
            val view = layoutInflater!!.inflate(R.layout.tab, null, false)
            val image = view.findViewById<ImageView>(R.id.img_tab)
            val tv = view.findViewById<PercentTextView>(R.id.tv_tab)
            tv.setText(TAB_TITLES[i])
            image.setImageResource(TAB_IMGS[i])
            tab.customView = view
            binding!!.tabLayout.addTab(tab)
        }

        val adapter = FragmentAdapter(supportFragmentManager)
        val vedioFragment = VedioFragment()
        val smartControlFragment = SmartControlFragment()
        val analysisFragment = AnalysisFragment()

        adapter.addFragment(vedioFragment, TAG_VEDIO)
        adapter.addFragment(smartControlFragment, TAG_CONTROL)
        adapter.addFragment(analysisFragment, TAG_ANALYSIS)
        binding!!.viewPager.adapter = adapter
        binding!!.viewPager.offscreenPageLimit = 3
        binding!!.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                binding!!.tabLayout.getTabAt(position)!!.select()
            }

        })


        binding!!.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding!!.viewPager.currentItem = tab!!.position
            }
        })
    }

    // 初始化SDK
    private fun initSDK() {
        HiChipSDK.init(object : HiChipSDK.HiChipInitCallback {

            override fun onSuccess() {

            }

            override fun onFali(arg0: Int, arg1: Int) {

            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        for (camera in HiDataValue.CameraList) {
            // camera.registerIOSessionListener(MainActivity.this);

            camera.disconnect()
        }

        HiChipSDK.uninit()

        val pid = android.os.Process.myPid()
        android.os.Process.killProcess(pid)
    }
}

package net.suntrans.building.control

import android.content.DialogInterface
import android.content.Intent.getIntent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import net.suntrans.building.App
import net.suntrans.building.BasedFragment
import net.suntrans.building.R
import net.suntrans.building.databinding.FragmentControlBinding

/**
 * Created by Looney on 2018/2/6.
 * Des:智能控制片段
 */

class SmartControlFragment : BasedFragment() {

    private var webview: WebView? = null
    private var house_id: String? = null
    private var token: String? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentControlBinding>(inflater, R.layout.fragment_control, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        house_id ="1"
        token = "Bearer " + App.getSharedPreferences().getString("access_token", "-1")
        webview = view!!.findViewById(R.id.webView) as WebView
        setUpWebview(webview!!)

        webview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                loadJs()
            }
        }


        webview!!.loadUrl("file:///android_asset/SuntransDemo/floor_plan.html")
        webview!!.addJavascriptInterface(AndroidtoJs(), "control")
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

    private fun loadJs() {
        var js = ""
        js += "var newscript = document.createElement(\"script\");"
        js += "newscript.src=\"./js/designer.js\";"
        js += ("newscript.onload=function(){"
                + "init(\""
                + token + "\",\""
                + house_id + "\");};")
        js += "document.body.appendChild(newscript);"

        //                System.out.println(js);
        webview!!.loadUrl("javascript:$js")
    }

    // 继承自Object类
    inner class AndroidtoJs : Any() {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        fun switchChannel(control: String) {
            val split = control.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val channel_id = split[0]
            val title = split[1]
            val status = if (split[2] == "1") "关闭" else "打开"
            val datapoint = split[3]
            val din = split[4]
            //            System.out.println(datapoint + "," + din);
            AlertDialog.Builder(activity)
                    .setMessage("是否$status$title")
                    .setPositiveButton("确定") { dialog, which ->
                        val cmd = if (status == "打开") "1" else "0"
//                        sendCmd(channel_id, datapoint, din, cmd)
                    }
                    .setNegativeButton("取消", null).create().show()
        }
    }

    override fun onDestroy() {
        webview!!.destroy()
        super.onDestroy()
    }
}

package net.suntrans.building

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import io.reactivex.disposables.CompositeDisposable
import net.suntrans.building.utils.StatusBarCompat

/**
 * Created by Looney on 2018/1/23.
 * Des:
 */
open class BasedActivity : AppCompatActivity() {
    protected val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
       
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

    }

    private fun fixStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val statusBarFix = findViewById<View>(R.id.statusBarFix)
            val statusBarHeight = StatusBarCompat.getStatusBarHeight(this.applicationContext)
            if (statusBarFix != null)
                statusBarFix!!.layoutParams.height = statusBarHeight
        }
    }


    fun navigate(activity: Context, toActivity: Class<*>) {
        activity.startActivity(Intent(activity, toActivity))
    }

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

}

package net.suntrans.building

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import com.bumptech.glide.Glide
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import net.suntrans.building.databinding.ActivitySplashBinding

/**
 * 启动页
 *
 *
 * Created by woxingxiao on 2017-02-09.
 */
class SplashActivity : BasedActivity() {


    private var mHandler: Handler? = null
    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        mHandler = Handler()
        val generator = RandomTransitionGenerator(20000, LinearInterpolator())

        binding!!.splashBgImg.setTransitionGenerator(generator)
        Glide.with(this).load(R.drawable.splash).into(binding!!.splashBgImg)
        binding!!.splashRootLayout.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v!!.removeOnLayoutChangeListener(this)

                var transX = binding!!.splashAppSloganText.measuredWidth shr 1
                var transY = binding!!.splashAppSloganText.measuredHeight
                binding!!.splashLogoImg
                        .animate()
                        .setDuration(500)
                        .setStartDelay(500)
                        .translationX(-transX.toFloat())
                        .setInterpolator( LinearInterpolator())

                binding!!.splashAppNameText.alpha=0f
                binding!!.splashAppNameText
                        .animate()
                        .setDuration(500)
                        .setStartDelay(500)
                        .alpha(1.0f)
                        .translationX(transX.toFloat())
                        .setInterpolator( LinearInterpolator())

                binding!!.splashAppSloganText.alpha = 0f

                binding!!.splashAppSloganText
                        .animate()
                        .translationX(transX.toFloat())
                        .setDuration(500)
                        .setStartDelay(500)
                        .setInterpolator( LinearInterpolator())
                        .setListener(object : AnimatorListenerAdapter(){
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                binding!!.splashAppNameText.animate()
                                        .translationY((-transY shr 1).toFloat())
                                        .setDuration(300)
                                        .setInterpolator(LinearInterpolator())

                                binding!!.splashAppSloganText
                                        .animate()
                                        .alpha(1f)
                                        .translationY(transY.toFloat())
                                        .setDuration(300)
                                        .setInterpolator(LinearInterpolator())
                                        .setListener(object : AnimatorListenerAdapter() {
                                            override fun onAnimationEnd(animation: Animator) {
                                                jumpToMain()
                                            }
                                        })
                            }
                        })
            }
        })

    }

    override fun onPause() {
        super.onPause()
        binding!!.splashBgImg.pause()
    }

    private fun jumpToMain() {
        mHandler!!.postDelayed({
           val token = App.getSharedPreferences().getString("access_token","")
            if (TextUtils.isEmpty(token)){
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }else{
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }


        }, 800)
    }


    override fun onDestroy() {
        super.onDestroy()
        mHandler!!.removeCallbacksAndMessages(null)
    }

}

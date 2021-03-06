package net.suntrans.building.control;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.suntrans.building.App;
import net.suntrans.building.BasedActivity;
import net.suntrans.building.R;
import net.suntrans.building.api.Api;
import net.suntrans.building.api.RetrofitHelper;
import net.suntrans.building.utils.LogUtil;
import net.suntrans.building.utils.UiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Looney on 2018/3/9.
 */

public class AreaPlanActivity extends BasedActivity {
    private String house_id;
    private String token;
    private WebView webview;
    private OrientationEventListener listener;

    private int mScreenProtrait = 1;
    private int mCurrentOrient = 1;
    private RelativeLayout toolbar;
    private View statusbar;
    private Configuration newConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LogUtil.i("onCreate");

        setContentView(R.layout.activity_plan);
//        StatusBarCompat.compat(findViewById(R.id.root));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int logoPadding = metrics.widthPixels / 25;

        ImageView logo = findViewById(R.id.logo);
        logo.setPadding(logoPadding,logoPadding,logoPadding,logoPadding);

        toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));

        findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        house_id = getIntent().getStringExtra("house_id");
        token = "Bearer " + App.getSharedPreferences().getString("access_token", "-1");
        webview = (WebView) findViewById(R.id.webview);
        setUpWebview(webview);
//        setOrientationEventListener();

//        listener.enable();


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadJs();
            }
        });


        webview.loadUrl("file:///android_asset/SuntransDemo/floor_plan.html");
        webview.addJavascriptInterface(new AndroidtoJs(), "control");

    }

    private void loadJs() {
        String js = "";
        js += "var newscript = document.createElement(\"script\");";
        js += "newscript.src=\"./js/designer.js\";";
        js += "newscript.onload=function(){"
                + "init(\""
                + token + "\",\""
                + house_id + "\");};";
        js += "document.body.appendChild(newscript);";

//                System.out.println(js);
        webview.loadUrl("javascript:" + js);
    }

    private void setUpWebview(WebView webview) {
        WebSettings settings = webview.getSettings();

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setGeolocationEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        webview.setInitialScale(0);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setWebViewClient(new WebViewClient());
        webview.setVerticalScrollBarEnabled(false);

        WebSettings localWebSettings = webview.getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        webview.setHorizontalScrollBarEnabled(false);//水平不显示


    }

    // 继承自Object类
    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void alert(String msg1) {
            try {
                JSONObject jsonObject = new JSONObject(msg1);
                String code = jsonObject.getString("code");
                String msg = jsonObject.getString("msg");
                String device = jsonObject.getString("device");

                if (code.equals("404")) {
                    UiUtils.showToast(msg);

                } else if (code.equals("101")) {
                    UiUtils.showToast(msg);

                } else if (code.equals("403")) {
                    UiUtils.showToast("设备不在线");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        newConfig = getResources().getConfiguration();
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            toolbar.setVisibility(View.GONE);
//            statusbar.setVisibility(View.GONE);
//            //横屏
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            toolbar.setVisibility(View.VISIBLE);
//            statusbar.setVisibility(View.VISIBLE);
//            //竖屏
//        } else if (newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
//            //键盘没关闭。屏幕方向为横屏
//        } else if (newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
//            //键盘关闭。屏幕方向为竖屏
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
//        listener.disable();
        handler.removeCallbacksAndMessages(null);
        LogUtil.i("onDestroy");
        webview.destroy();
        super.onDestroy();
    }


    private void setOrientationEventListener() {
        listener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {

                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;  //手机平放时，检测不到有效的角度
                }
                //只检测是否有四个角度的改变
                if (orientation > 315 || orientation < 45) { //0度
                    orientation = 0;
                } else if (orientation > 45 && orientation < 135) { //90度
                    orientation = 90;
                } else if (orientation > 135 && orientation < 225) { //180度
                    orientation = 180;
                } else if (orientation > 225 && orientation < 315) { //270度
                    orientation = 270;
                } else {
                    return;
                }
                if (mCurrentOrient != orientation) {
                    orientationChanged(orientation);
                }
//                Log.i("MyOrientationDetector ", "onOrientationChanged:" + orientation);

            }
        };
    }

    private void reLoadJS() {
        String js = "";
        js += "var newscript = document.createElement(\"script\");";
        js += "newscript.text=\'initContainerByToken(\"%1$s\",\"%2$s\")\';";
        js += "document.body.appendChild(newscript);";

        js = String.format(js, token, house_id);
        System.out.println(js);
        webview.loadUrl("javascript:" + js);
    }

    private void orientationChanged(int orientaion) {
        System.out.println("屏幕方向改变");
        if (orientaion == 270) {
            toolbar.setVisibility(View.GONE);
            statusbar.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }
        if (orientaion == 90) {
            toolbar.setVisibility(View.GONE);
            statusbar.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }
        if (orientaion == 0) {
            toolbar.setVisibility(View.VISIBLE);
            statusbar.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        if (orientaion == 180) {
            toolbar.setVisibility(View.VISIBLE);
            statusbar.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);

        }
        reLoadJS();
        mCurrentOrient = orientaion;

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private Api api = RetrofitHelper.getApi();

    private void sendCmd(String channel_id, String datapoint, String din, String cmd) {
//        api.switchChannel(channel_id, datapoint,
//                din, cmd)
//                .compose(this.<ControlEntity>bindUntilEvent(ActivityEvent.DESTROY))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new BaseSubscriber<ControlEntity>(this) {
//                    @Override
//                    public void onNext(ControlEntity controlEntity) {
//                        UiUtils.showToast(controlEntity.msg);
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                reLoadJS();
//                            }
//                        },1000);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                    }
//                });
    }

    private Handler handler = new Handler();
}

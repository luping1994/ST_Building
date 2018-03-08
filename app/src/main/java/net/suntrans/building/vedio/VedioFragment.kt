package net.suntrans.building.vedio

import android.Manifest
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.nfc.Tag
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hichip.base.HiLog
import com.hichip.callback.ICameraIOSessionCallback
import com.hichip.content.HiChipDefines
import com.hichip.control.HiCamera
import com.hichip.tools.Packet
import com.tencent.android.tpush.XGPushConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_camerahi.*
import net.suntrans.building.BasedFragment
import net.suntrans.building.R
import net.suntrans.building.api.RetrofitHelper
import net.suntrans.building.databinding.FragmentVedioBinding
import net.suntrans.building.vedio.camhi.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

private val VedioFragment.strings: Array<out String>?
    get() {
        val stringArray = activity.resources.getStringArray(R.array.connect_state)
        return stringArray
    }

/**
 * Created by Looney on 2018/2/6.
 * Des:视频监控片段
 */

class VedioFragment : BasedFragment(), ICameraIOSessionCallback {
    private val REQUEST_CODE_CAMERA_LIVE_VIEW = 2

    override fun receiveSessionState(arg0: HiCamera?, arg1: Int) {
        if (HiDataValue.isDebug)
            HiLog.v("uid:" + arg0!!.getUid() + "  state:" + arg1)

        val msg = handler.obtainMessage()
        msg.what = HiDataValue.HANDLE_MESSAGE_SESSION_STATE
        msg.arg1 = arg1
        msg.obj = arg0
        handler.sendMessage(msg)
    }

    override fun receiveIOCtrlData(arg0: HiCamera?, arg1: Int, arg2: ByteArray?, arg3: Int) {
        if (arg1 == HiChipDefines.HI_P2P_GET_SNAP && arg3 == 0) {
            val camera = arg0 as MyCamera
            if (!camera.reciveBmpBuffer(arg2)) {
                return
            }

        }

        val bundle = Bundle()
        bundle.putByteArray(HiDataValue.EXTRAS_KEY_DATA, arg2)
        val msg = handler.obtainMessage()
        msg.what = HiDataValue.HANDLE_MESSAGE_RECEIVE_IOCTRL
        msg.obj = arg0
        msg.arg1 = arg1
        msg.arg2 = arg3
        msg.data = bundle
        handler.sendMessage(msg)
    }

    private var binding: FragmentVedioBinding? = null
    private var cameraAdapter: CameraAdapter1? = null
    private var itemHeight: Int = 0
    private var itemWidth: Int = 0
    private var itemPadding: Int = 0
    private var itemPaddingTop: Int = 0
    private var str_state: Array<out String>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentVedioBinding>(inflater, R.layout.fragment_vedio, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        for (i in 1..3) {
//            val cameraHi = CameraHi()
//            datas!!.add(cameraHi)
//        }
        cameraAdapter = CameraAdapter1(R.layout.item_camerahi, HiDataValue.CameraList)
        binding!!.recyclerView.adapter = cameraAdapter

        //获取屏幕宽度
        val metric = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metric)

        itemWidth = metric.widthPixels
        itemHeight = (metric.widthPixels.toFloat() * 12 / 16).toInt()
        itemPadding = itemWidth / 25
        itemPaddingTop = itemWidth / 15
//        println("item高度为$itemHeight,屏幕宽度为${metric.widthPixels}")
        str_state = activity.resources.getStringArray(R.array.connect_state)

        //给recyclerview动态设置一个paddingbottom
        binding!!.recyclerView.setPadding(0, 0, 0, itemPaddingTop)

        getData()

    }


    //RecyclerView adapter
    private inner class CameraAdapter1(layoutResId: Int, data: MutableList<MyCamera>) : RecyclerView.Adapter<VH>() {
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
            return VH(view)
        }

        override fun getItemCount(): Int {

            return cameraLists!!.size
        }

        override fun onBindViewHolder(holder: VH?, position: Int) {

            holder!!.setData(position)

        }

    }

    //ViewHolder
    private inner class VH(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var image: ImageView? = null
        var stateTx: TextView? = null

        init {
            name = itemView!!.findViewById(R.id.name1)
            image = itemView!!.findViewById(R.id.snapshot)
            stateTx = itemView!!.findViewById(R.id.state)
            itemView.setOnClickListener {

                var selectedCamera = HiDataValue.CameraList!![adapterPosition]
                if (selectedCamera.getConnectState() === HiCamera.CAMERA_CONNECTION_STATE_LOGIN) {
                    val extras = Bundle()
                    extras.putString(HiDataValue.EXTRAS_KEY_UID, selectedCamera.getUid())

                    val intent = Intent()
                    intent.putExtras(extras)
                    intent.setClass(activity, LiveViewActivity::class.java!!)
                    startActivityForResult(intent, REQUEST_CODE_CAMERA_LIVE_VIEW)

                    HiDataValue.isOnLiveView = true
                    selectedCamera.setAlarmState(0)

                    cameraAdapter!!.notifyDataSetChanged()
                } else {
                    selectedCamera.connect()
                }
            }
        }

        fun setData(position: Int) {
            name!!.text = HiDataValue.CameraList!![position].nikeName
            val state = HiDataValue.CameraList!![position].getConnectState()
            if (state >= 0 && state <= 4) {
                val strState = "(" + str_state!![state] + ")"
                stateTx!!.text = strState
                if (state == 4) {
                    stateTx!!.setTextColor(Color.parseColor("#00ff00"))
                } else {
                    stateTx!!.setTextColor(Color.parseColor("#ff0000"))

                }
            }
            val bitmap = HiDataValue.CameraList!![position].snapshot
            if (bitmap != null){
                image!!.setImageBitmap(bitmap)
            }else{
                image!!.setImageResource(R.drawable.noimg)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    //抓取相机列表数据
    private fun getData() {
        mDisposable.add(RetrofitHelper.getApi()
                .camHiList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(Consumer {
                    HiDataValue.CameraList!!.clear()
                    for (item in it) {
                        val camera = MyCamera(item.dev_nickname, item.dev_uid, item.view_acc, item.view_pwd)
                        camera.snapshot = loadImageFromUrl(context, camera)
                        camera.videoQuality = 0
                        camera.saveInCameraList()
                    }
                    activity.runOnUiThread {
                        cameraAdapter!!.notifyDataSetChanged()
                    }
                    initCamara()
                }, Consumer {

                }))
    }

    private val TAG = "VedioFragment"
    private fun initCamara() {
        if (HiDataValue.isDebug)
            HiLog.e("HiDataValue.CameraList.size()=${HiDataValue.CameraList.size}")
        for (camera in HiDataValue.CameraList) {
            if (HiDataValue.isDebug)
                HiLog.e(camera.uid + ": LOGIN register")
            camera.registerIOSessionListener(this@VedioFragment)

            Log.e(TAG, camera.uid)
            camera.connect()
        }
    }

    fun loadImageFromUrl(context: Context, camera: MyCamera): Bitmap? {
        // 是否SD卡可用
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // 检查是或有保存图片的文件夹，没有就创建一个
            val FileUrl = (Environment.getExternalStorageDirectory().toString() + "/android/data/"
                    + context.resources.getString(R.string.app_name) + "/")
            val folder = File(FileUrl)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val f = File(FileUrl + camera.uid)
            // SD卡中是否有该文件，有则直接读取返回
            if (f.exists()) {
                var fis: FileInputStream? = null
                try {
                    fis = FileInputStream(f)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

                return BitmapFactory.decodeStream(fis)
            } else {
                return null
            }
        }

        return null

    }

    protected fun sendServer(mCamera: MyCamera) {
        //		//测试
        //		mCamera.sendIOCtrl(CamHiDefines.HI_P2P_ALARM_ADDRESS_GET, null);
        if (mCamera.serverData == null) {
            HiLog.v("bruce save sever ")
            mCamera.serverData = HiDataValue.CAMERA_ALARM_ADDRESS
            //更新数据库 注释掉
//            mCamera.updateServerInDatabase(activity)
        }
        if (!mCamera.getCommandFunction(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET)) {
            HiLog.v("SERVER ADDRESS SET: false ")
            return
        }
        if (!mCamera.handSubXYZ()) {
            val info = CamHiDefines.HI_P2P_ALARM_ADDRESS.parseContent(HiDataValue.CAMERA_ALARM_ADDRESS)
            mCamera.sendIOCtrl(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET, info)
        } else {
            val info = CamHiDefines.HI_P2P_ALARM_ADDRESS.parseContent(HiDataValue.CAMERA_ALARM_ADDRESS_THERE)
            mCamera.sendIOCtrl(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET, info)
        }
    }

    private fun setTime(camera: MyCamera) {
        val cal = Calendar.getInstance(TimeZone.getDefault())
        cal.timeInMillis = System.currentTimeMillis()

        val time = HiChipDefines.HI_P2P_S_TIME_PARAM.parseContent(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND))

        camera.sendIOCtrl(HiChipDefines.HI_P2P_SET_TIME_PARAM, time)
    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                HiDataValue.HANDLE_MESSAGE_SESSION_STATE -> {
                    if (cameraAdapter != null)
                        cameraAdapter!!.notifyDataSetChanged()
                    when (msg.arg1) {
                        HiCamera.CAMERA_CONNECTION_STATE_DISCONNECTED -> if (HiDataValue.isDebug)
                            HiLog.e("disconnected")
                        HiCamera.CAMERA_CONNECTION_STATE_LOGIN -> {
                            val camera = msg.obj as MyCamera
                            camera.mIsConnect = true
                            if (HiDataValue.isDebug) HiLog.e("uid:" + camera.uid + " LOGIN")
                            setTime(camera)

                            setServer(camera)

                            //cameraLogin((MyCamera) msg.obj);

                            camera.sendIOCtrl(HiChipDefines.HI_P2P_GET_TIME_ZONE, ByteArray(0))
                        }
                        HiCamera.CAMERA_CONNECTION_STATE_WRONG_PASSWORD ->
                            // HiToast.showToast(getActivity(), "wrong password");
                            if (HiDataValue.isDebug)
                                HiLog.e("wrong password")
                        HiCamera.CAMERA_CONNECTION_STATE_CONNECTING -> {
                        }
                    }// HiToast.showToast(getActivity(), "connectting");
                    // HiLog.e("connectting");
                }

                HiDataValue.HANDLE_MESSAGE_RECEIVE_IOCTRL -> {
                    if (msg.arg2 == 0) {

                        val camera = msg.obj as MyCamera

                        when (msg.arg1) {
                            HiChipDefines.HI_P2P_GET_SNAP -> {
                                // Bundle bundle = msg.getData();
                                // byte[] data = bundle.getByteArray("data");
                                cameraAdapter!!.notifyDataSetChanged()

                                // DatabaseManager manager = new
                                // DatabaseManager(getActivity());

                                // byte[] buff = camera.getBmpBuffer();
                                if (camera.snapshot != null) {

                                    val rootFolder = File(
                                            Environment.getExternalStorageDirectory().absolutePath + "/")
                                    val sargetFolder = File(rootFolder.absolutePath + "/android/data/"
                                            + activity.resources.getString(R.string.app_name))

                                    if (!rootFolder.exists()) {
                                        rootFolder.mkdirs()
                                    }
                                    if (!sargetFolder.exists()) {
                                        sargetFolder.mkdirs()
                                    }
                                    if (HiDataValue.isDebug)
                                        HiLog.e(camera.uid + ": save  snapshot")
                                    HiTools.saveBitmap(camera.snapshot, sargetFolder.absolutePath + "/" + camera.uid)
                                    if (HiDataValue.isDebug)
                                        HiLog.v(sargetFolder.absolutePath + "/" + camera.uid)

                                } else {
                                    if (HiDataValue.isDebug)
                                        HiLog.e(camera.uid + ":  camera.snapshot =null")
                                }
                            }

                            HiChipDefines.HI_P2P_GET_TIME_ZONE -> {
                                val bundle = msg.data
                                val data = bundle.getByteArray(HiDataValue.EXTRAS_KEY_DATA)

                                val timezone = HiChipDefines.HI_P2P_S_TIME_ZONE(data!!)

                                if (timezone.u32DstMode == 1) {
                                    camera.summerTimer = true
                                } else {
                                    camera.summerTimer = false
                                }

                            }

                            CamHiDefines.HI_P2P_ALARM_TOKEN_REGIST -> {
                                val bundle = msg.data
                                val data = bundle.getByteArray(HiDataValue.EXTRAS_KEY_DATA)

                            }
                            CamHiDefines.HI_P2P_ALARM_TOKEN_UNREGIST -> {
                                val bundle = msg.data
                                val data = bundle.getByteArray(HiDataValue.EXTRAS_KEY_DATA)
                                if (HiDataValue.isDebug)
                                    HiLog.e(Packet.getString(data!!))

                            }
                            CamHiDefines.HI_P2P_ALARM_ADDRESS_SET -> {
                                val bundle = msg.data
                                val data = bundle.getByteArray(HiDataValue.EXTRAS_KEY_DATA)
                                if (HiDataValue.isDebug)
                                    HiLog.e(Packet.getString(data!!))

                            }
                            CamHiDefines.HI_P2P_ALARM_ADDRESS_GET -> {
                                val bundle = msg.data
                                val data = bundle.getByteArray(HiDataValue.EXTRAS_KEY_DATA)
                            }

                        // 服务器直推的回调
                            HiChipDefines.HI_P2P_ALARM_EVENT -> {

                                if (camera.pushState === 0) {
                                    return
                                }

                                /*
						 * //相对摄像机时间的每30秒一次回调， if(System.currentTimeMillis() -
						 * camera.getLastAlarmTime() < 30000) {
						 *
						 * HiLog.e("Time lastAlarmTime:"+(System.
						 * currentTimeMillis() - lastAlarmTime));
						 *
						 * return; }
						 */
                                camera.lastAlarmTime = System.currentTimeMillis()

                                val bundle = msg.data
                                val data = bundle.getByteArray(HiDataValue.EXTRAS_KEY_DATA)
                                val event = HiChipDefines.HI_P2P_EVENT(data!!)

                                // showP2PPushMessage(camera.getUid(),event.u32Event);
                                showAlarmNotification(camera, event.u32Event, System.currentTimeMillis())
                                // HiLog.v("alarm time:"+event.u32Time);

                                saveAlarmData(camera, event.u32Event, (System.currentTimeMillis() / 1000).toInt())
                                camera.alarmState = 1
                                cameraAdapter!!.notifyDataSetChanged()
                            }
                        }
                    }
                }

                HiDataValue.HANDLE_MESSAGE_DELETE_FILE -> {

                    val camera = msg.obj as MyCamera

                    camera.disconnect()
                    camera.deleteInCameraList()
//                    camera.deleteInDatabase(activity)
                    cameraAdapter!!.notifyDataSetChanged()
                    dismissLoadingProgress()

                    showSuccessDialog()

                }
            }
        }
    }

    protected fun sendRegisterToken(mCamera: MyCamera) {
        if (mCamera.pushState === 1 || mCamera.pushState === 0) {// if
            // open
            // ,
            // send
            // ;
            return
        }
        HiLog.v("bruce sendRegisterToken")

        if (!mCamera.getCommandFunction(CamHiDefines.HI_P2P_ALARM_TOKEN_REGIST)) {
            if (HiDataValue.isDebug)
                HiLog.v("REGIST FUCTION: false ")
            return
        }

        val info = CamHiDefines.HI_P2P_ALARM_TOKEN_INFO.parseContent(0, mCamera.pushState,
                (System.currentTimeMillis() / 1000 / 3600).toInt(), 1)

        mCamera.sendIOCtrl(CamHiDefines.HI_P2P_ALARM_TOKEN_REGIST, info)
    }

    internal var bindPushResult: MyCamera.OnBindPushResult = object : MyCamera.OnBindPushResult {
        override fun onBindSuccess(camera: MyCamera) {

            if (!camera.handSubXYZ()) {
                camera.serverData = HiDataValue.CAMERA_ALARM_ADDRESS
            } else {
                camera.serverData = HiDataValue.CAMERA_ALARM_ADDRESS_THERE
            }
            sendServer(camera)
            sendRegisterToken(camera)
        }

        override fun onBindFail(camera: MyCamera) {
            // TODO Auto-generated method stub
        }

        override fun onUnBindSuccess(camera: MyCamera) {
            camera.bindPushState(true, this)
        }

        override fun onUnBindFail(camera: MyCamera) {
            // TODO Auto-generated method stub
        }

    }

    protected fun setServer(mCamera: MyCamera) {

        if (!mCamera.getCommandFunction(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET)) {
            HiLog.v("uid " + mCamera.uid + " bruce Device not support,return ")
            HiLog.e("CamraFragment  camera.getCommandFunction(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET) is " + mCamera.getCommandFunction(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET))

            return
        } else {
            HiLog.e("CamraFragment  camera.getCommandFunction(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET) is " + mCamera.getCommandFunction(CamHiDefines.HI_P2P_ALARM_ADDRESS_SET))

        }

        // bruce add
        HiLog.v("uid " + mCamera.uid + "bruce getPushState  " + mCamera.pushState)
        if (mCamera.serverData != null && !mCamera.serverData.equals(HiDataValue.CAMERA_ALARM_ADDRESS)) {
            if (mCamera.pushState > 1) {
                HiLog.v("bruce change old addr " + mCamera.serverData + "new addr "
                        + HiDataValue.CAMERA_ALARM_ADDRESS)
                if (HiDataValue.XGToken == null) {

                    if (HiDataValue.ANDROID_VERSION >= 6) {
                        if (!HiTools.checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                        }
                    }

                    HiDataValue.XGToken = XGPushConfig.getToken(activity)
                }
                mCamera.bindPushState(false, bindPushResult)
                return
            }
        }

        sendServer(mCamera)
        sendRegisterToken(mCamera)

    }

    private fun dismissLoadingProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showSuccessDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun saveAlarmData(camera: MyCamera, u32Event: Int, toInt: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showAlarmNotification(camera: MyCamera, u32Event: Int, currentTimeMillis: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

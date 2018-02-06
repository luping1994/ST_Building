package net.suntrans.building.vedio

import android.databinding.DataBindingUtil
import android.graphics.Camera
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import net.suntrans.building.BasedFragment
import net.suntrans.building.R
import net.suntrans.building.databinding.FragmentVedioBinding
import net.suntrans.building.domin.CameraHi

/**
 * Created by Looney on 2018/2/6.
 * Des:视频监控片段
 */

class VedioFragment : BasedFragment() {

    var binding: FragmentVedioBinding? = null
    var datas: MutableList<CameraHi>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentVedioBinding>(inflater, R.layout.fragment_vedio, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        datas = ArrayList()
        for ( i in 1..3){
            val cameraHi = CameraHi()
            datas!!.add(cameraHi)
        }
        val cameraAdapter = CameraAdapter(R.layout.item_camerahi, datas as ArrayList<CameraHi>)
        binding!!.recyclerView.adapter = cameraAdapter
    }

    private inner class CameraAdapter(layoutResId: Int, data: List<CameraHi>) : BaseQuickAdapter<CameraHi, BaseViewHolder>(layoutResId, data) {

        override fun convert(helper: BaseViewHolder?, item: CameraHi?) {


        }

    }
}

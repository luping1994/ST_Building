package net.suntrans.building.control

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.suntrans.building.BasedFragment
import net.suntrans.building.R
import net.suntrans.building.databinding.FragmentControlBinding

/**
 * Created by Looney on 2018/2/6.
 * Des:智能控制片段
 */

class SmartControlFragment : BasedFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentControlBinding>(inflater, R.layout.fragment_control, container, false)
        return binding.root

    }
}

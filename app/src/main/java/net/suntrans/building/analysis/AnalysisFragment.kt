package net.suntrans.building.analysis

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.suntrans.building.BasedFragment
import net.suntrans.building.R
import net.suntrans.building.databinding.FragmentAnalysisBinding

/**
 * Created by Looney on 2018/2/6.
 * Des:分析片段
 */

class AnalysisFragment : BasedFragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAnalysisBinding>(inflater, R.layout.fragment_analysis, container, false)
        return binding.root

    }
    
}

package net.suntrans.building

import android.support.v4.app.Fragment

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Looney on 2018/2/6.
 * Des:
 */

open class BasedFragment : Fragment() {
    protected val mDisposable = CompositeDisposable()

    override fun onStop() {
        super.onStop()
        // clear all the subscription
        mDisposable.clear()
    }
}

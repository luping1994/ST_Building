package net.suntrans.building

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.TextUtils
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import net.suntrans.building.api.RetrofitHelper

import net.suntrans.building.databinding.ActivityLoginBinding
import net.suntrans.building.utils.UiUtils

/**
 * Created by Looney on 2018/2/6.
 * Des:
 */

class LoginActivity : BasedActivity() {

    private var binding: ActivityLoginBinding? = null
    private var isLogining = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val account1 = App.getSharedPreferences().getString("account", "")
        val password1 = App.getSharedPreferences().getString("password", "")
        binding!!.account.setText(account1)
        binding!!.password.setText(password1)
        binding!!.login.setOnClickListener {
            val account = binding!!.account.text.toString()
            val password = binding!!.password.text.toString()

            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                UiUtils.showToast(getString(R.string.tips_error))
                return@setOnClickListener
            }

            isLogining = true
            mDisposable.addAll(RetrofitHelper.getLoginApi()
                    .login("password", "1", "559eb687a4fcafdabe991c320172fcc9", account, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        isLogining = false
                        if (it.access_token != null) {
                            App.getSharedPreferences()
                                    .edit()
                                    .putString("account", account)
                                    .putString("password", password)
                                    .putString("access_token", it.access_token)
                                    .commit()
                            println(it.access_token)
                            navigate(this@LoginActivity, MainActivity::class.java)
                            finish()
                        }else{
                            UiUtils.showToast(getString(R.string.warning_password_error))
                        }
                    }, Consumer {
                        UiUtils.showToast(it.message)

                    })
            )
        }
    }
}

package com.ryan.androider

import android.util.Log
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.ryan.androider.databinding.ActivityMainBinding
import com.ryan.androider.jni.TestJni
import com.ryan.core.base.mvvm.BaseActivity
import com.ryan.core.base.mvvm.BaseViewModel
import com.ryan.core.utils.FragmentTabUtils
import com.ryan.discover.fragment.DiscoverFragment
import com.ryan.home.fragment.HomeFragment
import com.ryan.mine.fragment.MineFragment
import com.ryan.playmodule.fragment.PlayFragment


/**
 * @Author: Ryan
 * @Date: 2020/7/16 14:20
 * @Description: main
 */
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    private var fragments = mutableListOf<Fragment>()
    private var mRadioGroup: RadioGroup? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewObservable() {
        TODO("Not yet implemented")
    }

    override fun initView() {
        init()
    }

    /**
     * 初始化
     */
    private fun init() {
        mRadioGroup = findViewById(R.id.rg_main)
        fragments.add(HomeFragment())
        fragments.add(PlayFragment())
        fragments.add(DiscoverFragment())
        fragments.add(MineFragment())
        FragmentTabUtils(supportFragmentManager, fragments, R.id.fl_content, mRadioGroup)
        Log.e("NDK", "**-*-*-*-* " + TestJni().printlnSomething())
    }

    companion object {
        init {
            System.loadLibrary("libTest-lib")
        }
    }
}

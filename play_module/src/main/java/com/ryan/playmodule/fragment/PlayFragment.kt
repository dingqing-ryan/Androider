package com.ryan.playmodule.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ryan.core.base.mvvm.BaseFragment
import com.ryan.playmodule.R
import com.ryan.playmodule.databinding.FragmentPlayBinding
import com.ryan.playmodule.vm.PlayViewModel


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 *
 */
class PlayFragment : BaseFragment<FragmentPlayBinding, PlayViewModel>() {


    override fun getLayoutId(inflater: LayoutInflater?, container: ViewGroup?): Int {
        return R.layout.fragment_play
    }

    override fun initViewObservable() {

    }

    override fun initView() {
        mBinding.tvSelect.setOnClickListener {

        }
    }
}

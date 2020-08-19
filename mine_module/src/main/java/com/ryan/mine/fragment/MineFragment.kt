package com.ryan.mine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ryan.mine.R


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 *
 */
class MineFragment : Fragment() {
    private var mView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_mine, container, false)
        return mView
    }
}

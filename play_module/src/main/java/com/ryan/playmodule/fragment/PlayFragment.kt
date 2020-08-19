package com.ryan.playmodule.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ryan.playmodule.R


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 *
 */
class PlayFragment : Fragment() {
    private var mView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_play, container, false)
        return mView
    }
}

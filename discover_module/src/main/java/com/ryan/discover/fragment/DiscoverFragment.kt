package com.ryan.discover.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ryan.discover.R


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 *
 */
class DiscoverFragment : Fragment() {
    private var mView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_discover, container, false)
        return mView
    }
}

package com.huading.configure.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseFragment : Fragment() {
    private var mRootView: View? = null
    var mContext: Activity? = null
    private var inputMethodManager: InputMethodManager? = null

    val mScope: CoroutineScope by lazy {
        MainScope()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(getViewId(), container, false)

        inputMethodManager =
            mContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        return mRootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initViewModel()
        initLisenter()
    }

    abstract fun getViewId(): Int

    open fun initData() {
    }

    open fun initView() {
    }

    open fun initViewModel() {
    }

    open fun initLisenter() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mScope.cancel()
    }
}
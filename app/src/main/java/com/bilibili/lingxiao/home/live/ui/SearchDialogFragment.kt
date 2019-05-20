package com.bilibili.lingxiao.home.live.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.play.adapter.CommentAdapter
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.widget.RippleAnimation
import kotlinx.android.synthetic.main.fragment_comment_detail.*
import kotlinx.android.synthetic.main.fragment_dialog_search.*
import kotlinx.android.synthetic.main.fragment_fans_detail.*
import org.greenrobot.eventbus.EventBus

class SearchDialogFragment :DialogFragment(){
    var height = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fragmentDialog)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setWindowAnimations(R.style.contextMenuAnim)
        val root = inflater.inflate(R.layout.fragment_dialog_search, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_exit.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        //EventBus.getDefault().register(this)
        var win = getDialog().getWindow()
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        var  dm = DisplayMetrics()
        getActivity()!!.getWindowManager().getDefaultDisplay().getMetrics(dm)
        var params = win.getAttributes()
        params.gravity = Gravity.TOP
        //使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT

        win.setAttributes(params)
    }

    override fun onStop() {
        super.onStop()
        //EventBus.getDefault().unregister(this)
    }

}
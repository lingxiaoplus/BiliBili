package com.bilibili.lingxiao.utils

import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
object ToastUtil {
    private var mToast: Toast? = null

    fun show(msg: String?) {
        if (mToast == null) {
            mToast = Toast.makeText(UIUtil.getContext(), msg, Toast.LENGTH_SHORT)
        } else {
            mToast?.setText(msg)
        }
        mToast?.show()
    }
}
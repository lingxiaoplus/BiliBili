package com.camera.lingxiao.common.utills

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView

class PopwindowUtil private constructor(private val mContext: Context) {
    var mWidth = 0f
    var mHeight = 0f
    private var mIsFocusable = true
    private var mIsOutside = true
    private var mResLayoutId = -1
    var mContentView: View? = null
    private var mPopupWindow: PopupWindow? = null
    private var mAnimationStyle = -1
    private var mElevation :Float = 0f

    private var mClippEnable = true//default is true
    private var mIgnoreCheekPress = false
    private var mInputMode = -1
    private var mOnDismissListener: PopupWindow.OnDismissListener? = null
    private var mSoftInputMode = -1
    private var mTouchable = true//default is ture
    private var mOnTouchListener: View.OnTouchListener? = null
    private var mBackgroundDrawable: Drawable? = null

    private val sparseArray = SparseArray<View>()

    /**
     * 相对于某个控件的位置 带偏移量
     * @param anchor 指定位于哪个控件的相对位置
     * @param x
     * @param y
     * @return
     */
    fun showAsDropDown(anchor: View, x: Int, y: Int): PopwindowUtil {
        mPopupWindow?.showAsDropDown(anchor, x, y)
        return this
    }

    fun showAsDropDown(anchor: View): PopwindowUtil {
        mPopupWindow?.showAsDropDown(anchor)
        return this
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun showAsDropDown(anchor: View, xOff: Int, yOff: Int, gravity: Int): PopwindowUtil {
        mPopupWindow?.showAsDropDown(anchor, xOff, yOff, gravity)
        return this
    }

    /**
     * 相对于父控件的位置
     * @param parent
     * @param gravity
     * @param x
     * @param y
     * @return
     */
    fun showAtLocation(parent: View, x: Int, y: Int ,gravity: Int): PopwindowUtil {
        mPopupWindow?.showAtLocation(parent, gravity, x, y)
        return this
    }

    /**
     * 设置有阴影背景
     */
    fun showAtLocation(parent: View, x: Int, y: Int ,gravity: Int,bgAlpha: Float): PopwindowUtil {
        backgroundAlpha(bgAlpha,true)
        mPopupWindow?.let {
            it.showAtLocation(parent, gravity, x, y)
            it.setOnDismissListener {
                backgroundAlpha(bgAlpha,false)
            }
        }
        return this
    }

    fun showAtLocation(root: View): PopwindowUtil {
        val windowPos = calculatePopWindowPos(root, mContentView!!)
        val xOff = 10// 可以自己调整偏移
        windowPos[0] -= xOff
        mPopupWindow?.showAtLocation(
            root, Gravity.TOP or Gravity.START,
            windowPos[0], windowPos[1]
        )
        return this
    }

    /**
     * 添加属性
     * @param popupWindow
     */
    private fun apply(popupWindow: PopupWindow) {
        popupWindow.isClippingEnabled = mClippEnable
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress()
        }
        if (mInputMode != -1) {
            popupWindow.inputMethodMode = mInputMode
        }
        if (mSoftInputMode != -1) {
            popupWindow.softInputMode = mSoftInputMode
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener)
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener)
        }
        popupWindow.isTouchable = mTouchable
    }

    private fun build(): PopupWindow {

        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null)
        }

        if (mWidth != 0f && mHeight != 0f) {
            mPopupWindow = PopupWindow(mContentView, mWidth.toInt(), mHeight.toInt())
        } else {
            mPopupWindow =
                PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        if (mAnimationStyle != -1) {
            mPopupWindow!!.animationStyle = mAnimationStyle
        }
        if (mElevation != 0f){
            mPopupWindow!!.elevation = mElevation;
        }

        apply(mPopupWindow!!)//设置一些属性

        mPopupWindow!!.isFocusable = mIsFocusable
        if (null != mBackgroundDrawable) {
            mPopupWindow!!.setBackgroundDrawable(mBackgroundDrawable)
        }
        mPopupWindow!!.isOutsideTouchable = mIsOutside

        if (mWidth == 0f || mHeight == 0f) {
            mPopupWindow!!.contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow!!.contentView.measuredWidth.toFloat()
            mHeight = mPopupWindow!!.contentView.measuredHeight.toFloat()
        }

        mPopupWindow!!.update()

        return mPopupWindow!!
    }

    fun <T : View> getView(resId: Int): T? {
        var view: View? = sparseArray.get(resId)
        if (mContentView == null) {
            Log.i("PopwindowUtil", "mContentView is null!")
            return null
        }
        if (view == null) {
            view = mContentView!!.findViewById(resId)
            sparseArray.put(resId, view)
        }
        return view as T?
    }

    fun setText(resId: Int, text: String) {
        val textView = getView<TextView>(resId)
        textView!!.text = text
    }

    fun dissmiss() {
        if (mPopupWindow != null) {
            mPopupWindow!!.dismiss()
        }
    }

    class PopupWindowBuilder(context: Context) {
        private val mCustomPopWindow: PopwindowUtil

        init {
            mCustomPopWindow = PopwindowUtil(context)
        }

        fun size(width: Float, height: Float): PopupWindowBuilder {
            mCustomPopWindow.mWidth = width
            mCustomPopWindow.mHeight = height
            return this
        }


        fun setFocusable(focusable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsFocusable = focusable
            return this
        }


        fun setView(resLayoutId: Int): PopupWindowBuilder {
            mCustomPopWindow.mResLayoutId = resLayoutId
            mCustomPopWindow.mContentView = null
            return this
        }

        fun setView(view: View): PopupWindowBuilder {
            mCustomPopWindow.mContentView = view
            mCustomPopWindow.mResLayoutId = -1
            return this
        }

        fun setElevation(elevation :Float): PopupWindowBuilder {
            mCustomPopWindow.mElevation = elevation
            return this
        }

        fun setOutsideTouchable(outsideTouchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsOutside = outsideTouchable
            return this
        }

        /**
         * 设置弹窗动画
         * @param animationStyle
         * @return
         */
        fun setAnimationStyle(animationStyle: Int): PopupWindowBuilder {
            mCustomPopWindow.mAnimationStyle = animationStyle
            return this
        }


        fun setClippingEnable(enable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mClippEnable = enable
            return this
        }


        fun setIgnoreCheekPress(ignoreCheekPress: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIgnoreCheekPress = ignoreCheekPress
            return this
        }

        fun setInputMethodMode(mode: Int): PopupWindowBuilder {
            mCustomPopWindow.mInputMode = mode
            return this
        }

        fun setOnDissmissListener(onDissmissListener: PopupWindow.OnDismissListener): PopupWindowBuilder {
            mCustomPopWindow.mOnDismissListener = onDissmissListener
            return this
        }


        fun setSoftInputMode(softInputMode: Int): PopupWindowBuilder {
            mCustomPopWindow.mSoftInputMode = softInputMode
            return this
        }


        fun setTouchable(touchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mTouchable = touchable
            return this
        }

        fun setTouchIntercepter(touchIntercepter: View.OnTouchListener): PopupWindowBuilder {
            mCustomPopWindow.mOnTouchListener = touchIntercepter
            return this
        }

        fun setBackgroundDrawable(drawable: Drawable): PopupWindowBuilder {
            mCustomPopWindow.mBackgroundDrawable = drawable
            return this
        }

        fun create(): PopwindowUtil {
            //构建PopWindow
            mCustomPopWindow.build()
            return mCustomPopWindow
        }

    }

    companion object {


        /**
         * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
         * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
         * @param anchorView  呼出window的view
         * @param contentView   window的内容布局
         * @return window显示的左上角的xOff,yOff坐标
         */
        private fun calculatePopWindowPos(anchorView: View, contentView: View): IntArray {
            val windowPos = IntArray(2)
            val anchorLoc = IntArray(2)
            // 获取锚点View在屏幕上的左上角坐标位置
            anchorView.getLocationOnScreen(anchorLoc)
            val anchorHeight = anchorView.height
            // 获取屏幕的高宽
            val screenHeight = getScreenHeight(anchorView.context)
            val screenWidth = getScreenWidth(anchorView.context)
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            // 计算contentView的高宽
            val windowHeight = contentView.measuredHeight
            val windowWidth = contentView.measuredWidth
            // 判断需要向上弹出还是向下弹出显示
            val isNeedShowUp = screenHeight - anchorLoc[1] - anchorHeight < windowHeight
            if (isNeedShowUp) {
                windowPos[0] = screenWidth - windowWidth
                windowPos[1] = anchorLoc[1] - windowHeight
            } else {
                windowPos[0] = screenWidth - windowWidth
                windowPos[1] = anchorLoc[1] + anchorHeight
            }
            return windowPos
        }

        /**
         * 获取屏幕高度(px)
         */
        fun getScreenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        /**
         * 获取屏幕宽度(px)
         */
        fun getScreenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }
    }

    var isStarted = false
    fun backgroundAlpha(bgAlpha:Float,show:Boolean) {
        if (isStarted){
            return
        }
        isStarted = true
        var animator:ValueAnimator
        if (show)
            animator = ValueAnimator.ofFloat(1f, bgAlpha).setDuration(500)
        else
            animator = ValueAnimator.ofFloat(bgAlpha, 1f).setDuration(500)
        getActivityFromContext(mContext)?.let {
            var updateListener = ValueAnimator.AnimatorUpdateListener { animation ->
                var mCurrentAlpha = (animation.animatedValue as Float)
                val lp = it.getWindow().getAttributes()
                LogUtils.d("阴影动画修改值${mCurrentAlpha}, 是否是显示的${show}")
                lp.alpha = mCurrentAlpha
                it.getWindow().setAttributes(lp)
            }
            var animatorListener = object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    //动画播放完毕
                    isStarted = false
                }
            }
            animator.addUpdateListener(updateListener)
            animator.addListener(animatorListener)
            animator.start()
        }
    }
    private fun getActivityFromContext(context: Context?): Activity? {
        var context = context
        if (null != context) {
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }else if (context is Fragment){
                    return context.activity
                }
                context = context.baseContext
            }
        }
        return null
    }
}

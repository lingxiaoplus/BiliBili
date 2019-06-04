package com.bilibili.lingxiao.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.facebook.drawee.backends.pipeline.Fresco

class ScrollerloadRecyclerView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ShimmerRecyclerView(context, attrs, defStyleAttr){
    init {
        addOnScrollListener(ImageAutoLoadListener())
    }

    inner class ImageAutoLoadListener :OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when(newState){
                SCROLL_STATE_IDLE -> Fresco.getImagePipeline().resume()  //空闲状态 加载图片
                SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING ->{
                    //惯性滑动和滚动 停止加载图片
                    Fresco.getImagePipeline().pause()
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }
}
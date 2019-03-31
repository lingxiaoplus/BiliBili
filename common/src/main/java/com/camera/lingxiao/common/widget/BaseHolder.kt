package com.camera.lingxiao.common.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView

//import butterknife.Unbinder

abstract class BaseHolder<Data>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews: SparseArray<View>
    var callback: AdapterCallback<Data>? = null
    //var unbinder: Unbinder? = null
    protected var mData: Data? = null;
    protected var mPosition: Int = 0

    init {
        mViews = SparseArray()
    }

    /**
     * 通过viewid查找view
     * @param viewId
     * @param <T>
     * @return
    </T> */
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
        }
        return view as T
    }

    fun setText(viewId: Int, text: String): BaseHolder<*> {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): BaseHolder<*> {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    /* public BaseHolder setImageUrl(int viewId, String picPath,long time) {
        ImageView view = getView(viewId);
        GlideHelper.loadImageWithData(picPath,view,time);
        return this;
    }
*/
    fun setOnClickListener(
        viewId: Int,
        listener: View.OnClickListener
    ): BaseHolder<*> {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    /**
     * 用于绑定数据的触发
     *
     * @param data 绑定的数据
     */
    internal fun bind(data: Data, pos: Int) {
        this.mData = data
        this.mPosition = pos
        onBind(data, pos)
    }

    /**
     * 当触发绑定数据的时候，的回掉；必须复写
     *
     * @param data 绑定的数据
     */
    protected abstract fun onBind(data: Data, position: Int)

    /**
     * Holder自己对自己对应的Data进行更新操作
     *
     * @param data Data数据
     */
    fun updateData(data: Data) {
        this.callback?.update(data, this)
    }
}

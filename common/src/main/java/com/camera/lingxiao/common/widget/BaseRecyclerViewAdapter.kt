package com.camera.lingxiao.common.widget

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.camera.lingxiao.common.R

import java.util.ArrayList
import java.util.Collections


abstract class BaseRecyclerViewAdapter<Data>(
    private val mDataList: MutableList<Data>,
    private var mListener: AdapterListener<Data>?
) : RecyclerView.Adapter<BaseHolder<Data>>(), View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {

    /**
     * 返回整个集合
     *
     * @return List<Data>
    </Data> */
    val items: List<Data>
        get() = mDataList

    @JvmOverloads constructor(listener: AdapterListener<Data>? = null) : this(ArrayList<Data>(), listener) {}

    /**
     * 复写默认的布局类型返回
     *
     * @param position 坐标
     * @return 类型，其实复写后返回的都是XML文件的ID
     */
    override fun getItemViewType(position: Int): Int {
        return getItemViewType(position, mDataList[position])
    }

    /**
     * 得到布局的类型
     *
     * @param position 坐标
     * @param data     当前的数据
     * @return XML文件的ID，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract fun getItemViewType(position: Int, data: Data): Int

    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面的类型,约定为XML布局的Id
     * @return ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Data> {
        // 得到LayoutInflater用于把XML初始化为View
        val inflater = LayoutInflater.from(parent.context)
        // 把XML id为viewType的文件初始化为一个root View
        val root = inflater.inflate(viewType, parent, false)
        // 通过子类必须实现的方法，得到一个ViewHolder
        val holder = onCreateViewHolder(root, viewType)


        // 设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder)
        // 设置事件点击
        root.setOnClickListener(this)
        root.setOnLongClickListener(this)

        // 进行界面注解绑定
        //holder.unbinder = ButterKnife.bind(holder, root)
        // 绑定callback
        holder.callback = this

        return holder
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型，其实就是XML的ID
     * @return ViewHolder
     */
    protected abstract fun onCreateViewHolder(root: View, viewType: Int): BaseHolder<Data>

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    override fun onBindViewHolder(holder: BaseHolder<Data>, position: Int) {
        // 得到需要绑定的数据
        val data = mDataList[position]
        // 触发Holder的绑定方法
        holder.bind(data, position)
    }

    /**
     * 得到当前集合的数据量
     */
    override fun getItemCount(): Int {
        return mDataList.size
    }

    /**
     * 插入一条数据并通知插入
     *
     * @param data Data
     */
    fun add(data: Data) {
        mDataList.add(data)
        notifyItemInserted(mDataList.size - 1)
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    fun add(vararg dataList: Data) {
        if (dataList != null && dataList.size > 0) {
            val startPos = mDataList.size
            Collections.addAll(mDataList, *dataList)
            notifyItemRangeInserted(startPos, dataList.size)
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    fun add(dataList: Collection<Data>?) {
        if (dataList != null && dataList.size > 0) {
            val startPos = mDataList.size
            mDataList.addAll(dataList)
            notifyItemRangeInserted(startPos, dataList.size)
        }
    }

    /**
     * 删除操作
     */
    fun clear() {
        mDataList.clear()
        notifyDataSetChanged()
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     *
     * @param dataList 一个新的集合
     */
    fun replace(dataList: Collection<Data>?) {
        mDataList.clear()
        if (dataList == null || dataList.size == 0)
            return
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun update(data: Data, holder: BaseHolder<Data>) {
        // 得到当前ViewHolder的坐标
        val pos = holder.adapterPosition
        if (pos >= 0) {
            // 进行数据的移除与更新
            mDataList.removeAt(pos)
            mDataList.add(pos, data)
            // 通知这个坐标下的数据有更新
            notifyItemChanged(pos)
        }
    }

    override fun onClick(v: View) {
        val viewHolder = v.getTag(R.id.tag_recycler_holder) as BaseHolder<*>
        if (this.mListener != null) {
            // 得到ViewHolder当前对应的适配器中的坐标
            val pos = viewHolder.adapterPosition
            // 回掉方法
            this.mListener!!.onItemClick(viewHolder, mDataList[pos], pos)
        }

    }

    override fun onLongClick(v: View): Boolean {
        val viewHolder = v.getTag(R.id.tag_recycler_holder) as BaseHolder<*>
        if (this.mListener != null) {
            // 得到ViewHolder当前对应的适配器中的坐标
            val pos = viewHolder.adapterPosition
            // 回掉方法
            this.mListener!!.onItemLongClick(viewHolder, mDataList[pos], pos)
            return true
        }
        return false
    }

    /**
     * 设置适配器的监听
     *
     * @param adapterListener AdapterListener
     */
    fun setListener(adapterListener: AdapterListener<Data>) {
        this.mListener = adapterListener
    }

    /**
     * 我们的自定义监听器
     *
     * @param <Data> 范型
    </Data> */
    interface AdapterListener<Data> {
        // 当Cell点击的时候触发
        fun onItemClick(holder: BaseHolder<*>, data: Data, position: Int)

        // 当Cell长按时触发
        fun onItemLongClick(holder: BaseHolder<*>, data: Data, position: Int)
    }


    /**
     * 对回调接口做一次实现AdapterListener
     *
     * @param <Data>
    </Data> */
    abstract class AdapterListenerImpl<Data> : AdapterListener<Data> {

        override fun onItemClick(holder: BaseHolder<*>, data: Data, position: Int) {

        }

        override fun onItemLongClick(holder: BaseHolder<*>, data: Data, position: Int) {

        }
    }
}
/**
 * 构造函数模块
 */

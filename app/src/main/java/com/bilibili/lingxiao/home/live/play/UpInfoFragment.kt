package com.bilibili.lingxiao.home.live.play

import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.LiveData
import com.bilibili.lingxiao.home.live.model.LiveUpData
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_interact.*
import kotlinx.android.synthetic.main.fragment_up_info.*
import kotlinx.android.synthetic.main.layout_header_room_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UpInfoFragment :BaseFragment(){
    private val TAG = UpInfoFragment::class.java.simpleName
    override val contentLayoutId: Int
        get() = R.layout.fragment_up_info
    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)

    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetHomeInfo(live: LiveData.RecommendDataBean.LivesBean){
        image_header.setImageURI(Uri.parse(live.owner.face))
        up_name.text = live.owner.name
        //category.text = live.area
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetUpInfo(liveUpData: LiveUpData){
        people_num.text = "房间号：" + liveUpData.roomId
        fensi_num.text = "粉丝：" + StringUtil.getBigDecimalNumber(liveUpData.attention)
        Log.d(TAG,"")
        certification.text = liveUpData.newPendants.badge?.desc
        if (liveUpData.description != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //FROM_HTML_MODE_COMPACT 块元素之间使用一个换行符分隔
                expand_textview.text = Html.fromHtml(liveUpData.description,Html.FROM_HTML_MODE_COMPACT)
            }else{
                //Bug，解析后的文本，块元素之间换行默认为两行，而且无法更改。
                expand_textview.text = Html.fromHtml(liveUpData.description)
            }
        }else{
            expand_textview.text = "暂时没有"
        }
    }
}
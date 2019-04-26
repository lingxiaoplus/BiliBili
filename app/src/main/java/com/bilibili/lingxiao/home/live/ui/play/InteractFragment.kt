package com.bilibili.lingxiao.home.live.ui.play

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.LiveData
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.layout_header_room_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class InteractFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_interact

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetHomeInfo(live: LiveData.RecommendDataBean.LivesBean){
        image_header.setImageURI(Uri.parse(live.owner.face))
        up_name.text = live.owner.name
        people_num.text = "人气：" + live.online
        //fensi_num.text = "粉丝：" +
        category.text = live.area
    }
}
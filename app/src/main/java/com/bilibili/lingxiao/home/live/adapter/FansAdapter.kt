package com.bilibili.lingxiao.home.live.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.FansGoldListData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class FansAdapter(layoutResId: Int, data: MutableList<FansGoldListData.FansInfo>?) :
    BaseQuickAdapter<FansGoldListData.FansInfo, BaseViewHolder>(layoutResId, data) {
    private val levelImages = arrayOf(
        R.drawable.ic_live_rank_1,
        R.drawable.ic_live_rank_2,
        R.drawable.ic_live_rank_3
    )

    override fun convert(helper: BaseViewHolder, item: FansGoldListData.FansInfo) {
        var image_level = helper.getView<ImageView>(R.id.image_rank_level)
        var level = item.rank - 1
        if (level < 3){
            //前三名才有图标
            image_level.visibility = View.VISIBLE
            helper.setText(R.id.text_rank_level,"")
            image_level.setImageResource(levelImages[level])
        }else{
            helper.setText(R.id.text_rank_level,"" + item.rank)
            image_level.visibility = View.INVISIBLE
        }

        var image_header = helper.getView<SimpleDraweeView>(R.id.image_header)
        image_header.setImageURI(Uri.parse(item.face + GlobalProperties.IMAGE_RULE_60_60))

        helper.setText(R.id.username,item.uname)
        helper.setText(R.id.text_gold_num,"" + item.score)
        /*if (item.icon != null){
            var image_gold = helper.getView<SimpleDraweeView>(R.id.image_rank_gold)
            image_gold.setImageURI(Uri.parse(item.icon))
        }*/
        if (item.medal_name != null){
            helper.setText(R.id.text_medal_name,item.medal_name)
            var text_medal = helper.getView<TextView>(R.id.text_medal_name)
            text_medal.visibility = View.VISIBLE
            if (item.color != null){
                //text_medal.setBackgroundColor(item.color)
            }
            helper.getView<LinearLayout>(R.id.ll_gold).visibility = View.GONE
        }
    }
}
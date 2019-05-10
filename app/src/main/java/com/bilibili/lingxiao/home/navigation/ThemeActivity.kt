package com.bilibili.lingxiao.home.navigation

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_theme.*
import kotlinx.android.synthetic.main.title_bar.*

class ThemeActivity : BaseActivity() {
    private var themeList = arrayListOf<ThemeData>()
    override val contentLayoutId: Int
        get() = R.layout.activity_theme

    override fun initWidget() {
        super.initWidget()
        var names = resources.getStringArray(R.array.theme_name)
        var colors = resources.getIntArray(R.array.theme_color)
        for ((index,name) in names.withIndex()){
            if (index < 3){
                themeList.add(ThemeData(colors[index],name,false,false))
            }else{
                themeList.add(ThemeData(colors[index],name,true,false))
            }
        }
        themeList[0].choose = true
        var adapter = ThemeAdapter(R.layout.item_theme,themeList)
        recycerView.adapter = adapter
        recycerView.layoutManager = LinearLayoutManager(this)
        setToolbarBack(title_bar)
        title_bar.title = "主题颜色"
    }

    data class ThemeData(var color:Int,var name:String,var pay:Boolean,var choose:Boolean){

    }

    inner class ThemeAdapter(layoutResId: Int, data: MutableList<ThemeData>?) :
    BaseQuickAdapter<ThemeData,BaseViewHolder>(layoutResId,data){
        override fun convert(helper: BaseViewHolder, item: ThemeData) {
            helper.setBackgroundColor(R.id.image_color,item.color)
            helper.setText(R.id.text_theme_name,item.name)
            if (item.pay){
                helper.setVisible(R.id.text_theme_price,true)
                helper.setVisible(R.id.button_pay,true)
            }else{
                helper.setVisible(R.id.text_theme_price,false)
                helper.setVisible(R.id.button_pay,false)
            }
            if (item.choose){
                helper.setVisible(R.id.image_choose,true)
            }else{
                helper.setVisible(R.id.image_choose,false)
            }
        }
    }
}

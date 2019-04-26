package com.bilibili.lingxiao.home.live

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader
import com.facebook.drawee.view.SimpleDraweeView
import android.R.attr.path
import android.media.Image
import android.net.Uri


class BannerImageLoader :ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        //用fresco加载图片简单用法，记得要写下面的createImageView方法
        val uri = Uri.parse(path as String)
        imageView?.setImageURI(uri)
    }

    override fun createImageView(context: Context?): ImageView {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        val simpleDraweeView = SimpleDraweeView(context)
        return simpleDraweeView
    }
}
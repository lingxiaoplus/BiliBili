package com.camera.lingxiao.common.app

import android.os.Environment

import com.camera.lingxiao.common.R

/**
 * Created by lingxiao on 2017/9/1.
 */

object ContentValue {
    val imgRule = "?imageView2/3/h/230"//图片规则，从服务器取230大小的图片
    val bigImgRule = "?imageView2/3/h/1080"
    val hor_720ImgRule = "?imageView2/3/h/720"

    val vertical720_ImgRule = "?imageView16/9/h/1080"
    val vertical1080_ImgRule = "?imageView16/9/h/1080"
    //升级接口
    val UPDATEURL = "https://www.lingxiaosuse.cn/tudimension/update.json"
    val PERMESSION_REQUEST_CODE = 200
    val KEY_USERNAME = "key_username"
    val KEY_PSD = "key_psd"
    /**
     * 七牛桶
     */
    val BUCKET = "smailchat"
    val QINIU_BASE_URL = "http://chat.lingxiaosuse.cn/"
    //是否是第一次进入
    var ISFIRST_KEY = "isfirst_key"
    //服务器版本号
    var VERSION_CODE = "versino_code"
    //描述
    var VERSION_DES = "version_des"
    //下载地址
    var DOWNLOAD_URL = "download_url"

    //是否自动检测更新
    var IS_CHECK = "is_check"
    //是否开启日图
    var IS_OPEN_DAILY = "is_open_daily"
    //
    //干货集中营api
    val GANKURL = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"

    //baseurl
    val BASE_URL = "http://app.bilibili.com"
    /**
     * 竖屏 最热
     */
    val VERTICAL_URLS = "/v1/vertical/vertical" + "?limit=30?adult=false&first=1&order=hot"
    /**
     * banner
     */
    val BANNER_URL = "/v1/wallpaper/"
    /**
     * 主页面
     */
    val HOMEPAGE_URL = "/v3/homepage"

    /**
     * 竖屏
     */
    val VERTICAL_URL = "/v1/vertical/vertical"

    /**
     * 专辑
     */
    val SPECIAL_URL = "/v1/wallpaper/album"
    /**
     * 分类
     */
    val CATEGORY_URL = "/v1/wallpaper/category"
    /**
     * 竖屏分类
     */
    val CATEGORY_VERTICAL_URL = "/v1/vertical/category"

    /**
     * 评论
     */
    val COMMENT_URL = "/v2/wallpaper/wallpaper"

    /**
     * 每次请求多少个数据
     */
    val limit = 30
    /**
     * 轮播图
     */
    val TYPE_ALBUM = "album"
    /**
     * 分类
     */
    val TYPE_CATEGORY = "category"

    //安卓壁纸的搜索
    val SEARCH_URL = "http://so.picasso.adesk.com"
    //关键词
    val SEARCH_KEY_URL = "/v1/push/keyword?versionCode=181&channel=huawei&first=0&adult=false"

    //保存的图片路径
    val PATH = Environment
        .getExternalStorageDirectory()
        .absolutePath + "/tudimension"

    //百度识图
    val BAIDU_URL = "http://image.baidu.com/wiseshitu?guss=1&queryImageUrl="
    //搜狗识图
    val SOUGOU_URL = "http://pic.sogou.com/"
    //google识图
    val GOOGLE_URL = "https://images.google.com/imghp?hl=zh-CN&gws_rd=ssl"

    //一言
    val HITOKOTO_URL = "http://api.hitokoto.cn/"
    //mzitu网址
    val MZITU_URL = "http://www.mzitu.com/"

    /**
     * cosplay.la
     */
    val COSPLAY_LA_URL = "http://api.cosplay.la/share/GetPhotoList"

    val COSPLAY_LA_DETAIL_URL = "http://api.cosplay.la/share/GetById"
    /**
     * cosplay requestcode
     */
    val COSPLAY_REQUEST_CODE = "8A409431-D3EC-443F-A3B6-098F105B26B0"

    //浏览器标志
    val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0"

    //收藏的网址
    val COLLECT_URL = "collect_url"

    //搜丝吧
    val SOUSIBA_URL = "http://www.sousi88.cc"

    /**
     * 当前皮肤的id
     */
    val SKIN_ID = "skin_id"
    /**
     * 记录侧滑模块
     */
    val DRAWER_MODEL = "drawer_model"
    /**
     * 设置轮播图时间
     */
    val BANNER_TIMER = 2000

    /**
     * 设置浏览图片的分辨率
     */
    val PIC_RESOLUTION = "pic_resolution"
    /**
     * 设置浏览图片的分辨率
     */
    val ANIMATOR_TYPE = "animator_type"

}

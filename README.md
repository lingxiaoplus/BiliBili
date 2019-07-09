# BiliBili

使用ijkplayer，实现一个仿B站的Android客户端。使用组件化的思想对项目进行拆分，目前分出两个组件，一个是网络请求组件，一个是视频播放组件。

扫码体验：

![](https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/QRCode_258.png)





### 组件化实现方案

组件化使用的方案出自张华洋的文章：[Android组件化方案](https://blog.csdn.net/guiying712/article/details/55213884)

在gradle.properties中，有一个isModule值，为true时是组件化模式，为false为library。在业务组件的build.gradle中读取 isModule，代码如下：

```java
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```

在 AndroidStudio 中每一个组件都会有对应的 AndroidManifest.xml，application和library使用的AndroidManifest.xml不同，所以要为组件开发模式下的业务组件再创建一个 AndroidManifest.xml，然后根据isModule指定AndroidManifest.xml的文件路径，让业务组件在集成模式和组件模式下使用不同的AndroidManifest.xml：

```java
sourceSets {
        main {
            if (isModule.toBoolean()) {
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
```

### 组件功能介绍

#### ijkplayer组件（功能组件）

该组件是一个视频播放组件，在ubuntu16.04上集成编译了ijkplayer，支持rtsp和rtmp的视频直播推流。提供了一个自定义view，可以使用该view实现本地/网络视频的播放、暂停、快进、视频亮度、音量的调节。[ijkplayer0.8.8下载地址](!https://blog-1252348761.cos.ap-chengdu.myqcloud.com/camera/ijkplayer0.8.8-2019-02-20.zip )

使用方式如下：

声明所需权限，用于播放网络视频和本地视频：

```xml
<uses-permission android:name="android.permission.INTERNET" />  
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

注意，如果横竖屏切换不想重新走一遍生命周期，还需要在表单中设置Activity的configChanges属性：

```xml
<activity android:name=".PlayActivity"
                  android:configChanges="orientation|keyboardHidden|screenSize" >
</activity
```



在布局中添加如下view：

```xml
<com.bilibili.lingxiao.ijkplayer.widget.SimplePlayerView
            android:id="@+id/simple_view"
            android:layout_width="match_parent"
            android:layout_height="180dp">
    </com.bilibili.lingxiao.ijkplayer.widget.SimplePlayerView>
```

在Activity/Fragment中的oncreate方法里：

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_play)
    //屏幕常亮
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
	/** 普通播放 start **/
	var url = getIntent().getStringExtra("url");
	simple_view
            .setVideoUrl(url)
            .setVideoTitle("这是标题")
            .startPlay()
}
override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //横竖屏切换 显示/隐藏actionbar
        var isPortrait = simple_view.onConfigurationChang(newConfig)
        if (isPortrait) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }

    override fun onBackPressed() {
        simple_view.onBackPressed()
        if (!simple_view.isPortrait){
            supportActionBar?.show()
        }else{
            super.onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        simple_view.onPause()
    }

    override fun onResume() {
        super.onResume()
        simple_view.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        simple_view.onDestory()
    }
```



#### common组件（功能组件）

1. common组件是基础库，添加一些公用的类；
2. 网络请求、图片加载、工具类、base类等；
3. 声明APP需要的uses-permission；
4. 定义mvp架构实现网络请求



### 目前完成的功能

- 整体架构搭建
- 对b站客户端抓包，分析接口
- 主界面布局完成
- 完成直播播放页面
- 完成推荐视频的播放
- 视频弹幕获取



项目截图：


<table>
	<tr>
		<th>直播界面</th>
		<th>聊天界面</th>
		<th>个人信息popwindow</th>
		<th>主播信息界面</th>
		<th>粉丝榜</th>
		<th>大航海</th>
	</tr>
	<tr>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-23-55-347_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-24-08-833_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-24-17-260_com.bilibili.lingxiao.png"/>
      </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-24-27-253_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-24-35-335_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-24-46-649_com.bilibili.lingxiao.png"/>
		  </td>
	</tr>
</table>





<table>
	<tr>
		<th>视频播放</th>
		<th>视频评论</th>
		<th>侧滑</th>
		<th>分区</th>
		<th>追番</th>
		<th>webview</th>
	</tr>
	<tr>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-25-12-073_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-25-16-370_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-25-29-487_com.bilibili.lingxiao.png"/>
      </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-32-27-197_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-32-34-114_com.bilibili.lingxiao.png"/>
		  </td>
		  <td>
			  <img src="https://blog-1252348761.cos.ap-chengdu.myqcloud.com/http/bilibili/Screenshot_2019-04-28-17-33-13-842_com.bilibili.lingxiao.png"/>
		  </td>
	</tr>
</table>


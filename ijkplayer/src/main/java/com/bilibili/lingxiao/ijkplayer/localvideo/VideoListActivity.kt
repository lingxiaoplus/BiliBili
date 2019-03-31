package com.bilibili.lingxiao.ijkplayer.localvideo

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.bilibili.lingxiao.ijkplayer.PlayActivity
import com.bilibili.lingxiao.ijkplayer.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_video_list.*


import java.util.ArrayList


import pub.devrel.easypermissions.EasyPermissions

class VideoListActivity : AppCompatActivity() {

    private val listImage = ArrayList<VideoModel>()
    private var mAdapter: VideoAdapter? = null

    private val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.DATA
    )
    private val orderBy = MediaStore.Video.Media.DISPLAY_NAME
    private val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView( R.layout.activity_video_list)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("本地视频列表")
        }
        val manager = LinearLayoutManager(this)
        recycerView.layoutManager = manager
        mAdapter = VideoAdapter(R.layout.video_item, listImage)
        recycerView.adapter = mAdapter
        swipeLayout.setRefreshing(true)

        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            EasyPermissions.requestPermissions(this, "需要权限", 100, *perms)
        } else {
            getContentProvider(uri, projection, orderBy)
        }

        mAdapter!!.setOnItemChildClickListener { adapter: BaseQuickAdapter<Any,BaseViewHolder>, view: View, position: Int ->
            val intent = Intent(applicationContext, PlayActivity::class.java)
            intent.putExtra("path", listImage[position].path)
            startActivity(intent)
        }
        swipeLayout.setOnRefreshListener({
            listImage.clear()
            getContentProvider(uri, projection, orderBy)
        })
        swipeLayout.setColorSchemeColors(
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorAccent)
        )
    }


    /**
     * 获取ContentProvider
     * @param projection
     * @param orderBy
     */
    fun getContentProvider(uri: Uri, projection: Array<String>, orderBy: String) {
        // TODO Auto-generated method stub
        val cursor = contentResolver.query(uri, projection, null, null, orderBy) ?: return
        if (cursor.moveToFirst()) {
            do {
                val model = VideoModel()
                model.id = cursor.getLong(0)
                model.name = cursor.getString(1)
                model.size = cursor.getInt(2)
                model.path = cursor.getString(3)
                listImage.add(model)
            } while (cursor.moveToNext())
        }
        cursor.close()
        mAdapter!!.notifyDataSetChanged()
        swipeLayout.setRefreshing(false)
        Log.d(TAG, "getContentProvider: $listImage")
    }

    companion object {
        private val TAG = VideoListActivity::class.java.simpleName
    }
}

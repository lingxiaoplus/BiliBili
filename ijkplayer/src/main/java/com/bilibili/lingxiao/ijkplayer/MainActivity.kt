package com.bilibili.lingxiao.ijkplayer

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest.permission
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import pub.devrel.easypermissions.AppSettingsDialog


class MainActivity : AppCompatActivity() ,EasyPermissions.PermissionCallbacks{
    private val mPermessions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    var urls = arrayOf("http://videotest-1252348761.cos.ap-chengdu.myqcloud.com/background.mp4",
            "rtsp://192.168.0.216:554/h264/ch1/main/av_stream",
        "http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_url.setOnClickListener{
            showSelect()
        }
        //权限检测
        if (!EasyPermissions.hasPermissions(this, *mPermessions)){
            //没有权限就申请
            EasyPermissions.requestPermissions(this, "申请权限",
                100, *mPermessions);
        }


    }

    private fun showSelect() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setItems(urls) { dialogInterface, i ->
            var intent = Intent(this@MainActivity,PlayActivity::class.java)
            intent.putExtra("url",urls[i])
            startActivity(intent)
            dialogInterface.dismiss()
        }
        builder.show();
    }
}

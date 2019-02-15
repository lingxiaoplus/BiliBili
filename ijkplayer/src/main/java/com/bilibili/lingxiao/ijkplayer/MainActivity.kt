package com.bilibili.lingxiao.ijkplayer

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var urls = arrayOf("http://videotest-1252348761.cos.ap-chengdu.myqcloud.com/background.mp4?q-sign-algorithm=sha1&q-ak=AKIDVH7tOmsZsr6istaMjGk5bCH1gsmx2OuY&q-sign-time=1550208621;1550210421&q-key-time=1550208621;1550210421&q-header-list=&q-url-param-list=&q-signature=1a9f10acc2f5156dfedfd9fc9ca35a3677500f5f&x-cos-security-token=45cd8271fbb72b7d95fb8c45d788e5a6cdb25ba810001",
        "http://218.207.213.137//PLTV/88888888/224/3221225879/index.m3u8",
        "http://183.251.61.207/PLTV/88888888/224/3221225829/index.m3u8")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_url.setOnClickListener{
            showSelect()
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

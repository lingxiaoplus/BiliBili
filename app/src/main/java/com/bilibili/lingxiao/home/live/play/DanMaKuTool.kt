package com.bilibili.lingxiao.home.live.play

import android.util.Log
import com.bilibili.lingxiao.GlobalProperties
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset
import java.util.*
import kotlin.concurrent.timerTask

object DanMaKuTool {
    private val TAG = DanMaKuTool::class.java.simpleName
    private var webSocket: okhttp3.WebSocket? =null
    fun joinRoom(roomId:Int){
        var client = OkHttpClient.Builder().build()
        var request = Request
            .Builder()
            .url(GlobalProperties.LIVE_DANMAKU_URL)
            .build()
        webSocket = client.newWebSocket(request,object : WebSocketListener(){
            override fun onOpen(webSocket: okhttp3.WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                var inRoomMessage = JSONObject()
                inRoomMessage.put("clientver","1.5.10.1")
                inRoomMessage.put("platform","web")
                inRoomMessage.put("protover",1)
                inRoomMessage.put("uid",0)
                inRoomMessage.put("roomid",roomId)  //必填

                var bytes = inRoomMessage.toString().toByteArray(Charsets.UTF_8)
                var buffer = sendCmd(7,bytes)
                webSocket.send(ByteString.of(buffer))
                startTask(webSocket)
                Log.i(TAG,"websocket连接成功，发送进房消息" + inRoomMessage.toString())
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.i(TAG,"websocket接收消息" + bytes)
            }
            override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.i(TAG,"websocket断开连接")
            }

            override fun onFailure(webSocket: okhttp3.WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.i(TAG,"websocket连接失败" + response.toString(),t)
            }
        })

    }

    private fun sendCmd(cmd:Int,bytes:ByteArray) :ByteBuffer{
        var buffer = ByteBuffer.allocate(16 + bytes.size)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        buffer.putInt(16 + bytes.size)
        buffer.putShort(16)  //头部长度
        buffer.putShort(1)  //协议版本，目前是1
        buffer.putInt(cmd)  //操作码（封包类型）
        buffer.putInt(1)  //sequence，可以取常数1
        Log.i(TAG,"发送消息的头部长度" + buffer.position())
        buffer.put(bytes)
        return buffer
    }

    //每秒发送一条消息
    private var timer: Timer? = null
    private fun startTask(webSocket: okhttp3.WebSocket?){
        timer = Timer()
        var timerTask = timerTask {
            webSocket?.send("" + System.currentTimeMillis())
            //除了文本内容外，还可以将如图像，声音，视频等内容转为ByteString发送
            //boolean send(ByteString bytes);
        }
        timer?.schedule(timerTask, 30000, 1000)
    }

    fun exitRoom(){
        webSocket?.cancel()
        timer?.cancel()
    }
}
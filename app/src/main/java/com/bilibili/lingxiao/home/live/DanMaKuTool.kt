package com.bilibili.lingxiao.home.live

import android.util.Log
import com.bilibili.lingxiao.GlobalProperties
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.ByteOrder
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
                inRoomMessage.put("clientver","1.6.3")
                inRoomMessage.put("platform","web")
                inRoomMessage.put("protover",2)
                inRoomMessage.put("roomid",roomId)  //必填
                inRoomMessage.put("type",2)
                var bytes = inRoomMessage.toString().toByteArray(Charsets.UTF_8)
                sendCmd(7, bytes, webSocket)
                startTask(webSocket)
                Log.d(TAG,"websocket连接成功，发送进房消息$inRoomMessage")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d(TAG,"websocket接收消息$bytes")
            }
            override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(TAG,"websocket断开连接")
                exitRoom()
            }

            override fun onFailure(webSocket: okhttp3.WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG,"websocket连接失败: $response , throw: $t")
                exitRoom()
            }
        })
    }

    /**
     * @param cmd 命令
     * @param data 数据包
     */
    private fun sendCmd(cmd: Int, data: ByteArray, webSocket: WebSocket){
        var buffer = ByteBuffer.allocate(16 + data.size)
        buffer.order(ByteOrder.BIG_ENDIAN)  //字节序为大端模式
        buffer.putInt(16 + data.size)
        buffer.putShort(16)  //头部长度
        buffer.putShort(1)  //协议版本，目前是1
        buffer.putInt(cmd)  //操作码（封包类型）
        buffer.putInt(1)  //sequence，可以取常数1
        buffer.put(data)
        webSocket.send(ByteString.of(buffer))
    }

    //每秒发送一条消息
    private var timer: Timer? = null
    private fun startTask(webSocket: okhttp3.WebSocket?){
        timer = Timer()
        var timerTask = timerTask {
            sendCmd(2, "".toByteArray(), webSocket!!)
            //webSocket?.send("" + System.currentTimeMillis())
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
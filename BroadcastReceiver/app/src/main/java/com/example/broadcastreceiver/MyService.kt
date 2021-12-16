package com.example.broadcastreceiver

import android.app.Service
import android.app.appsearch.GlobalSearchSession
import android.content.Intent
import android.os.IBinder
import android.view.KeyEvent
import kotlin.concurrent.thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyService : Service() {
    private var channel = ""
    private lateinit var thread: Thread
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }

        broadcast(
            when (channel) {
                "music" -> "歡迎來到音樂頻道"
                "new" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                else -> "頻道錯誤"
            }
        )

        if (::thread.isInitialized && thread.isAlive)
            thread.interrupt()

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            broadcast(
                when (channel) {
                    "music" -> "即將播放本月TOP10音樂"
                    "new" -> "即將為您提供獨家新聞"
                    "sport" -> "即將播報本周NBA賽事"
                    else -> "頻道錯誤"
                }
            )
        }
        return START_STICKY
    }


    override fun onBind(intent: Intent): IBinder ?= null
    private fun broadcast(msg: String) =
        sendBroadcast(Intent(channel).putExtra("msg",msg))
}
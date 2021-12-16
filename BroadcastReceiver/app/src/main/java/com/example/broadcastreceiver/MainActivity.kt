package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {





    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent){
            intent.extras?.let{
                val tv_msg = findViewById<TextView>(R.id.tv_msg)
                tv_msg.text = "${it.getString("msg")}"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_music).setOnClickListener{
            register("music")
        }

        findViewById<Button>(R.id.btn_new).setOnClickListener{
            register("new")
        }

        findViewById<Button>(R.id.btn_sport).setOnClickListener{
            register("sport")
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun register(channel:String){
        val intentFilter = IntentFilter(channel)
        registerReceiver(receiver, intentFilter)
        val i = Intent(this, MyService::class.java)
        startService(i.putExtra("channel",channel))
    }
}
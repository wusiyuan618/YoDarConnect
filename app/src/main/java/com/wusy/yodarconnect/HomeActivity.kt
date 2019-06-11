package com.wusy.yodarconnect

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject




class HomeActivity : Activity() {
    private var yodarAPI: IYodarApi? = null
    private var yodarBradcast:YodarBradcast?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        yodarAPI= YodarAPI.getInstance(this)
        tv_debug.movementMethod = ScrollingMovementMethod.getInstance()
        initClick()
        yodarBradcast= YodarBradcast()
        var intentFilter = IntentFilter()
        intentFilter.addAction(YodarAPI.BROADCASTMESSAGE)
        registerReceiver(yodarBradcast, intentFilter)
    }
    fun initClick(){
        btn_getInfo.setOnClickListener {
            yodarAPI?.getHostInfo()
        }
        btn_getTableList.setOnClickListener {
//            yodarAPI?.getMusicList(0,0)
            yodarAPI?.requestPlayerInfo()
        }

        btn_getMusicList.setOnClickListener {
            yodarAPI?.getMusicList(2,0)

        }
        btn_tableUp.setOnClickListener {
            yodarAPI?.requestTurnUpTable(true)
        }
        btn_tableDown.setOnClickListener {
            yodarAPI?.requestTurnUpTable(false)
        }
        btn_playMusic.setOnClickListener {
            yodarAPI?.requestPlayMusic(1,3)
        }
        btn_TurnUp.setOnClickListener {
            yodarAPI?.requestNestMusic(true)
        }
        btn_TurnDown.setOnClickListener {
            yodarAPI?.requestNestMusic(false)
        }
        btn_mute.setOnClickListener {
            yodarAPI?.requestMute()
        }
        btn_SingleCycle.setOnClickListener {
            yodarAPI?.requestSingleCycle(true)
        }
        btn_ShufflePlayback.setOnClickListener {
            yodarAPI?.requestShufflePlayback(true)
        }
        btn_playOrPause.setOnClickListener {
            yodarAPI?.requestPlayOrPause()
        }
        btn_boot.setOnClickListener {
            yodarAPI?.requestBoot()
        }
        btn_unboot.setOnClickListener {
            yodarAPI?.requestUnBoot()
        }
    }
    /**
     * 向TextView中添加内容
     * 当TextView的内容超标时，一直显示最后一行
     */
    fun refreshLogView(msg: String) {
        runOnUiThread {
            tv_debug.append(msg+"\n")
            var offset = tv_debug.lineCount * tv_debug.lineHeight
            if (offset > tv_debug.height) {
                tv_debug.scrollTo(0, offset - tv_debug.height + tv_debug.lineHeight * 2)
            }
        }
    }
    /**
     * 将json对象转换成Map
     *
     * @param jsonObject json对象
     * @return Map对象
     */
    fun jsonToMap(jsonStr: String): Map<String, String> {
        val result = HashMap<String, String>()
        var jsonObject =JSONObject(jsonStr)
        val iterator = jsonObject.keys()
        var key: String? = null
        var value: String? = null
        while (iterator.hasNext()) {
            key = iterator.next()
            value = jsonObject.getString(key)
            result[key!!] = value!!
        }
        return result
    }
    override fun onDestroy() {
        super.onDestroy()
        if(yodarBradcast!=null) unregisterReceiver(yodarBradcast)
    }
    internal inner class YodarBradcast: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action==YodarAPI.BROADCASTMESSAGE){
                var jsonStr=intent.getStringExtra("data")
                var maps =jsonToMap(jsonStr)
                when(maps["Command"]?.toByte()){
                    0xef.toByte() -> {//返回了设备信息
                        refreshLogView("成功连接设备："+maps["Name"])
                    }
                    0x0f.toByte() -> {//返回了设备信息
                        if(maps["id"]=="0"){
                            refreshLogView("获取到了目录:")
                            for (i in YodarAPI.tableBean.arg.nodeList.indices) {
                                refreshLogView("${i+1}-"+YodarAPI.tableBean.arg.nodeList.get(i).name)
                            }
                        }else{
                            refreshLogView("获取到了音乐（分页获取，一页6首）:")
                            for (i in YodarAPI.musicBean.arg.nodeList.indices) {
                                refreshLogView("${i+1}-"+YodarAPI.musicBean.arg.nodeList.get(i).name)
                            }
                        }
                    }
                }
            }
        }

    }
}

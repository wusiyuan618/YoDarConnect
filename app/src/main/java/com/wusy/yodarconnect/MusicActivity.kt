package com.wusy.yodarconnect

import android.app.Activity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : Activity(), SeekBar.OnSeekBarChangeListener {
    var currentTime:Int=0
    var isPlay=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        seekBar.setOnSeekBarChangeListener(this)
        startPro()
        btn_play.setOnClickListener {
            if(isPlay){//执行暂停
                isPlay=false
                currentTime=seekBar.progress
            }else{//执行播放
                isPlay=true

            }
        }
    }
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tv_currentTime.text = progress.toString() + ""
        currentTime=progress
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
    }
    fun resetPro(max:Int){
        seekBar.progress=0
        seekBar.max=max
    }
    fun proToSkip(){

    }
    fun startPro(){
        isPlay=true
        Thread(Runnable {
            while (seekBar.progress<seekBar.max){
                Thread.sleep(1000)
                if(!isPlay) continue
                seekBar.progress+=1
                runOnUiThread {
                    tv_currentTime.text= seekBar.progress.toString()
                }
            }
        }).start()
    }
}
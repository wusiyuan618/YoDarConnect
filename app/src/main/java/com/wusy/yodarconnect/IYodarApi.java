package com.wusy.yodarconnect;

public interface IYodarApi {
    /**
     * 获取主机信息
     * @return
     */
    void getHostInfo();
    /**
     * 开机
     * @return
     */
    void requestBoot();

    /**
     * 关机
     * @return
     */
    void requestUnBoot();

    /**
     * 上下一曲
     * type=0x05上一曲
     * type=0x09下一曲
     */
    void requestNestMusic(boolean isUp);

    /**
     * 播放或者暂停
     * @return
     */
    void requestPlayOrPause();
    /**
     * 静音与解除静音
     */
    void requestMute();
    /**
     * 单曲循环
     */
    void requestSingleCycle(boolean isNeed);
    /**
     * 随机播放
     */
    void requestShufflePlayback(boolean isNeed);
    /**
     * 请求进入选曲页面
     * error：能够正常通信，设备部响应
     */
    void requestBeginSelect();

    /**
     * 退出选曲
     * error：无法通讯，请求超时
     * @return
     */
    void requestEndSelect();
    /**
     * 上下翻页
     * error：能够正常通信，设备部响应
     */
    void requestPageTurning(boolean isUp);
    /**
     * 选择目录
     */
    void requestSelectTable();
    /**
     * 返回目录列表
     */
    void requestBackTableList();
    /**
     * 目录上下翻页
     */
    void requestTurnUpTable(boolean isUp);
    /**
     * 选择歌曲
     */
    void requestSelectMusic();
    /**
     * 获取SDK歌单
     */
    void getMusicList(int id, int begin, int size);
    /**
     * 播放指定音乐
     */
    void requestPlayMusic(int albumId, int id);
    /**
     * 获取当前播放的音乐信息
     */
    void requestPlayerInfo();
    /**
     * 音量设置
     */
    void setVoiceSize(int size);
    /**
     * 当前音乐播放时间设置
     */
    void setMusicTime(int second);
    /**
     * 音源设置
     * 0x0b MP3
     * 0x0f 云音乐
     */
    void setMusicSource(byte source);
}

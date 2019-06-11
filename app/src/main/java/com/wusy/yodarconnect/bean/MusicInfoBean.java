package com.wusy.yodarconnect.bean;

import java.io.Serializable;

public class MusicInfoBean implements Serializable {

    /**
     * album : 灰姑娘
     * artist : 陈雪凝
     * bass : 13
     * download : 0
     * duration : 293
     * eq : 4
     * id : 2
     * like : 0
     * mute : 0
     * name : 灰姑娘
     * picUrl : http://y.gtimg.cn/music/photo_new/T003R300x300M000004boRsu14cBvh.jpg
     * playMode : 0
     * playTime : 216
     * source : 5
     * state : 3
     * treb : 7
     * volume : 88
     */

    private String album;
    private String artist;
    private int bass;
    private int download;
    private int duration;
    private int eq;
    private int id;
    private int like;
    private int mute;
    private String name;
    private String picUrl;
    private int playMode;
    private int playTime;
    private int source;
    private int state;
    private int treb;
    private int volume;

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getBass() {
        return bass;
    }

    public void setBass(int bass) {
        this.bass = bass;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEq() {
        return eq;
    }

    public void setEq(int eq) {
        this.eq = eq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getMute() {
        return mute;
    }

    public void setMute(int mute) {
        this.mute = mute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getPlayMode() {
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTreb() {
        return treb;
    }

    public void setTreb(int treb) {
        this.treb = treb;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}

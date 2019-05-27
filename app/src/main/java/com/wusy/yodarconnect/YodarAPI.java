package com.wusy.yodarconnect;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YodarAPI implements IYodarApi, UDPUtil.OnResolveDataListener {
    private static UDPUtil udpUtil;
    private static IYodarApi yodarAPI;
    private Map<String, Object> hostInfo;
    public static TableBean tableBean;
    public static MusicBean musicBean;
    public static final String BROADCASTMESSAGE="YODARAPI.BROADCAST.UDPRECIVER";
    private Context mC;
    private YodarAPI(Context context) throws IOException {
        this.mC=context;
        udpUtil = UDPUtil.getInstance();
        udpUtil.setOnResolveDataListener(this);
    }

    public synchronized static IYodarApi getInstance(Context context) {
        if (yodarAPI == null) {
            try {
                yodarAPI = new YodarAPI(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return yodarAPI;
    }

    @Override
    public void getHostInfo() {
        byte[] sendBuf = new byte[3];
        sendBuf[0] = (byte) 0xce;
        sendBuf[1] = 0x00;
        sendBuf[2] = (byte) (0xce ^ 0x00);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestBoot() {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xA3;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x07;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestUnBoot() {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xA3;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x03;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestNestMusic(boolean isUp) {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xA3;
        sendBuf[1] = (byte) (address << 4);
        if(isUp) sendBuf[2] = 0x05;
        else   sendBuf[2] = 0x09;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestPlayOrPause() {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xA3;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x02;
        sendBuf[3] = 0x00;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestMute() {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xAB;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestSingleCycle(boolean isNeed) {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xEA;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x01;
        if (isNeed) sendBuf[3] = 0x01;
        else sendBuf[3] = 0x00;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestShufflePlayback(boolean isNeed) {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0XEA;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x03;
        if (isNeed) sendBuf[3] = 0x01;
        else sendBuf[3] = 0x00;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestBeginSelect() {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xE5;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x00;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestEndSelect() {
        byte[] sendBuf = new byte[5];
        byte address = (byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xE5;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x03;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestPageTurning(boolean isUp) {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xE5;
        sendBuf[1] = (byte) (address << 4);
        if (isUp) sendBuf[2] = 0x01;
        else sendBuf[2] = 0x02;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestSelectTable() {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xE2;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0;
        sendBuf[3] = 0x01;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);

    }

    @Override
    public void requestBackTableList() {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xED;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x03;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestTurnUpTable(boolean isUp) {
        byte[] sendBuf = new byte[5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xED;
        sendBuf[1] = (byte) (address << 4);
        if (isUp) sendBuf[2] = 0x01;
        else sendBuf[2] = 0x02;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestSelectMusic() {
        byte[] sendBuf = new byte[5];
        byte address = (byte) hostInfo.get("Address");
        sendBuf[0] = (byte) 0xE3;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 1;
        sendBuf[3] = 0;
        sendBuf[4] = XOR(sendBuf, 1, 3);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void getMusicList(int id,int begin) {
        JSONObject json=new JSONObject();
        try {
            JSONObject arg=new JSONObject();
            arg.put("begin",begin);
            arg.put("id",id);
            arg.put("size",6);
            arg.put("type",2);
            json.put("arg",arg);
            json.put("call","list.dirNodeList");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] jsonByte=json.toString().getBytes();
        byte[] sendBuf = new byte[jsonByte.length+5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = 0x0f;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x00;
        sendBuf[3] = (byte) (json.toString().getBytes().length+5);
        for(int i=0;i<jsonByte.length;i++){
            sendBuf[i+4]=jsonByte[i];
        }
        sendBuf[sendBuf.length-1]=XOR(sendBuf, 0, sendBuf.length-1);
        udpUtil.sendUDP(sendBuf);
    }

    @Override
    public void requestPlayMusic(int albumId,int id) {
        JSONObject json=new JSONObject();
        try {
            JSONObject arg=new JSONObject();
            arg.put("albumId",albumId);
            arg.put("id",id);
            json.put("arg",arg);
            json.put("call","player.play");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] jsonByte=json.toString().getBytes();
        byte[] sendBuf = new byte[jsonByte.length+5];
        byte address =(byte) hostInfo.get("Address");
        sendBuf[0] = 0x0f;
        sendBuf[1] = (byte) (address << 4);
        sendBuf[2] = 0x00;
        sendBuf[3] = (byte) (json.toString().getBytes().length+5);
        for(int i=0;i<jsonByte.length;i++){
            sendBuf[i+4]=jsonByte[i];
        }
        sendBuf[sendBuf.length-1]=XOR(sendBuf, 0, sendBuf.length-1);
        udpUtil.sendUDP(sendBuf);
    }

    /**
     * 循环异或
     *
     * @return
     */
    private byte XOR(byte[] bytes, int start, int len) {
        byte b = bytes[start];
        for (int i = start; i < start + len - 1; i++) {
            b = (byte) (b ^ bytes[i + 1]);
        }
        return b;
    }

    @Override
    public void resolveData(byte[] bytes,int len) {
        Map<String,Object> maps=new HashMap<>();
        switch (bytes[0]){
            case (byte) 0xef://主机信息返回
                maps.put("Command",bytes[0]);
                maps.put("Address",bytes[5]);
                maps.put("Model",bytes[4]);//通道数
                maps.put("iphoneId",bytes[5]);//编号
                maps.put("NameId",bytes[6]);
                maps.put("Name",udpUtil.byteToString(bytes,8,bytes[7]));
                hostInfo=maps;
                break;
            case (byte)0x0f:
                String josnStr=udpUtil.byteToString(bytes,4,len-5);
                JSONObject jsonObject=null;
                try {
                    jsonObject=new JSONObject(josnStr);
                    JSONObject joArg=jsonObject.getJSONObject("arg");
                    maps.put("Command",bytes[0]);
                    maps.put("id",joArg.getInt("id"));
                    if(joArg.getInt("id")==0){//获取的目录
                        tableBean=new Gson().fromJson(josnStr,TableBean.class);
                    }else{//获取的音乐
                        joArg.getJSONArray("nodeList");
                        musicBean=new Gson().fromJson(josnStr,MusicBean.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                break;
        }
        Intent intent=new Intent();
        intent.setAction(BROADCASTMESSAGE);
        intent.putExtra("data",new Gson().toJson(maps));
        mC.sendBroadcast(intent);
    }
}

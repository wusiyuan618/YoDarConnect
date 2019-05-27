//package com.wusy.yodarconnect;
//
//import android.util.Log;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketTimeoutException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 基于UDP与Yodar协议
// * 为实现Yodar设备的背景音乐控制功能提供API
// * 必须用发送的socket去接收Yodar回调的UDP包，否则收不到
// * 发送和接收用不同的Port，避免需要2次接收（因为相同的话自己发的UDP包也能收到）
// */
//public class YodarApiOld implements IYodarApi{
//    private static IYodarApi yodarApi;
//    private Map<String,String> hostInfo;
//    private final int SENDPORT=10061;
//    private final int REVCIVEPORT=10062;
//    private final int SOCKETTIMEOUT=1000;
//    private final String IP="255.255.255.255";
//    private int reSetCount=2;
//    private int currentCount=0;
//    private boolean isRequestHostInfo=false;
//    private YodarApiOld(){
//
//    }
//    public synchronized static IYodarApi getInstance(){
//        if(yodarApi==null) yodarApi=new YodarApiOld();
//        return yodarApi;
//    }
//    @Override
//    public Map<String,String> getHostInfo(){
//        isRequestHostInfo=true;
//        byte[] sendBuf=new byte[3];
//        sendBuf[0]=(byte) 0xce;
//        sendBuf[1]=0x00;
//        sendBuf[2]=(byte) (0xce^0x00);
//        return SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                Map<String,String> map=new HashMap<>();
//                map.put("Address",String.valueOf(bytes[1]));
//                map.put("Model",String.valueOf(bytes[4]));//通道数
//                map.put("iphoneId",String.valueOf(bytes[5]));//编号
//                map.put("NameId",String.valueOf(bytes[6]));
//                map.put("Name",byteToString(bytes,8,bytes[7]));
//                hostInfo=map;
//                return map;
//            }
//        });
//    }
//
//    @Override
//    public boolean requestBoot() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xA3;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x07;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestUnboot() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xA3;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x03;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public Map<String,String> requestNestMusic(byte type) {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xA3;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=type;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        return SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                Map<String,String> maps=new HashMap<>();
//                maps.put("Name",byteToString(bytes,4,bytes[2]-6));
//                Log.i("wsy","歌名="+byteToString(bytes,4,bytes[2]-5));
//                return new HashMap<>();
//            }
//        });
//    }
//
//    @Override
//    public boolean requestPlayOrPause() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xA3;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x02;
//        sendBuf[3]=0x00;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestMute() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xAB;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestSingleCycle(boolean isNeed) {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xEA;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x01;
//        if (isNeed) sendBuf[3]=0x01;
//        else sendBuf[3]=0x00;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestShufflePlayback(boolean isNeed) {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0XEA;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x03;
//        if (isNeed) sendBuf[3]=0x01;
//        else sendBuf[3]=0x00;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestBeginSelect() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xE5;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x00;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestEndSelect() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xE5;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x03;
//        sendBuf[3]=0;
//        sendBuf[4]= (byte) (sendBuf[1]^sendBuf[2]^sendBuf[3]);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestPageTurning(boolean isUp) {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xE5;
//        sendBuf[1]= (byte) (address<< 4);
//        if(isUp)  sendBuf[2]=0x01;
//        else sendBuf[2]=0x02;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public Map<String, String> requestSelectTable() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xE2;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0;
//        sendBuf[3]=0x01;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        return SendUdpAlways(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//
//    }
//
//    @Override
//    public boolean requestBackTableList() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xED;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=0x03;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestTurnUpTable(boolean isUp) {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xED;
//        sendBuf[1]= (byte) (address<< 4);
//        if(isUp)  sendBuf[2]=0x01;
//        else sendBuf[2]=0x02;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdpAlways(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    @Override
//    public boolean requestSelectMusic() {
//        byte[] sendBuf=new byte[5];
//        byte address =Byte.valueOf(hostInfo.get("Address"));
//        sendBuf[0]=(byte) 0xE3;
//        sendBuf[1]= (byte) (address<< 4);
//        sendBuf[2]=1;
//        sendBuf[3]=0;
//        sendBuf[4]=XOR(sendBuf,1,3);
//        Map<String,String> maps=SendUdp(sendBuf, new OnResolveDataListener() {
//            @Override
//            public Map<String, String> resolveData(byte[] bytes) {
//                return new HashMap<>();
//            }
//        });
//        return maps!=null;
//    }
//
//    /**
//     * 发送UDP包给Yodar并返回接收内容
//     * @param sendBuf
//     * @param listener
//     * @return
//     * @throws IOException
//     */
//    private Map<String,String> SendUdp(byte[] sendBuf,OnResolveDataListener listener){
//        Log.i("wsy","开始构建并发送UDP包");
//        DatagramSocket ds = null;
//        try {
//            ds= new DatagramSocket(REVCIVEPORT);
//            ds.setBroadcast(true);
//            DatagramPacket sendDp = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(IP), SENDPORT);
//            ds.send(sendDp);
//            ds.setSoTimeout(SOCKETTIMEOUT);
//            byte[] receiveBuf = new byte[1024];
//            DatagramPacket receiveDp = new DatagramPacket(receiveBuf, receiveBuf.length);
//
//            ds.receive(receiveDp); // 该方法为阻塞式的方法
//            String ip = receiveDp.getAddress().getHostAddress();
//            int port = receiveDp.getPort();
//
//            byte[] text = receiveDp.getData();
//            Map<String, String> maps = listener.resolveData(text);
////            for (int i=0;i<receiveDp.getLength();i++) {
////                Log.d("wsy","接收到第"+i+"byte的值="+ text[i]);
////            }
//            Log.d("wsy", ip + ":" + port + ":" + byteToString(text, 0, receiveDp.getLength()));
//            if(!isRequestHostInfo) currentCount=0;
//            isRequestHostInfo=false;
//            return maps;
//        }catch (SocketTimeoutException e){
//            Log.e("wsy","socket请求超时");
//        }catch(Exception e){
//            e.printStackTrace();
//        } finally {
//            if(ds!=null){
//                ds.disconnect();
//                ds.close();
//            }
//        }
//        try {
//            if(currentCount<reSetCount){
//                Log.e("wsy","设置失败,1S后自动重设，当前次数="+currentCount);
//                Thread.sleep(1000);
//                getHostInfo();
//                currentCount++;
//                SendUdp(sendBuf,listener);
//            }else{
//                Log.e("wsy","设置失败");
//                currentCount=0;
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            Log.e("wsy","设置失败");
//        }
//        return null;
//    }
//    /**
//     * 发送UDP包给Yodar并返回接收内容
//     * @param sendBuf
//     * @param listener
//     * @return
//     * @throws IOException
//     */
//    private Map<String,String> SendUdpAlways(byte[] sendBuf,OnResolveDataListener listener){
//        Log.i("wsy","开始构建并发送UDP包");
//        DatagramSocket ds = null;
//        Map<String, String> maps = new HashMap<>();
//        try {
//            ds= new DatagramSocket(REVCIVEPORT);
//            ds.setBroadcast(true);
//            DatagramPacket sendDp = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(IP), SENDPORT);
//            ds.send(sendDp);
//            ds.setSoTimeout(SOCKETTIMEOUT);
//            byte[] receiveBuf = new byte[1024];
//            DatagramPacket receiveDp = new DatagramPacket(receiveBuf, receiveBuf.length);
//            while (true){
//                ds.receive(receiveDp); // 该方法为阻塞式的方法
//                String ip = receiveDp.getAddress().getHostAddress();
//                int port = receiveDp.getPort();
//                byte[] text = receiveDp.getData();
//                maps.putAll(listener.resolveData(text));
//                Log.d("wsy", ip + ":" + port + ":" + byteToString(text, 0, receiveDp.getLength()));
//            }
//        }catch (SocketTimeoutException e){
//            Log.e("wsy","数据接受完毕");
//        }catch(Exception e){
//            e.printStackTrace();
//        } finally {
//            if(ds!=null){
//                ds.disconnect();
//                ds.close();
//            }
//        }
//        return maps;
//    }
//    /**
//     * 将Byte转为String（按照ASCLL码表将10进制的byte值转成字符）
//     * @param b
//     * @param start
//     * @param len
//     * @return
//     */
//    public String byteToString(byte[] b,int start,int len){
//        try {
//            byte[] bytes=new byte[len];
//            for (int i=start;i<start+len;i++){
//                bytes[(i-start)]=b[i];
//            }
//            return new String(bytes,"gbk");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "转化出错";
//    }
//    public interface OnResolveDataListener{
//        Map<String,String> resolveData(byte[] bytes);
//    }
//    public void logMap(Map<String,String> map){
//        Log.d("wsy","------start log maps-------");
//        for (String key:map.keySet()) {
//            Log.d("wsy",key+"="+map.get(key));
//        }
//        Log.d("wsy","------end log maps------");
//
//    }
//
//    /**
//     * 循环异或
//     * @return
//     */
//    private byte XOR(byte[] bytes,int start,int len){
//        byte b=bytes[start];
//        for (int i=start;i<start+len-1;i++){
//            b= (byte) (b^bytes[i+1]);
//        }
//        return b;
//    }
//}

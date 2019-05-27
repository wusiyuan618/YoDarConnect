package com.wusy.yodarconnect;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class UDPUtil {
    private static UDPUtil udpUtil;
    DatagramSocket ds;
    private final int SENDPORT=10061;
    private final int REVCIVEPORT=10062;
    private final int SOCKETTIMEOUT=1000;
    private final String IP="255.255.255.255";
    private OnResolveDataListener onResolveDataListener;
    private UDPUtil() throws IOException {
        init();
        startUDPService();
    }
    private void init() throws IOException {
        ds= new DatagramSocket(REVCIVEPORT);
        ds.setBroadcast(true);
//        ds.setSoTimeout(SOCKETTIMEOUT);//设置超时时间
    }
    public synchronized static UDPUtil  getInstance() throws IOException {
        if (udpUtil==null) udpUtil=new UDPUtil();
        return udpUtil;
    }
    public void sendUDP(final byte[] sendBuf){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramPacket sendDp = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(IP), SENDPORT);
                    ds.send(sendDp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void startUDPService(){
        if(ds==null)return;
        Log.i("wsy","UDP Service star");
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] receiveBuf = new byte[1024];
                DatagramPacket receiveDp = new DatagramPacket(receiveBuf, receiveBuf.length);
                while (true){
                    try {
                        ds.receive(receiveDp); // 该方法为阻塞式的方法
                        String ip = receiveDp.getAddress().getHostAddress();
                        int port = receiveDp.getPort();
                        byte[] text = receiveDp.getData();
//                        for (int i=0;i<receiveDp.getLength();i++) {
//                            Log.d("wsy","接收到第"+i+"byte的值="+ text[i]);
//                        }
                        Log.d("wsy", ip + ":" + port + ":" + byteToString(text, 0, receiveDp.getLength()));
                        if(onResolveDataListener!=null) onResolveDataListener.resolveData(text,receiveDp.getLength());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("wsy","UDP Service error end");
                    }
                }
            }
        }).start();

    }
    /**
     * 将Byte转为String（按照ASCLL码表将10进制的byte值转成字符）
     * @param b
     * @param start
     * @param len
     * @return
     */
    public String byteToString(byte[] b,int start,int len){
        try {
            byte[] bytes=new byte[len];
            for (int i=start;i<start+len;i++){
                bytes[(i-start)]=b[i];
            }
            return new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "转化出错";
    }
    public interface OnResolveDataListener{
        void resolveData(byte[] bytes,int len);
    }

    public void setOnResolveDataListener(OnResolveDataListener onResolveDataListener) {
        this.onResolveDataListener = onResolveDataListener;
    }
}

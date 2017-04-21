package com.udpCilnt;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by young on 2017/4/20.
 */
public class Udp {

    public void send(String message,String ip){
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(9004);
            byte[] buf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), 9001);
            datagramSocket.send(packet);
           // tp.setText(tp.getText() + ":" + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }
}

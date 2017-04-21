package com.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by young on 2017/4/19.
 */
public class MainWindow {

    private JLabel lb_addIp;
    private JButton btn_add;
    private JPanel jp_qq;
    private JLabel lb_selectIP;
    private JComboBox<String> cb_selectIP;
    private JTextField tf_addip;
    private JTextArea ta_charHistory;
    private JTextField tf_input;
    private JButton btn_submit;
    private JLabel lb_charHistory;
    private JLabel lb_message;
    private JTextPane tp;

    private String ip;
    private String sendMessage;

    private int sendport = 10023;

    private int receviceport = 10024;

    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    public String name;
    private List<String> messageList = new ArrayList<>();

    public MainWindow() {

        btn_add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ip = tf_addip.getText();
                Object[] com = new Object[]{ip};
                cb_selectIP.addItem(ip);
            }
        });
        btn_submit.addMouseListener(new MouseAdapter() {
            Logger logger = Logger.getLogger("submit");

            @Override
            public void mouseClicked(MouseEvent e) {
                if (ip == null) {
                    lb_message.setForeground(Color.red);
                    lb_message.setText("请输入IP。");
                    return;
                }
                try {
                    InetAddress inetAddress = InetAddress.getByName(ip);
                    if (!inetAddress.isReachable(2000)) {
                        lb_message.setForeground(Color.red);
                        lb_message.setText("无法链接该地址！");
                        return;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                sendMessage = tf_input.getText();

                logger.info("sendMessage" + sendMessage);


                new Thread(() -> {
                    send(sendMessage);
                    lb_message.setForeground(Color.green);
                    lb_message.setText("发送成功。");
                }).start();
                new Thread(() -> {
                    receive();
                }).start();
            }
        });

    }

    public void receive() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(sendport, InetAddress.getByName(ip));
            byte[] buf = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            datagramSocket.receive(datagramPacket);
            String id = datagramPacket.getAddress().getHostAddress();
            int port = datagramPacket.getPort();
            byte[] date = datagramPacket.getData();
            System.out.println(Arrays.toString(date));
            String mes = ip + ":" + new String(date) + "\n";
            //System.out.println("ip=" + id + "port=" + port);
            tp.setText(tp.getText() + mes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert datagramSocket != null;
            datagramSocket.close();
        }
    }

    public void initWindow() {
        JFrame frame = new JFrame("QQ");
        frame.setContentPane(new MainWindow().jp_qq);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        tp.setEditable(false);
    }

    public void send(String message) {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(receviceport);
            byte[] buf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), sendport);
            datagramSocket.send(packet);
            tp.setText(tp.getText() + name + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }

}
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by young on 2017/4/20.
 */
public class Send implements Runnable {
    DatagramSocket ds = null;
    DatagramPacket dp=null;
    InetAddress address = null;
    int port = 0;
    byte[] buf = new byte[256];

    public Send(){
        try {
            ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

    }
}

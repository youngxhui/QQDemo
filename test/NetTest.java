import sun.nio.ch.Net;

import java.io.IOException;
import java.net.*;

/**
 * Created by young on 2017/4/20.
 */
public class NetTest {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress);
    }
}

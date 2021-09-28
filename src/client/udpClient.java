package client;

import java.net.*;
import java.io.*;

public class udpClient extends clientAbstract{

    DatagramSocket datagramSocket;
    InetAddress address;
    byte[] buffer;

    public udpClient(String port_, String host_) {
        super(port_, host_);
        try {
            this.datagramSocket = new DatagramSocket(port);
            this.address = InetAddress.getByName(hostname);
        } catch (Exception e) {
            runnable = false;
            clientLog.callLogger("setting up datagram socket failed " +e.toString());
        }
    }
    
    @Override
    public
    void run() {
        while(running)
        {
            try {
                String requestToSend = serverDataGET("1");
                buffer = requestToSend.getBytes();
                DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length, address, port);
                datagramSocket.send(dataPacket);
                datagramSocket.receive(dataPacket);
                String serverMsg = new String(dataPacket.getData(), 0, dataPacket.getLength());
                clientLog.callLogger("server request granted : " +serverMsg);
            } catch (Exception e) {
                clientLog.callLogger(e.toString());
                break;
            }
        }
    }

    public static void main(String[] args) {
        udpClient client = new udpClient(getPort(), getHost());
        if(client.runnable)
        {
            client.running = true;
            client.run();
        }    
    }
    
}

package server;

import java.io.*;
import java.net.*;

import client.clientLogger;

public class udpServer extends serverAbstract{

    DatagramSocket datagramSocket;
    byte[] buffer = new byte[256];


    public udpServer(String port_) {
        super(port_, "UDP");
        try {
            this.datagramSocket = new DatagramSocket(port);
            startServer();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public
    void startServer() {
        running = true;
        run();
    }

    @Override
    public
    void stopServer() {
        running = false;
    }

    @Override
    public
    void run()
    {
        while(running)
        {
            try {
                DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(dataPacket);
                InetAddress address =  dataPacket.getAddress();
                int clientPort = dataPacket.getPort();

                String clientRequest = new String(dataPacket.getData(),0, dataPacket.getLength());
                serverLog.callLogger("request from client is : " +clientRequest);
                dataPacket = new DatagramPacket(buffer, buffer.length,address,clientPort);
                
            } catch (Exception e) {
                //TODO: handle exception
                break;
            }
        }
        //DatagramSocket aSocket = null;
        //try {
        //    int socket_no = port;
        //    aSocket = new DatagramSocket(socket_no);
        //    while(running)
        //    {
//
        //            byte[] buffer = new byte[1000];
        //            while(true)
        //            {
        //                DatagramPacket request = new DatagramPacket(buffer,
        //                buffer.length);
        //                aSocket.receive(request);
        //                DatagramPacket reply = new DatagramPacket(request.getData(),
        //                request.getLength(),request.getAddress(),
        //                request.getPort());
        //                aSocket.send(reply);
        //            }
        //    }
        //}
        //catch (SocketException e) {
        //    //serverLog.callLogger("Socket: " + e.getMessage());
        //}
        //catch (IOException e) {
        //    //serverLog.callLogger("IO: " + e.getMessage());
        //}
        //finally {
        //    if (aSocket != null) 
        //        aSocket.close();
        //}
    } 

    public static void main(String[] args) {
        if( args.length == 0 )
        {
            System.out.println( "Please add a port number to connect to server." );
            System.exit( 0 );
        }
        String mentionedPort = args[0];
        udpServer server = new udpServer(mentionedPort);
        if(server.runnable)
            server.run();
    }
}
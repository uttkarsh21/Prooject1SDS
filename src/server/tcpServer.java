package server;

import java.io.*;
import java.net.*;

public class tcpServer extends serverAbstract {

    ServerSocket serverSocket;

    public tcpServer(String port_) {
        super(port_, "TCP");
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            serverLog.callLogger(e.toString());
        }
    }

    static tcpServer tcpServer;
    public static void main(String[] args) {
        if( args.length == 0 )
        {
            System.out.println( "Please add a port number && hostname to connect to server." );
            System.exit( 0 );
        }
        String mentionedPort = args[0];
        tcpServer = new tcpServer(mentionedPort);
        if(tcpServer.runnable)
            tcpServer.startServer();
        
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
        try {
            if(serverSocket != null)
                serverSocket.close();
        } catch (Exception e) {
            serverLog.callLogger(e.toString());
        }
    } //never used since server runs forever;

    @Override
    public
    void run()
    {
        try
        {
            while(running)
            {
                tcpServer.serverLog.callLogger("Listening for the client!");    

                Socket socket = serverSocket.accept();
    
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF("connection acquired");

                DataInputStream dataReceived = new DataInputStream(socket.getInputStream());
               
                String s = new String (dataReceived.readUTF());
                tcpServer.serverLog.callLogger("string received from client : " + s);      
               
                String op = catchServerRequest(s);

                tcpServer.serverLog.callLogger("sending reply back to client : " +op);
                outputStream.writeUTF(op);    

                outputStream.close();
                socket.close();
            }
            System.out.println("closing server");
            
        } catch (Exception e) {
            serverLog.callLogger("exception thrown : " +e);
            System.exit(0);
        }
    }

}

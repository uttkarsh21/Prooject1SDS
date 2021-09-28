package server;

import java.io.*;
import java.net.*;

public class tcpServer extends serverAbstract {
    public tcpServer(String port_) {
        super(port_, "TCP");
    }

    static tcpServer tcpServer;
    public static void main(String[] args) {
        Console console = System.console();
        String port_ = console.readLine("Enter a port : ");
        tcpServer = new tcpServer(port_);
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
    } //never used since server runs forever;

    @Override
    public
    void run()
    {
        try
        {
            ServerSocket thisServer = new ServerSocket(port);
            while(running)
            {
                tcpServer.serverLog.callLogger("Listening for the client!");    

                Socket socket1 = thisServer.accept();
        
                OutputStream socket1out = socket1.getOutputStream();
                DataOutputStream outputStream = new DataOutputStream(socket1out);
                outputStream.writeUTF("connection acquired");

                InputStream input = socket1.getInputStream();
                DataInputStream dataReceived = new DataInputStream(input);
               
                String s = new String (dataReceived.readUTF());
                tcpServer.serverLog.callLogger("string received from client : " + s);      
               
                String op = catchServerRequest(s);

                tcpServer.serverLog.callLogger("sending reply back to client : " +op);
                outputStream.writeUTF(op);    

                outputStream.close();
                socket1.close();
            }
            System.out.println("closing server");
            thisServer.close();
        } catch (Exception e) {
            serverLog.callLogger("exception thrown : " +e);
            System.exit(0);
        }
    }

}

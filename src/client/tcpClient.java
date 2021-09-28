package client;

import java.net.*;
import java.io.*;

public class tcpClient extends clientAbstract {
    static tcpClient tcpClient;
    Socket socketClient;
    public static void main(String[] args) throws IOException {
        if( args.length == 0 )
        {
            System.out.println( "Please add a port number && hostname to connect to server." );
            System.exit( 0 );
        }
        String mentionedPort = args[0];
        String mentionedHost = args[1];
        tcpClient = new tcpClient(mentionedPort,mentionedHost);
        if(tcpClient.runnable)
        {
            tcpClient.socketClient = new Socket(tcpClient.hostname,tcpClient.port);
            tcpClient.running = true;
            tcpClient.run();
        }
    }

    public tcpClient(String port_, String host_) {
        super(port_, host_);
    }
    
    @Override
    public
    void run()
    {
        try
        {           
            while(running)
            {
                DataInputStream inputStream = new DataInputStream(socketClient.getInputStream());
    
                String st = new String (inputStream.readUTF());
                tcpClient.clientLog.callLogger("Server " +st);
        
                DataOutputStream outputStream = new DataOutputStream(socketClient.getOutputStream());        
            
                outputStream.writeUTF(tcpClient.serverDataGET("1"));
        
                inputStream = new DataInputStream(socketClient.getInputStream());
                st = new String (inputStream.readUTF());
                tcpClient.clientLog.callLogger("Reply from server is " +st);
    
                tcpClient.clientLog.callLogger("closing client now!");
                inputStream.close();
            }
        } catch (SocketException e) {
            tcpClient.clientLog.callLogger("Server port not found, exception thrown : " +e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            tcpClient.clientLog.callLogger(e.toString());
        }
    }

    @Override
    public void startClient() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stopClient() {
        try {
            socketClient.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}

package server;

import java.net.UnknownHostException;
import java.util.HashMap;

import java.io.*;

public abstract class serverAbstract {
    public int port; 
    public String serverType;
    public boolean running;
    public boolean runnable;
    public String hostname;
    public serverLogger serverLog;

    public HashMap<String, String> serverStore;

    public serverAbstract(String port_, String type_)
    {
        runnable = true;
        serverStore = new HashMap<String, String>();
        serverLog = new serverLogger();
        serverLog.setLogger("serverLog.log", true);
        try {
            port =  Integer.parseInt(port_);
            serverLog.callLogger("initiated TCP server");
        } catch (NumberFormatException e) {
            serverLog.callLogger("invalid port info given");
            runnable = false;
            return;
        }
        
        serverType = type_;
        try {
            hostname = java.net.InetAddress.getLocalHost().getHostName();
            serverLog.callLogger("port is "+port +" , on server type " +serverType + " , hostname is " +hostname);
        } catch (UnknownHostException e) {
            serverLog.callLogger(e.toString());
            runnable = false;
        }
    }

    public abstract void startServer();
    public abstract void stopServer();
    public abstract void run();


    public String dataPUT(String key, String value) {
        String action = "done";

        serverStore.put(key, value);
        action = "server Store updated, key : " + key + " :: value : " +value;

        return action;        
    }

    public String dataGET(String key) {
        String action = "done";

        action = "value on given key : " + key + " , is " + serverStore.get(key);
        return action;           
    }

    public String dataDELETE(String key) {
        String action = "done";

        action = "key:value pair deleted :: " + key + " : " + serverStore.get(key);
        serverStore.remove(key);

        return action;             
    }

    public String catchServerRequest(String received)
    {
        String reply = "Unrecognized error occured on client request : " + received;
        String[] s = received.split(",");
        if(s.length > 1)
        {
            if(s[s.length - 1] == "PUT")
                reply = dataPUT(s[0], s[1]);
            else if(s[s.length - 1] == "GET")
                reply = dataGET(s[0]);
            else if(s[s.length - 1] == "DELETE")
                reply = dataDELETE(s[0]);
            else if(s.length > 2)
            {
                serverLog.callLogger("client request incorrect : " +s[s.length-1] + " , complete request : " +received);
                reply = "client request incorrect : " +s[s.length-1] + " , complete request : " +received;
            }
        }
        else
        {
            serverLog.callLogger("client request received not decipherable! received :" + received);
            reply = "client request received not decipherable! received :" + received;
        }

        return reply;
    }
}

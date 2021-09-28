package client;

import java.util.HashMap;
import java.io.*;

public abstract class clientAbstract {
    public boolean running;
    public boolean runnable;
    public clientLogger clientLog;
    public int port;
    public String hostname;
    public HashMap<String, String> initClientStore;

    public clientAbstract(String port_, String host_)
    {
        runnable = true;
        initClientStore = new HashMap<String,String>();
        initializeStore();
        clientLog = new clientLogger();
        clientLog.setLogger("clientLog.log", true);
        try {
            port =  Integer.parseInt(port_);
            clientLog.callLogger("looking for tcp server on port -" +port);
            hostname = host_;
 
        } catch (NumberFormatException e) {
            clientLog.callLogger("invalid port info given");
            runnable = false;
          return;
        }


    }

    public void initializeStore(){
        initClientStore.put("1", "Uttkarsh Narayan");
        initClientStore.put("2", "Rohan Gal");
        initClientStore.put("3", "Drek Rin");
        initClientStore.put("4", "Riku Kin");
        initClientStore.put("5", "Thomas Bane");
    }

    public String serverDataPUT(String key, String val)
    {
        return key + "," + val +","+"PUT"; 
    }
    public String serverDataGET(String key)
    {
        return key + ","+"GET"; 
    }
    public String serverDataDELETE(String key)
    {
        return key + ","+"DELETE"; 
    }

    public static String getPort()
    {
        Console console = System.console();
        String port_ = console.readLine("Enter a port : ");
        return port_;
    }

    public static String getHost()
    {
        Console console = System.console();
        String host = console.readLine("Enter a hostname : ");
        return host;
    }

    public abstract void run();
}

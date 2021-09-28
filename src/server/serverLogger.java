package server;

import java.util.logging.*;
import java.io.*;

public class serverLogger {
    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    FileHandler f;
    public void setLogger(String name, boolean append)
    {
        
        LogManager.getLogManager().reset();
        log.setLevel(Level.ALL);
        try
        {
            f = new FileHandler(name, append);
            SimpleFormatter format = new SimpleFormatter();
            f.setFormatter(format);
            //f.setLevel(Level.FINE);    
            log.addHandler(f);
        }catch(IOException e_)
        {
            //ignore this
        }
    }
    
    public void callLogger(String e)
    {
        System.out.println("logging :" +e);
        log.log(Level.FINE, e);

    }  
}

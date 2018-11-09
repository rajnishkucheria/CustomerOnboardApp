package zytest.custonboardapp.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadTestData {
    Properties p = new Properties();
    public Properties getObjectRepository() throws IOException{
        //Read object repository file
        InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/java/zytest/custonboardapp/objects/TestData.properties"));
        //load all objects
        p.load(stream);
         return p;
    }
    
    public Properties getAPIRepository() throws IOException{
        //Read object repository file
        InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/java/zytest/custonboardapp/objects/MockAPI.properties"));
        //load all objects
        p.load(stream);
         return p;
    }
    
}

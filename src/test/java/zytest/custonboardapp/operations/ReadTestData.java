package zytest.custonboardapp.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadTestData {
    Properties p = new Properties();
    public Properties getObjectRepository() throws IOException{
    	System.out.println(System.getProperty("user.dir"));
        //Read object repository file
        InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/java/zytest/custonboardapp/objects/TestData.properties"));
        //load all objects
        p.load(stream);
         return p;
    }
    
}

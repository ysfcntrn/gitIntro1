package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties configFile;

    static {
        String path = "configuration.properties";
        try {
        FileInputStream inputStream = new FileInputStream(path);

        configFile = new Properties();

        configFile.load(inputStream);

        inputStream.close();

        }catch (Exception e){

            e.printStackTrace();
        }
    }
 public static String getProperty(String keyValue){

        return configFile.getProperty(keyValue);
     }



}

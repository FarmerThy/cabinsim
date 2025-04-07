package org.cloudbus.cloudsim.cabinsim;

import org.cloudbus.cloudsim.Log;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonReader {
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
        } catch (IOException e) {
            Log.printlnConcat("Problem in reading json file. Error: ", e.getMessage());
        }
        return jsonStr;
    }
}

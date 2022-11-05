package com.envision.automation.framework.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtils {
    public JSONObject mainJsonObj;
public  JSONObject getMainJsonObj(){
    return  mainJsonObj;
}


    public void loadTestDataFile(String filename) throws IOException, ParseException {
        File jsonFile= new File(System.getProperty("user.dir")+ "/src/test/resources/testData/"+ filename);

        JSONParser parser=new JSONParser();
        Object jsonFileObject=parser.parse(new FileReader(jsonFile));
      this.mainJsonObj=(JSONObject) jsonFileObject;

    }

    public JSONObject getJsonObject(JSONObject jsonObject,String jsonObjectName){
return (JSONObject) jsonObject.get(jsonObjectName);
    }

    public  String getJsonObjectValue(JSONObject jsonObject,String key){
    return jsonObject.get(key).toString();
    }
    public static void main(String[] args) throws IOException, ParseException {
       /* File jsonFile= new File(System.getProperty("user.dir")+ "/src/test/resources/testData/testData.json"
        );
        JSONParser parser=new JSONParser();
        Object jsonFileObject=parser.parse(new FileReader(jsonFile));

        System.out.println(jsonFileObject.toString());

        JSONObject convertedJsonObj=(JSONObject)jsonFileObject;
         JSONObject loginData=(JSONObject) convertedJsonObj.get("loginData");
        System.out.println(loginData);

        String username=loginData.get("username").toString();
        String password=loginData.get("password").toString();
        System.out.println(username+ " " + password);*/

        JsonUtils jsonUtils=new JsonUtils();
        jsonUtils.loadTestDataFile("testData.json");
        JSONObject loginData=jsonUtils.getJsonObject(jsonUtils.mainJsonObj,"loginData");
       String username=jsonUtils.getJsonObjectValue(loginData,"username");
        String password=jsonUtils.getJsonObjectValue(loginData,"password");
        System.out.println(username+"#"+password);
}
}

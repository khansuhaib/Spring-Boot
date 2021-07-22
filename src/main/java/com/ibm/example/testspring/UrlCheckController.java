package com.ibm.example.testspring;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UrlCheckController {
    
    private final String SITE_IS_UP = "Site is up";
    private final String SITE_IS_DOWN = "Site is down";
    private final String INCORRERCT_URL = "URL in correct!";

    @RequestMapping("/")
    public String index(){
        return "My first app";
    }

    @GetMapping("/check")
    public String getUrlStatusMessage(@RequestParam String url){
        String returnMessage = "";
         
           // int responcecode=0;
            try {
                URL urlObj = new URL(url);
                HttpURLConnection conn;
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int responcecode = conn.getResponseCode() / 100;
               // returnMessage = "Test = " + String.valueOf(responcecode) + " = " + (responcecode !=2 && responcecode !=3) + " == ";
                if (responcecode !=2 && responcecode !=3)
                    returnMessage =  SITE_IS_DOWN;
                else
                    returnMessage = SITE_IS_UP;

            } catch (MalformedURLException e){
                returnMessage = INCORRERCT_URL;
            } catch (IOException e) {
                returnMessage  = SITE_IS_DOWN;
            } 
       
        return returnMessage;
    }





    @RequestMapping("/arraylist")
    public String myArrayDisplay(){

        List<String> langs = new ArrayList<>();

        langs.add("Java");
        langs.add("Python");
        langs.add(1, "C#");
        langs.add(0, "Ruby");
        langs.add(0, "C");

        StringBuilder sb = new StringBuilder();
        for (String s : langs)
        {
            sb.append(s);
            sb.append("\t");
        }

        return sb.toString();
    }
}

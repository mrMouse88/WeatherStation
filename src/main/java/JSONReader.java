import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class JSONReader {
    public static String URL_CURRENT_WEATHER = "http://api.openweathermap.org/data/2.5/weather?q=Wroclaw,pl&units=metric&lang=pl&APPID=c9760569a5921f68f540acd64a575ea3";

    public static JSONObject readJsonFromUrl(String url) {
        InputStream inputStream;
        JSONParser parser = new JSONParser();
        JSONObject json = null;

        try {
            inputStream = new URL(url).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            json = (JSONObject) parser.parse(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }


}
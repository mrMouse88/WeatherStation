import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ParseData {
    JSONObject object;
    JSONObject weather;
    JSONObject main;
    JSONObject wind;

    public ParseData(JSONObject object){
        this.object = object;
        JSONArray temp = (JSONArray) object.get("weather");
        this.weather = (JSONObject) temp.get(0);
        this.main = (JSONObject) object.get("main");
        this.wind = (JSONObject) object.get("wind");
    }

    public String getTemperature(){
        return main.get("temp").toString();
    }

    public String getPressure(){
        return main.get("pressure").toString();
    }

    public String getHumidity(){
        return main.get("humidity").toString();
    }

    public String getDescription(){
        return weather.get("description").toString();
    }

    public String getWindSpeed(){
        return wind.get("speed").toString();
    }

    public String getCity(){
        return object.get("name").toString();
    }

    public String getImageUrl(){
        return "http://openweathermap.org/img/w/"+weather.get("icon").toString()+".png";
    }
}

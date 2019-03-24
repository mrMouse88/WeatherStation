import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeatherStation extends JFrame {
    private JPanel panel;
    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel iconPanel;
    private JLabel clockData;
    private JLabel tempLabel;
    private JLabel humidityLabel;
    private JLabel pressureLabel;
    private JLabel tempData;
    private JLabel pressureData;
    private JLabel humidityData;
    private JLabel windLabel;
    private JLabel windData;
    private JLabel cityData;
    private JLabel descData;
    private JLabel imageData;

    public WeatherStation() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setWeatherData();
        setDescription();
        setClock();

        Timer timerClock = new Timer(1000, e -> setClock());
        timerClock.start();

        Timer timerWeather = new Timer(30000, e -> setWeatherData());
        timerWeather.start();

        setSize(320,200);
        add(panel);
//        pack();
        setVisible(true);
    }

    private void setWeatherData() {
        ParseData parseData = new ParseData(JSONReader.readJsonFromUrl(JSONReader.URL_CURRENT_WEATHER));
        tempData.setText(parseData.getTemperature() + " °C");
        humidityData.setText(parseData.getHumidity() + " %");
        pressureData.setText(parseData.getPressure() + " hPa");
        windData.setText(parseData.getWindSpeed() + " m/sec");
        descData.setText(parseData.getDescription());

        //draw image
        ImageIcon image = new ImageIcon(getImage(parseData.getImageUrl()));

        imageData.setIcon(image);
    }

    private void setDescription() {
        ParseData parseData = new ParseData(JSONReader.readJsonFromUrl(JSONReader.URL_CURRENT_WEATHER));
        cityData.setText(parseData.getCity());
    }

    private void setClock() {
        GregorianCalendar gc = new GregorianCalendar();
        StringBuilder time = new StringBuilder();
        time.append(addZero(gc.get(Calendar.HOUR_OF_DAY)));
        time.append(":");
        time.append(addZero(gc.get(Calendar.MINUTE)));
        time.append(":");
        time.append(addZero(gc.get(Calendar.SECOND)));
        clockData.setText(time.toString());
    }

    private Image getImage(String url){
        Image image = null;
        try {
            URL imageUrl = new URL(url);
            image = ImageIO.read(imageUrl);
            image = image.getScaledInstance(75,75, Image.SCALE_SMOOTH);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private String addZero(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return "" + time;
    }

}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeatherStationTab extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JPanel currentPanel;
    private JPanel forecastPanel;
    private JPanel bottomPanel;
    private JLabel clock;
    private JLabel city;
    private JPanel currentPanelLeft;
    private JPanel currentPanelRight;
    private JPanel currentPanelIcon;
    private JLabel icon;
    private JLabel tempLabel;
    private JLabel pressureLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;
    private JLabel tempData;
    private JLabel pressureData;
    private JLabel humidityData;
    private JLabel windSpeedData;
    private JLabel descriptionData;

    public WeatherStationTab() {
        super("Weather Station");
        add(rootPanel);

        //bottom panel - clock and city
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(clock);
        bottomPanel.add(city);
        bottomPanel.setSize(500, 20);

        //panel with current weather
        setCurrentWeather();
        Timer currentWeatherTimer = new Timer(30000, e -> setCurrentWeather());
        currentWeatherTimer.start();

        //panel with forecast weather

        //clock
        Timer clockTimer = new Timer(1000, e -> clock.setText(setClock()));
        clockTimer.start();

        //settings
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }


    //set current weather data
    private void setCurrentWeather() {
        ParseData parseData = new ParseData(JSONReader.readJsonFromUrl(JSONReader.URL_CURRENT_WEATHER));
        tempData.setText(parseData.getTemperature() + " °C");
        humidityData.setText(parseData.getHumidity() + " %");
        windSpeedData.setText(parseData.getWindSpeed() + " hPa");
        pressureData.setText(parseData.getPressure() + " m/sec");
        descriptionData.setText(parseData.getDescription());
        city.setText(parseData.getCity());

        //draw image
        ImageIcon image = new ImageIcon(getImage(parseData.getImageUrl()));
        icon.setIcon(image);
    }

    //returning string with current time
    private String setClock() {
        GregorianCalendar gc = new GregorianCalendar();
        StringBuilder time = new StringBuilder();
        time.append(addZero(gc.get(Calendar.HOUR_OF_DAY)));
        time.append(":");
        time.append(addZero(gc.get(Calendar.MINUTE)));
        time.append(":");
        time.append(addZero(gc.get(Calendar.SECOND)));
        return time.toString();
    }

    //helper function to add 0 to time if less then 10
    private String addZero(int t) {
        if (t < 10) {
            return "0" + t;
        } else {
            return "" + t;
        }
    }

    //helper function to get image
    private Image getImage(String url) {
        Image image = null;
        try {
            URL imageUrl = new URL(url);
            image = ImageIO.read(imageUrl);
            image = image.getScaledInstance(75, 75, Image.SCALE_SMOOTH);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void main(String[] args) {
        new WeatherStationTab();
    }
}

//YichunZhang k19012458 & YuanfeiQiu k19030615 
/**
 * Class Weather - Weather can change, and it influences the behaviour 
 * of some simulated aspects.
 * This class keeps track of the change of weather: sunny, rainy, foggy.
 * And provide an interface "actByWeather" for classes who implement it achieving 
 * different act in sunny, rainy and foggy.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public class Weather
{
    // A string array contains weather options: sunny, rainy and foggy.
    private String[] weather_container = {"sunny", "rainy", "foggy"};
    // Lasting days of the weather. When interval become 0, 
    // change to another random weather and random interval. 
    private int weatherInterval;
    // An int variable represents for current weather.
    // 0 indicates sunny, 1 indicates rainy, 2 indicates foggy.
    private int curWeather; 
    
    /**
     * Create weather with a weather type and its lasting days.
     * @param curWeather 0 -> sunny, 1 -> rainy, 2 -> foggy.
     * @param weatherInterval Lasting days of the weather.
     */
    public Weather(int curWeather, int weatherInterval)
    {
        this.curWeather = curWeather;
        this.weatherInterval = weatherInterval;
    }

    /**
     * Set weather.
     * @param curWeather 0 -> sunny, 1 -> rainy, 2 -> foggy.
     */
    public void setWeather(int curWeather)
    {
        this.curWeather = curWeather;
    }

    /**
     * Return current weather.
     */
    public String getWeather()
    {
        return weather_container[curWeather];
    }

    /**
     * Set lasting days of the weather.
     */
    public void setWeatherInterval(int weatherInterval)
    {
        this.weatherInterval = weatherInterval;
    }

    /**
     * Return lasting days of the weather.
     */
    public int getWeatherInterval()
    {
        return weatherInterval;
    }

    /**
     * Different behave in different weather.
     * @param actByWeather Interface type.
     */
    public void weatherAct(ActByWeather actByWeather)
    {
        if(curWeather == 0) // sunny
            actByWeather.sunny_act();
        else if(curWeather == 1) // rainy
            actByWeather.rainy_act();
        else if(curWeather == 2) // foggy
            actByWeather.foggy_act();
    }
}
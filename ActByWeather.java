//YichunZhang k19012458 & YuanfeiQiu k19030615 
/**
 * Interface ActByWeather.
 * Speices which implement this interface can act differently according to weather.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public interface ActByWeather
{
    /**
     * How the speices act in sunny days.
     */
    void sunny_act();

    /**
     * How the speices act in rainy days.
     */
    void rainy_act();

    /**
     * How the speices act in foggy days.
     */
    void foggy_act();
}
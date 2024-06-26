//YichunZhang k19012458 & YuanfeiQiu k19030615 
/**
 * Interface ActByTime.
 * Speices which implement this interface can act differently according to time.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public interface ActByTime
{
    /**
     * How the speices act in the days.
     */
    void day_act();
    
    /**
     * How the speices act at night.
     */
    void night_act();
}
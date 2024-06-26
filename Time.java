//YichunZhang k19012458 & YuanfeiQiu k19030615 
/**
 * Class Time.
 * This class keeps track of the time of day: day and night.
 * And provide an interface "ActByTime" for classes who implement it achieving different act in day and night. 
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public class Time
{
    // A string array contains time types: day and night.
    private String[] timeContainer = {"day", "night"};
    // Whether it's night or not. True if night, false if day.
    private static boolean isNight;

    /**
     * Create time with indicating either day or night.
     * @param curTime True if night, false if day.
     */
    public Time(boolean curTime)
    {
        this.isNight = isNight;
    }

    /**
     * Return whether it's night or not. True if night, false if day.
     */
    public static boolean getIsNight()
    {
        return isNight;
    }

    /**
     * Return a String of current time. "day" or "night".
     */
    public String getTime()
    {
        if(isNight)
            return timeContainer[1];
        else
            return timeContainer[0];
    }

    /**
     * Set the time as day.
     */
    public void setDay()
    {
        isNight = false;
    }

    /**
     * Set the time as night.
     */
    public void setNight()
    {
        isNight = true;
    }

    /**
     * Different behaviour in day or night.
     * @param actByTime Interface type.
     */
    public void timeAct(ActByTime actByTime)
    {
        if(isNight)
            actByTime.night_act();
        else
            actByTime.day_act();
    }
}
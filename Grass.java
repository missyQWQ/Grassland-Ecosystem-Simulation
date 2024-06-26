//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
/**
 * Class Grass - inherits from Plants and implements ActByWeather.
 * Grass is able to: 
 *  1) Grow. Grows at a specific growth rate and has a maximum growth number.
 *  2) Act differently according to the weather. It can't grow without rain.
 *  3) Die. Grass will die when it's eaten (by sheep).
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public class Grass extends Plants implements ActByWeather 
{
    // The likelihood of a grass growing.
    private static final double GROW_RATE = 0.07;
    // The maximum number of new grasses grow.
    private static final int MAX_GROW_SIZE = 1;

    /**
     * Create a new grass. 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * Return the likelihood of a grass growing.
     */
    @Override
    protected double getGrowRate() 
    {
        return GROW_RATE;
    }

    /**
     * Return the maximum number of new grasses grow.
     */
    @Override
    protected int getGrowSize()
    {
        return MAX_GROW_SIZE;
    }

    /**
     * Return a newly grown grass.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    protected Species generateChild(Field field, Location loc)
    {
        Species young = new Grass(field, loc);
        return young;
    }

    /**
     * Implement interface 'ActByWeather' - grass cannot grow in sunny days.
     */
    @Override
    public void sunny_act()
    {
        setGrowable(false);
    }

    /**
     * Implement interface 'ActByWeather' - grass can grow in rainy days.
     */
    @Override
    public void rainy_act()
    {
        setGrowable(true);
    }

    /**
     * Implement interface 'ActByWeather' - grass cannot grow in foggy days.
     */
    @Override
    public void foggy_act()
    {
        setGrowable(false);
    }
}
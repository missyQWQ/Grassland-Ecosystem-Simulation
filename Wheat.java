//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
/**
 * Class Wheat - inherits from Plants.
 * Wheat is able to: 
 *  1) Grow. Grows at a specific growth rate and has a maximum growth number.
 *     Wheat will not be influenced by weather so it can grow no matter what weather is.
 *  2) Die. Wheat will die when it's eaten (by rats). 
 * 
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public class Wheat extends Plants
{
    // The likelihood of a wheat growing.
    private static final double GROW_RATE = 0.07;
    // The maximum number of new wheats grow.
    private static final int MAX_GROW_SIZE = 1;
    
    /**
     * Create a new wheat. 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wheat(Field field, Location location)
    {
        super(field, location);
    }
    
    /**
     * Return the likelihood of a wheat growing.
     */
    @Override
    protected double getGrowRate()
    {
        return GROW_RATE;
    }
    
    /**
     * Return the maximum number of new wheats grow.
     */
    @Override
    protected int getGrowSize()
    {
        return MAX_GROW_SIZE;
    }
    
    /**
     * Return a newly grown wheat.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    protected Species generateChild(Field field, Location loc)
    {
        Species young = new Wheat(field, loc);
        return young;
    }
}
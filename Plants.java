//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
/**
 * Class Plants - inherits from Species.
 *              - superclass of Grass and Wheat.
 * Plant is able to: 
 *  1) Grow a specific number (may be zero) of new plants if the current plant is alive and growable.
 *  2) Die. Plant will die when it's eaten.
 *  3) Act differently according to weather: 
 *     Grass will be influenced by weather and it can grow only in rainy days. 
 *     Wheat will not be influenced by weather so it can grow no matter what weather is.
 *  
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public abstract class Plants extends Species
{
    // Whether the plant is growable or not.
    private boolean isGrowable;

    /**
     * Create a new plant and set the plant is growable. 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plants(Field field, Location location)
    {
        super(field, location);
        isGrowable = true;
    }

    /**
     * This is what the plant does most of the time - grow new plants if 
     * the current plant is alive and growable.
     * @param newPlants A list to return newly grown plants.
     */
    @Override
    protected void act(List<Species> newPlants)
    {
        if(isAlive() && isGrowable)
            giveBirth(newPlants);
    }

    /**
     * Return the number of growth if it can grow (may be zero).
     */
    @Override
    protected int breed()
    {
        int numOfNewPlants = 0;
        if(rand.nextDouble() <= getGrowRate()) {
            numOfNewPlants = rand.nextInt(getGrowSize()) + 1;
        }
        return numOfNewPlants;
    }

    /**
     * Set the plant is growable or not.
     * @param isGrowable
     */
    protected void setGrowable(boolean isGrowable)
    {
        this.isGrowable = isGrowable;
    }

    /**
     * Return the likelihood of a plant growing.
     */
    protected abstract double getGrowRate();

    /**
     * Return the maximum number of new plants grow.
     */
    protected abstract int getGrowSize();
}
//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
import java.util.Iterator;
/**
 * Class Rat - inherits from Animal and implements ActByTime.
 * Rat (herbivore) is able to: 
 *  1) Age. Increment rat's age after each move (each step). This could result in rat's death when it reaches the age a rat can live.
 *  2) Eat. Rat eats wheats.
 *  3) Move. Rat will move towards a source of food (wheat) if found, and move to a free location if no food found. 
 *     If no food or free location, then die. Move will influenced by time.
 *  4) Breed. Female rat may propagate when meets male rat (in neighbouring cell). 
 *  5) Die. Rat might die of old, overcrowding, hungry, be eaten and diseases.
 *  6) Act differently according to time: 
 *     Rat can move at night, but will sleep in the day and cannot move during that time. 
 *  7) Not influenced by weather, so rat can eat no matter what weather is.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public class Rat extends Animal implements ActByTime
{
    // Characteristics shared by all rats (class variables).

    // The age at which a rat can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a rat can live.
    private static final int MAX_AGE = 30;
    // The likelihood of a rat breeding.
    private static final double BREEDING_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 11;
    // The likelihood of a male rat gets birth, female otherwise.
    private static final double MALE_PROBABILITY = 0.5;
    // The food value of a wheat. In effect, this is the number of steps a rat can go before it has to eat again.
    private static final int WHEAT_FOOD_VALUE = 30;

    /**
     * Create a new rat. A rat may be created with zero or random age, 
     * and with wheat or random food value.
     * @param randAgeFood If true, the rat will have a random age and food level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rat(boolean randAgeFood, Field field, Location location)
    {
        super(field, location, randAgeFood);
    }

    /**
     * Return the age to which a rat can live.
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the food value of a wheat. In effect, this is the number of steps 
     * a rat can go before it has to eat again.
     */
    @Override
    protected int getFoodValue()
    {
        return WHEAT_FOOD_VALUE;
    }
      
    /**
     * Return the age at which a rat can start to breed.
     */
    @Override
    protected int getBreedAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the likelihood of a rat breeding.
     */
    @Override
    protected double getBreedPro()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum number of rat births.
     */
    @Override
    protected int getLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return the likelihood of a male rat gets birth, female otherwise.
     */
    @Override
    protected double getMalePro()
    {
        return MALE_PROBABILITY;
    }
    
    /**
     * Return whether rat eat this food. True if the food is wheat, false otherwise.
     * @param food
     */
    @Override
    protected boolean isFoodSource(Object food)
    {
        if(food instanceof Wheat)
            return true;
        else
            return false;
    }
    
    /**
     * Return a newly born rat.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    protected Species generateChild(Field field, Location loc)
    {
        Species young = new Rat(false, field, loc);
        return young;
    }
    
    /**
     * Implement interface 'ActByTime' - rat will sleep in the day and cannot move 
     * during that time.
     */
    @Override
    public void day_act()
    {
        setMovable(false);
    }
    
    /**
     * Implement interface 'ActByTime' - rat can move at night.
     */
    @Override
    public void night_act()
    {
        setMovable(true);
    }
}
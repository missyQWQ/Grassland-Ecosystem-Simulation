//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
import java.util.Iterator;
/**
 * Class Snake - inherits from Animal and implements ActByTime, ActByWeather.
 * Snake (carnivore ) is able to: 
 *  1) Age. Increment snake's age after each move (each step). This could result in snake's death when it reaches the age a snake can live.
 *  2) Prey. Snake eats rats. Prey will be influenced by weather.
 *  3) Move. Snake will move towards a source of food (rat) if found, and move to a free location if no food found. 
 *     If no food or free location, then die. Move will be influenced by time.
 *  4) Breed. Female snake may propagate when meets male snake (in neighbouring cell). 
 *  5) Die. Snake might die of old, overcrowding, hungry and diseases.
 *  6) Act differently according to time:
 *     Snake can move at night, but will sleep in the day and cannot move during that time. 
 *  7) Act differently according to weather. 
 *     Snake cannot see well in fog so it can't prey in foggy days.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public class Snake extends Animal implements ActByTime, ActByWeather
{
    // Characteristics shared by all snakes (class variables).
    
    // The age at which a snake can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a snake can live.
    private static final int MAX_AGE = 65;
    // The likelihood of a snake breeding.
    private static final double BREEDING_PROBABILITY = 0.43;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 16;
    // The likelihood of a male snake gets birth, female otherwise.
    private static final double MALE_PROBABILITY = 0.5;
    // The food value of a rat. In effect, this is the number of steps a snake can go before it has to eat again.
    private static final int RAT_FOOD_VALUE = 20;
    
    /**
     * Create a new snake. A snake may be created with zero or random age, 
     * and with rat or random food value.
     * @param randAgeFood If true, the snake will have a random age and food level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Snake(boolean randAgeFood, Field field, Location location)
    {
        super(field, location, randAgeFood);
    }

    /**
     * Return the age to which a snake can live.
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the food value of a rat. In effect, this is the number of steps 
     * a snake can go before it has to eat again.
     */
    @Override
    protected int getFoodValue()
    {
        return RAT_FOOD_VALUE;
    }
    
    /**
     * Return the age at which a snake can start to breed.
     */
    @Override
    protected int getBreedAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the likelihood of a snake breeding.
     */
    @Override
    protected double getBreedPro()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum number of snake births.
     */
    @Override
    protected int getLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return the likelihood of a male snake gets birth, female otherwise.
     */
    @Override
    protected double getMalePro()
    {
        return MALE_PROBABILITY;
    }
    
    /**
     * Return whether snake eat this food. True if the food is rat, false otherwise.
     * @param food
     */
    @Override
    protected boolean isFoodSource(Object food)
    {
        if(food instanceof Rat)
            return true;
        else
            return false;
    }
    
    /**
     * Return a newly born snake.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    protected Species generateChild(Field field, Location loc)
    {
        Species young = new Snake(false, field, loc);
        return young;
    }
    
    /**
     * Implement interface 'ActByTime' - snake will sleep in the day and 
     * cannot move during that time.
     */
    @Override
    public void day_act()
    {
        setMovable(false);
    }
    
    /**
     * Implement interface 'ActByTime' - snake can move at night.
     */
    @Override
    public void night_act()
    {
        setMovable(true);
    }
    
    /**
     * Implement interface 'ActByWeather' - snake is able to prey in sunny days.
     */
    @Override
    public void sunny_act()
    {
        setIsAbleToPrey(true);
    }
    
    /**
     * Implement interface 'ActByWeather' - snake is able to prey in rainy days.
     */
    @Override
    public void rainy_act()
    {
        setIsAbleToPrey(true);
    }
    
    /**
     * Implement interface 'ActByWeather' - snake cannot see well in fog so 
     * it can't prey in foggy days.
     */
    @Override
    public void foggy_act()
    {
        setIsAbleToPrey(false);
    }
}
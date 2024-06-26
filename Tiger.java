//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
import java.util.Iterator;
/**
 * Class Tiger - inherits from Animal and implements ActByTime, ActByWeather.
 * Tiger (carnivore ) is able to: 
 *  1) Age. Increment tiger's age after each move (each step). This could result in tiger's death when it reaches the age a tiger can live.
 *  2) Prey. Tiger eats sheep. Prey will be influenced by weather.
 *  3) Move. Tiger will move towards a source of food (sheep) if found, and move to a free location if no food found. 
 *     If no food or free location, then die. Move will be influenced by time.
 *  4) Breed. Female tiger may propagate when meets male tiger (in neighbouring cell). 
 *  5) Die. Tiger might die of old, overcrowding, hungry and diseases.
 *  6) Act differently according to time:
 *     Tiger can move in the day, but will sleep at night and cannot move during that time.
 *  7) Act differently according to weather. 
 *     Tiger cannot see well in fog so it can't prey in foggy days.
 *     Tiger is too lazy to prey in rain so it also can't prey in rainy days.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public class Tiger extends Animal implements ActByTime, ActByWeather
{
    // Characteristics shared by all wolves (class variables).
    
    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 4;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 125;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 18;
    // The likelihood of a male tiger gets birth, female otherwise.
    private static final double MALE_PROBABILITY = 0.5;
    // The food value of sheep. In effect, this is the number of steps a tiger can go before it has to eat again.
    private static final int SHEEP_FOOD_VALUE = 25;
    
    /**
     * Create a new tiger. A tiger may be created with zero or random age, 
     * and with sheep or random food value.
     * @param randAgeFood If true, the tiger will have a random age and food level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tiger(boolean randAgeFood, Field field, Location location)
    {
        super(field, location, randAgeFood);
    }

    /**
     * Return the age to which a tiger can live.
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the food value of sheep. In effect, this is the number of steps a 
     * tiger can go before it has to eat again.
     */
    @Override
    protected int getFoodValue()
    {
        return SHEEP_FOOD_VALUE;
    }
    
    /**
     * Return the age at which a tiger can start to breed.
     */
    @Override
    protected int getBreedAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the likelihood of a tiger breeding.
     */
    @Override
    protected double getBreedPro()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum number of tiger births.
     */
    @Override
    protected int getLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return the likelihood of a male tiger gets birth, female otherwise.
     */
    @Override
    protected double getMalePro()
    {
        return MALE_PROBABILITY;
    }
    
    /**
     * Return whether tiger eat this food. True if the food is sheep, false otherwise.
     * @param food
     */
    @Override
    protected boolean isFoodSource(Object food)
    {
        if(food instanceof Sheep)
            return true;
        else
            return false;
    }
    
    /**
     * Return a newly born tiger.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    protected Species generateChild(Field field, Location loc)
    {
        Species young = new Tiger(false, field, loc);
        return young;
    }
    
    /**
     * Implement interface 'ActByTime' - tiger can move in the day.
     */
    @Override
    public void day_act()
    {
        setMovable(true);
    }
    
    /**
     * Implement interface 'ActByTime' - tiger will sleep at night and 
     * cannot move during that time.
     */
    @Override
    public void night_act()
    {
        setMovable(false);
    }
    
    /**
     * Implement interface 'ActByWeather' - tiger is able to prey in sunny days.
     */
    @Override
    public void sunny_act()
    {
        setIsAbleToPrey(true);
    }
    
    /**
     * Implement interface 'ActByWeather' - tiger is too lazy to prey in rain 
     * so it can't prey in rainy days.
     */
    @Override
    public void rainy_act()
    {
        setIsAbleToPrey(false);
    }
    
    /**
     * Implement interface 'ActByWeather' - tiger cannot see well in fog 
     * so it can't prey in foggy days.
     */
    @Override
    public void foggy_act()
    {
        setIsAbleToPrey(false);
    }
}
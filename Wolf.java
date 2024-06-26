//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
import java.util.Iterator;
/**
 * Class Wolf - inherits from Animal and implements ActByWeather.
 * Wolf (carnivore ) is able to: 
 *  1) Age. Increment wolf's age after each move (each step). This could result in wolf's death when it reaches the age a wolf can live.
 *  2) Prey. Wolf eats sheep. Prey will be influenced by weather.
 *  3) Move. Wolf will move towards a source of food (sheep) if found, and move to a free location if no food found. 
 *     If no food or free location, then die.
 *  4) Breed. Female wolf may propagate when meets male wolf (in neighbouring cell). 
 *  5) Die. Wolf might die of old, overcrowding, hungry and diseases.
 *  6) Will not influenced by time.
 *  7) Act differently according to weather. 
 *     Wolf cannot see well in fog so it can't prey in foggy days.
 * 
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public class Wolf extends Animal implements ActByWeather
{
    // Characteristics shared by all wolves (class variables).
    
    // The age at which a wolf can start to breed.
    private static final int BREEDING_AGE = 7;
    // The age to which a wolf can live.
    private static final int MAX_AGE = 90;
    // The likelihood of a wolf breeding.
    private static final double BREEDING_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 9;
    // The likelihood of a male wolf gets birth, female otherwise.
    private static final double MALE_PROBABILITY = 0.5;
    // The food value of sheep. In effect, this is the number of steps a wolf 
    // can go before it has to eat again.
    private static final int SHEEP_FOOD_VALUE = 18;
    
    /**
     * Create a new wolf. A wolf may be created with zero or random age, 
     * and with sheep or random food value.
     * @param randAgeFood If true, the wolf will have a random age and food level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randAgeFood, Field field, Location location)
    {
        super(field, location, randAgeFood);
    }
    
    /**
     * Return the age to which a wolf can live.
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the food value of sheep. In effect, this is the number of steps 
     * a wolf can go before it has to eat again.
     */
    @Override
    protected int getFoodValue()
    {
        return SHEEP_FOOD_VALUE;
    }
    
    /**
     * Return the age at which a wolf can start to breed.
     */
    @Override
    protected int getBreedAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the likelihood of a wolf breeding.
     */
    @Override
    protected double getBreedPro()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum number of wolf births.
     */
    @Override
    protected int getLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return the likelihood of a male wolf gets birth, female otherwise.
     */
    @Override
    protected double getMalePro()
    {
        return MALE_PROBABILITY;
    }
    
    /**
     * Return whether wolf eat this food. True if the food is sheep, false otherwise.
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
     * Return a newly born wolf.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    protected Species generateChild(Field field, Location loc)
    {
        Species young = new Wolf(false, field, loc);
        return young;
    }
    
    /**
     * Implement interface 'ActByWeather' - wolf is able to prey in sunny days.
     */
    @Override
    public void sunny_act()
    {
        setIsAbleToPrey(true);
    }
    
    /**
     * Implement interface 'ActByWeather' - wolf is able to prey in rainy days.
     */
    @Override
    public void rainy_act()
    {
        setIsAbleToPrey(true);
    }
    
    /**
     * Implement interface 'ActByWeather' - wolf cannot see well in fog 
     * so it can't prey in foggy days.
     */
    @Override
    public void foggy_act()
    {
        setIsAbleToPrey(false);
    }
}
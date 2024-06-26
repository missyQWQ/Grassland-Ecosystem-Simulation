//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
import java.util.Iterator;
/**
 * Class Sheep - inherits from Animal and implements ActByTime.
 * Sheep (herbivore) is able to: 
 *  1) Age. Increment sheep's age after each move (each step). This could result in sheep's death when it reaches the age a sheep can live.
 *  2) Eat. Sheep eats grasses.
 *  3) Move. Sheep will move towards a source of food (grass) if found, and move to a free location if no food found. 
 *     If no food or free location, then die. Move will influenced by time.
 *  4) Breed. Female sheep may propagate when meets male sheep (in neighbouring cell). 
 *  5) Die. Sheep might die of old, overcrowding, hungry, be eaten and diseases.
 *  6) Act differently according to time: 
 *     Sheep can move in the day, but will sleep at night and cannot move during that time. 
 *  7) Not influenced by weather, so sheep can eat no matter what weather is.
 * 
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/21
 */
public class Sheep extends Animal implements ActByTime
{
    // Characteristics shared by all sheep (class variables).

    // The age at which a sheep can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a sheep can live.
    private static final int MAX_AGE = 89;
    // The likelihood of a sheep breeding.
    private static final double BREEDING_PROBABILITY = 0.46;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 23;
    // The likelihood of a male sheep gets birth, female otherwise.
    private static final double MALE_PROBABILITY = 0.5;
    // The food value of a grass. In effect, this is the number of steps sheep 
    // can go before it has to eat again.
    private static final int GRASS_FOOD_VALUE = 35;

    /**
     * Create a new sheep. A sheep may be created with zero or random age, 
     * and with grass or random food value.
     * @param randAgeFood If true, the sheep will have a random age and food level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Sheep(boolean randAgeFood, Field field, Location location)
    {
        super(field, location, randAgeFood);
    }

    /**
     * Return the age to which a sheep can live.
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return the food value of a grass. In effect, this is the number of steps 
     * sheep can go before it has to eat again.
     */
    @Override
    protected int getFoodValue()
    {
        return GRASS_FOOD_VALUE;
    }

    /**
     * Return the age at which a sheep can start to breed.
     */
    @Override
    protected int getBreedAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Return the likelihood of a sheep breeding.
     */
    @Override
    protected double getBreedPro()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximum number of sheep births.
     */
    @Override
    protected int getLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * Return the likelihood of a male sheep gets birth, female otherwise.
     */
    @Override
    protected double getMalePro()
    {
        return MALE_PROBABILITY;
    }
    
    /**
     * Return whether sheep eat this food. True if the food is grass, false otherwise.
     * @param food
     */
    @Override
    protected boolean isFoodSource(Object food)
    {
        if(food instanceof Grass)
            return true;
        else
            return false;
    }

    /**
     * Return a newly born sheep.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    protected Species generateChild(Field field, Location loc)
    {
        Species young = new Sheep(false, field, loc);
        return young;
    }

    /**
     * Implement interface 'ActByTime' - sheep can move in the day.
     */
    @Override
    public void day_act()
    {
        setMovable(true);
    }

    /**
     * Implement interface 'ActByTime' - sheep will sleep at night and cannot 
     * move during that time.
     */
    @Override
    public void night_act()
    {
        setMovable(false);
    }
}
//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
import java.util.Random;
/**
 * Class Species - superclass of Animal and Plants
 * Species has:
 *  1) status either alive or dead.
 *  2) each at a unique location in field.
 *  3) generate new species and make them into free adjacent locations.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public abstract class Species
{
    // Whether the species is alive or not.
    private boolean alive;
    // The species's field.
    private Field field;
    // The species's position in the field.
    private Location location;
    // A random number generator to control breeding and gender.
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Create a new species at location in field. 
     * Initialize the animal is alive.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Species(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    /**
     * Check whether the species is alive or not.
     * @return true if the species is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the species is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the species's location.
     * @return The species's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the species at the new location in the given field.
     * @param newLocation The species's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the species's field.
     * @return The species's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Check whether or not this species is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSpecies A list to return newly born species.
     */
    protected void giveBirth(List<Species> newSpecies)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Species young = generateChild(field, loc);
            newSpecies.add(young);
        }
    }

    // <------ Abstract Methods ------>
    
    /**
     * Make species act - do a list of what they want.
     * @param newSpecies A list to receive newly born species.
     */
    protected abstract void act(List<Species> newSpecies);

    /**
     * Return a newly born species.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    protected abstract Species generateChild(Field field, Location loc);

    /**
     * Return the number of births (may be zero).
     */
    protected abstract int breed();
}
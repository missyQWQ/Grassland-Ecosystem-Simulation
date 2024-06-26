//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.List;
import java.util.Iterator;
/**
 * Class Animal - inherits from Species
 *              - superclass of Tiger, Wolf, Snake, Sheep and Rat.
 * Animal:
 *  1) Predators (including Tiger, Wolf, Snake) can prey (eat another species). Prey will be influenced by weather.
 *     Tiger and Wolf compete for the same food source: Sheep.
 *     Snake eats Rat.
 *  2) Herbivore (including Sheep, Rat) can eat (plants). Eat will not be influenced by weather.
 *     Sheep eats Grass.
 *     Rat eats Wheat.
 *  3) All the animal distinguish male and female individuals. Animal can only propagate when a male and female individual meet.
 *     Femal animal may breed when meets male animal with the same species (in neighbouring cell). 
 *  4) Act differently according to time:
 *     Tiger and Sheep move in the day, but don't move at night.
 *     Snake and Rat don't move in the day, but move at night.
 *     Wolf, Grass and Wheat don't infulenced by time.
 *  5) Act differently according to weather. 
 *     Tiger can't prey in both foggy days and rainy days.
 *     Wolf and Snake can't prey only in foggy days.
 *     Grass grows only in rainy days.
 *     Sheep, Rat and Wheat don't influenced by weather.
 *  6) Animal will get diseases, and might die of old, overcrowding, hungry and diseases.
 *     There are 2 diseases: Parasitic Disease and Brucellosis.
 *     Parasitic Disease: Infection occurs when two animals meet. Spread through animal-animal contact.
 *     Brucellosis: Affect only particular species (Sheep, Tiger, Wolf).
 *                  At first it only spread in sheep through sheep-sheep contact.
 *                  Then tiger and wolf will contract it from sheep if they consume sheep.
 *                  Finally it spread through tiger-tiger contact, wolf-wolf contact, sheep-sheep contact.
 * 
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public abstract class Animal extends Species
{
    // Characteristics shared by all animals (class variables).

    // The probability that animal will infected by parasitic disease.
    private static final double PARASITIC_DISEASE_INFECTION_PROBABILITY = 0.2;
    // The probability that animal died of parasitic disease.
    private static final double PARASITIC_DISEASE_DEATH_PROBABILITY = 0.03;
    // The probability that animal will infected by brucellosis.
    private static final double BRUCELLOSIS_INFECTION_PROBABILITY = 0.3;
    // The probability that animal died of brucellosis.
    private static final double BRUCELLOSIS_DEATH_PROBABILITY = 0.05;
    // The number of infection by parasitic disease.
    private static int paraInfecNum = 0;
    // The number of death of parasitic disease.
    private static int paraDeathNum = 0;
    // The number of cures of parasitic disease.
    private static int paraCureNum = 0;
    // The number of infection by brucellosis.
    private static int brucInfecNum = 0;
    // The number of death of brucellosis.
    private static int brucDeathNum = 0;
    // The number of cures of brucellosis.
    private static int brucCureNum = 0;

    // Individual characteristics (instance fields).

    // The animal's age.
    private int age;
    // The animal's gender. True if it's male, false if female.
    private boolean isMale;
    // The animal's food level, which is increased by eating its spefic food.
    private int foodLevel;
    // Whether the animal can move or not.
    private boolean isMovable;
    // Whether the animal (if it's predator) can prey or not.
    private boolean isAbleToPrey;
    // Disease that the animal get.
    private Disease disease;

    /**
     * Create a new animal at location in field. 
     * Initialize the animal can move, can prey (if it's predator), 
     * is healthy (has no disease), and set the animal's gender.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param randAgeFood If true, the animal will have a random age and food level.
     */
    public Animal(Field field, Location location, boolean randAgeFood)
    {
        super(field, location);
        isMovable = true;
        isAbleToPrey = true;
        setGender();
        initialAnimal(randAgeFood);
        disease = new Disease(-1, -1);
    }

    /**
     * Make this animal act: it age, increment hunger, spread disease if it's infected.
     * Breed, eat and move if it's alive and movable.
     * In the process, it might die of old age, hunger, diseases.
     * @param newAnimals A list to receive newly born animals.
     */
    @Override
    protected void act(List<Species> newAnimals)
    {
        incrementAge();
        incrementHunger();
        spreadDisease();
        if(isAlive() && isMovable) {
            giveBirth(newAnimals);
            eatAndMove();
        }
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     * Animal can only propagate when a male and female individual meet.
     * @return The number of births (may be zero).
     */
    @Override
    protected int breed()
    {
        int births = 0;
        if(!isMale && age >= getBreedAge() && rand.nextDouble() <= getBreedPro() 
            && meetOppSex()) {
            births = rand.nextInt(getLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Set whether the animal is able to move.
     * @para isMovable True if it is, false otherwise.
     */
    protected void setMovable(boolean isMovable)
    {
        this.isMovable = isMovable;
    }

    /**
     * Set whether the animal is able to prey.
     * @para isAbleToPrey True if it is, false otherwise.
     */
    protected void setIsAbleToPrey(boolean isAbleToPrey)
    {
        this.isAbleToPrey = isAbleToPrey;
    }

    /**
     * Set disease of the animal.
     * @para diseaseType Type of disease. 0 for Parasitic Disease, 
     * 1 for Brucellosis, and -1 if it's healthy.
     * @para latentPeriod Disease's latent Period. During this period, animal 
     * can infect others; At the end of this period, animal will either die or cure.
     */
    protected void setDisease(int diseaseType, int latentPeriod)
    {
        disease.setDisease(diseaseType, latentPeriod);
    }

    // <------ Private Methods ------>

    /**
     * Initialize animal's age and food level.
     * If random age and food level, set the animal with age less than its max age 
     * and food value less than its food can provide.
     * If not, set the new born animal with age zero and full food value.
     * @param randAgeFood If true, the animal will have a random age and food level.
     */
    private void initialAnimal(boolean randAgeFood)
    {
        if(randAgeFood) {
            age = rand.nextInt(getMaxAge());
            foodLevel = rand.nextInt(getFoodValue());
        }
        else {
            age = 0;
            foodLevel = getFoodValue();
        }
    }

    /**
     * Increase the age. This could result in the animal's death and update death data.
     */
    private void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            updateDeathData();
            setDead();
        }
    }

    /**
     * Make the animal more hungry. This could result in the animal's death and update death data.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            updateDeathData();
            setDead();
        }
    }

    /**
     *  There are 2 diseases: Parasitic Disease and Brucellosis.
     *  Parasitic Disease: 
     *   Infection occurs when two animals meet. 
     *   Spread through animal-animal contact.
     *  Brucellosis: 
     *   Affect only particular species (Sheep, Tiger, Wolf).
     *   At first it only spreads in sheep through sheep-sheep contact.
     *   Then tiger and wolf will contract it from sheep if they consume the sick sheep. -> (Achieve in findFood method)
     *   Finally it spread through tiger-tiger contact, wolf-wolf contact, sheep-sheep contact.
     */
    private void spreadDisease()
    {
        // Check if the current animal is alive and has got disease.
        // "-1" represent healthy, "!= -1" means it's unhealthy now.
        if(isAlive() && !isHealthy()) {
            List<Location> adjacent = getField().adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location where = it.next();
                Object adjacentAnimal = getField().getObjectAt(where);
                if(adjacentAnimal != null) {
                    // Check if the current animal has parasitic disease 
                    // and the neighbouring cell is animal. 
                    if(isPara() && adjacentAnimal instanceof Animal) {
                        Animal animal = (Animal) adjacentAnimal;
                        // Ensure the parasitic disease infection probability 
                        // and the neighbouring animal is healthy.
                        if(rand.nextDouble() <= PARASITIC_DISEASE_INFECTION_PROBABILITY 
                           && animal.isHealthy()) {
                            // Set the neighbouring animal parasitic disease 
                            // with 1-10 days latent period.
                            animal.setDisease(0, rand.nextInt(10) + 1);     
                            setParaInfecNum(getParaInfecNum() + 1);
                        }
                    }
                    // Check if the current animal has brucellosis and the
                    // neighbouring animal is the same species as current animal.
                    if(isBruc() && this.getClass() == adjacentAnimal.getClass()) {
                        Animal animal = (Animal) adjacentAnimal;
                        // Ensure the brucellosis infection probability and 
                        // the neighbouring animal is healthy.
                        if(rand.nextDouble() <= BRUCELLOSIS_INFECTION_PROBABILITY 
                           && animal.isHealthy()) {
                            // Set the neighbouring animal brucellosis 
                            // with 1-10 days latent period.
                            animal.setDisease(1, rand.nextInt(10) + 1);
                            setBrucInfecNum(getBrucInfecNum() + 1);
                        }
                    }
                }
            }
            // Decrease latent period. Animal will either die or cure 
            // when latent period ends.
            setLatentPeriod(getLatentPeriod()-1);
            if(getLatentPeriod() == 0)
                dieOrCure();
        }
    }

    /**
     * Move towards a source of food if found.
     * No food found - try to move to a free location.
     * If no possible to move, set dead because of overcrowding.
     */
    private void eatAndMove()
    {
        // Move towards a source of food if found.
        Location newLocation = findFood();
        if(newLocation == null) { 
            // No food found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
        }
        // See if it was possible to move.
        if(newLocation != null) {
            setLocation(newLocation);
        }
        else {
            // Overcrowding.
            updateDeathData();
            setDead();
        }    
    }

    /**
     * Look for the animal's adjacent to the current location.
     * Only the first live food is eaten.
     * If the eaten food has brucellosis, then the predator will contract 
     * brucellosis as well if it's healthy.
     * @return Where food was found, or null if it wasn't or unable to prey.
     */
    private Location findFood()
    {
        // return null if the animal is unable to prey.
        if(!getIsAbleToPrey())
            return null;
        // Look for the animal's adjacent to the current location.
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object food = field.getObjectAt(where);
            // Check if the food is the food source of the current animal.
            if(isFoodSource(food)) {
                Species species = (Species) food;
                // Only the first live food is eaten.
                if(species.isAlive()) { 
                    species.setDead();
                    if(species instanceof Animal) {
                        Animal animal = (Animal) species;
                        // Check if the eaten food has brucellosis and the 
                        // current predator is healthy. 
                        // If they are, the predator will also contract brucellosis.
                        if(animal.isBruc() && this.isHealthy()) {
                            setDisease(1, rand.nextInt(10) + 1);
                            setBrucInfecNum(getBrucInfecNum() + 1);
                        }
                        animal.updateDeathData();
                    }
                    foodLevel = getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Return whether the animal is able to prey. True if it is, false otherwise.
     */
    private boolean getIsAbleToPrey()
    {
        return isAbleToPrey;
    }

    /**
     * Return if the animal is healthy.
     */
    private boolean isHealthy()
    {
        return disease.getCurStatus() == -1;
    }

    /**
     * Return if the animal has brucellosis.
     */
    private boolean isBruc()
    {
        return disease.getCurStatus() == 1;
    }

    /**
     * Return if the animal has parasitic disease.
     */
    private boolean isPara()
    {
        return disease.getCurStatus() == 0;
    }

    /**
     * Animal can propagate if a male and female individual of the 
     * same species meet(in a neighbouring cell).
     */
    private boolean meetOppSex()
    {
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object adjacentAnimal = getField().getObjectAt(where);
            // Check if the neighbouring animal exists, and is the same species 
            // as the current animal.
            if(adjacentAnimal != null && 
               adjacentAnimal.getClass() == this.getClass()) {   
                Animal sameKindAnimal = (Animal) adjacentAnimal;
                // Check if the neighbouring animal has different gender with 
                // the current animal.
                if(this.getGender() != sameKindAnimal.getGender())
                    return true;
            }
        }
        return false;
    }

    /**
     * Set the animal's gender. True if it's male, false if female.
     * @param MALE_PROBABILITY
     */
    private void setGender()
    {
        isMale = true;
        if(rand.nextDouble() <= getMalePro())
            isMale = false;
    }

    /**
     * Return the animal's gender. True if it's male, false is female.
     */
    private boolean getGender()
    {
        return isMale;
    }

    /**
     * Set the animal either die or cure under different diseases and 
     * their corresponding death probability.
     */
    private void dieOrCure()
    {
        if(isPara()) {
            if(rand.nextDouble() <= PARASITIC_DISEASE_DEATH_PROBABILITY) {
                setDead();
                dieFromPara();
            }
            else
                cureFromPara();
        }
        if(isBruc()) {
            if(rand.nextDouble() <= BRUCELLOSIS_DEATH_PROBABILITY) {
                setDead();
                dieFromBruc();
            }
            else
                cureFromBruc();
        }
    }

    /**
     * Update death data with different disease.
     */
    private void updateDeathData()
    {
        if(isPara())
            dieFromPara();
        else if(isBruc()) 
            dieFromBruc();
    }

    /**
     * Update death and infection data and reset disease status when 
     * the animal dead of parasitic disease.
     */
    private void dieFromPara()
    {
        paraDeathNum++;
        paraInfecNum--;
        resetDisease();
    }

    /**
     * Update cure and infection data and reset disease status when 
     * the animal cured from parasitic disease.
     */
    private void cureFromPara()
    {
        paraCureNum++;
        paraInfecNum--;
        resetDisease();
    }

    /**
     * Update death and infection data and reset disease status when 
     * the animal dead of brucellosis.
     */
    private void dieFromBruc()
    {
        brucDeathNum++;
        brucInfecNum--;
        resetDisease();
    }

    /**
     * Update cure and infection data and reset disease status when 
     * the animal cured from brucellosis.
     */
    private void cureFromBruc()
    {
        brucCureNum++;
        brucInfecNum--;
        resetDisease();
    }

    /**
     * Reset disease status. Set the animal as healthy.
     */
    private void resetDisease()
    {
        disease.reset();
    }

    /**
     * Set latent period of the disease.
     * During this period, animal can infect others; 
     * At the end of this period, animal will either die or cure.
     * @para latentPeriod
     */
    private void setLatentPeriod(int latentPeriod)
    {
        disease.setLatentPeriod(latentPeriod);
    }

    /**
     * Return latent period of the disease.
     * During this period, animal can infect others; 
     * At the end of this period, animal will either die or cure.
     */
    private int getLatentPeriod()
    {
        return disease.getLatentPeriod();
    }

    // <------ Abstract Methods ------>

    protected abstract int getMaxAge();

    protected abstract int getFoodValue();

    protected abstract boolean isFoodSource(Object food);

    protected abstract int getBreedAge();

    protected abstract double getBreedPro();

    protected abstract int getLitterSize();

    protected abstract double getMalePro();

    // <------ Static Methods ------>
    // help Class Simulator to invokes disease data and shows them on screen. 

    /**
     * Return the number of infection by parasitic disease.
     */
    public static int getParaInfecNum()
    {
        return paraInfecNum;
    }

    /**
     * Set the number of infection by parasitic disease.
     * @param num The number of infection by parasitic disease.
     */
    public static void setParaInfecNum(int num)
    {
        paraInfecNum = num;
    }

    /**
     * Return the number of death of parasitic disease. 
     */
    public static int getParaDeathNum()
    {
        return paraDeathNum;
    }

    /**
     * Return the number of cures of parasitic disease. 
     */
    public static int getParaCureNum()
    {
        return paraCureNum;
    }

    /**
     * Return the number of infection by brucellosis.
     */
    public static int getBrucInfecNum()
    {
        return brucInfecNum;
    }

    /**
     * Set the number of infection by brucellosis.
     * @param num The number of infection by brucellosis.
     */
    public static void setBrucInfecNum(int num)
    {
        brucInfecNum = num;
    }

    /**
     * Return the number of death of brucellosis.
     */
    public static int getBrucDeathNum()
    {
        return brucDeathNum;
    }

    /**
     * Return the number of cures of brucellosis.
     */
    public static int getBrucCureNum()
    {
        return brucCureNum;
    }
}
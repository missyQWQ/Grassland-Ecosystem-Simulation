//YichunZhang k19012458 & YuanfeiQiu k19030615 
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
/**
 * A predator-prey simulator, based on a rectangular field
 * Containing kinds of species: animals including sheep, tiger, rat, wolf and snake; plants including wheat and grass.
 * Among these species:
 *  1) Rats eat wheats,
 *     Sheep eat grasses,
 *     Snakes eat rats,
 *     Tigers and wolves eat sheep.
 *  2) Tiger, snake, sheep and rat will be influenced by time. 
 *     Wolf, grass and wheat will not.
 *     Tiger, snake, wolf and grass will be influenced by weather. 
 *     Sheep, Rat and wheat will not.
 *  3) They will get disease: Parasitic Disease or Brucellosis.
 *     Parasitic Disease: 
 *      Infection occurs when two animals meet. 
 *      Spread through animal-animal contact.
 *     Brucellosis: 
 *      Affect only particular species (Sheep, Tiger, Wolf).
 *      At first it only spread in sheep through sheep-sheep contact.
 *      Then tiger and wolf will contract it from sheep if they consume sheep.
 *      Finally it spread through tiger-tiger contact, wolf-wolf contact, sheep-sheep contact.
 * 
 * @author David J. Barnes and Michael Kölling, Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a wolf will be created in any given grid position.
    private static final double WOLF_CREATION_PROBABILITY = 0.02;
    // The probability that a tiger will be created in any given grid position.
    private static final double TIGER_CREATION_PROBABILITY = 0.01;
    // The probability that a snake will be created in any given grid position.
    private static final double SNAKE_CREATION_PROBABILITY = 0.05;
    // The probability that a sheep will be created in any given grid position.
    private static final double SHEEP_CREATION_PROBABILITY = 0.08; 
    // The probability that a rat will be created in any given grid position.
    private static final double RAT_CREATION_PROBABILITY = 0.07;
    // The probability that a grass will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.04;
    // The probability that a wheat will be created in any given grid position.
    private static final double WHEAT_CREATION_PROBABILITY = 0.03;
    // The probability that a parasitic disease will be created in animal.
    private static final double PARASITIC_DISEASE_CREATION_PROBABILITY = 0.06;
    // The probability that a brucellosis will be created in sheep.
    private static final double BRUCELLOSIS_CREATION_PROBABILITY = 0.09;
    // A random number generator to control creation.
    private static final Random rand = Randomizer.getRandom();

    // List of species in the field.
    private List<Species> species;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // Time of day: day or night.
    private Time time;
    // Weather of the day: sunny, rainy or foggy.
    private Weather weather;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        species = new ArrayList<>();
        field = new Field(depth, width);
        // Set time to night as default.
        time = new Time(false);
        // Set random weather with random lasting days (1-15 days) of the weather.
        weather = new Weather(rand.nextInt(3), rand.nextInt(15) + 1);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Tiger.class, new Color(255, 128, 0)); // dark orange
        view.setColor(Wolf.class, Color.BLACK);
        view.setColor(Snake.class, Color.MAGENTA);
        view.setColor(Sheep.class, Color.RED);
        view.setColor(Rat.class, Color.BLUE);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Wheat.class, Color.YELLOW);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each species.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn species.
        List<Species> newSpecies = new ArrayList<>();        
        // Let all species act.
        for(Iterator<Species> it = species.iterator(); it.hasNext(); ) {
            Species cur_species = it.next();
            // Use interface ActByWeather to achieve different behaviour 
            // in different weather for those species who implements it.
            if(cur_species instanceof Grass || cur_species instanceof Tiger || 
            cur_species instanceof Wolf  || cur_species instanceof Snake)
                weather.weatherAct((ActByWeather)cur_species);
            // Use interface ActByTime to achieve different behaviour 
            // in different time for those species who implements it.
            if(cur_species instanceof Tiger || cur_species instanceof Snake ||
            cur_species instanceof Sheep || cur_species instanceof Rat)
                time.timeAct((ActByTime)cur_species);
            // Let the species act.
            cur_species.act(newSpecies);
            // Remove the species if it's not alive any more.
            if(! cur_species.isAlive()) {
                it.remove();
            }
        }

        generateTime(); // Generate time information.
        generateWeather();  // Generate weather information.

        // Add the newly born species to the main lists.
        species.addAll(newSpecies);

        view.showStatus(step, field, time.getTime(), weather.getWeather(), 
                        Animal.getParaInfecNum(), Animal.getParaDeathNum(), 
                        Animal.getParaCureNum(), Animal.getBrucInfecNum(), 
                        Animal.getBrucDeathNum(), Animal.getBrucCureNum());
    }

    /**
     * Generate time of the simulator. Every 10 steps is a day/night.
     */
    private void generateTime()
    {
        if(step % 20 < 10)
            time.setDay();
        else
            time.setNight();
    }

    /**
     * Generate weather of the simulator.
     * Set random weather with random lasting days (1-15 days) of the weather.
     * Change weather when the left days of the current weather become 0.
     */
    private void generateWeather()
    {
        if(weather.getWeatherInterval() <= 0) {
            weather.setWeather(rand.nextInt(3));
            weather.setWeatherInterval(rand.nextInt(15) + 1);
        }
        weather.setWeatherInterval(weather.getWeatherInterval()-1);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        species.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field, time.getTime(), weather.getWeather(),
                        Animal.getParaInfecNum(), Animal.getParaDeathNum(), 
                        Animal.getParaCureNum(), Animal.getBrucInfecNum(), 
                        Animal.getBrucDeathNum(), Animal.getBrucCureNum());
    }

    /**
     * Randomly populate the field with kinds of species.
     * Randomly infect animal with diseases.
     * Tiger, Wolf, Snake, Rat may be infected by parasitic disease.
     * Sheep may be infected by brucellosis.
     */
    private void populate()
    {
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= TIGER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Tiger tiger = new Tiger(true, field, location);
                    species.add(tiger);
                    if(rand.nextDouble() <= PARASITIC_DISEASE_CREATION_PROBABILITY){
                        tiger.setDisease(0, rand.nextInt(10) + 1);
                        Animal.setParaInfecNum(Animal.getParaInfecNum() + 1);
                    }
                }
                else if(rand.nextDouble() <= WOLF_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Wolf wolf = new Wolf(true, field, location);
                    species.add(wolf);
                    if(rand.nextDouble() <= PARASITIC_DISEASE_CREATION_PROBABILITY){
                        wolf.setDisease(0, rand.nextInt(10) + 1);
                        Animal.setParaInfecNum(Animal.getParaInfecNum() + 1);
                    }
                }
                else if(rand.nextDouble() <= SNAKE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Snake snake = new Snake(true, field, location);
                    species.add(snake);
                    if(rand.nextDouble() <= PARASITIC_DISEASE_CREATION_PROBABILITY){
                        snake.setDisease(0, rand.nextInt(10) + 1);
                        Animal.setParaInfecNum(Animal.getParaInfecNum() + 1);
                    }
                }
                else if(rand.nextDouble() <= SHEEP_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sheep sheep = new Sheep(true, field, location);
                    species.add(sheep);
                    if(rand.nextDouble() <= BRUCELLOSIS_CREATION_PROBABILITY) {
                        sheep.setDisease(1, rand.nextInt(10) + 1);
                        Animal.setBrucInfecNum(Animal.getBrucInfecNum() + 1);
                    }
                }
                else if(rand.nextDouble() <= RAT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rat rat = new Rat(true, field, location);
                    species.add(rat);
                    if(rand.nextDouble() <= PARASITIC_DISEASE_CREATION_PROBABILITY){
                        rat.setDisease(0, rand.nextInt(10) + 1);
                        Animal.setParaInfecNum(Animal.getParaInfecNum() + 1);
                    }
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(field, location);
                    species.add(grass);
                }
                else if(rand.nextDouble() <= WHEAT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Wheat wheat = new Wheat(field, location);
                    species.add(wheat);
                }
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
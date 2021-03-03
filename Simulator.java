import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing small fish, sharks, turtles, doplhins, crabs, corpses, est.
 * 
 * @author David J. Barnes and Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Simulator
{
    // Constants representing configuration information for the simulation. (Class variables)
    
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The default amount of steps for a day-night cycle.
    private static final int DEFAULT_DAYNIGHT_CYCLE = 100;
    // The default daily temperature increase
    private static final double DEFAULT_TEMP_INCREASE = 1;
    // The probability that a sark will be created in any given grid position.
    private static final double SHARK_CREATION_PROBABILITY = 0.015;
    // The probability that a turtle will be created in any given grid position.
    private static final double TURTLE_CREATION_PROBABILITY = 0.02;
    // The probability that a dolphin will be created in any given grid position.
    private static final double DOLPHIN_CREATION_PROBABILITY = 0.015;
    // The probability that a small fish will be created in any given grid position.
    private static final double SMALLFISH_CREATION_PROBABILITY = 0.1;
    // The probability that a crab be created in any given grid position.
    private static final double CRAB_CREATION_PROBABILITY = 0.07;
    // The probability that a seagrass be created in any given grid position.
    private static final double SEAGRASS_CREATION_PROBABILITY = 0.03;
    // The probability that a algae be created in any given grid position.
    private static final double ALGAE_CREATION_PROBABILITY = 0.03;

    // Instance fields representing configuration information for the simulation. (Instace fields)
    
    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The amount of day light.
    private double dayLight;
    // Represent the time of day.
    private boolean isDay;
    // The amount of steps of a day-night cycle.
    private int dayNightCycle;
    // Period of the cos wave, describing the day-night cycle and temperature.
    private double period;
    // Mark the beginning of day/night.
    private double timeTracker;
    // The amount of steps need of a day/night to pass.
    private double halfCycle;
    // The time of day.
    private String dayTime;
    // The temperature of the surroudning.
    private double temperature;
    // The amount the average temperature increases per day-night cycle.
    private double dailyTempIncrease;
    // The amount the average temperature increases per each step.
    private double stepTempIncrease;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH, DEFAULT_DAYNIGHT_CYCLE, DEFAULT_TEMP_INCREASE);
    }

    /**
     * Create a simulation field with the given size.
     * 
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     * @param dayNightCycle The amount of steps of a day-night cycle.
     * @param dailyTempIncrease The amount the average temperature increases per day-night cycle.
     */
    public Simulator(int depth, int width, int dayNightCycle, double dailyTempIncrease)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        actors = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Crab.class, Color.YELLOW);
        view.setColor(Shark.class, Color.BLUE);
        view.setColor(Turtle.class, Color.ORANGE);
        view.setColor(SmallFish.class, new Color(102, 255, 255, 255));
        view.setColor(Dolphin.class, new Color(178, 102, 255, 255));
        view.setColor(Seagrass.class, new Color(17, 156, 2, 255));
        view.setColor(Algae.class, new Color(0, 255, 145, 255));
        view.setColor(Corpse.class, new Color(150, 75, 0, 255));
        
        
        // Initialise the fields.
        this.dayNightCycle = dayNightCycle;
        double denominator = dayNightCycle;
        period = 2/denominator;
        isDay = true;
        dayTime = "day";
        timeTracker = denominator/4;
        halfCycle = denominator/2;
        
        this.dailyTempIncrease = dailyTempIncrease;
        stepTempIncrease = dailyTempIncrease/denominator;
        temperature = 0.0;

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
     * Change the time of day.
     */
    private void changeDayTime()
    {
        isDay = !isDay;
        if(isDay){
            dayTime = "day";
        }
        else if(!isDay){
            dayTime = "night";
        }
    }
    
    /**
     * Compute day light and temperature based on the current step.
     */
    private void computeEnvironmentTraits()
    {
        double dayNightWave = Math.cos(period * Math.PI * step);
        dayLight = 0.375 * dayNightWave + 0.625;
        
        double tempIncrease = step * stepTempIncrease;
        temperature = 5 * dayNightWave + 15 + tempIncrease;
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * 
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * actor.
     */
    public void simulateOneStep()
    {
        step++;

        computeEnvironmentTraits();

        if(step == timeTracker){
            changeDayTime();
            timeTracker = timeTracker + halfCycle;
        }

        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<>();        
        // Let all rabbits act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors, isDay, temperature);
            if(!actor.isAlive()) {
                it.remove();
            }
        }

        // Add the newly born foxes and rabbits to the main lists.
        actors.addAll(newActors);

        view.showStatus(step, field, dayLight, dayTime, temperature);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        isDay = true;
        computeEnvironmentTraits();
        double denominator = dayNightCycle;
        timeTracker = denominator/4;
        // Show the starting state in the view.
        view.showStatus(step, field, dayLight, dayTime, temperature);
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= SHARK_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Shark shark = new Shark(field, location);
                    actors.add(shark);
                }
                else if(rand.nextDouble() <= TURTLE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Turtle turtle = new Turtle(field, location);
                    actors.add(turtle);
                }
                else if(rand.nextDouble() <= DOLPHIN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Dolphin dolphin = new Dolphin(field, location);
                    actors.add(dolphin);
                }
                else if(rand.nextDouble() <= SMALLFISH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    SmallFish smallFish = new SmallFish(field, location);
                    actors.add(smallFish);
                }
                else if(rand.nextDouble() <= CRAB_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Crab crab = new Crab(field, location);
                    actors.add(crab);
                }
                else if(rand.nextDouble() <= SEAGRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Seagrass seagrass = new Seagrass(field, location);
                    actors.add(seagrass);
                }
                else if(rand.nextDouble() <= ALGAE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Algae algae = new Algae(field, location);
                    actors.add(algae);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     * 
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
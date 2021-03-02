import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // 
    private static final int DEFAULT_CELESTIAL_CYCLE = 100;
    // The default daily temperature increase
    private static final double DEFAULT_TEMP_INCREASE = 1;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.1;
    //
    private static final double SEAGRASS_CREATION_PROBABILITY = 0.03;
    private static final double ALGAE_CREATION_PROBABILITY = 0.03;

    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    private double dayLight;

    private boolean isDay;

    private int dayNightCycle;

    private double period;

    private double timeTracker;

    private double halfCycle;
    
    private String dayTime;
    
    private double temperature;
    
    private double dailyTempIncrease;
    
    private double stepTempIncrease;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH, DEFAULT_CELESTIAL_CYCLE, DEFAULT_TEMP_INCREASE);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
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
        view.setColor(SmallFish.class, Color.ORANGE);
        view.setColor(Shark.class, Color.BLUE);
        view.setColor(Seagrass.class, new Color(17, 156, 2, 255));
        view.setColor(Algae.class, new Color(176, 255, 167, 255));
        view.setColor(Corpse.class, new Color(150, 75, 0, 255));
        
        
        //
        this.dayNightCycle = dayNightCycle;
        double denominator = dayNightCycle;
        period = 2/denominator;
        this.dailyTempIncrease = dailyTempIncrease;
        stepTempIncrease = dailyTempIncrease/denominator;
        System.out.println(stepTempIncrease);
        isDay = true;
        dayTime = "day";
        timeTracker = denominator/4;
        halfCycle = denominator/2;
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
     * 
     */
    public void changeDayTime(){
        isDay = !isDay;
        if(isDay){
            dayTime = "day";
        }
        else if(!isDay){
            dayTime = "night";
        }
    }
    
    /**
     * 
     */
    public void computeDayLight(){
        double dayNightWave = Math.cos(period*Math.PI*step);
        dayLight = 0.375*dayNightWave+0.625;
        double tempIncrease = step*stepTempIncrease;
        temperature = 5*dayNightWave+15+tempIncrease;
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
            //delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        computeDayLight();

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
        computeDayLight();
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
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Shark shark = new Shark(field, location);
                    actors.add(shark);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    SmallFish smallFish = new SmallFish(field, location);
                    actors.add(smallFish);
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

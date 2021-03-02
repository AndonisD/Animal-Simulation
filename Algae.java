import java.util.List;

/**
 * Write a description of class Algae here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Algae extends Plant
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a seagrass starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 60;
    // The ability of a seagrass to undertake a specific action.
    private static final int INITIAL_VITALITY = 4;
    // The seagrass' worth as a food source.
    private static final int FOOD_VALUE = 5;
    // The age at which a seagrass starts to have a chance of reproducing.
    private static final int REPRODUCTION_AGE = 5;
    // The probability of a seagrass reproducing.
    private static final double RERODUCTION_PROBABILITY = 0.06;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    
    private static final double MAX_TEMP = 30;
    
    private static final double MIN_TEMP = 0;
    
    /**
     * Constructor for objects of class Algae
     */
    public Algae(Field field, Location location)
    {
        super(field, location);
        setSpeciesName("Algae");
        setAgeOfDecay(AGE_OF_DECAY);
        setVitality(INITIAL_VITALITY);
        setFoodValue(FOOD_VALUE);
        setRateOfDecay(RATE_OF_DECAY);
        
        setMaxFoodLevel(1);
        incrementFoodLevel(1);  //<--change lol
    }
    
    /**
     * This is what the seagrass does most of the time. 
     * It will reproduce and die of old age.
     * 
     * @param newSeagrass A list to return new plants of type seagrass.
     */
    public void act(List<Actor> newActors, boolean isDay, double temperature)
    {
        if(isAlive()) {
            // Try to reproduce.
            if(canReproduce(REPRODUCTION_AGE, RERODUCTION_PROBABILITY) && isDay){
                reproduce(newActors);
            }
            // Tracks the vitality of the plant.
            if(getVitality() <= 0){
                setDead();
            }
            incrementAge();
            decideDeath(temperature, newActors);
        }
    }

}

import java.util.List;

/**
 * A simple model of a seagrass.
 * Seagrass can age, reproduce and die.
 *
 * @author Ivan Arabadzhiev, Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Seagrass extends Plant
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a seagrass starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 32;
    // The ability of a seagrass to undertake a specific action.
    private static final int INITIAL_VITALITY = 4;
    // The seagrass' worth as a food source.
    private static final int FOOD_VALUE = 5;
    // The age at which a seagrass starts to have a chance of reproducing.
    private static final int REPRODUCTION_AGE = 5;
    // The probability of a seagrass reproducing.
    private static final double RERODUCTION_PROBABILITY = 0.04;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    
    private static final double MAX_TEMP = 30;
    
    private static final double MIN_TEMP = 0;

    /**
     * Create a new seagrass. A seagrass is created with age of zero.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Seagrass(Field field, Location location)
    {
        super(field, location);
        setActorName("Seagrass");
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
            if(canReproduce(REPRODUCTION_AGE, RERODUCTION_PROBABILITY)){
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
import java.util.List;

/**
 * A simple model of a algae.
 * Algae can age, reproduce and die.
 *
 * @author Ivan Arabadzhiev, Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Algae extends Plant
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a algae starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 60;
    // The ability of a algae to undertake a specific action.
    private static final int INITIAL_VITALITY = 4;
    // The algae's worth as a food source.
    private static final int FOOD_VALUE = 5;
    // The maximum temperature of a algae living in its surrounding.
    private static final double MAX_TEMP = 30;
    // The minimum temperature of a algae living in its surrounding.
    private static final double MIN_TEMP = 0;
    // The age at which a algae starts to have a chance of reproducing.
    private static final int REPRODUCTION_AGE = 5;
    // The probability of a algae reproducing.
    private static final double RERODUCTION_PROBABILITY = 0.08;
    // The number of cells that dictate how far seagrass can create an offspring. 
    private static final int RERODUCTION_RANGE = 3;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    
    
    
    /**
     * Create a new algae. A algae is created with age of zero.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Algae(Field field, Location location)
    {
        super(field, location);
        setActorName("Algae");
        setAgeOfDecay(AGE_OF_DECAY);
        setVitality(INITIAL_VITALITY);
        setFoodValue(FOOD_VALUE);
        setRateOfDecay(RATE_OF_DECAY);
    }
    
    /**
     * This is what the algae does most of the time. 
     * It will reproduce and die of old age.
     * 
     * @param newAlgae A list to return new plants of type algae.
     * @param isDay The time of day.
     * @param temperature The temperature of the surrounding.
     */
    public void act(List<Actor> newAlgae, boolean isDay, double temperature)
    {
        if(isAlive()) {
            // Try to reproduce.
            if(canReproduce(REPRODUCTION_AGE, RERODUCTION_PROBABILITY) && isDay){
                reproduce(newAlgae);
            }
            // Tracks the vitality of the plant.
            if(getVitality() <= 0){
                setDead();
            }
            incrementAge();
            decideDeath(newAlgae, temperature);
        }
    }
}
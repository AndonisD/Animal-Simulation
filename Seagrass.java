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
    private static final int AGE_OF_DECAY = 30;
    // The ability of a seagrass to undertake a specific action.
    private static final int INITIAL_VITALITY = 3;
    // The seagrass' worth as a food source.
    private static final int FOOD_VALUE = 4;
    // The age at which a seagrass starts to have a chance of reproducing.
    private static final int REPRODUCTION_AGE = 1;
    // The probability of a seagrass reproducing.
    private static final double RERODUCTION_PROBABILITY = 0.06;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;

    /**
     * Create a new seagrass. A seagrass is created with age of zero.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Seagrass(Field field, Location location)
    {
        super(field, location);
        setSpeciesName("Seagrass");
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
    public void act(List<Organism> newSeagrass)
    {
        if(isAlive()) {
            // Try to reproduce.
            if(canReproduce(REPRODUCTION_AGE, RERODUCTION_PROBABILITY)){
                reproduce(newSeagrass);
            }
            // Tracks the vitality of the plant.
            if(getVitality() <= 0){
                setDead();
            }
            incrementAge();
            decideDeath();
        }
    }

    /**
     * Check whether or not this seagrass is to reproduce at this step.
     * New individuals will be made into free adjacent locations.
     * 
     * @param newSeagrass A list to return new plants of type seagrass.
     */
    protected void reproduce(List<Organism> newSeagrass)
    {
        Field field = getField();
        Location newLocation = field.freeAdjacentLocation(getLocation(), 4);
        if(newLocation != null){
            Seagrass young = new Seagrass(field, newLocation);
            newSeagrass.add(young);
        }
    }
}
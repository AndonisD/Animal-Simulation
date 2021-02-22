import java.util.List;

/**
 * A simple model of a small fish.
 * Small fish age, move, feed, mate, breed, and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev, Adonis Daskalopulos
 * @version 2021.03.03
 */
public class SmallFish extends Animal
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a small fish starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 30;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    // The maximum food level a small fish can reach from feeding on a food source.
    private static final int MAX_FOOD_LEVEL = 9;
    // The small fish' worth as a food source.
    private static final int FOOD_VALUE = 9;
    // The age at which a small fish can start to breed.
    private static final int BREEDING_AGE = 5;
    // The probability of a female meeting a male.
    private static final double MALE_TO_FEMALE_RATIO = 0.5;
    // The likelihood of small fish mating.
    private static final double IMPREGNATION_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 1;

    /**
     * Create a new small fish. A small fish is created with age
     * zero (a new born). The gender is randomly decided.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public SmallFish(Field field, Location location)
    {
        super(field, location);
        setSpeciesName("SmallFish");
        if(getRandom().nextDouble() <= MALE_TO_FEMALE_RATIO){
            changeGender();
        }
        setAgeOfDecay(AGE_OF_DECAY);
        setRateOfDecay(RATE_OF_DECAY);
        setMaxFoodLevel(MAX_FOOD_LEVEL);
        setFoodValue(FOOD_VALUE);
        incrementFoodLevel(getMaxFoodLevel());
        addDiet("Seagrass");
    }   

    /**
     * This is what the small fish does most of the time - it swims 
     * around and eat. It will search for a mate, breed or die of old age.
     * 
     * @param newSmallFish A list to return newly hatched small fish.
     */
    public void act(List<Organism> newSmallFish)
    {
        computeAge();
        
        if(isAlive()) {
            // Try to reproduce.
            if(foundMate()){
                giveBirth(newSmallFish, litterSize());
            }
            // Try to find one of its food sources.
            findFoodPlant();
            //
            if(checkInfected()){
             spreadInfection();
             
            }
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Check whether or not this small fish is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newSmallFish A list to return newly hatched small fish.
     * @param litterSize The number of births.
     */
    protected void giveBirth(List<Organism> newSmallFish, int litterSize)
    {
        // New small fish are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        for(int b = 0; b < litterSize && free.size() > 0; b++) {
            Location loc = free.remove(0);
            SmallFish young = new SmallFish(field, loc);
            newSmallFish.add(young);
        }
    }

    /**
     * Return the generated number, representing the number of births.
     * 
     * @return A number representing the number of births.
     */
    private int litterSize()
    {
        return impregnate(BREEDING_AGE, MAX_LITTER_SIZE, PREGNANCY_PERIOD, 
                          IMPREGNATION_PROBABILITY);
    }
}
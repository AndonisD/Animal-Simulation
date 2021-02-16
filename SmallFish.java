import java.util.List;
import java.util.Random;

/**
 * A simple model of a small fish.
 * Small fish age, move, breed, and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev, Adonis Daskalopulos
 * @version 2016.02.29 (2)
 */
public class SmallFish extends Animal
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a small fish can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age at which a small fish starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 30;
    // The likelihood of a small fish dying.
    private static final double RATE_OF_DECAY = 0.1;
    // The likelihood of a small fish breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The small fish's age.
    private int age;
    // The steps left before next pregnancy.
    private int timeUntilImpregnation;
    // The probability of a small fish dying.
    private double deathProbability;

    /**
     * Create a new small fish. A small fish is created with age
     * zero (a new born). The pregnancy period is set in.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public SmallFish(Field field, Location location)
    {
        super(field, location);
        age = 0;
        timeUntilImpregnation = PREGNANCY_PERIOD;
        deathProbability = 0.0;
    }
    
    /**
     * This is what the small fish does most of the time - it swims 
     * around. Sometimes it will breed or die of old age.
     * 
     * @param newSmallFish A list to return newly hatched small fish.
     */
    public void act(List<Animal> newSmallFish)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newSmallFish);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
                System.out.println("Rabbit died");
            }
        }
    }

    /**
     * Increase the age.
     * This could result in the small fish's death, depending on its age.
     */
    private void incrementAge()
    {
        age++;
        if(age > AGE_OF_DECAY) {
            deathProbability = deathProbability + RATE_OF_DECAY;
            if(rand.nextDouble() <= deathProbability) {
                setDead();
            }
        }
    }
    
    /**
     * Check whether or not this small fish is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newSmallFish A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newSmallFish)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            SmallFish young = new SmallFish(field, loc);
            newSmallFish.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * 
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        timeUntilImpregnation--;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
            timeUntilImpregnation = PREGNANCY_PERIOD;
        }
        return births;
    }

    /**
     * A small fish can breed if it has reached the breeding age 
     * and its pregnancy period is over.
     * 
     * @return true if the small fish can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE && timeUntilImpregnation <= 0;
    }
}

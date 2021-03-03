import java.util.List;
import java.util.Arrays;

/**
 * A simple model of a crab.
 * Crabs age, move, feed, mate, breed, get infected and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev, Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Crab extends Animal
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a crab starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 50;
    // The maximum food level a crab can reach from feeding on a food source.
    private static final int MAX_FOOD_LEVEL = 20;
    // A list holding the food source(s) for the crabs.
    private static final List<String> DEFAULT_DIET = Arrays.asList("Algae", "Corpse", "SmallFish");
    // The crab's worth as a food source.
    private static final int FOOD_VALUE = 8;
    // The maximum temperature of a crab living in its surrounding.
    private static final double MAX_TEMP = 50;
    // The minimum temperature of a crab living in its surrounding.
    private static final double MIN_TEMP = 0;
    // The age at which a crab can start to breed.
    private static final int BREEDING_AGE = 6;
    // The probability of a female meeting a male.
    private static final double MALE_TO_FEMALE_RATIO = 0.5;
    // The likelihood of crab mating.
    private static final double IMPREGNATION_PROBABILITY = 0.8;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 5;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;

    /**
     * Create a new crab. A crab is created with age
     * zero (a new born). The gender is randomly decided.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Crab(Field field, Location location)
    {
        super(field, location);
        setActorName("Crab");
        if(getRandom().nextDouble() <= MALE_TO_FEMALE_RATIO){
            changeGender();
        }
        setAgeOfDecay(AGE_OF_DECAY);
        setMaxFoodLevel(MAX_FOOD_LEVEL);
        setDiet(DEFAULT_DIET);
        incrementFoodLevel(getMaxFoodLevel());
        setFoodValue(FOOD_VALUE);
        setMaxTemp(MAX_TEMP);
        setMinTemp(MIN_TEMP);
        setRateOfDecay(RATE_OF_DECAY);
    }   

    /**
     * This is what the crabs does most of the time - it moves 
     * around and eat. It will search for a mate, breed, die of old age, get infected.
     * 
     * @param newCrabs A list to return newly hatched crabs.
     * @param isDay The time of day.
     * @param temperature The temperature of the surrounding.
     */
    public void act(List<Actor> newCrabs, boolean isDay, double temperature)
    {   
        if(isAlive()) {
            if(isDay){
                // Try to reproduce.
                if(foundMate()){
                    int litterSize = impregnate(BREEDING_AGE, MAX_LITTER_SIZE, PREGNANCY_PERIOD, 
                                                IMPREGNATION_PROBABILITY);
                    giveBirth(newCrabs, litterSize);
                }
                // Try to infect others if it is a carrier of a disease.
                if(checkInfected()){
                    spreadInfection();
                }
                // Try to find one of its food sources.
                Location newLocation = findFood();
                if(newLocation == null) { 
                    // No food found.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // Try to move to a new location.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    leaveCorpseAfterDeath(newCrabs);
                    return;
                }
                incrementAge();
                decrementFoodLevel();
                decideDeath(newCrabs, temperature);
            }
        }
    }
}
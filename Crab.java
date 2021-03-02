import java.util.List;
import java.util.Arrays;


/**
 * A simple model of a small fish.
 * Small fish age, move, feed, mate, breed, and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev, Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Crab extends Animal
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a small fish starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 50;
    // The maximum food level a small fish can reach from feeding on a food source.
    private static final int MAX_FOOD_LEVEL = 20;
    // The small fish' worth as a food source.
    private static final int FOOD_VALUE = 8;
    // The age at which a small fish can start to breed.
    private static final int BREEDING_AGE = 6;
    // The probability of a female meeting a male.
    private static final double MALE_TO_FEMALE_RATIO = 0.5;
    // The likelihood of small fish mating.
    private static final double IMPREGNATION_PROBABILITY = 0.8;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 5;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    
    private static final double MAX_TEMP = 50;
    
    private static final double MIN_TEMP = 0;
    
    private static final List<String> DEFAULT_DIET = Arrays.asList("Algae", "Corpse", "SmallFish");

    /**
     * Create a new small fish. A small fish is created with age
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
        incrementFoodLevel(getMaxFoodLevel());
        setFoodValue(FOOD_VALUE);
        setDiet(DEFAULT_DIET);
        setRateOfDecay(RATE_OF_DECAY);
        setMaxTemp(MAX_TEMP);
        setMinTemp(MIN_TEMP);
    }   

    /**
     * This is what the small fish does most of the time - it swims 
     * around and eat. It will search for a mate, breed or die of old age.
     * 
     * @param newSmallFish A list to return newly hatched small fish.
     */
    public void act(List<Actor> newActors, boolean isDay, double temperature)
    {   
        if(isAlive()) {
            if(isDay){
                // Try to reproduce.
                if(foundMate()){
                    int litterSize = impregnate(BREEDING_AGE, MAX_LITTER_SIZE, PREGNANCY_PERIOD, 
                            IMPREGNATION_PROBABILITY);
                    giveBirth(newActors, litterSize);
                }
                // Try to find one of its food sources.
                // Try to infect others if it is a carrier of a disease.
                if(checkInfected()){
                    spreadInfection();
                }
                // Try to move into a free location.
                Location newLocation = findFood();
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    die(newActors);
                    return;
                }
                incrementAge();
                decrementFoodLevel();
                decideDeath(temperature, newActors);
            }


        }
    }

}

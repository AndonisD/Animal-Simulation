import java.util.List;
import java.util.Arrays;

/**
 * A simple model of a Turtle.
 * Turtles age, move, feed, mate, breed, get infected and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Turtle extends Animal
{
    // Characteristics shared by all Sharks (class variables).

    // The age at which a shark starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 200;
    // The maximum food level a shark can reach from feeding on a food source.
    private static final int MAX_FOOD_LEVEL = 60;
    // The animal's food source(s).
    private static final List<String> DEFAULT_DIET = Arrays.asList("Crab", "Seagrass");
    // The age at which a shark can start to breed.
    private static final int BREEDING_AGE = 18;
    // The probability of a female meeting a male.
    private static final double MALE_TO_FEMALE_RATIO = 0.5;
    // The likelihood of sharks mating.
    private static final double IMPREGNATION_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 6;   
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;

    /**
     * Create a turtle. A turtle can be created as a new born (age zero
     * and not hungry). The gender is randomly decided.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Turtle(Field field, Location location)
    {
        super(field, location);
        setActorName("Turtle");
        if(getRandom().nextDouble() <= MALE_TO_FEMALE_RATIO){
            changeGender();
        }
        setAgeOfDecay(AGE_OF_DECAY);
        setDiet(DEFAULT_DIET);
        setMaxFoodLevel(MAX_FOOD_LEVEL);
        incrementFoodLevel(getMaxFoodLevel()/2);
        setRateOfDecay(RATE_OF_DECAY);
    }

    /**
     * This is what the turtles do most of the time: it hunts for
     * its food sources. In the process, it searches for mate, it might breed, 
     * die of hunger, die of old age, get infected.
     * 
     * @param newSmallSharks A list to return newly born turtles.
     * @param isDay The time of day.
     * @param temperature The temperature of the surrounding.
     */
    public void act(List<Actor> newTurtles, boolean isDay, double temperature)
    {
        if(isAlive()) {
            // Try to reproduce.
            if(foundMate()){
                int litterSize = impregnate(BREEDING_AGE, MAX_LITTER_SIZE, PREGNANCY_PERIOD, 
                                            IMPREGNATION_PROBABILITY);
                giveBirth(newTurtles, litterSize);
            }     
            // Try to infect others if it is a carrier of a disease.
            if(checkInfected()) {
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
                leaveCorpseAfterDeath(newTurtles);
            }
            incrementAge();
            decrementFoodLevel();
            decideDeath(newTurtles, temperature);
        }
    }
}
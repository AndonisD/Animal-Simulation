import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a Shark.
 * Foxes age, move, eat, breed and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Fox extends Animal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age at which a small fish can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age at which a small fish starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 140;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    // The likelihood of a small fish mating.
    private static final double IMPREGNATION_PROBABILITY = 0.2;  //0.08
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 1;   
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The probability of a female meeting a male.
    private static final double MALE_TO_FEMALE_RATIO = 0.5;
    
    // The food value of a single rabbit. In effect, this is the    
    // number of steps a fox can go before it has to eat again.
    private static final int MAX_FOOD_LEVEL = 9;


    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(Field field, Location location)
    {
        super(field, location);
        if(getRandom().nextDouble() <= MALE_TO_FEMALE_RATIO){
            changeGender();
        }
        setMaxFoodLevel(MAX_FOOD_LEVEL);
        incrementFoodLevel(getMaxFoodLevel());    //<-- add maxFoodValue param
        setSpeciesName("Fox");
        addDiet("SmallFish");
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Organism> newSmallFoxes)
    {
        incrementAge(AGE_OF_DECAY, RATE_OF_DECAY);
        decrementFoodLevel();
        if(isAlive()) {
            if(foundMate()){
                giveBirth(newSmallFoxes, litterSize());
            }          
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
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
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Organism> newFoxes, int litterSize)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        for(int b = 0; b < litterSize && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fox young = new Fox(field, loc);
            newFoxes.add(young);
        }
    }
        
    /**
     * Return the generated number, representing the number of births.
     * 
     * @return 
     */
    private int litterSize()
    {
        return impregnate(BREEDING_AGE, MAX_LITTER_SIZE, PREGNANCY_PERIOD, 
                          IMPREGNATION_PROBABILITY);
    }

}

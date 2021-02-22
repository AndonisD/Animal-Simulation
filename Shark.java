import java.util.List;

/**
 * A simple model of a Shark.
 * Sharks age, move, feed, mate, breed and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Shark extends Animal
{
    // Characteristics shared by all Sharks (class variables).

    // The age at which a shark starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 140;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    // The maximum food level a shark can reach from feeding on a food source.
    private static final int MAX_FOOD_LEVEL = 9;
    // The age at which a shark can start to breed.
    private static final int BREEDING_AGE = 15;
    // The probability of a female meeting a male.
    private static final double MALE_TO_FEMALE_RATIO = 0.5;
    // The likelihood of sharks mating.
    private static final double IMPREGNATION_PROBABILITY = 0.2;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 1;   

    /**
     * Create a shark. A shark can be created as a new born (age zero
     * and not hungry). The gender is randomly decided.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Shark(Field field, Location location)
    {
        super(field, location);
        setSpeciesName("Shark");
        if(getRandom().nextDouble() <= MALE_TO_FEMALE_RATIO){
            changeGender();
        }
        setMaxFoodLevel(MAX_FOOD_LEVEL);
        incrementFoodLevel(getMaxFoodLevel());
        addDiet("SmallFish");
    }

    /**
     * This is what the shark does most of the time: it hunts for
     * its food sources. In the process, it searches for mate, it might breed, 
     * die of hunger, or die of old age.
     * 
     * @param field The field currently occupied.
     * @param newSmallSharks A list to return newly born sharks.
     */
    public void act(List<Organism> newSmallSharks)
    {
        computeAge(AGE_OF_DECAY, RATE_OF_DECAY);
        decrementFoodLevel();
        if(isAlive()) {
            // Try to reproduce.
            if(foundMate()){
                giveBirth(newSmallSharks, litterSize());
            }          
            // Move towards a source of food if found.
            Location newLocation = findFoodAnimal();
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
     * Check whether or not this shark is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newSmallSharks A list to return newly born sharks.
     */
    private void giveBirth(List<Organism> newSmallSharks, int litterSize)
    {
        // New sharks are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        for(int b = 0; b < litterSize && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Shark young = new Shark(field, loc);
            newSmallSharks.add(young);
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
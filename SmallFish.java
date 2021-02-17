import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a small fish.
 * Small fish age, move, feed, breed, and die.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev, Adonis Daskalopulos
 * @version 2021.02.17
 */
public class SmallFish extends Animal
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a small fish can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age at which a small fish starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 30;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;
    // The likelihood of a small fish mating.
    private static final double IMPREGNATION_PROBABILITY = 0.12;
    // The minimun of steps before next pregnancy.
    private static final int PREGNANCY_PERIOD = 1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The probability of a female meeting a male.
    private static final double MALE_TO_FEMALE_RATIO = 0.5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).

    // The small fish's age.
    private int age;
    // The steps left before next pregnancy.
    private int timeUntilImpregnation;
    // The probability of a small fish dying.
    private double deathProbability;
    // The gender of a small fish.
    private boolean isFemale;

    /**
     * Create a new small fish. A small fish is created with age
     * zero (a new born). The pregnancy period for females is set in. The gender is
     * randomly decided.
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
        isFemale = true;
        if(rand.nextDouble() <= MALE_TO_FEMALE_RATIO){
            changeGender();
        }
    }   

    /**
     * This is what the small fish does most of the time - it swims 
     * around. It will search for a mate, breed or die of old age.
     * 
     * @param newSmallFish A list to return newly hatched small fish.
     */
    public void act(List<Animal> newSmallFish)
    {
        incrementAge();
        if(isAlive()) {
            findMate(newSmallFish);
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
                System.out.println("Small fish died");
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
     * Change the gender of the small fish.
     */
    private void changeGender()
    {
        isFemale = !isFemale;
    }

    /**
     * Check if the small fish is a female. If false, it is a male.
     * 
     * @return true if the small fish is female, false otherwise.
     */
    private boolean checkFemale()
    {
        return isFemale;
    }
    
    /**
     * The process of a small fish finding a mate of the same species
     * and of the opposite gender.
     *
     * @param  newSmallFish  A list to return newly born rabbits.
     */
    private void findMate(List<Animal> newSmallFish)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof SmallFish) {
                SmallFish mate = (SmallFish) animal;
                boolean mateGender = mate.checkFemale();
                if(mate.isAlive() && isFemale == !mateGender) { 
                    impregnate(newSmallFish);
                }
            }
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * 
     * @return The number of births (may be zero).
     */
    private void impregnate(List<Animal> newSmallFish)
    {
        timeUntilImpregnation--;
        if(canBreed() && rand.nextDouble() <= IMPREGNATION_PROBABILITY) {
            int litterSize = 0;
            litterSize = rand.nextInt(MAX_LITTER_SIZE) + 1;
            timeUntilImpregnation = PREGNANCY_PERIOD;
            giveBirth(newSmallFish, litterSize);
        }
    }

    /**
     * Check whether or not this small fish is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newSmallFish A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newSmallFish, int litterSize)
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
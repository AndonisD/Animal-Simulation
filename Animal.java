import java.util.List;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Animal extends Organism
{
    //
    
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;
    // The steps left before next pregnancy.
    private int timeUntilImpregnation;
    // The gender of a small fish.
    private boolean isFemale;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field, location);
        isFemale = true;
    }

    /**
     * Increase the age.
     * This could result in the small fish's death, depending on its age.
     */
    protected void incrementAge(int ageOfDecay, double rateOfDecay)
     {
        computeAge();
        if(getAge() > ageOfDecay) {
            computeDeathProbability(rateOfDecay);
            if(getRandom().nextDouble() <= getDeathProbability()) {
                setDead();
            }
        }
    }

    /**
     * Change the gender of the small fish.
     */
    protected void changeGender()
    {
        isFemale = !isFemale;
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * 
     * @return The number of births (may be zero).
     */
    protected int impregnate(int breedingAge, int maxLitterSize, 
                             int pregnancyPeriod, double impregnationProbability)
    {
        int litterSize = 0;
        timeUntilImpregnation--;
        if(canBreed(breedingAge) && getRandom().nextDouble() <= impregnationProbability) {
            litterSize = getRandom().nextInt(maxLitterSize) + 1;
            timeUntilImpregnation = pregnancyPeriod;
            //giveBirth(newSmallFish, litterSize);
        }
        return litterSize;
    }

    /**
     * A small fish can breed if it has reached the breeding age 
     * and its pregnancy period is over.
     * 
     * @return true if the small fish can breed, false otherwise.
     */
    protected boolean canBreed(int breedingAge)
    {
        return getAge() >= breedingAge && timeUntilImpregnation <= 0;
    }

    // Instance fields accessor methods.

    /**
     * 
     */
    protected int getFoodLevel()
    {
        return foodLevel;
    }

    /**
     * 
     */
    protected int getTimeUntilImpregnation()
    {
        return timeUntilImpregnation;
    }

    /**
     * Check if the small fish is a female. If false, it is a male.
     * 
     * @return true if the small fish is female, false otherwise.
     */
    protected boolean checkFemale()
    {
        return isFemale;
    }
}

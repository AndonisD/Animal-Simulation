import java.util.List;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Animal extends Organism
{
    //Characteristics shared by all animals (instance fields).
    
    // The animal's food level, which is increased by feeding from its food source.
    private int foodLevel;
    // The steps left before next impregnation.
    private int timeUntilImpregnation;
    // The gender of an animal.
    private boolean isFemale;

    /**
     * Create a new female animal at location in field.
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
     * This could result in the animal's death, depending on its age.
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
     * Change the gender of the animal.
     */
    protected void changeGender()
    {
        isFemale = !isFemale;
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * 
     * @param breedingAge The age at which a small fish can start to breed.
     * @param maxLitterSize The maximum number of births.
     * @param pregnancyPeriod The minimun of steps before next pregnancy.
     * @param impregnationProbability The likelihood of a small fish mating.
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
        }
        return litterSize;
    }

    /**
     * An animal can breed if it has reached its breeding age 
     * and their pregnancy period is over.
     * 
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canBreed(int breedingAge)
    {
        return getAge() >= breedingAge && timeUntilImpregnation <= 0;
    }

    // Instance fields accessor methods.

    /**
     * Return the food level of an animal.
     * 
     * @return The food level of the animal
     */
    protected int getFoodLevel()
    {
        return foodLevel;
    }

    /**
     * Return the time before next impregnation.
     * 
     * @return The remaining time before next impregnation.
     */
    protected int getTimeUntilImpregnation()
    {
        return timeUntilImpregnation;
    }

    /**
     * Accessor method for checking the animal's sex.
     * 
     * @return True if the animal is female, false otherwise.
     */
    protected boolean checkFemale()
    {
        return isFemale;
    }
}

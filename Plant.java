import java.util.List;
import java.util.Iterator;

/**
 * An abstract class representing shared characteristics of plants.
 *
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Plant extends Organism
{
    // Characteristics shared by all animals (class variables).
    
    // The probability of an animal getting a desease.
    private static final double INFECTION_PROBABILITY = 0.01;
    // The probability of an animal spreading tthe desease.
    private static final double SPREADING_PROBABILITY = 0.1;
    
    /**
     * Create a new plant at a location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        changeKingdom();
        setInfectionProbability(INFECTION_PROBABILITY);
        setSpreadingProbability(SPREADING_PROBABILITY);
    }

    // Abstract methods.
    
    /**
     * Check whether or not this plant is to reproduce at this step.
     * 
     * @param newPlants A list to return new plants.
     */
    abstract protected void reproduce(List<Organism> newPlants);
    
    // Class variables accessor methods.
    
    /**
     * Return the infection probability.
     * 
     * @return The infection probability.
     */
    protected double getInfectionProbability()
    {
        return INFECTION_PROBABILITY;
    }
    
    /**
     * Return the spreading probability.
     * 
     * @return The spreading probability.
     */
    protected double getSpreadingPorbability()
    {
        return SPREADING_PROBABILITY;
    }
    
    // Mutator methods, describing action or process.

    /**
     * A plant can reproduce if it has reached its reproducing age 
     * and their reproduction probability allows it.
     * 
     * @param reproductionAge The minimum age at which a plant can start reproducing.
     * @param reproductionProbability The probability of a plant reproducing.
     * 
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canReproduce(int reproductionAge, double reproductionProbability)
    {
        return getAge() >= reproductionAge && getRandom().nextDouble() <= reproductionProbability;
    }
    
    /**
     * The process of a plant spreading the disease to other organisms.
     */
    protected void spreadInfection()
    {
        
    }
}

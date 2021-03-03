import java.util.List;
import java.util.Iterator;
import java.lang.reflect.*;

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
    }
    
    // Generic methods.
    
    /**
     * Check whether or not this seagrass is to reproduce at this step.
     * New individuals will be made into free adjacent locations.
     * 
     * @param newSeagrass A list to return new plants of type seagrass.
     */
    protected void reproduce(List<Actor> newActors)
    {
        Field field = getField();
        Location newLocation = field.freeAdjacentLocation(getLocation(), 4);
        if(newLocation != null){
            try
            {
                // Uses Java Reflection to make new instances of the Animal subclass calling the method
                Constructor<? extends Plant> constructor = getClass().getDeclaredConstructor(Field.class, Location.class);
                Plant newPlant = constructor.newInstance(getField(), newLocation) ;
                newActors.add(newPlant);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
    
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
        return getAge() >= reproductionAge && testProbability(reproductionProbability);
    }
    
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
}
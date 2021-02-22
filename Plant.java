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
}

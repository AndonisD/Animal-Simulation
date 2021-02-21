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
    // Characteristics shared by all plants (instance fields).
    
    //
    

    /**
     * Create a new plant at a location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        
    }
}

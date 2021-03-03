import java.awt.Color;

/**
 * Provide a counter for a participant in the simulation.
 * This includes an identifying string and a count of how
 * many participants of this type currently exist within 
 * the simulation.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2021.03.03
 */
public class Counter
{
    // Instance fields.
    
    // A name for this type of simulation participant
    private String name;
    // How many of this type exist in the simulation.
    private int count;

    /**
     * Provide a name for one of the simulation types.
     * 
     * @param name  A name, e.g. "Small fish".
     */
    public Counter(String name)
    {
        this.name = name;
        count = 0;
    }
    
    /**
     * Return the name, the short description of this type.
     * 
     * @return The short description of this type.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the currrent count for this type.
     * 
     * @return The current count for this type.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Increment the current count by one.
     */
    public void increment()
    {
        count++;
    }
    
    /**
     * Reset the current count to zero.
     */
    public void reset()
    {
        count = 0;
    }
}
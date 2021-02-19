import java.util.List; 
import java.util.Random;

/**
 * A class representing shared characteristics of organisms.
 *
 * @author Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Organism
{
    //
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    //
    
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // Whether the animal is alive or not.
    private boolean alive;
    // The small fish's age.
    private int age;
    // The probability of a small fish dying.
    private double deathProbability;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Organism(Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        alive = true;
        age = 0;
        deathProbability = 0.0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Organism> newOrganisms);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    // Class variables accessor methods.

    /**
     * 
     */
    protected Random getRandom()
    {
        return rand;
    }
    
    // Instance fields accessor methods.

    /**
     * 
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * 
     */
    protected double getDeathProbability()
    {
        return deathProbability;
    }
    
    //
    
    /**
     * 
     */
    protected void computeAge()
    {
        age++;
    }
    
    /**
     * 
     */
    protected void computeDeathProbability(double rateOfDecay)
    {
        deathProbability = deathProbability + rateOfDecay;
    }
}
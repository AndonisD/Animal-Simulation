import java.util.List; 
import java.util.Random;

/**
 * A class representing shared characteristics of organisms.
 *
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Organism
{
    //Characteristics shared by all organisms (class variables).
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    //Characteristics shared by all organisms (instance fields).
    
    // The organism's field.
    private Field field;
    // The organism's position in the field.
    private Location location;
    // Whether the organism is alive or not.
    private boolean alive;
    // The organism's age.
    private int age;
    // The probability of a organism dying.
    private double deathProbability;
    
    /**
     * Create a new organism at location in field.
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
     * Make this organism act - that is: make it do
     * whatever it wants/needs to do.
     * 
     * @param newOrganisms A list to receive newly born organisms.
     */
    abstract public void act(List<Organism> newOrganisms);

    /**
     * Check whether the organism is alive or not.
     * 
     * @return true if the organism is still alive, false otherwise.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the organism is no longer alive.
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
     * Return the organism's location.
     * 
     * @return The organism's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the organism at the new location in the given field.
     * 
     * @param newLocation The organism's new location.
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
     * Return the organism's field.
     * 
     * @return The organism's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    // Class variables accessor methods.

    /**
     * Return the random number for breeding control.
     * 
     * @return The random number for breeding control.
     */
    protected Random getRandom()
    {
        return rand;
    }
    
    // Instance fields accessor methods.

    /**
     * Return the age of the organism.
     * 
     * @return The age of the organism.
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * Return the death probability of the organism.
     * 
     * @return The death probablity of the organism.
     */
    protected double getDeathProbability()
    {
        return deathProbability;
    }
    
    // Instance fields mutator methods.
    
    /**
     *  Increments the age of an organism.
     */
    protected void computeAge()
    {
        age++;
    }
    
    /**
     *  Computes the death probablity of an organism.
     */
    protected void computeDeathProbability(double rateOfDecay)
    {
        deathProbability = deathProbability + rateOfDecay;
    }
}
import java.util.Random;
import java.util.List; 

/**
 * An abstract class representing shared characteristics of all actors.
 *
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Actor
{
    // Characteristics shared by all actors (class variables).
    
    // Random number generator.
    private static final Random rand = new Random();

    // Characteristics shared by all actors (instance fields).
    
    // The simulation field.
    private Field field;
    // The actor's location in the field.
    private Location location;
    // Whether the actor is alive or not.
    private boolean alive;
    // The name of the actor.
    private String actorName;
    // The actor's worth as a food source.
    private int foodValue;
    
    /**
     * Create a new actor at location in field with their food value.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Actor(Field field, Location location){
        this.field = field;
        setLocation(location);
        alive = true;
        actorName = "Actor";
        foodValue = 0;
    }

    /**
     * Clear the instance fields of a dead actor.
     */
    protected void clearFields(){
        Field field = getField();
        Location location = getLocation();
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
 
    // Generic methods.
    
    /**
     * Return the result of a given probability.
     * 
     * @param probability The probability it evaluates.
     * 
     * @return The result of a given probability.
     */
    protected boolean testProbability(double probability)
    {
        return getRandom().nextDouble() <= probability;
    }
    
    /**
     * Whenever an actor dies there is a possibility of them leaving a corpse.
     * 
     * @param newActors A list to receive new actors.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    protected void leaveCorpse(List<Actor> newActors, Field field, Location location){
        Corpse corpse = new Corpse(field, location);
        newActors.add(corpse);
    }
    
    // Abstract methods.
    
    /**
     * Make this actor act - that is: make it do
     * whatever it wants/needs to do.
     * 
     * @param newActors A list to receive new actors.
     * @param isDay The time of day. True if day, False otherwise.
     * @param temperature The temperature of the surrounding.
     */
    abstract public void act(List<Actor> newActors, boolean isDay, double temperature);

    // Class variables accessor methods.
    
    /**
     * Return the random number generator.
     *  
     * @return The random number generator.
     */
    protected Random getRandom(){
        return rand;
    }
    
    // Instance fields accessor methods.
    
    /**
     * Return the actor's field.
     * 
     * @return The actor's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Return the actor's location.
     * 
     * @return The actor's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Check whether the actor is alive or not.
     * 
     * @return True if the actor is still alive, False otherwise.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    @Override
    /**
     * Return the object - actor as a String.
     * 
     * @return A String of the actor object.
     */
    public String toString()
    {
        return actorName;
    }
    
    /**
     * Return the food value of the actor.
     * 
     * @return The food value of the actor.
     */
    protected int getFoodValue()
    {
        return foodValue;
    }
    
    // Instance field mutator methods.

    /**
     * Place the actor at the new location in the given field.
     * 
     * @param newLocation The actor's new location.
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
     * Set the String value in the actor's name field.
     * 
     * @param actorName The name of the actor.
     */
    protected void setActorName(String actorName)
    {
        this.actorName = actorName;
    }

    /**
     * Indicate that the actor is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        clearFields();
    }
    
    /**
     * Set the value of the food value field.
     * 
     * @param foodValue The actor's worth as a food source.
     */
    protected void setFoodValue(int foodValue)
    {
        this.foodValue = foodValue;
    }
}

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
    private static Random rand;

    // Characteristics shared by all actors (instance fields).
    
    // The simulation field.
    private Field field;
    // The actor's position in the field.
    private Location location;
    // Whether the actor is alive or not.
    private boolean alive;
    // The name of the actor.
    private String actorName;
    // The oactor's worth as a food source.
    private int foodValue;
    

    public Actor(Field field, Location location){
        this.field = field;
        setLocation(location);
        rand = new Random();
        alive = true;
        actorName = "Actor";
        foodValue = 0;
    }

    /**
     * Make this organism act - that is: make it do
     * whatever it wants/needs to do.
     * 
     * @param newOrganisms A list to receive newly born organisms.
     */
    abstract public void act(List<Actor> newActors, boolean isDay, double temperature);

    /**
     * Return the organism's field.
     * 
     * @return The organism's field.
     */
    protected Field getField()
    {
        return field;
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

    protected Random getRandom(){
        return rand;
    }

    /**
     * Check whether the organism is alive or not.
     * 
     * @return True if the organism is still alive, False otherwise.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    @Override
    /**
     * Return the object - organism as a String.
     * 
     * @return A String of the organism object.
     */
    public String toString()
    {
        return actorName;
    }
    
        /**
     * Return the food value of the organism.
     * 
     * @return The food value of the organism.
     */
    protected int getFoodValue()
    {
        return foodValue;
    }
    
    /**
     * Set the value of the food value field.
     * 
     * @param foodValue The organism's worth as a food source.
     */
    protected void setFoodValue(int foodValue)
    {
        this.foodValue = foodValue;
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
     * Set the String value in the species' name field.
     * 
     * @param speciesName The name of the species.
     */
    protected void setActorName(String actorName)
    {
        this.actorName = actorName;
    }

    /**
     * Indicate that the organism is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        clearFields();
    }

    protected void clearFields(){
        Field field = getField();
        Location location = getLocation();
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    protected void leaveCorpse(List<Actor> newActors, Field field, Location location){
        Corpse corpse = new Corpse(field, location);
        newActors.add(corpse);
    }

}

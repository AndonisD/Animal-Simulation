import java.util.List; 

/**
 * An abstract class representing shared characteristics of corpse.
 *
 * @author Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public class Corpse extends Actor
{
    // Characteristics shared by all animals (class variables)
    
    // The probability of a corpse decomposing.
    private static final double DECOMPOSITION_PROBABILITY = 0.1;
    // The corpse's worth as a food source.
    private static final int FOOD_VALUE = 4;

    /**
     * Create a new corpse at a location in field, after an organism has died.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Corpse(Field field, Location location)
    {
        super(field, location);
        setActorName("Corpse");
        setFoodValue(FOOD_VALUE);
    }

    /**
     * This is what the corpses do - decomposing over time.
     * 
     * @param newCoprses A list to return new corpses.
     * @param isDay The time of day.
     * @param temperature The temperature of the surrounding.
     */
    public void act(List<Actor> newCoprses, boolean isDay, double temperature)
    { 
        if(isAlive()){
            if(testProbability(DECOMPOSITION_PROBABILITY)){
                setDead();
            }
        }
    }
}
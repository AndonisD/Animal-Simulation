import java.util.List; 
/**
 * Write a description of class Corpse here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Corpse extends Actor
{
    
    private static final double DECOMPOSITION_PROBABILITY = 0.1;
    
    private static final int FOOD_VALUE = 4;

    /**
     * Constructor for objects of class Corpse
     */
    public Corpse(Field field, Location location)
    {
        super(field, location);
        setActorName("Corpse");
        setFoodValue(FOOD_VALUE);
    }

    /**
     * This is what the small fish does most of the time - it swims 
     * around and eat. It will search for a mate, breed or die of old age.
     * 
     * @param newSmallFish A list to return newly hatched small fish.
     */
    public void act(List<Actor> newActors, boolean isDay, double temperature)
    { 
        if(isAlive()){
            if(getRandom().nextDouble() <= DECOMPOSITION_PROBABILITY){
                setDead();
            }
        }

    }

}

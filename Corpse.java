import java.util.List; 
/**
 * Write a description of class Corpse here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Corpse extends Actor
{
    // instance variables - replace the example below with your own
    private double decompositionProbability;

    /**
     * Constructor for objects of class Corpse
     */
    public Corpse(Field field, Location location)
    {
        super(field, location);
        decompositionProbability = 0.01;
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
            if(getRandom().nextDouble() <= decompositionProbability){
                setDead();
                System.out.println("corpse gone");
            }
        }

    }

}

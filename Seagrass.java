import java.util.List;

/**
 * Write a description of class Algae here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Seagrass extends Plant
{
    // Characteristics shared by all small fish (class variables).

    // The age at which a small fish starts to have a chance of dying of age.
    private static final int AGE_OF_DECAY = 30;
    //
    private static final int AGE_OF_REPRODUCTION = 1;
    //
    private static final int INITIAL_VITALITY = 3;
    // The small fish' worth as a food source.
    private static final int FOOD_VALUE = 4;
    //
    private static final double RERODUCTION_PROBABILITY = 0.06;
    // The rate of change of death probability.
    private static final double RATE_OF_DECAY = 0.1;

    /**
     * 
     */
    public Seagrass(Field field, Location location)
    {
        super(field, location);
        setSpeciesName("Seagrass");
        setVitality(INITIAL_VITALITY);
        setFoodValue(FOOD_VALUE);
    }

    /**
     * 
     */
    public void act(List<Organism> newSeagrass)
    {
        computeAge(AGE_OF_DECAY, RATE_OF_DECAY);
        if(isAlive()) {
            //
            if(canReproduce(AGE_OF_REPRODUCTION, RERODUCTION_PROBABILITY)){
                reproduce(newSeagrass);
            }
            //
            if(getVitality() <= 0){
                setDead();
                System.out.println("GONE");
            }
        }
    }

    /**
     * 
     */
    protected void reproduce(List<Organism> newSeagrass)
    {
        Field field = getField();
        Location newLocation = field.freeAdjacentLocation(getLocation());
        if(newLocation != null){
            Seagrass young = new Seagrass(field, newLocation);
            newSeagrass.add(young);
        }
    }
}
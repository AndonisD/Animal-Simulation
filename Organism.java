import java.util.List; 
import java.util.Random;
import java.util.HashSet;

/**
 * An abstract class representing shared characteristics of organisms.
 *
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Organism
{
    // Characteristics shared by all organisms (class variables).

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Characteristics shared by all organisms (instance fields).

    // The organism's field.
    private Field field;
    // The organism's position in the field.
    private Location location;
    // Whether the organism is alive or not.
    private boolean alive;
    // The kingdom that the organism represents.
    private boolean isAnimal;
    // The name of the distinct species.
    private String speciesName;    
    // The organism's age.
    private int age;
    //
    private int ageOfDecay;
    //
    private double rateOfDecay;
    // The ability to undertake an action.
    private int vitality;
    // A set holding the food sources for a specific species.
    private HashSet<String> diet;
    // The organism's food level.
    private int foodLevel;
    // The organism's maximum food level.
    private int maxFoodLevel;
    // The organism's worth as a food source.
    private int foodValue;
    // The probability of a organism dying.
    private double deathProbability;
    //
    private boolean isInfected;
    
    private double infectionProbability;
    
    private double spreadingProbability;
    
    /**
     * Create a new organism at location in field with specific traits.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Organism(Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        alive = true;
        isAnimal = true;
        speciesName = "";
        age = 0;
        vitality = 0;
        diet = new HashSet<>();
        foodLevel = 0;
        maxFoodLevel = 0;
        foodValue = 0;
        deathProbability = 0.0;
        isInfected = false;
        infectionProbability = 0.0;
        spreadingProbability = 0.0;
    }

    /**
     * Make this organism act - that is: make it do
     * whatever it wants/needs to do.
     * 
     * @param newOrganisms A list to receive newly born organisms.
     */
    abstract public void act(List<Organism> newOrganisms);

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

    /**
     * Check whether the organism is alive or not.
     * 
     * @return True if the organism is still alive, False otherwise.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    /**
     *  Check whether the organism is representative of the animal kingdom.
     *  
     *  @return True if it is from the animal kingdom, False if it is from the plant kingdom.
     */
    protected boolean isAnimal()
    {
        return isAnimal;
    }

    @Override
    /**
     * Return the object - organism as a String.
     * 
     * @return A String of the organism object.
     */
    public String toString()
    {
        return speciesName;
    }

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
     * Return the vitality of the organism.
     * 
     * @return The vitality of the organism.
     */
    protected int getVitality()
    {
        return vitality;
    }
    
    /**
     * Return the set holding the organism's diet.
     * 
     * @return The set holding the otganism's diet.
     */
    protected HashSet<String> getDiet()
    {
        return diet;
    }

    /**
     * Return a boolean value depening whether or not this food source
     * is in the diet set or not.
     * 
     * @return True if the food source is in the set, False otherwise.
     */
    protected boolean dietContains(String foodName)
    {
        return diet.contains(foodName);
    }

    /**
     * Return the food level of an organism.
     * 
     * @return The food level of the organism.
     */
    protected int getFoodLevel()
    {
        return foodLevel;
    }

    /**
     * Return the maximum food level of an organism.
     * 
     * @return The maximum food level of the organism.
     */
    protected int getMaxFoodLevel()
    {
        return maxFoodLevel;
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
     * Return the death probability of the organism.
     * 
     * @return The death probablity of the organism.
     */
    protected double getDeathProbability()
    {
        return deathProbability;
    }
    
    protected boolean isInfected()
    {
        return isInfected;
    }
    
    /**
     * Return the death probability of the organism.
     * 
     * @return The death probablity of the organism.
     */
    protected double getInfectionProbability()
    {
        return infectionProbability;
    }
    
    /**
     * Return the death probability of the organism.
     * 
     * @return The death probablity of the organism.
     */
    protected int getAgeOfDecay()
    {
        return ageOfDecay;
    }
    
    /**
     * Return the death probability of the organism.
     * 
     * @return The death probablity of the organism.
     */
    protected double getRateOfDecay()
    {
        return rateOfDecay;
    }
    
    protected double getSpreadingProbability()
    {
        return spreadingProbability;
    }

    // Instance fields mutator methods.

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
     * Change the organism's state of life.
     */
    protected void changeState()
    {
        alive = !alive;
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
     * Change the organism's kingdom.
     */
    protected void changeKingdom()
    {
        isAnimal = !isAnimal;
    }
    
    /**
     *  Increments the age of an organism.
     */
    protected void incrementAge()
    {
        age++;
    }

    /**
     * Computes the age.
     * This could result in the organism's death, depending on its age.
     * 
     * @param ageOfDecay The age at which an ogranism starts to have a chance of dying.
     * @param rateOfDecay The rate of change of death probability.
     */
    protected void computeAge()
    {
        incrementAge();
        if(getAge() > ageOfDecay) {
            computeDeathProbability(rateOfDecay);
            if(getRandom().nextDouble() <= getDeathProbability()) {
                setDead();
            }
        }
    }

    /**
     * Set the value of the vitality field.
     * 
     * @param vitality The vitality of the organism.
     */
    protected void setVitality(int vitality)
    {
        this.vitality = vitality;
    }
    
    /**
     * Decrement the vitality of an organism. 
     */
    protected void decrementVitality()
    {
        vitality--;
    }
    
    /**
     * Set the String value in the species' name field.
     * 
     * @param speciesName The name of the species.
     */
    protected void setSpeciesName(String speciesName)
    {
        this.speciesName = speciesName;
    }

    /**
     * Add a food source to the set of diet of an ogranism.
     * 
     * @param food The food that an ogranism can consume.
     */
    protected void addDiet(String... food)
    {
        for(String foodName : food){
            diet.add(foodName);
        }
    }

    /**
     * Remove a food source from the set of diet of an ogranism.
     * 
     * @ param foodName The food that is to be removed.
     */
    protected void removeFromDiet(String foodName)
    {
        diet.remove(foodName);
    }

    /**
     *  Upon feeding on a food source the organism's food level is increased,
     *  if the food level exceeds the maximum food level it is set to the 
     *  maximum food level.
     *  
     *  @param foodValue The organism's worth as a food source.
     */
    protected void incrementFoodLevel(int foodValue)
    {
        int newFoodLevel = foodLevel + foodValue;
        if(newFoodLevel > maxFoodLevel){
            foodLevel = maxFoodLevel;
        }
        else{
            foodLevel = newFoodLevel;
        }
    }

    /**
     * Upon "acting" the food level of an ogranism decreases, this may result in
     * the organism's death.
     */
    protected void decrementFoodLevel()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     *  Set the value of the max food level field.
     *  
     *  @param maxFoodLevel The organism's food level.
     */
    protected void setMaxFoodLevel(int maxFoodLevel)
    {
        this.maxFoodLevel = maxFoodLevel;
    }

    /**
     * Set the value to the food value field.
     * 
     * @param foodValue The organism's worth as a food source.
     */
    protected void setFoodValue(int foodValue)
    {
        this.foodValue = foodValue;
    }

    /**
     *  Compute the death probability.
     *  
     *  @param rateOfDecay The rate of change of death probability.
     */
    protected void computeDeathProbability(double rateOfDecay)
    {
        deathProbability = deathProbability + rateOfDecay;
    }
    
    /**
     *  
     */
    protected void changeInfected()
    {
        isInfected = !isInfected;
    }
    
    /**
     *  
     */
    protected boolean checkInfected()
    {
        if(isInfected){
            return true;
        }
        else if(testProbability(infectionProbability)){
            //infect();
            isInfected = true;
            System.out.println("randomly infected");
            return true;
        }
        return false;
    }
    
    /**
     * Set the value to the food value field.
     * 
     * @param foodValue The organism's worth as a food source.
     */
    protected void setInfectionProbability(double infectionProbability)
    {
        this.infectionProbability = infectionProbability;
    }
    
    public void infect()
    {
        isInfected = true;
        System.out.println("got infected");
    }
    
    /**
     * Set the value to the food value field.
     * 
     * @param foodValue The organism's worth as a food source.
     */
    protected void setAgeOfDecay(int ageOfDecay)
    {
        this.ageOfDecay = ageOfDecay;
    }
    
    /**
     * Set the value to the food value field.
     * 
     * @param foodValue The organism's worth as a food source.
     */
    protected void setRateOfDecay(double rateOfDecay)
    {
        this.rateOfDecay = rateOfDecay;
    }
    
    /**
     * Set the value to the food value field.
     * 
     * @param foodValue The organism's worth as a food source.
     */
    protected void setSpreadingProbability(double spreadingProbability)
    {
        this.spreadingProbability = spreadingProbability;
    }

    protected boolean testProbability(double probability)
    {
        return getRandom().nextDouble() <= probability;
    }
    
    //
    
    abstract protected void spreadInfection();
    
    
    
}
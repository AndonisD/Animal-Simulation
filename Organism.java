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
    // The age at which a shark starts to have a chance of dying of age.
    private int ageOfDecay;
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
    // Whether the organism is infected or not.
    private boolean isInfected;
    // The probability of an organism getting infected by a disease
    private double infectionProbability;
    // The probability of an organism passing the disease to another organism.
    private double spreadingProbability;
    // The probability of a organism dying.
    private double deathProbability;
    // The rate of change of death probability.
    private double rateOfDecay;

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
        ageOfDecay = 0;
        vitality = 0;
        diet = new HashSet<>();
        foodLevel = 0;
        maxFoodLevel = 0;
        foodValue = 0;
        isInfected = false;
        infectionProbability = 0.0;
        spreadingProbability = 0.0;
        deathProbability = 0.0;
        rateOfDecay = 0.0;
    }

    // Abstract methods 
    
    /**
     * Make this organism act - that is: make it do
     * whatever it wants/needs to do.
     * 
     * @param newOrganisms A list to receive newly born organisms.
     */
    abstract public void act(List<Organism> newOrganisms);

    /**
     * Make this organism spread the disease it carries.
     */
    abstract protected void spreadInfection();
    
    // ...
    
    /**
     * 
     */
    protected boolean testProbability(double probability)
    {
        return getRandom().nextDouble() <= probability;
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
     * Check whether the organism is representative of the animal kingdom.
     *  
     * @return True if it is from the animal kingdom, False if it is from the plant kingdom.
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
     * Return The age at which an organism starts to have a chance of dying of age.
     * 
     * @return The age at which an organism starts to have a chance of dying of age.
     */
    protected int getAgeOfDecay()
    {
        return ageOfDecay;
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
     * is in the diet set.
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
     * Return whether the organism has been infected by a disease.
     * 
     * @return True if it is infected, False otherwise.
     */
    protected boolean isInfected()
    {
        return isInfected;
    }

    /**
     * Return the infection probability of the organism.
     * 
     * @return The infection probablity of the organism.
     */
    protected double getInfectionProbability()
    {
        return infectionProbability;
    }

    /**
     * Return the spreading probability of the organism
     * 
     * @return The spreading probability of the organism.
     */
    protected double getSpreadingProbability()
    {
        return spreadingProbability;
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
    
    /**
     * Return the rate of decay of an organism.
     * 
     * @return The rate of decay of an organism.
     */
    protected double getRateOfDecay()
    {
        return rateOfDecay;
    }

    // Instance field set methods.
    
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
     * Set the value to the food value field.
     * 
     * @param foodValue The organism's worth as a food source.
     */
    protected void setAgeOfDecay(int ageOfDecay)
    {
        this.ageOfDecay = ageOfDecay;
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
     * Set the value of the max food level field.
     *  
     * @param maxFoodLevel The organism's food level.
     */
    protected void setMaxFoodLevel(int maxFoodLevel)
    {
        this.maxFoodLevel = maxFoodLevel;
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
     * Set the value of the infection probability field.
     * 
     * @param infectionProbability The probability of an organism catching a disease.
     */
    protected void setInfectionProbability(double infectionProbability)
    {
        this.infectionProbability = infectionProbability;
    }
    
    /**
     * Set the value of the spreading probability field.
     * 
     * @param spreadingProbability The probability of an organism passing the disease to other organisms.
     */
    protected void setSpreadingProbability(double spreadingProbability)
    {
        this.spreadingProbability = spreadingProbability;
    }
    
    /**
     * Set the value of the rate of decay field.
     * 
     * @param rateOfDecay The rate of change of death probability.
     */
    protected void setRateOfDecay(double rateOfDecay)
    {
        this.rateOfDecay = rateOfDecay;
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
     * Change the organism's state of life.
     */
    protected void changeState()
    {
        alive = !alive;
    }

    /**
     * Change the organism's kingdom.
     */
    protected void changeKingdom()
    {
        isAnimal = !isAnimal;
    }

    /**
     * Increments the age of an organism.
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
     * Decrement the vitality of an organism. 
     */
    protected void decrementVitality()
    {
        vitality--;
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
     * @param foodName The food that is to be removed.
     */
    protected void removeFromDiet(String foodName)
    {
        diet.remove(foodName);
    }

    /**
     * Upon feeding on a food source the organism's food level is increased,
     * if the food level exceeds the maximum food level it is set to the 
     * maximum food level.
     *  
     * @param foodValue The organism's worth as a food source.
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
     * Change whether the organism carries a disease or not.
     */
    protected void changeInfected()
    {
        isInfected = !isInfected;
    }
    
    /**
     * Infect an organism with a disease.
     * The diseases boost the chances of organism dying earlier.
     */
    protected void infect()
    {
        if(!isInfected && testProbability(spreadingProbability)){
            isInfected = true;
            rateOfDecay = 1; 
        }
    } 
    
    /**
     *  Check whether an organism has already been infected or not.
     *  Infect them if they are not carrying a disease.
     *  
     *  @return True if it is infected, False otherwise.
     */
    protected boolean checkInfected()
    {
        if(isInfected){
            return true;
        }
        else if(testProbability(infectionProbability)){
            infect();
            return true;
        }
        return false;
    }
    
    /**
     * Compute the death probability.
     *  
     * @param rateOfDecay The rate of change of death probability.
     */
    protected void computeDeathProbability(double rateOfDecay)
    {
        deathProbability = deathProbability + rateOfDecay;
    } 
}
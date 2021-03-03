import java.util.List;
import java.util.Iterator;
import java.lang.reflect.*;

/**
 * An abstract class representing shared characteristics of animals.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Animal extends Organism
{
    // Characteristics shared by all animals (class variables)

    // The probability of an animal getting a desease.
    private static final double INFECTION_PROBABILITY = 0.0005;
    // The probability of an animal spreading the desease.
    private static final double SPREADING_PROBABILITY = 0.5;
    //  The probability of an animal curing itself from a desease.
    private static final double CURE_PROBABILITY = 0.2;
    //  The probability of an animal curing itself from a desease.
    private static final double CORPSE_PROBABILITY = 0.01;

    // Characteristics shared by all animals (instance fields).    

    // The gender of an animal.
    private boolean isFemale;
    // The steps left before next impregnation.
    private int timeUntilImpregnation;

    /**
     * Create a new female animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field, location);
        isFemale = true;
        setInfectionProbability(INFECTION_PROBABILITY);
        setSpreadingProbability(SPREADING_PROBABILITY);
        setCureProbability(CURE_PROBABILITY);
        setCorpseProbability(CORPSE_PROBABILITY);
    }
    
    // Generic methods.
    
    /**
     * Look for food source adjacent to the current location.
     * Only the first live food source is eaten.
     * 
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object obj = field.getObjectAt(where);
            //empty cell
            if(obj == null){
                continue;
            }
            //is not part of the diet
            if(!dietContains(obj.toString())) {
                continue;   
            }
            //is dead
            Actor food = (Actor) obj;
            if(!food.isAlive()) {
                continue;
             }
            return eat(where, food);
        }
        return null;
    }

    /**
     * Feed upon a food source and return its location.
     * 
     * @param where The location of the food source.
     * @param food The food source.
     * 
     * @return The location of the food source or null if it is a plant.
     */
    private Location eat(Location where, Actor food){
        if(food instanceof Organism){
            Organism organism = (Organism) food;
            if(organism.isAnimal()) { 
                organism.setDead();                       
            }
            // Is a Plant - Plants get eaten bit by bit instead of getting killed.
            else{
                organism.decrementVitality();
                where = null;
            }
        }
        else{
            food.setDead(); 
        }
        incrementFoodLevel(food.getFoodValue());
        return where;
    }
    
    /**
     * The process of an animal finding a mate of the same species
     * and of the opposite gender.
     * 
     * @return True if the adjecent organism is covering the needed requirements, False otherwise.
     */
    protected boolean foundMate()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object obj = field.getObjectAt(where);
            if(obj == null){
                continue;
            }
            
            if(!toString().equals(obj.toString())) {
                continue;
            }
            Animal mate = (Animal)obj;
            boolean mateGender = mate.isFemale();
            if(mate.isAlive() && isFemale() == !mateGender) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create new instances of the same species. 
     * 
     * @param newAnimals A list to return newly born animals.
     * @param litterSize The number of births.
     */
    protected void giveBirth(List<Actor> newAnimals, int litterSize){
        if(!isFemale()){
            return;
        }
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        for(int b = 0; b < litterSize && free.size() > 0; b++) {
            Location loc = free.remove(0);
            try
            {
                // Uses Java Reflection to make new instances of the Animal subclass calling the method.
                Constructor<? extends Animal> constructor = getClass().getDeclaredConstructor(Field.class, Location.class);
                Animal newBorn = constructor.newInstance(getField(), loc) ;
                newAnimals.add(newBorn);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * 
     * @param breedingAge The age at which a small fish can start to breed.
     * @param maxLitterSize The maximum number of births.
     * @param pregnancyPeriod The minimun of steps before next pregnancy.
     * @param impregnationProbability The likelihood of a small fish mating.
     * 
     * @return The number of births (may be zero).
     */
    protected int impregnate(int breedingAge, int maxLitterSize, 
                             int pregnancyPeriod, double impregnationProbability)
    {
        if(!isFemale()){
            return 0;
        }
        int litterSize = 0;
        timeUntilImpregnation--;
        if(canBreed(breedingAge) && getRandom().nextDouble() <= impregnationProbability) {
            litterSize = getRandom().nextInt(maxLitterSize) + 1;
            timeUntilImpregnation = pregnancyPeriod;
        }
        return litterSize;
    }

    /**
     * An animal can breed if it has reached its breeding age 
     * and their pregnancy period is over.
     * 
     * @param breedingAge The minimum age for the breeding process to occur.
     * 
     * @return True if the animal can breed, False otherwise.
     */
    protected boolean canBreed(int breedingAge)
    {
        return getAge() >= breedingAge && timeUntilImpregnation <= 0;
    }

    /**
     * The process of an animal spreading the disease to other animals.
     */
    protected void spreadInfection()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object obj = field.getObjectAt(where);
            if(obj instanceof Organism){
                Organism animal = (Organism) obj;
                if(animal.isAlive() && animal.isAnimal()) { 
                    animal.infect();
                }
            }
        }
    }
    
    // Class variables accessor methods.
    
    /**
     * Return the infection probability.
     * 
     * @return The infection probability.
     */
    protected double getInfectionProbability()
    {
        return INFECTION_PROBABILITY;
    }

    /**
     * Return the spreading probability.
     * 
     * @return The spreading probability.
     */
    protected double getSpreadingPorbability()
    {
        return SPREADING_PROBABILITY;
    }

    /**
     * Return the cure probability.
     * 
     * @return The cure probability.
     */
    protected double getCureProbability()
    {
        return CURE_PROBABILITY;
    }

    // Instance fields accessor methods.

    /**
     * Return the time before next impregnation.
     * 
     * @return The remaining time before next impregnation.
     */
    protected int getTimeUntilImpregnation()
    {
        return timeUntilImpregnation;
    }

    /**
     * Return boolean value depending on the animal's sex.
     * 
     * @return True if the animal is female, false otherwise.
     */
    protected boolean isFemale()
    {
        return isFemale;
    }

    // Instance fields mutator methods.

    /**
     * Change the gender of the animal.
     */
    protected void changeGender()
    {
        isFemale = !isFemale;
    }
}
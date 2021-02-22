import java.util.List;
import java.util.Iterator;

/**
 * An abstract class representing shared characteristics of animals.
 * 
 * @author David J. Barnes, Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public abstract class Animal extends Organism
{
    // Characteristics shared by all animals (class variables).
    private static final double INFECTION_PROBABILITY = 0.001;

    private static final double SPREADING_PROBABILITY = 0.01;

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
    protected boolean checkFemale()
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

    // Mutator methods, describing action or process.

    /**
     * Look for food source adjacent to the current location.
     * Only the first live food source is eaten.
     * 
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFoodAnimal()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if(organism != null){
                if(dietContains(organism.toString())) {
                    Organism food = (Organism) organism;
                    if(food.isAlive() && food.isAnimal()) { 
                        food.setDead();
                        incrementFoodLevel(food.getFoodValue());
                        return where;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Look for food source adjacent to the current location.
     * Only the first live food source is eaten.
     */
    protected void findFoodPlant()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if(organism != null){
                if(dietContains(organism.toString())) {
                    Organism food = (Organism) organism;
                    if(food.isAlive() && !food.isAnimal()) { 
                        food.decrementVitality();
                        incrementFoodLevel(food.getFoodValue());
                    }
                }
            }
        }
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
            Object organism = field.getObjectAt(where);
            if(organism != null){
                if(toString().equals(organism.toString())) {
                    Animal mate = (Animal)organism;
                    boolean mateGender = mate.checkFemale();
                    if(mate.isAlive() && checkFemale() == !mateGender) {
                        return true;
                    }
                }
            }
        }
        return false;
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
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canBreed(int breedingAge)
    {
        return getAge() >= breedingAge && timeUntilImpregnation <= 0;
    }

    protected void spreadInfection(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if(organism != null){
                Organism animal = (Organism) organism;
                if(animal.isAlive() && animal.isAnimal()) { 
                    animal.infect();
                    System.out.println("spreading");
                }
            }
        }
    }
    
    abstract protected void giveBirth(List<Organism> newSmallFish, int litterSize);
}
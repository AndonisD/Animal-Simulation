import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kölling, Ivan Arabadzhiev and Adonis Daskalopulos
 * @version 2021.03.03
 */
public class SimulatorView extends JFrame
{
    // Class variables.
    
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;
    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    // Color used for organisms that are infected.
    private static final Color INFECTED_COLOR = Color.red;
    //
    private final String STEP_PREFIX = "Step: ";
    //
    private final String POPULATION_PREFIX = "Population: ";
    // The prefix for the temperature label.
    private final String TEMPERATURE_PREFIX = "Temperature (°C): ";
    // The prefix for the day time label.
    private final String DAYTIME_PREFIX = "Time: ";
    //
    private JLabel stepLabel, dayTime, temperature, population;
    //
    private FieldView fieldView;

    // Instance fields.
    
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     * 
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width)
    {
        stats = new FieldStats();
        colors = new LinkedHashMap<>();

        setTitle("Fox and Rabbit Simulation");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        temperature = new JLabel(TEMPERATURE_PREFIX, JLabel.CENTER);
        dayTime = new JLabel(DAYTIME_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);

        setLocation(100, 50);
        fieldView = new FieldView(height, width);
        Container contents = getContentPane();
        JPanel infoPane = new JPanel(new BorderLayout());
        infoPane.add(stepLabel, BorderLayout.WEST);
        infoPane.add(dayTime, BorderLayout.CENTER);
        infoPane.add(temperature, BorderLayout.EAST);
        contents.add(infoPane, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Define a color to be used for a given class of animal.
     * 
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * Return the color of the objects.
     * 
     * @param animalClass The class' name.
     * 
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * 
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     * @param dayLight The brightness of empty cells.
     * @param time The time of day.
     * @param currentTemp The current temperature.
     */
    public void showStatus(int step, Field field, double dayLight, String time, double currentTemp)
    {
        if(!isVisible()) {
            setVisible(true);
        }

        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();

        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null && animal instanceof Actor) {
                    stats.incrementCount(animal.getClass());
                    if(animal instanceof Corpse){
                        fieldView.drawMark(col, row, getColor(animal.getClass()));
                    }
                    else{
                        Organism organism = (Organism) animal;
                        if(organism.isInfected()){
                            fieldView.drawMark(col, row, INFECTED_COLOR);
                        }
                        else{
                            fieldView.drawMark(col, row, getColor(animal.getClass()));
                        }
                    }

                }
                else {
                    fieldView.drawMark(col, row, computeEmptyColor(dayLight));
                }
            }
        }
        stats.countFinished();

        String temperatureString = String.format("%.2f", currentTemp);

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        dayTime.setText(DAYTIME_PREFIX + time);
        temperature.setText(TEMPERATURE_PREFIX + temperatureString);
        fieldView.repaint();
    }

    /**
     * Computes the brightness of the empty cells.
     * 
     * @param fraction The fraction of the color values.
     * 
     * @return The computed color.
     */
    private Color computeEmptyColor(double fraction){
        int colorValue = (int) Math.round(255 * fraction);
        int red = colorValue;
        int green = colorValue;
        int blue = colorValue;

        return new Color(red, green, blue, 255);
    }

    /**
     * Determine whether the simulation should continue to run.
     * 
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }

    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
//Niklas Nesseler 7367375
package logic;


import start.ColorPanel;
import start.DisplayPanel;
import start.SelectColorAction;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Utility {
    /**
     * An array of predefined colors.
     *
     * This array contains the following colors:
     * Red, Green, Blue, Yellow, Black, Orange, Pink, Magenta, Cyan.
     *
     * It is a constant array of type Color[].
     *
     * Example usage:
     * Color selectedColor = COLORS[0];
     *
     * @see java.awt.Color
     */

    public static final HashMap<Color, String> COLORS = new HashMap<>(Map.of(
            Color.RED, "Red",
            Color.GREEN, "Green",
            Color.BLUE, "Blue",
            Color.YELLOW, "Yellow",
            Color.BLACK, "Black",
            Color.ORANGE, "Orange",
            Color.PINK, "Pink",
            Color.MAGENTA, "Magenta",
            Color.CYAN, "Cyan"));
    /**
     * A static final instance of the {@link Random} class, used for generating random numbers.
     *
     * The {@code random} variable is an instance of the {@link Random} class, which provides methods for generating random numbers of various types. It is marked as {@code static final}, meaning that the variable is a class-level constant and its value cannot be modified after initialization.
     *
     * This variable is useful for generating random numbers in applications where randomization is required, such as generating random passwords, simulating game events, or generating test data.
     *
     * Usage example:
     * <pre>{@code
     *     int randomNumber = random.nextInt(100); // Generates a random integer between 0 and 99 (inclusive)
     *     double randomDouble = random.nextDouble(); // Generates a random double between 0.0 (inclusive) and 1.0 (exclusive)
     * }</pre>
     *
     * This variable has a scope of class-level, meaning it can be accessed by any method of the class it is defined in.
     *
     * @see Random
     */
    private static final Random random = new Random();
    /**
     * Gets the current color panel.
     *
     * @return The current color panel.
     */
    private static ColorPanel colorPanel;
    /**
     * Represents an array of colors that have been selected.
     *
     * This variable is declared as private static, meaning that it is accessible within the class only.
     *
     * The type of each element in the array is Color.
     */
    private static Color[] selectedColors;
    /**
     * A static variable representing a timer object.
     *
     * This variable can be used to schedule and cancel scheduled tasks. It is shared among all instances of the class
     * and can be accessed from any method of the class.
     *
     * The Timer class allows to schedule tasks to be executed after a certain delay, or to execute them repeatedly with a fixed delay
     * between executions. The timer can be cancelled at any time, and the scheduled tasks will not be executed anymore.
     */
    private static Timer timer;
    /**
     * The timerSeconds variable represents the number of seconds for a timer.
     * It is a private static integer variable which can only be accessed within the class it is declared in.
     * <p>
     * The value of timerSeconds can be set and retrieved using respective setter and getter methods.
     */
    private static int timerSeconds;
    /**
     * Represents a private static class-level variable, which holds an instance of DisplayPanel.
     *
     * <p> DisplayPanel is a UI component which renders content on the screen. This variable holds an
     * instance of DisplayPanel, which can be used by other classes to manage and update the displayed content.</p>
     *
     * <p>This variable is marked as private and static which means that it can be accessed only within the class where it is
     * declared, and it will hold the same value for all instances of the class.</p>
     *
     * @see DisplayPanel
     */
    private static DisplayPanel displayPanel;

    /**
     * Returns a randomly generated java.awt.Color object.
     *
     * @return a randomly generated java.awt.Color object
     */
    public static java.awt.Color generateRandomColor() {
        return selectedColors[random.nextInt(selectedColors.length)];
    }

    /**
     * Returns the singleton instance of ColorPanel.
     *
     * @return the ColorPanel instance.
     */
    public static ColorPanel getColorPanel() {
        return colorPanel;
    }

    /**
     * Sets the color panel used for displaying colors in the application.
     *
     * @param colorPanel the color panel to set
     */
    public static void setColorPanel(ColorPanel colorPanel) {
        Utility.colorPanel = colorPanel;
    }

    /**
     * Returns an array of Colors that have been selected.
     *
     * @return an array of Color objects that have been selected
     */
    public static Color[] getSelectedColors() {
        return selectedColors;
    }

    /**
     * Sets the selected colors for the application.
     *
     * @param selectedColors an array of Color objects representing the selected colors
     */
    public static void setSelectedColors(Color[] selectedColors) {
        Utility.selectedColors = selectedColors;
    }

    /**
     * Returns the Timer instance.
     *
     * The Timer instance is a static variable that is shared across the application.
     *
     * @return the Timer instance
     */
    public static Timer getTimer() {
        return timer;
    }

    /**
     * Sets the timer for the Utility class.
     *
     * @param timer the Timer object to be set
     */
    public static void setTimer(Timer timer) {
        Utility.timer = timer;
    }

    /**
     * Returns the timerSeconds value which indicates the number of seconds elapsed in the timer.
     *
     * @return an integer value representing the current number of seconds elapsed in the timer.
     */
    public static int getTimerSeconds() {
        return timerSeconds;
    }

    /**
     * Sets the timer duration in seconds.
     *
     * @param timerSeconds the number of seconds for the timer duration
     */
    public static void setTimerSeconds(int timerSeconds) {
        Utility.timerSeconds = timerSeconds;
    }

    /**
     * Returns the display panel that holds the user interface components.
     *
     * @return the DisplayPanel object that contains the main UI components
     */
    public static DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    /**
     * Finds a Field object based on the specified JPanel panel.
     *
     * @param panel the JPanel panel to search for in the Field objects.
     * @return the Field object that contains the specified JPanel, or null if it is not found.
     */
    public static Field findFieldBasedOnPanel(Game game, JPanel panel) {
        for (var column : game.getBoard()) {
            for (var field : column) {
                if (field.getColorPanel() == panel) {
                    return field;
                }
            }
        }
        return null;
    }


    /**
     * The previousColor variable represents the previously selected color.
     * It is a static member of the class and is used to store the color value of the previously selected color.
     * The initial value of previousColor is set to null.
     * <p>
     * Usage:
     * - To access the previous selected color value, use previousColor.
     * - To update the previous selected color value, assign a new color value to previousColor.
     * <p>
     * Example:
     * previousColor = Color.BLUE;
     * <p>
     * Note that this variable is meant to be used in conjunction with a selectable color feature,
     * where the previously selected color needs to be remembered and utilized later in the application.
     */
    private static Color previousColor = null;

    /**
     * Assign unique colors to the top right and bottom left cell panels,
     * making sure that they have different colors.
     *
     * This method does not return any value.
     */
    public static void colorEdges(Game game) {
        var rows = game.getBoard()[0].length;
        var columns = game.getBoard().length;
        var topRight = game.getBoard()[0][rows - 1];
        var bottomLeft = game.getBoard()[columns - 1][0];
        //make sure these both have different colors
        while (topRight.getColorPanel().getBackground().equals(bottomLeft.getColorPanel().getBackground())) {
            var color = Utility.getUniqueColor(game, rows, columns, 0, columns - 1, null);
            bottomLeft.setColor(Objects.requireNonNull(color).getRGB());
        }

    }

    /**
     * Generate a unique color for a specific cell in the game board
     *
     * @param rows    the number of rows in the game board
     * @param columns the number of columns in the game board
     * @param row     the row index of the cell
     * @param column  the column index of the cell
     * @return void
     * @throws RuntimeException if no color is found
     */
    public static void genColor(Game game, int rows, int columns, int row, int column) {
        Color fieldColor;
        //color the fields in different colors
        //Get matching color
        do {
            fieldColor = Utility.getUniqueColor(game, rows, columns, row, column, previousColor);
            //restart the function if there is no color
            if (fieldColor == null) {
                previousColor = null;
                throw new RuntimeException("No color found");
            }
        } while (fieldColor.equals(previousColor)); // Generiere eine neue Farbe, solange sie gleich der vorherigen ist
        var field = new Field(row, column, fieldColor.getRGB());
        var cellPanel = field.getColorPanel();
        cellPanel.addMouseListener(new SelectColorAction());
        game.getBoard()[column][row] = field;
        cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        previousColor = fieldColor;
    }

    /**
     * Returns a unique color for a specified cell panel based on its adjacent cell panels
     *
     * @param rows          the number of rows in the game board
     * @param columns       the number of columns in the game board
     * @param row           the row index of the cell panel
     * @param column        the column index of the cell panel
     * @param previousColor the color of the previous cell panel
     * @return a Color object representing a unique color for the cell panel
     * @throws RuntimeException if no color is found
     */
    public static Color getUniqueColor(Game game, int rows, int columns, int row, int column, Color previousColor) {
        Set<Color> adjacentColors = new HashSet<>();
        // Überprüfe die umliegenden Zellen und füge ihre Farben zum Set hinzu
        for (int currentRow = Math.max(0, row - 1); currentRow <= Math.min(rows - 1, row + 1); currentRow++) {
            for (int currentColumn = Math.max(0, column - 1); currentColumn <= Math.min(columns - 1, column + 1); currentColumn++) {
                if ((currentRow != row || currentColumn != column) && game.getBoard()[currentColumn][currentRow] != null) { // Ignoriere die aktuelle Zelle
                    adjacentColors.add(game.getBoard()[currentColumn][currentRow].getColorRGB());
                }
            }
        }
        // Wähle eine neue Farbe, die nicht im Set ist
        var possibleColors = new HashSet<>(java.util.List.of(Utility.getSelectedColors()));

        possibleColors.removeAll(adjacentColors);
        possibleColors.remove(previousColor);

        if (possibleColors.isEmpty()) {
            return null;
        }

        //choose a random color
        return possibleColors.stream().skip(new Random().nextInt(possibleColors.size())).findFirst().orElse(null);
    }

    /**
     * Sets the DisplayPanel object to be used by the Utility class.
     *
     * @param displayPanel the DisplayPanel object to be set
     */
    public static void setDisplayPanel(DisplayPanel displayPanel) {
        Utility.displayPanel = displayPanel;
    }

    /**
     * This method returns a randomly selected color from the pre-defined set of colors.
     *
     * @return a randomly selected Color object
     **/
    public static java.awt.Color randomColor() {
        return Utility.getSelectedColors()[random.nextInt(Utility.getSelectedColors().length)];
    }

    /**
     * This method generates an array of random colors.
     *
     * @param colorCount the number of random colors to generate
     * @return an array of random colors
     */
    public static Color[] getRandomColors(int colorCount) {
        var randomColors = new HashSet<Color>();

        while (randomColors.size() < colorCount) {
            randomColors.add(new ArrayList<>(COLORS.keySet()).get(random.nextInt(COLORS.values().size())));
        }

        return randomColors.toArray(new Color[0]);

    }

    public static Color getPreviousColor() {
        return previousColor;
    }

    public static void setPreviousColor(Color previousColor) {
        Utility.previousColor = previousColor;
    }

    /**
     * Updates the selected colors based on the given color count.
     *
     * @param colorCount the number of colors to update the selected colors with
     */
    public static void updateSelectedColors(int colorCount) {

        //get random colors from Utility:colors based on colorCount
        var randomColors = Utility.getRandomColors(colorCount);
        Utility.setSelectedColors(randomColors);
    }
}

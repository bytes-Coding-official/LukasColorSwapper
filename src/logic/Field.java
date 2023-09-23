
package logic;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Field {

    /**
     * The colorPanel variable is a private final JPanel.
     * It represents a panel that is used to display color information.
     * <p>
     * The colorPanel cannot be modified once it is assigned a value,
     * and it is a JPanel component used in the graphical user interface.
     * <p>
     * This variable should not be directly accessed from outside the class,
     * as it is intended for internal use only.
     */
    private final JPanel colorPanel;
    /**
     * Represents the color of an object.
     *
     * <p>
     * The color is stored as an integer value.
     * </p>
     *
     * <p>
     * Note that the value of the color is not validated or restricted in any way.
     * </p>
     *
     * @see Object
     */
    private int color;
    /**
     * The variable represents the current row value.
     *
     * @since 1.0
     */
    private int row;
    /**
     * Represents the column number of a particular element.
     *
     * <p>
     * The column number is used to identify the position of an element within a two-dimensional data structure, such as a matrix or a table.
     * This variable is private, meaning that it can only be accessed within the same class.
     * </p>
     *
     * <p>
     * Note: The column number is zero-based, meaning that the first column is represented by 0, the second column by 1, and so on.
     * </p>
     *
     * @since 1.0
     */
    private int col;

    /**
     * Constructs a new Field object with the given row, column, and color.
     *
     * @param row   the row of the field
     * @param col   the column of the field
     * @param color the color of the field
     */
    public Field(int row, int col, int color) {
        this.color = color;
        this.row = row;
        this.col = col;
        colorPanel = new JPanel();
        colorPanel.setBackground(new Color(color));
    }

    /**
     * Constructs a new Field object with the given column, row, and color.
     *
     * @param col   the column of the field
     * @param row   the row of the field
     * @param color the color of the field
     * @return a new Field object with the given column, row, and color
     */
    /*public static Field wrongField(int col, int row, int color){
        return new Field(row, col, color);
    }*/
    /**
     * Returns the color of the field.
     *
     * @return the color of the field
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets the color of the field and updates the color panel accordingly.
     *
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
        colorPanel.setBackground(new Color(color));
    }

    /**
     * Sets the color of the field and updates the color panel accordingly.
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        setColor(color.getRGB());
    }

    /**
     * Gets the color of the field as a new instance of Color.
     *
     * @return the color of the field
     */
    public Color getColorRGB() {
        return new Color(color);
    }

    /**
     * Returns the row number of the field.
     *
     * @return the row number of the field
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index for the field.
     *
     * @param row the row index to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Retrieves the value of the col field.
     *
     * @return the value of the col field
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the col value of the field.
     *
     * @param col the col value to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Retrieves the color panel associated with the field.
     *
     * @return the color panel
     */
    public JPanel getColorPanel() {
        return colorPanel;
    }

    /**
     * Returns the RGB color represented by the current color value.
     *
     * @return the RGB color
     */
    public Color getRGBColor() {
        return new Color(color);
    }

    /**
     * Checks if the given field is adjacent to this field.
     *
     * @param field the field to check adjacency with
     * @return true if the given field is adjacent; false otherwise
     */
    public boolean isAdjacent(Field field) {
        return (Math.abs(this.row - field.row) == 1 && this.col == field.col)
                || (Math.abs(this.col - field.col) == 1 && this.row == field.row);
    }

    /**
     * Returns a HashSet of the adjacent cells to the given field.
     *
     * @param field the CellPanel for which adjacent cells are needed
     * @return a HashSet containing adjacent cells to the given field
     */
    public HashSet<Field> getAdjacentFields(Game game, Field field) {
        return new HashSet<>(game.getNeighbors(field));
    }


    /**
     * Returns a string representation of the Field object.
     * The string representation includes the values of the colorPanel, color, row, and col fields.
     *
     * @return a string representation of the Field object
     */
    @Override
    public String toString() {
        return "Field{" +
                "color = " + Utility.COLORS.get(getColorRGB()) +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}

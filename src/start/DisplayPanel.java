//Niklas Nesseler 7367375
package start;


import logic.Game;
import logic.Utility;

import javax.swing.*;

public class DisplayPanel extends JPanel {

    /**
     * Represents the number of rows in a given data structure.
     */
    private int rows;
    /**
     * Represents the number of columns in a grid or table.
     */
    private int columns;

    /**
     * Constructs a new DisplayPanel object with a specified number of rows and columns
     *
     * @param rows    - the number of rows in the DisplayPanel
     * @param columns - the number of columns in the DisplayPanel
     * @return void
     */
    public DisplayPanel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;


    }


    /**
     * Update the size of the board layout and populate it with cell panels.
     * <p>
     * This method updates the size of the board layout by setting a new SquareGridLayout with the specified number
     * of columns and rows. It then removes all existing components from the DisplayPanel.
     * Next, it iterates over each column and row in the board and adds the corresponding cell panel's color panel
     * to the DisplayPanel. After adding all the cell panels, it calls the revalidate() method to recalculate the
     * layout and the repaint() method to update the display.
     */
    public void updateBoardSize() {
        setLayout(new SquareGridLayout(columns, rows));
        removeAll();
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                add(Game.getGame().getBoard()[column][row].getColorPanel());
            }
        }
        revalidate();
        repaint();
    }


    /**
     * Generate a random board layout by populating it with cell panels of random colors.
     * Handles exceptions by recursively calling itself until a valid board layout is generated.
     */
    public void generateRandomBoard() {
        removeAll();
        setLayout(new SquareGridLayout(columns, rows));
        //Color previousColor = null;
        for (var column = 0; column < this.columns; column++) {
            for (var row = 0; row < this.rows; row++) {
                try {
                    Utility.genColor(Game.getGame(), rows, columns, row, column);
                } catch (Exception e) {
                    generateRandomBoard();
                    return;
                }
                add(Game.getGame().getBoard()[column][row].getColorPanel());
            }
        }
        Utility.colorEdges(Game.getGame());
        revalidate();
        repaint();
    }


    /**
     * Creates a Component object with the specified row and column.
     *
     * @param row    An integer representing the row number of the cell panel.
     * @param column An integer representing the column number of the cell panel.
     * @return A Component object with the specified row and column.
     */


    /**
     * Clears the game board of all background colors and borders.
     * Goes through every cell in the game board and sets their background
     * color and border to null. This resets the game board for a new game.
     *
     * @return void
     */
    public void clearBoard() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                Game.getGame().getBoard()[column][row].getColorPanel().setBackground(null);
                Game.getGame().getBoard()[column][row].getColorPanel().setBorder(null);
            }
        }
    }

    /**
     * Returns the number of rows in the data set.
     *
     * @return the number of rows in the data set
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of the cell panels in the grid layout and calls the method for
     * creating the cell panels.
     *
     * @param rows the number of rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
        Game.getGame().gameFieldCreation(columns, rows);
    }

    /**
     * Retrieves the number of columns in the data structure.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns of the grid and updates the layout of the cell panels accordingly.
     *
     * @param columns the new number of columns in the grid
     */
    public void setColumns(int columns) {
        this.columns = columns;
        Game.getGame().gameFieldCreation(columns, rows);
    }


}

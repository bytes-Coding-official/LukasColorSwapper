
package start;/*
 * Siehe Hinweise auf dem Aufgabenblatt.
 */

import logic.Game;
import logic.Utility;

import javax.swing.*;

public class Start {

    /**
     * Executes the main method of the program. Initializes the GameWindow GUI using SwingUtilities.invokeLater
     * and passes the GameWindow::createAndShowGUI method as a lambda expression to create and display the window.
     * This method does not take any parameters and does not return anything.
     *
     * @param args an array of command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::createAndShowGUI);

    }


    private static void colorBoard(Game game) {
        var columns = game.getBoard().length;
        var rows = game.getBoard()[0].length;
        for (var column = 0; column < columns; column++) {
            for (var row = 0; row < rows; row++) {
                try {
                    Utility.genColor(game, rows, columns, row, column);
                } catch (Exception e) {
                   colorBoard(game);
                }
            }
        }
        Utility.colorEdges(game);
    }
}

//Niklas Nesseler 7367375
package start;

import logic.Game;
import logic.Utility;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class SelectColorAction extends MouseAdapter {

    /**
     * Responds to a mouse click event on the given CellPanel, registering a move made by the current player
     *
     * @param e the MouseEvent triggered by clicking on the CellPanel
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //clicked on appropiate field
        var panel = (JPanel) e.getSource();

        var field = Utility.findFieldBasedOnPanel(Game.getGame(), panel);

        System.out.println("Clicked on: " + field.getColumn() +" "+ field.getRow());


        
        System.out.println(Game.getGame().printBoard(Game.getGame().getBoard()));        
        //get the color of the field
        //get current Player
        var row = Objects.requireNonNull(field).getRow();
        var col = field.getColumn();
        if (!Game.getGame().isInitialized()) return;

        var currentPlayer = Game.getGame().getCurrentPlayer();
        currentPlayer.makeMove(row, col);


    }
}

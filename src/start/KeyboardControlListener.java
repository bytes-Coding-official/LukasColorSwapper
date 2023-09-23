
package start;

import logic.AIPlayer;
import logic.Game;
import logic.Utility;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyboardControlListener extends AbstractAction {

    /**
     * Method to handle the action performed by the user.
     * This method is called automatically when a certain action is performed by the user,
     * and the corresponding event is triggered.
     *
     * @param e Action event occurred
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        var number = Integer.parseInt(e.getActionCommand());
        if (!Game.getGame().isInitialized()) return;
        if (number > Utility.getColorPanel().getColorPanels().length) return;

        var panel = Utility.getColorPanel().getColorPanels()[number - 1];
        var color = panel.getBackground();
        var player = Game.getGame().getCurrentPlayer();
        var component = player.getComponent();

        //any field that has the color
        var field = component.adjacentFieldsOfComponent(Game.getGame()).stream().filter(fieldElement -> fieldElement.getRGBColor().equals(color)).findFirst().orElse(null);
        if (field == null) {
            PopUpCreator.createPopUp("Invalid Move!", "Invalid Move");
            return;
        }
        var row = field.getRow();
        var col = field.getCol();
        if (player instanceof AIPlayer) return;
        player.makeMove(row, col);


    }

}

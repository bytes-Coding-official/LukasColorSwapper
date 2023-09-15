//Niklas Nesseler 7367375
package start;


import logic.Field;
import logic.Player;
import logic.Utility;

import javax.swing.*;
import java.awt.*;


public class ColorPanel extends JPanel {
    /**
     * An array of JPanels representing various color options.
     *
     * @since Replace with the version number of the software development kit or module that introduced this variable.
     */
    private JPanel[] colorPanels;
    /**
     * An array of JLabel components used to represent colors.
     * Each label is used to display a different color.
     */
    private JLabel[] colorLabels;
    /**
     * The colorCount variable stores an integer value representing the number of colors in a given context.
     * <p>
     * This variable is only accessible within the context of its class and is used to keep track of
     * the number of colors present in some fashion.
     *
     * @since (insert version number)
     */
    private int colorCount;

    /**
     * Constructs a ColorPanel object with the specified number of colors.
     *
     * @param colorCount the number of colors to display in the color panel
     */
    public ColorPanel(int colorCount) {
        this.colorCount = colorCount;
        primarySetup();
        refreshColors();
        generateColorPanel();

    }


    /**
     * Initializes the colorPanels and colorLabels arrays with the given colorCount.
     * Sets the layout of the container to have one row and colorCount columns.
     */
    private void primarySetup() {
        colorPanels = new JPanel[colorCount];
        colorLabels = new JLabel[colorCount];
        setLayout(new GridLayout(1, colorCount));
    }


    /**
     * Updates the color of the field panel based on the given field and player information.
     *
     * @param field  The field object containing color information.
     * @param player The player object whose color needs to be updated.
     */
    public void updateFieldPanelColor(Field field, Player player) {
        var color = field.getColorRGB();
        field.getColorPanel().setOpaque(true);
        player.setColor(color);
        for (var boardField : player.getComponent().getFields()) {
            boardField.setColor(color);
            boardField.getColorPanel().setOpaque(true);
            boardField.getColorPanel().revalidate();
            boardField.getColorPanel().repaint();
        }
    }


    /**
     * Removes all existing components from the current panel and generates a new panel with colored panels and labels.
     * Each colored panel is assigned a number label.
     */
    public void generateColorPanel() {
        removeAll();
        for (int i = 0; i < colorCount; i++) {
            var panel = new JPanel();
            panel.setBackground(Utility.getSelectedColors()[i]);
            colorPanels[i] = panel;
            add(panel);
            var label = new JLabel(String.valueOf(i + 1));
            colorLabels[i] = label;
            add(label);
        }
    }

    /**
     * Refreshes the colors displayed in the color panel.
     * Removes all existing components from the panel, updates the colors and generates a new panel with colored panels and labels.
     * Each colored panel is assigned a number label.
     *
     * @return void
     */
    public void refreshColors() {
        primarySetup();
        removeAll();
        for (var i = 0; i < colorCount; i++) {
            var panel = new JPanel();
            panel.setBackground(Utility.getSelectedColors()[i]);
            colorPanels[i] = panel;
            add(panel);
        }
        revalidate();
        repaint();
        generateColorPanel();
    }

    /**
     * Returns an array of JPanel objects, which represents color panels.
     *
     * @return an array of JPanel objects representing the color panels
     */
    public JPanel[] getColorPanels() {
        return colorPanels;
    }

    /**
     * Returns an array of JLabel objects representing color labels.
     *
     * @return an array of JLabel objects representing color labels
     */
    public JLabel[] getColorLabels() {
        return colorLabels;
    }

    /**
     * Returns the number of colors.
     *
     * @return the number of colors as an integer.
     */
    public int getColorCount() {
        return colorCount;
    }

    public void setColorPanels(JPanel[] colorPanels) {
        this.colorPanels = colorPanels;
    }

    public void setColorLabels(JLabel[] colorLabels) {
        this.colorLabels = colorLabels;
    }

    /**
     * Sets the number of colors in the color palette of the object.
     *
     * @param colorCount the number of colors to be set in the color palette of the object.
     */
    public void setColorCount(int colorCount) {
        this.colorCount = colorCount;
    }
}


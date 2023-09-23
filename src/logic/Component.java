//Niklas Nesseler 7367375
package logic;

import start.GameWindow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a set of fields in a class.
 * It provides access to the fields and their properties.
 */

public class Component {

    /**
     * This class represents a set of fields in a class.
     * It provides access to the fields and their properties.
     */
    private final Set<Field> fields;

    /**
     * Constructs a new Component object.
     */
    public Component() {
        this.fields = new HashSet<>();
    }

    /**
     * Adds a new Field to this Component.
     *
     * @param field the Field to be added
     * @return the updated Component with the added Field
     */
    public Component addField(Field field) {
        fields.add(field);
        return this;
    }


    /**
     * Returns the set of fields associated with this object.
     *
     * @return the set of fields
     */
    public Set<Field> getFields() {
        return fields;
    }


    public boolean isAdjacent(Field field) {
        //go through all of my components and check if it is adjacent to any of them
        for (var f : fields) {
            if (f.isAdjacent(field)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns a set of CellPanels that are adjacent to the component.
     *
     * @return a set of adjacent CellPanels
     */
    public Set<Field> adjacentFieldsOfComponent(Game game) {
        var adjFields = new HashSet<Field>();
        for (var field : getFields()) {
            adjFields.addAll(field.getAdjacentFields(game, field));
        }
        getFields().forEach(adjFields::remove);
        return adjFields;
    }

    /**
     * Creates a new component with the given row and column coordinates.
     *
     * @param row    the row coordinate of the component
     * @param column the column coordinate of the component
     * @return the newly created component
     */
    public static Component createComponent(Game game, int row, int column) {
        var component = new Component();
        component.addField(game.getBoard()[column][row]);
        return component;
    }

    //should add all cells that are not already part of the component and that are adjacent to the component to the component when it has the same color
    public void tracePath(Game game, Player player) {
        var topRight = game.getBoard()[0][game.getRows() - 1];
        var lowLeft = game.getBoard()[game.getColumns() - 1][0];

        Field mainComponent;
        if (player.getName().equalsIgnoreCase("S1"))
            mainComponent = player.getComponent().getFields().stream().filter(field -> field.equals(lowLeft)).findFirst().orElseThrow();
        else if (player.getName().equalsIgnoreCase("S2")) mainComponent = player.getComponent().getFields().stream().filter(field -> field.equals(topRight)).findFirst().orElseThrow();
        else throw new IllegalArgumentException("Invalid player name");
        fields.clear();
        fields.add(mainComponent);

        boolean newField;
        do {
            newField = false;
            var newFields = new ArrayList<Field>();
            for (var field : fields) {
                var neighbors = game.getNeighbors(field);
                for (var neighbor : neighbors) {
                    if (!fields.contains(neighbor) && neighbor.getColor() == field.getColor()) {
                        newFields.add(neighbor);
                        newField = true;
                    }
                }
            }
            fields.addAll(newFields);
        } while (newField);

        if (GameWindow.getPlayer1Points() != null)
            GameWindow.getPlayer1Points().setText("Player 1: " + game.getPlayer1().getComponent().getFields().size());
        if (GameWindow.getPlayer2Points() != null)
            GameWindow.getPlayer2Points().setText("Player 2: " + game.getPlayer2().getComponent().getFields().size());
    }


}


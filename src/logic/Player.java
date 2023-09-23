
package logic;


import start.PopUpCreator;

import java.awt.*;

public class Player implements MoveValidator {
    /**
     * Represents a final Component instance that holds a visual element of the user interface.
     * This variable is immutable and can only be set once upon initialization.
     *
     * <p>
     * The Component class represents a visual element, such as a button, label, or panel,
     * that can be displayed on the user interface. This variable, "component", is a private
     * final instance of type Component and is used to store the visual element that this
     * instance holds.
     *
     * <p>
     * Since this variable is marked as final, its value cannot be changed once it is set
     * during initialization. This ensures that the component remains constant throughout
     * the lifetime of the object that contains it.
     */
    private final Component component;
    /**
     * A string representation of a name that is immutable.
     *
     * @since 1.0
     */
    private final String name;
    /**
     * Represents the color of an object.
     * The color is defined using the RGB color model and is represented in the software as an instance of the java.awt.Color class.
     */
    private Color color;
    /**
     * Represents an instance of the Game class, which handles the main game logic and provides access to game state and game settings.
     *
     * @var Game An instance of the Game class.
     */
    private Game game;

    /**
     * Constructor to create a new player object.
     *
     * @param component The game component that the player is associated with.
     * @param name      The name of the player.
     * @throws NullPointerException if the given component or name is null.
     */
    public Player(Component component, String name) {
        this.component = component;
        this.color = component.getFields().toArray(new Field[0])[0].getRGBColor();
        this.name = name;
    }


    /**
     * Returns the component object of the game board.
     *
     * @return A Component object representing the game board.
     */
    public Component getComponent() {
        return component;
    }


    /**
     * Method to retrieve the Color value of an object.
     *
     * @return The Color value associated with the object.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of an object.
     *
     * @param color The color to be set for the object.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the name of the object.
     *
     * @return The name of the object as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Method to make a move on a game board.
     *
     * @param row    The row of the cell to make a move on.
     * @param column The column of the cell to make a move on.
     * @throws IllegalArgumentException if the given row or column value is out of range or invalid.
     */
    public void makeMove(int row, int column) {
        //player will click on a field.
        var field = game.getBoard()[column][row];
        //check valid move
        if (!validateMove(field)) {
            PopUpCreator.createPopUp("Invalid Move!", "Invalid Move");
            return;
        }
        Utility.getColorPanel().updateFieldPanelColor(field, this);
        component.addField(field);
        component.tracePath(game, this);
        endTurn();
    }

    /**
     * Method to end the current player's turn and switch to the next player.
     * <p>
     * If a player has won the game or the game is currently paused, this method does nothing.
     * Otherwise, it switches to the next player and calls the startPlayersTurn method for the new player.
     */
    public void endTurn() {
        //check winning!
        if (game.checkWinning()) return;
        if (game.isGamePaused()) return;
        game.switchCurrentPlayers(this);
        game.getCurrentPlayer().startPlayersTurn(game);
    }

    /**
     * Method to set the current game being played.
     *
     * @param game The game object to set.
     * @throws NullPointerException if the given game object is null.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Method to retrieve the current game being played.
     *
     * @return The current game object.
     */
    protected Game getGame() {
        return game;
    }

    /**
     * Starts the turn of the selected player. If the player is an AI player, it will perform its move automatically.
     * If the player is a human, a pop-up message will be displayed to indicate the start of their turn.
     *
     * @throws NullPointerException if the player name is null.
     */
    public void startPlayersTurn(Game game) {
        if (this instanceof AIPlayer ai) {
            ai.perform(game);
        } else {
            PopUpCreator.createPopUp("The Player " + name + " is selected as the current player", "Beginning Player");
        }
    }


    /**
     * Validates a move for the current player.
     *
     * @param field the field to validate the move on
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean validateMove(Field field) {
        //check if the color is my own or is the opponents color
        if (field.getColorRGB().equals(color) || field.getRGBColor().equals(game.getOpponent(this).getColor())) {
            return false;
        }
        //check if the component is adjacent to my component
        return component.isAdjacent(field);
    }
}

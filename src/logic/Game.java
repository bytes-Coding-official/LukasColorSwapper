//Niklas Nesseler 7367375
package logic;

import start.GameWindow;
import start.PopUpCreator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {


    /**
     * Represents a single instance of the Game class.
     * <p>
     * This variable is used to store a reference to the current instance of
     * the Game class. It is declared as private and static, meaning it is
     * accessible only within the class itself and shared across all instances
     * of the class.
     * <p>
     * Note that this variable should be used with caution, as there should
     * typically only be one instance of the Game class at a time. It is
     * recommended to use a design pattern such as the Singleton pattern
     * to ensure that only one instance of the Game class is created and
     * accessed using this variable.
     * <p>
     * Example usage:
     * Game gameInstance = Game.getInstance();
     *
     * @see Game
     */
    private static Game instance;

    /**
     * Represents the first player of the game.
     */
    private Player player1;
    /**
     * Represents the second player in a game.
     */
    private Player player2;

    /**
     * The unchangedMovesCount variable stores the number of consecutive moves that have been made without changing the state of the game board.
     * This variable is used in various strategies that depend on making moves that result in a change in the board state, such as detecting a stuck state.
     *
     * @return The number of consecutive moves made without changing the state of the game board.
     */
    private final int unchangedMovesCount;
    /**
     * Represents the initialization state of a variable.
     *
     * <p>
     * The boolean value of this variable indicates whether an object has been initialized.
     * A value of {@code true} indicates that the object has been initialized,
     * while a value of {@code false} indicates that the object is still uninitialized.
     * </p>
     *
     * @since 1.0
     */
    private boolean isInitialized;
    /**
     * Represents the current state of the game.
     * <p>
     * If the game is running, this value is set to true; otherwise, it is set to false.
     */
    private boolean gameRunning;
    /**
     * Represents whether the game is currently paused or not.
     * If the value is true, the game is paused.
     * If the value is false, the game is currently running.
     */
    private boolean gamePaused = true;
    /**
     * Holds the current player of the game.
     * <p>
     * This variable is used to keep track of which player's turn it is in the game.
     * It is of type Player, which represents a player of the game.
     * Once a player takes their turn, this variable is updated to the next player.
     */
    private Player currentPlayer;


    /**
     * Represents the game board as a 2D array of fields.
     * Each field may contain different data depending on the game being played.
     */
    private Field[][] board;


    /**
     * Default constructor for the Game class. Initializes the unchangedMovesCount to 0
     * and assigns 'this' instance to the 'game' variable.
     */
    public Game() {
        this.unchangedMovesCount = 0;
    }

    /**
     * Returns an instance of the Game class. If the instance does not exist, a new instance is created.
     *
     * @return an instance of the Game class
     */

    /**
     * Returns the opponent of the given player.
     *
     * @param player the player whose opponent is to be returned
     * @return the opponent of the given player
     */
    public Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }

    /**
     * Returns the instance of the Game class.
     * <p>
     * If the instance is null, a new instance of the Game class is created and assigned to the instance variable.
     * Otherwise, the existing instance is returned.
     *
     * @return the instance of the Game class.
     */
    public static Game getGame() {
        if (instance == null)
            instance = new Game();
        return instance;
    }


    /**
     * Determines the winner of the game based on the number of fields controlled by each player
     *
     * @return the winning player if there is one, or null if it's a draw
     */
    private Player getWinner() {

        if (player1.getComponent().getFields().size() > player2.getComponent().getFields().size()) {
            return player1;
        } else if (player1.getComponent().getFields().size() < player2.getComponent().getFields().size()) {
            return player2;
        } else {
            return null; //Draw
        }
    }

    /**
     * Checks if the game has ended and creates a PopUp window displaying the winner or a draw message
     *
     * @return a boolean value indicating whether the game has ended or not
     */
    public boolean checkWinning() {
        if (isGameOver()) {
            var winner = getWinner();
            if (winner == null) {
                PopUpCreator.createPopUp("It's a draw!", "Game Over");
            } else {
                PopUpCreator.createPopUp("The winner is " + winner.getName(), "Game Over");
            }
            if (Utility.getTimer() != null)
                Utility.getTimer().stop();
            return true;
        }
        return false;
    }

    /**
     * Initializes a new game with two players and sets the current player to start their turn.
     * <p>
     * This method initializes and sets up the two players, sets the strategy for the AI player,
     * creates the player components, and selects a random player to start the game. It also sets
     * the isInitialized variable to true to prevent duplicate initialization.
     */
    public void initialize() {

        if (isInitialized) return;
        isInitialized = true;
        var topRight = Component.createComponent(this, getRows() - 1, 0);
        var lowLeft = Component.createComponent(this, 0, getColumns() - 1);
        var strategy = Strategies.getMatchingName(Objects.requireNonNull(GameWindow.getStrategySelect().getSelectedItem()).toString());
        player1 = new Player(lowLeft, "S1");
        player2 = new AIPlayer(topRight, "S2", strategy);


        //select a random player as the current player
        player1.setGame(this);
        player2.setGame(this);
        player1.getComponent().tracePath(this, player1);
        player2.getComponent().tracePath(this, player2);
        var currentPlayerName = Objects.requireNonNull(GameWindow.getPlayerSelect().getSelectedItem()).toString();
        if (currentPlayerName.equals(player1.getName()))
            currentPlayer = player1;
        else if (currentPlayerName.equals(player2.getName()))
            currentPlayer = player2;
        else throw new IllegalStateException("Invalid player name");

        currentPlayer.startPlayersTurn(this);

    }


    /**
     * Determines if there are no possible moves for a given player.
     * <p>
     * This method checks if there are any adjacent fields of the player's component that are valid moves for the player. If
     * a valid move is found, the method returns false indicating that there are possible moves for the player. If no valid
     * moves are found, the method returns true indicating that there are no possible moves for the player.
     *
     * @param player the player for whom to check for possible moves
     * @return true if there are no possible moves for the player, false otherwise
     */
    public boolean noPossibleMove(Player player) {
        for (var field : player.getComponent().adjacentFieldsOfComponent(this)) {
            if (player.validateMove(field))
                return false;
        }
        return true;
    }

    /**
     * Creates the cell panels for the board.
     *
     * @param columns the number of columns of the board
     * @param rows    the number of rows of the board
     */
    public void gameFieldCreation(int columns, int rows) {
        board = new Field[columns][rows];
        for (var column = 0; column < columns; column++) {
            for (var row = 0; row < rows; row++) {
                var field = new Field(row, column, Color.CYAN.getRGB());
                board[column][row] = field;
            }
        }
        if (Utility.getDisplayPanel() != null)
            Utility.getDisplayPanel().updateBoardSize();

    }


    /**
     * Returns an instance of the Player class representing player 1.
     *
     * @return instance of Player representing player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Sets the first player for the game.
     *
     * @param player1 the Player object representing the first player of the game
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Returns the player 2 object.
     *
     * @return the player 2 object
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Set the Player 2 object in the game.
     *
     * @param player2 The Player object representing the second player in the game.
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Switches the current player to his/her opponent
     *
     * @param player The current player to switch from.
     */
    public void switchCurrentPlayers(Player player) {
        currentPlayer = getOpponent(player);
    }


    /**
     * Retrieves the current player object.
     *
     * @return the current player object
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player object.
     *
     * @param currentPlayer the player object to be set as the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns a boolean indicating whether the game is currently running or not.
     *
     * @return true if the game is running, false otherwise.
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Sets the state of the game to running or not running.
     *
     * @param gameRunning a boolean indicating whether the game is set to running or not running.
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * Returns whether the object has been initialized or not.
     *
     * @return {@code true} if the object has been initialized; {@code false} otherwise
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Sets the value of the isInitialized flag.
     *
     * @param initialized the boolean value to be set for the isInitialized flag
     */
    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    /**
     * This method checks if the game is currently paused or not.
     *
     * @return true if the game is paused, false otherwise
     */
    public boolean isGamePaused() {
        return gamePaused;
    }

    /**
     * Sets the paused state of the game.
     *
     * @param gamePaused a boolean value indicating whether the game is paused or not
     */
    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    /**
     * Determines if the game is over.
     * <p>
     * This method checks if the game is over by tracing the path of the current player and their opponent, and then checking
     * if the board is in final configuration or if there are no possible moves left for the current player's opponent.
     * Additionally, if the count of unchanged moves is greater than or equal to four, the game is considered over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        currentPlayer.getComponent().tracePath(this, currentPlayer);
        getOpponent(currentPlayer).getComponent().tracePath(this, getOpponent(currentPlayer));
        //Check if the board is in final configuration
        if (isFinalConfiguration()) {
            return true;
        }
        if (noPossibleMove(getOpponent(currentPlayer))) {
            return true;
        }

        return unchangedMovesCount >= 4;
    }

    /**
     * Pauses the game.
     * <p>
     * If the game is already paused, this method does nothing.
     * Otherwise, it sets the gamePaused property of the game object to true,
     * indicating that the game is now paused. It also creates a popup dialog
     * with the title "Game Paused" and message "Game Paused" to notify the user
     * that the game has been paused.
     */
    public void pauseGame() {
        if (isGamePaused()) return;

        setGamePaused(true);

        PopUpCreator.createPopUp("Game Paused", "Game Paused");
    }

    /**
     * Stops the game by setting necessary flags and clearing the game instance.
     * After calling this method, the game will be completely stopped and cannot be resumed.
     * The gamePaused flag will not be affected by this method,
     * so if the game is currently paused, it will remain paused after calling stopGame().
     */
    public void stopGame() {
        setInitialized(false);
        setGameRunning(false);
    }

    /**
     * This method checks if all cells in the game board have been assigned a color.
     * If not, it returns false, otherwise true.
     *
     * @return true if all cells have been assigned a color, false otherwise.
     */
    public boolean isFinalConfiguration() {
        //iterate over all Cell panels
        for (Field[] fields : board) {
            for (var row = 0; row < board[0].length; row++) {
                var field = fields[row];
                //check if the cell is eighter the color of player1 or player2 if not return false
                if (field.getRGBColor() != player1.getColor() && field.getRGBColor() != player2.getColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a list of adjacent field panels to the given field panel.
     *
     * @param field a Field object representing the anchor field.
     * @return an ArrayList of Field objects representing adjacent fields.
     */
    public List<Field> getNeighbors(Field field) {
        var neighbors = new ArrayList<Field>();
        var up = field.getCol() - 1;
        var down = field.getCol() + 1;
        var left = field.getRow() - 1;
        var right = field.getRow() + 1;
        if (up >= 0)
            neighbors.add(board[up][field.getRow()]);
        if (down < board.length)
            neighbors.add(board[down][field.getRow()]);
        if (left >= 0)
            neighbors.add(board[field.getCol()][left]);
        if (right < board[0].length)
            neighbors.add(board[field.getCol()][right]);
        return neighbors;
    }

    /**
     * Returns the current game board.
     *
     * @return a two-dimensional array of Field objects representing the game board.
     */
    public Field[][] getBoard() {
        return board;
    }

    /**
     * This method creates a clone of the game board, including all Cell panels and their objects.
     *
     * @return a 2D array of Field objects representing the cloned game board.
     */
    public Field[][] cloneField() {
        var columns = board.length;
        var rows = board[0].length;
        //create a clone of that cellPanels and there objects
        Field[][] clone = new Field[columns][rows];
        for (var column = 0; column < columns; column++) {
            for (var row = 0; row < rows; row++) {
                var color = board[column][row].getColor();
                var field = new Field(row, column, color);
                clone[column][row] = field;
            }
        }
        return clone;
    }

    /**
     * Sets the board with the given 2D array of Field objects.
     *
     * @param board a 2D array of Field objects representing the game board.
     */
    public void setBoard(Field[][] board) {
        this.board = board;
    }

    /**
     * Prints the board with its colors.
     *
     * @param board a 2D array of Field objects representing the game board.
     * @return a String representation of the board with colors.
     */
    public static String printBoard(Field[][] board) {
        var builder = new StringBuilder();
        //print the board with its colors

        for (Field[] fields : board) {
            for (var row = 0; row < board[0].length; row++) {
                var field = fields[row];
                builder.append("Color:"+field.getRGBColor() +" Row:"+field.getRow() +" Col:"+field.getCol()).append(", ");
            }

            //remove the last comma
            builder.deleteCharAt(builder.length() - 2);

            builder.append("\n");
        }
        return builder.toString();

    }

    public static Game getInstance() {
        return instance;
    }

    public static void setInstance(Game instance) {
        Game.instance = instance;
    }

    public int getUnchangedMovesCount() {
        return unchangedMovesCount;
    }

    /**
     * Returns the number of rows in the board.
     *
     * @return an integer representing the number of rows in the board.
     */
    public int getRows() {
        return board[0].length;
    }

    /**
     * Returns the number of columns in the board.
     *
     * @return an int value representing the number of columns in the board.
     */
    public int getColumns() {
        return board.length;
    }
}

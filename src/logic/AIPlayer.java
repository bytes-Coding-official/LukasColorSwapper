//Niklas Nesseler 7367375
package logic;

import java.awt.*;
import java.util.HashMap;

public class AIPlayer extends Player implements Strategy {

    /**
     * The strategy variable represents the selected strategy to be used by the software. This variable is
     * set at initialization and remains constant during the execution of the software.
     * <p>
     * The strategy is used to define different algorithms or approaches to solving a particular problem. By
     * storing the strategy in this variable, it can be easily accessed and used throughout the software.
     * <p>
     * The strategy variable is declared as final, which means it cannot be changed once it has been set. This
     * ensures that the selected strategy remains consistent throughout the execution of the software.
     *
     * @see Strategies
     */
    private final Strategies strategy;

    /**
     * This constructor is used to create a new instance of the AIPlayer class.
     *
     * @param component the component where the game is played.
     * @param name      the name of the AIPlayer.
     * @param strategy  the strategy that the AIPlayer should use to make moves in the game.
     */
    public AIPlayer(Component component, String name, Strategies strategy) {
        super(component, name);
        this.strategy = strategy;
    }


    /**
     * This method pauses for one second before making a move in the game specified by the parameters.
     *
     * @param row    an integer representing the row of the cell where the move will be made.
     * @param column an integer representing the column of the cell where the move will be made.
     */
    @Override
    public void makeMove(int row, int column) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        super.makeMove(row, column);
    }

    /**
     * Creates a clone of a 2D array of CellPanels and updates cells in the clone to simulate a fake move in a game.
     *
     * @param board  A 2D array of CellPanels representing the game board.
     * @param field  A CellPanel object representing the cell that was selected for the fake move.
     * @param player The Player object representing the player making the fake move.
     * @return A new 2D array of CellPanels where the cells that were part of the fake move have been updated with the color
     * of the selected cell.
     */
    public static Field[][] performFakeMove(Field[][] board, Field field, Player player) {
        var boardCopy = board.clone();
        var color = field.getColor();
        for (var fieldElement : player.getComponent().getFields()) {
            boardCopy[fieldElement.getColumn()][fieldElement.getRow()].setColor(color);
        }
        return boardCopy;
    }

    /**
     * This method performs a move in a game using different strategies based on the current game state and the available options.
     * The strategy used is determined by the value of the 'strategy' field of the Game object.
     * If the strategy is 'GREEDY', the 'greedyStrategy' method is called to determine the best move.
     * If the strategy is 'BLOCKING', the 'blockingStrategy' method is called to determine the best move.
     * If the strategy is 'STAGNATION', the 'stagnationStrategy' method is called to determine the best move.
     * The chosen move is then used to perform a game move by calling the 'makeMove' method of the Game object.
     */
    public void perform(Game game) {
        var positions = switch (strategy) {
            case GREEDY -> greedyStrategy(game);
            case STAGNATION -> stagnationStrategy(game);
            case BLOCKING -> blockingStrategy(game);
        };
        makeMove(positions[0], positions[1]);
    }

    /**
     * Calculates the amount of times a given color appears in a 2D array of CellPanels.
     *
     * @param board A 2D array of CellPanels representing the game board.
     * @param color The Color object to be counted in the game board.
     * @return An integer representing the number of times the given color appears in the game board.
     */
    private int calculateColorAmount(Field[][] board, Color color) {
        var amount = 0;
        for (Field[] fields : board) {
            for (Field field : fields) {
                if (field.getRGBColor().equals(color)) {
                    amount++;
                }
            }
        }
        return amount;
    }

    /**
     * This method implements a greedy strategy in order to determine the best move to play in a game.
     *
     * @return an int array of size 2 containing the row and column values of the cell that has been determined to be the best move.
     */
    @Override
    public int[] greedyStrategy(Game game) {
        HashMap<Field, Integer> map = calculateMoves(game, this);
        //get the field with the highest amount
        var max = map.values().stream().max(Integer::compareTo).orElseThrow();
        var field = map.entrySet().stream().filter(entry -> entry.getValue().equals(max)).findFirst().orElseThrow().getKey();
        return new int[]{field.getRow(), field.getColumn()};
    }

    /**
     * Defines the blocking strategy of an entity implementing the BlockingStrategy interface.
     *
     * @return An integer array representing the blocking strategy.
     */
    @Override
    public int[] blockingStrategy(Game game) {
        // get the opponent's best move
        return calculateOpponentBestMove(game);
    }


    /**
     * Implements a stagnation strategy for AI players in a game. The method calculates the moves available to the
     * AI player and chooses the move that leads to the lowest number of available moves for the opponent player.
     *
     * @return An integer array containing the row and column index of the chosen move.
     */
    @Override
    public int[] stagnationStrategy(Game game) {
        HashMap<Field, Integer> map = calculateMoves(game, this);
        //get the field with the highest amount
        var min = map.values().stream().min(Integer::compareTo).orElseThrow();
        var field = map.entrySet().stream().filter(entry -> entry.getValue().equals(min)).findFirst().orElseThrow().getKey();
        return new int[]{field.getRow(), field.getColumn()};

    }


    /**
     * Calculates the possible moves for the current player by simulating each adjacent CellPanel being clicked.
     *
     * @param player The player whose moves are to be calculated.
     * @return A HashMap containing each adjacent CellPanel as the key and the number of times the color of the chosen component appears
     * on the game board after simulating the move as the value.
     */
    private HashMap<Field, Integer> calculateMoves(Game game, Player player) {
        var boardCopy = game.cloneField();
        var map = new HashMap<Field, Integer>();
        for (var adjacentField : player.getComponent().adjacentFieldsOfComponent(game)) {
            var field = boardCopy[adjacentField.getColumn()][adjacentField.getRow()];
            if (field.getColorRGB().equals(getColor()) || field.getRGBColor().equals(game.getOpponent(this).getColor())) continue;
            var fakeBoard = performFakeMove(boardCopy, field, player);
            var amount = calculateColorAmount(fakeBoard, field.getColorRGB());
            map.put(field, amount);
        }
        return map;
    }

    /**
     * Calculates the best move for the opponent player.
     *
     * @param game The current game instance.
     * @return An array representing the row and column of the best move for the opponent player.
     */
    private int[] calculateOpponentBestMove(Game game) {
        //calculate opponent greedy move
        var opponent = game.getOpponent(this);
        HashMap<Field, Integer> map = calculateMoves(game, opponent);
        //get the field with the highest amount
        var max = map.values().stream().max(Integer::compareTo).orElseThrow();
        var field = map.entrySet().stream().filter(entry -> entry.getValue().equals(max)).findFirst().orElseThrow().getKey();

        var adjacentFields = getComponent().adjacentFieldsOfComponent(game);

        for (var fields : adjacentFields) {
            if (field.getColorRGB().equals(fields.getColorRGB())) {
                return new int[]{fields.getRow(), fields.getColumn()};
            }
        }
        return greedyStrategy(game);
    }

    public Strategies getStrategy() {
        return strategy;
    }
}

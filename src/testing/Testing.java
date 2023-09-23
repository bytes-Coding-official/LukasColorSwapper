
package testing;

import logic.*;
import start.Start;

import java.util.ArrayList;
import java.util.List;

public class Testing {


    public static void main(String[] args) {
        
         Field field00t3 = new Field(0, 0, 2);
        Field field01t3 = new Field(0, 1, 4);
        Field field02t3 = new Field(0, 2, 5);
        Field field03t3 = new Field(0, 3, 6);
        Field field04t3 = new Field(0, 4, 1);
        Field[] row0 = new Field[]{field00t3, field01t3, field02t3, field03t3, field04t3};

        Field field10t3 = new Field(1, 0, 1);
        Field field11t3 = new Field(1, 1, 3);
        Field field12t3 = new Field(1, 2, 4);
        Field field13t3 = new Field(1, 3, 5);
        Field field14t3 = new Field(1, 4, 2);
        Field[] row1 = new Field[]{field10t3, field11t3, field12t3, field13t3, field14t3};

        Field field20t3 = new Field(2, 0, 4);
        Field field21t3 = new Field(2, 1, 2);
        Field field22t3 = new Field(2, 2, 1);
        Field field23t3 = new Field(2, 3, 6);
        Field field24t3 = new Field(2, 4, 1);
        Field[] row2 = new Field[]{field20t3, field21t3, field22t3, field23t3, field24t3};

        Field field30t3 = new Field(3, 0, 3);
        Field field31t3 = new Field(3, 1, 5);
        Field field32t3 = new Field(3, 2, 2);
        Field field33t3 = new Field(3, 3, 1);
        Field field34t3 = new Field(3, 4, 4);
        Field[] row3 = new Field[]{field30t3, field31t3, field32t3, field33t3, field34t3};

        Field field40t3 = new Field(4, 0, 1);
        Field field41t3 = new Field(4, 1, 2);
        Field field42t3 = new Field(4, 2, 1);
        Field field43t3 = new Field(4, 3, 3);
        Field field44t3 = new Field(4, 4, 2);
        Field[] row4 = new Field[]{field40t3, field41t3, field42t3, field43t3, field44t3};

        Field field50t3 = new Field(5, 0, 2);
        Field field51t3 = new Field(5, 1, 3);
        Field field52t3 = new Field(5, 2, 2);
        Field field53t3 = new Field(5, 3, 4);
        Field field54t3 = new Field(5, 4, 1);
        Field[] row5 = new Field[]{field50t3, field51t3, field52t3, field53t3, field54t3};
        
     

        var board3 = new Field[][]{row0,row1,row2,row3,row4,row5};

        Testing testingObject3 = new Testing(board3);
        System.out.println(testingObject3.isStartklar());

    }

    /*
     *
     * initialisierung von Field bei Tests:
     *
     * Field(row, column, color) -> neu: Field(column, row, color)
     *
     * */

    /**
     * Represents a game board consisting of fields.
     */
    private Field[][] board;

    /**
     * Initializes a new instance of the Testing class with the specified board.
     *
     * @param board the two-dimensional array representing the game board
     */
    public Testing(Field[][] board) {
        this.board = board;
    }

    /**
     * Checks if the game board is startklar.
     * <p>
     * A board is startklar when the field at the top right and bottom left corners
     * are in a different color than each other, and no adjacent fields have the same color.
     *
     * @return {@code true} if the board is startklar, {@code false} otherwise.
     */
    public boolean isStartklar() {
        var game = new Game();
        game.setBoard(board);
        //check if the board is startklar

        //a board ist startklar when the field top right and bottomLeft are in a different color than each other
        //the fieldColor does not have any adjacent fields with the same color

        //check if the field top right and bottomLeft are in a different color than each other
        if (board[0][board[0].length - 1].getColor() == board[board.length - 1][0].getColor()) {
            return false;
        }
        //check if the fieldColor does not have any adjacent fields with the same color
        for (Field[] fields : board) {
            for (int j = 0; j < board.length - 1; j++) {
            //KORREKT: for (int j = 0; j < board[0].length - 1; j++) {
                var sourroundingColors = fields[j].getAdjacentFields(game, fields[j]);
                for (var color : sourroundingColors) {
                    if (color.getColor() == fields[j].getColor()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the current configuration of the game is the final configuration.
     *
     * @return {@code true} if the current configuration is the final configuration, {@code false} otherwise.
     */
    public boolean isEndConfig() {
        var game = new Game();
        game.setBoard(board);
        game.setPlayer1(new Player(new Component().addField(board[0][board[0].length - 1]), "S1"));
        game.setPlayer2(new Player(new Component().addField(board[board.length - 1][0]), "S2"));
        game.getPlayer1().setGame(game);
        game.getPlayer2().setGame(game);

        return game.isFinalConfiguration();
    }


    /**
     * Executes test strategy 01.
     *
     * @return The color of the board field determined by the strategy.
     */
    public int testStrategy01() {
        var game = new Game();
        game.setBoard(board);
        game.setPlayer1(new Player(new Component().addField(board[0][board[0].length - 1]), "S1"));
        game.setPlayer2(new AIPlayer(new Component().addField(board[board.length - 1][0]), "S2", Strategies.STAGNATION));
        game.getPlayer1().setGame(game);
        game.getPlayer2().setGame(game);
        game.getPlayer1().getComponent().tracePath(game, game.getPlayer1());
        game.getPlayer2().getComponent().tracePath(game, game.getPlayer2());
        var field = ((AIPlayer) game.getPlayer2()).stagnationStrategy(game);
        var boardField = board[field[1]][field[0]];
        return boardField.getColor();
    }


    /**
     * Executes strategy 02 of the game.
     * <p>
     * This method creates a new game instance and sets up the board and players.
     * It then traces the paths for both players' components and executes the greedy strategy for player 2.
     * Finally, it returns the color of the board field determined by the strategy.
     *
     * @return The color of the board field determined by the strategy.
     */
    public int testStrategy02() {
        var game = new Game();
        game.setBoard(board);
        game.setPlayer1(new Player(new Component().addField(board[0][board[0].length - 1]), "S1"));
        game.setPlayer2(new AIPlayer(new Component().addField(board[board.length - 1][0]), "S2", Strategies.GREEDY));
        game.getPlayer1().setGame(game);
        game.getPlayer2().setGame(game);
        game.getPlayer1().getComponent().tracePath(game, game.getPlayer1());
        game.getPlayer2().getComponent().tracePath(game, game.getPlayer2());
        var field = ((AIPlayer) game.getPlayer2()).greedyStrategy(game);
        var boardField = board[field[1]][field[0]];
        return boardField.getColor();
    }

    /**
     * Executes strategy 03 for the game.
     * This method sets up the game, players, and their components.
     * It then traces the path for both players' components.
     * Finally, it applies the blocking strategy for player 2.
     *
     * @return The color of the board field after executing the strategy.
     */
    public int testStrategy03() {
        var game = new Game();
        game.setBoard(board);
        game.setPlayer1(new Player(new Component().addField(board[0][board[0].length - 1]), "S1"));
        game.setPlayer2(new AIPlayer(new Component().addField(board[board.length - 1][0]), "S2", Strategies.BLOCKING));
        game.getPlayer1().setGame(game);
        game.getPlayer2().setGame(game);
        game.getPlayer1().getComponent().tracePath(game, game.getPlayer1());
        game.getPlayer2().getComponent().tracePath(game, game.getPlayer2());
        var field = ((AIPlayer) game.getPlayer2()).blockingStrategy(game);
        var boardField = board[field[1]][field[0]];
        return boardField.getColor();
    }


    /**
     * Transfers the current board state to another board with specified number of moves.
     *
     * @param otherBoard The board to which the current state will be transferred.
     * @param moves      The number of moves to be performed on the transferred board.
     * @return True if the transfer and calculation is successful, false otherwise.
     */
    public boolean toBoard(Field[][] otherBoard, int moves) {
        return calculateAllMoves(board, otherBoard, moves, null);
    }

    /**
     * Calculates all possible moves within the given number of moves.
     *
     * @param currentBoard  The current board state represented as a 2D array of {@link Field} objects.
     * @param otherBoard    The target board state represented as a 2D array of {@link Field} objects to compare with.
     * @param moves         The number of moves to consider.
     * @param currentPlayer The current player making the moves.
     * @return {@code true} if there exists a sequence of moves that can transform the current board state to the target board state within the given number of moves, {@code false} otherwise.
     */
    private boolean calculateAllMoves(Field[][] currentBoard, Field[][] otherBoard, int moves, Player currentPlayer) {
        var game = new Game();
        game.setBoard(currentBoard);
        var topRight = game.getBoard()[0][new Game().getRows() - 1];
        var lowLeft = game.getBoard()[new Game().getColumns() - 1][0];
        game.setPlayer1(new Player(new Component().addField(lowLeft), "S1"));
        game.setPlayer2(new Player(new Component().addField(topRight), "S2"));
        game.getPlayer1().setGame(game);
        game.getPlayer2().setGame(game);
        game.getPlayer1().getComponent().tracePath(game, game.getPlayer1());
        game.getPlayer2().getComponent().tracePath(game, game.getPlayer2());
        game.setCurrentPlayer(currentPlayer == null ? game.getPlayer1() : currentPlayer.getName().equals(game.getPlayer1().getName()) ? game.getPlayer1() : game.getPlayer2());
        currentPlayer = game.getCurrentPlayer();
        if (boardsAreEqual(game.getBoard(), otherBoard)) {
            return true;
        }
        if (moves == 0) {
            return false;
        }

        //calulcate all possible moves within the given moves
        var fakeGameBoard = game.cloneField();
        for (var field : currentPlayer.getComponent().adjacentFieldsOfComponent(game)) {
            //colores the field thats all
            var fakeBoard = AIPlayer.performFakeMove(fakeGameBoard, field, currentPlayer);
            currentPlayer.setColor(field.getRGBColor());
            if (calculateAllMoves(fakeBoard, otherBoard, moves - 1, game.getOpponent(game.getCurrentPlayer())))
                return true;
        }
        return false;
    }


    /**
     * Checks if two given boards are equal.
     *
     * @param board1 the first board to compare
     * @param board2 the second board to compare
     * @return true if the boards are equal, false otherwise
     */
    public boolean boardsAreEqual(Field[][] board1, Field[][] board2) {
        //Check if these boards are identical to each other
        if (board1.length != board2.length || board1[0].length != board2[0].length) {
            return false;
        }
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board2[0].length; j++) {
                if (board1[i][j].getColor() != board2[i][j].getColor()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Calculates the minimum number of moves needed to reach the destination point from the given source point.
     *
     * @param x The x-coordinate of the source point.
     * @param y The y-coordinate of the source point.
     * @return The minimum number of moves, or -1 if it is not possible to reach the destination point.
     */
    public int minMoves(int x, int y) {
        return minMovesCalculator(board, x, y, 0, new ArrayList<>()).stream().min(Integer::compareTo).orElse(-1);
    }

    /**
     * Calculates the minimum moves required for a player to reach a specified field on the game board.
     *
     * @param currentBoard the current game board represented as a 2D array of Field objects
     * @param row          the target row of the field
     * @param column       the target column of the field
     * @param counter      the current move counter
     * @param moves        the list of minimum moves calculated so far
     * @return the updated list of minimum moves after evaluating the possible moves
     */
    private List<Integer> minMovesCalculator(Field[][] currentBoard, int row, int column, int counter, List<Integer> moves) {
        var game = new Game();
        game.setBoard(currentBoard);
        var lowLeft = game.getBoard()[game.getColumns() - 1][0];
        game.setPlayer1(new Player(new Component().addField(lowLeft), "S1"));
        game.getPlayer1().setGame(game);
        game.setPlayer2(game.getPlayer1());
        game.getPlayer2().setGame(game);
        game.setCurrentPlayer(game.getPlayer1());
        game.getPlayer1().getComponent().tracePath(game, game.getPlayer1());
        game.getPlayer2().getComponent().tracePath(game, game.getPlayer2());
        counter++;
        //calulcate all possible moves within the given moves
        var fakeGameBoard = game.cloneField();
        var adjFields = game.getCurrentPlayer().getComponent().adjacentFieldsOfComponent(game);

        for (var field : game.getCurrentPlayer().getComponent().adjacentFieldsOfComponent(game)) {
            //check if a field with the x and y are part of the component of the player
            var result = game.getCurrentPlayer().getComponent().getFields().stream().filter(Point -> Point.getRow() == row && Point.getCol() == column);
            if (result.findAny().isPresent() || field.getCol() == column && field.getRow() == row) {
                moves.add(counter);
                return moves;
            }
            var fakeBoard = AIPlayer.performFakeMove(fakeGameBoard, field, game.getCurrentPlayer());
            game.getCurrentPlayer().setColor(field.getRGBColor());
            minMovesCalculator(fakeBoard, row, column, counter, moves);
        }
        return moves;
    }


    /**
     * Finds the minimum number of moves required to achieve a certain condition.
     *
     * @param currentBoard The current state of the game board.
     * @param counter      The current move counter.
     * @param moves        The list of moves taken so far.
     * @return The updated list of moves after finding the minimum number of moves.
     */
    private List<Integer> minMovesOver(Field[][] currentBoard, int counter, List<Integer> moves) {
        var game = new Game();
        System.out.println("counter"+counter);
        game.setBoard(currentBoard);
        var lowLeft = game.getBoard()[game.getColumns() - 1][0];
        game.setPlayer1(new Player(new Component().addField(lowLeft), "S1"));
        game.getPlayer1().setGame(game);
        game.setPlayer2(game.getPlayer1());
        game.getPlayer2().setGame(game);
        game.setCurrentPlayer(game.getPlayer1());
        game.getPlayer1().getComponent().tracePath(game, game.getPlayer1());
        game.getPlayer2().getComponent().tracePath(game, game.getPlayer2());
        counter++;
        //calulcate all possible moves within the given moves
        var fakeGameBoard = game.cloneField();

        for (var field : game.getCurrentPlayer().getComponent().adjacentFieldsOfComponent(game)) {
            //check if a field with the x and y are part of the component of the player
            if (game.isGameOver()) {
                moves.add(counter);
                return moves;
            }
            var fakeBoard = AIPlayer.performFakeMove(fakeGameBoard, field, game.getCurrentPlayer());
            game.getCurrentPlayer().setColor(field.getRGBColor());
            minMovesOver(fakeBoard, counter, moves);
        }
        return moves;
    }

    /**
     * Calculates the minimum number of moves required to complete the full board.
     *
     * @return The minimum number of moves required to complete the full board, or -1 if no moves are possible.
     */
    public int minMovesFull() {
        return minMovesOver(board, 0, new ArrayList<>()).stream().min(Integer::compareTo).orElse(-1);
    }

    /**
     * Retrieves the game board.
     *
     * @return The game board as a 2D array of Field objects.
     */
    public Field[][] getBoard() {
        return board;
    }

    /**
     * Sets the specified board configuration for the game.
     *
     * @param board the 2D array representing the game board
     */
    public void setBoard(Field[][] board) {
        this.board = board;
    }
}

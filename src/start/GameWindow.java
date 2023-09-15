//Niklas Nesseler 7367375
package start;


import logic.Game;
import logic.Strategies;
import logic.Utility;

import javax.swing.*;
import java.awt.*;

public class GameWindow {

    /**
     * Represents a button used to start or stop an operation.
     */
    private static JButton startStopButton;
    /**
     * Represents the play/pause button of the application.
     * This button toggles between play and pause functionality.
     * The button is static because there should be only one instance of the button.
     */
    private static JButton playPauseButton;
    /**
     * A private static JComboBox that represents the player selection drop-down box.
     *
     * <p>The JComboBox contains a list of String values, where each item corresponds to a player.
     * The user can select a player from this drop-down list to perform certain operations.</p>
     *
     * @see JComboBox
     */
    private static JLabel player;
    private static JComboBox<String> playerSelect;
    /**
     * A private static JSpinner representing a color selection component.
     *
     * This variable is used to allow the user to select a color using a JSpinner widget.
     * It provides a convenient way for users to choose colors by selecting values in the spinner.
     *
     * The colorSpinner is a private static variable, which means it is accessible only within the class it is defined in
     * and can be shared among different instances of that class. Being static, it is accessible without creating an instance
     * of the class and is shared by all instances of that class.
     *
     * It is advisable to use this variable in conjunction with a ChangeListener to listen to changes in the selected color
     * and update the application accordingly.
     *
     * Example usage:
     *
     * // Create a JSpinner for color selection
     * JSpinner colorSpinner = new JSpinner();
     *
     * // Set up a SpinnerModel to define color values
     * SpinnerModel colorModel = new SpinnerListModel(Arrays.asList("Red", "Green", "Blue"));
     * colorSpinner.setModel(colorModel);
     *
     * // Add a ChangeListener to listen for color changes
     * colorSpinner.addChangeListener(e -> {
     *     // Get the selected color
     *     String selectedColor = (String) colorSpinner.getValue();
     *
     *     // Update application based on selected color
     *     updateDisplayColor(selectedColor);
     * });
     *
     * // Add the colorSpinner to a container or GUI component
     * frame.add(colorSpinner);
     *
     * Note: The above example is for illustrative purposes only and may not compile or work as is. It is meant to demonstrate
     * the possible usage of the colorSpinner variable. Actual implementation may vary depending on the requirements and
     * context of the application.
     */
    private static JLabel colors;
    private static JSpinner colorSpinner;
    /**
     * This variable represents a Java Swing component that allows the user to select a value from a range of numeric values using up/down arrows or by typing directly into the component.
     * <p>
     * The JSpinner is set as static and is only accessible within the class it is declared in.
     * <p>
     * This specific JSpinner is used to allow the user to select a value that represents a row in a table or grid.
     * <p>
     * Note: A SpinnerModel needs to be set to this JSpinner to specify the range of values and the behavior when the user interacts with the component.
     */
    private static JLabel rows;
    private static JSpinner rowSpinner;
    /**
     * Private static JSpinner variable columnSpinner holds an instance of JSpinner,
     * which displays the value of a specified column in a table.
     * The spinner allows the user to select a valid column number to display.
     *
     * This variable is used in conjunction with a table to allow the user to select
     * the column they would like to display a value for.
     *
     * Note: This variable is only used internally and is not intended to be accessed
     * or modified externally.
     */
    private static JLabel cols;
    private static JSpinner columnSpinner;
    /**
     * Represents the previous column in a table or grid as an integer variable.
     * This variable is used to keep track of the column that was accessed or modified previously,
     * allowing the program to make decisions based on the previous column.
     */
    private static int colPrev = 0;
    /**
     * Represents the previous column index for a specific operation.
     * This variable is static and private, meaning it can only be accessed within this class.
     * The default value is 0, which is used as the initial value for the previous column
     * when no other value has been assigned yet.
     */
    private static int coluPrev = 0;
    /**
     * Represents the row index of the previous row in a matrix.
     *
     * This variable is declared as private static which means it can be accessed by any method within the class.
     * Its initial value is 0 which implies that there is no previous row at the beginning of the matrix.
     */
    private static int rowPrev = 0;
    /**
     * A private static JComboBox that allows the user to choose a strategy.
     *
     * @return The JComboBox that allows the user to choose a strategy.
     */
    private static JLabel strategy;
    private static JComboBox<String> strategySelect;
    /**
     * JLabel used to display the timer value.
     */
    private static JLabel timerLabel;

    /**
     * This variable represents the current points of player 1 in the game.
     * It is a private static JLabel object and can be accessed using its getter method.
     * It is used to display the points of player 1 on the GUI of the game.
     */
    private static JLabel player1Points;
    /**
     * Represents a JLabel component that displays the number of points scored by player 2.
     *
     * <p>
     * This variable is static and can be accessed from any instance of the class in which it is declared.
     * The JLabel can be updated to change the displayed value using the appropriate class methods.
     * </p>
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * player2Points.setText(String.valueOf(score));
     * }</pre>
     * </p>
     */
    private static JLabel player2Points;

    /**
     * Represents the game status label on the user interface.
     * This variable holds a reference to a JLabel object which is used to display the current status of the game to the user.
     * This label is used to display messages like "Player won", "Game Tied", "It's player 1's turn".
     *
     * As it is a static variable, it is shared among all instances of the class in which it is declared.
     *
     * The variable is private to provide encapsulation of the game status and it is only accessible within the same class.
     */
    private static JLabel gameStatus;

    /**
     * This method creates and shows the GUI for the game window. It sets the size and minimum size for the window,
     * creates a menu panel, adds various buttons and drop down menus to it, creates a display panel and color panel,
     * and adds them to different parts of the window. It also creates a timer and sets up key mappings to listen for
     * input from the user.
     * <p>
     * This method does not return anything.
     */
    public static void createAndShowGUI() {
        var frame = new JFrame("Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set size of the Frame
        frame.setSize(600, 600);
        for (int i = 1; i <= 9; i++) {
            String key = String.valueOf(i);
            String numpadKey = "NUMPAD" + i;
            frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
            frame.getRootPane().getActionMap().put(key, new KeyboardControlListener());

            frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(numpadKey), numpadKey);
            frame.getRootPane().getActionMap().put(numpadKey, new KeyboardControlListener());
        }

        //Set Minimum Size
        frame.setMinimumSize(new Dimension(600, 600));

        //Position Frame in the center of the screen
        frame.setLocationRelativeTo(null);

        //Create Menu Panel
        var menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(200, 600));
        menuPanel.setBackground(Color.lightGray);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        //Add Help Button to menuPanel
        var helpButton = new JButton("Help");
        String message = "<html><body><b>THIS IS HOW THE GAME IS PLAYED</b><br>" +
                "You start in the bottom left corner.<br>" +
                "You choose a color that is not yours nor your opponents using your keyboard or by clicking the color on the board.<br>" +
                "Try to expand your field as much as you can.<br>" +
                "You win, if your field is bigger than the AI's.</body></html>";
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, message, "Instructions", JOptionPane.INFORMATION_MESSAGE));
        menuPanel.add(helpButton);

        //Add start.Start/Stop Button to MenuPanel
        startStopButton = new JButton("Start");
        startStopButton.addActionListener(new StartStopAction());
        menuPanel.add(startStopButton);


        //Add Play/Pause button to menuPanel
        playPauseButton = new JButton("Play");
        playPauseButton.addActionListener(new PlayPauseAction());
        menuPanel.add(playPauseButton);

        //Add logic.Player select dropdown to menuPanel
        player = new JLabel("Who starts?");
        var players = new String[]{"S1", "S2"};
        playerSelect = new JComboBox<>(players);
        menuPanel.add(player);
        menuPanel.add(playerSelect);

        //Add color Spinner to MenuPanel
        colors = new JLabel("How many colors?");
        var colorModel = new SpinnerNumberModel(5, 4, 9, 1);
        colorSpinner = new JSpinner(colorModel);
        ((JSpinner.DefaultEditor) colorSpinner.getEditor()).getTextField().setEditable(false);
        menuPanel.add(colors);
        menuPanel.add(colorSpinner);
        colorSpinner.addChangeListener(e -> {
            if (Game.getGame().isGameRunning()) {
                colorSpinner.setValue(colPrev);
                return;
            }
            var count = (Integer) colorSpinner.getValue();
            Utility.updateSelectedColors(count);
            Utility.getColorPanel().setColorCount((Integer) colorSpinner.getValue());
            Utility.getColorPanel().refreshColors();
            colPrev = count;

        });
        Utility.updateSelectedColors((Integer) colorModel.getValue());
        //Add row Spinner to MenuPanel
        rows = new JLabel("How many rows?");
        var rowModel = new SpinnerNumberModel(6, 3, 10, 1);
        rowSpinner = new JSpinner(rowModel);
        ((JSpinner.DefaultEditor) rowSpinner.getEditor()).getTextField().setEditable(false);
        menuPanel.add(rows);
        menuPanel.add(rowSpinner);

        rowSpinner.addChangeListener(e -> {
            if (Game.getGame().isGameRunning()) {
                rowSpinner.setValue(rowPrev);
                return;
            }
            Utility.getDisplayPanel().setRows((Integer) rowSpinner.getValue());
            rowPrev = (Integer) rowSpinner.getValue();
        });

        //Add column Spinner to MenuPanel
        cols = new JLabel("How many columns?");
        var columnModel = new SpinnerNumberModel(6, 3, 10, 1);
        columnSpinner = new JSpinner(columnModel);
        ((JSpinner.DefaultEditor) columnSpinner.getEditor()).getTextField().setEditable(false);
        menuPanel.add(cols);
        menuPanel.add(columnSpinner);

        columnSpinner.addChangeListener(e -> {
            if (Game.getGame().isGameRunning()) {
                columnSpinner.setValue(coluPrev);
                return;
            }
            Utility.getDisplayPanel().setColumns((Integer) columnSpinner.getValue());
            coluPrev = (Integer) columnSpinner.getValue();
        });

        /**
         * Represents a string variable named "strategy" that specifies the strategy being used in the software.
         * This variable is static, meaning it is a class-level variable and can be accessed without creating an instance of the class.
         * Its visibility is private, so it can only be accessed within the class in which it is defined.
         * Its data type is String, meaning it can hold alphanumeric characters and is immutable.
         * Its initial value is null, so it must be assigned a value before it can be used.
         * <p>
         * Example:
         * strategy = "Aggressive";
         */
        strategy = new JLabel("Which strategy?");
        var strategies = new String[]{Strategies.STAGNATION.getName(), Strategies.GREEDY.getName(), Strategies.BLOCKING.getName()};
        strategySelect = new JComboBox<>(strategies);
        menuPanel.add(strategy);
        menuPanel.add(strategySelect);
        //Add Timer Label to menuPanel
        timerLabel = new JLabel("00:00");
        player2Points = new JLabel("Player2: 0");
        player1Points = new JLabel("Player1: 0");
        menuPanel.add(timerLabel);
        menuPanel.add(player1Points);
        menuPanel.add(player2Points);

        var rows = (Integer) rowSpinner.getValue();
        var columns = (Integer) columnSpinner.getValue();
        var displayPanel = new DisplayPanel(rows, columns);
        //Create display Panel
        Utility.setDisplayPanel(displayPanel);
        displayPanel.setLayout(new GridLayout(columns, rows));
        displayPanel.setRows(rows);
        displayPanel.setColumns(columns);
        Utility.getDisplayPanel().setBackground(Color.LIGHT_GRAY);
        Utility.setColorPanel(new ColorPanel((Integer) colorSpinner.getValue()));


        gameStatus = new JLabel(" ");
        //Add Panels to Frame
        frame.getContentPane().add(BorderLayout.EAST, menuPanel);
        frame.getContentPane().add(BorderLayout.CENTER, Utility.getDisplayPanel());
        frame.getContentPane().add(Utility.getColorPanel(), BorderLayout.SOUTH);
        frame.getContentPane().add(gameStatus, BorderLayout.NORTH);

        //Display the Window
        frame.setVisible(true);

        //Create Timer
        Utility.setTimer(new Timer(1000, e -> {
            Utility.setTimerSeconds(Utility.getTimerSeconds() + 1);

            timerLabel.setText(String.format("%02d:%02d", Utility.getTimerSeconds() / 60, Utility.getTimerSeconds() % 60));
        }));
    }



    /**
     * This method returns the start/stop button that was created in the {@link #createAndShowGUI()} method.
     * This button can be used to start or stop the game.
     *
     * @return the start/stop button
     */
    public static JButton getStartStopButton() {
        return startStopButton;
    }

    /**
     * This method returns the play/pause button that is present on the menu panel of the game window.
     * This button can be used to start or pause the game.
     *
     * @return the play/pause button present on the menu panel of the game window.
     */
    public static JButton getPlayPauseButton() {
        return playPauseButton;
    }


    /**
     * This method returns the player select drop down menu used in the game window. It allows the user to choose
     * which player they want to be represented as in the game.
     *
     * @return the player select drop down menu used in the game window.
     */
    public static JComboBox<String> getPlayerSelect() {
        return playerSelect;
    }

    /**
     * This method returns the color spinner used in the menu panel. It allows the user to select the number of colors
     * to be used in the game, and updates the color panel accordingly when the user changes the value. The minimum and
     * maximum values for the spinner are set to 4 and 9 respectively, with a step size of 1. A change listener is also
     * added to the spinner to update the color panel and prevent changes during game play.
     *
     * @return the color spinner used in the game window's menu panel.
     */
    public static JSpinner getColorSpinner() {
        return colorSpinner;
    }

    /**
     * This method returns the JSpinner instance used for selecting the number of rows in the game board.
     *
     * @return the JSpinner instance used for selecting the number of rows in the game board
     */
    public static JSpinner getRowSpinner() {
        return rowSpinner;
    }

    /**
     * Returns the JSpinner instance used for selecting the number of columns in the game board.
     *
     * @return the JSpinner instance used for selecting the number of columns in the game board
     */
    public static JSpinner getColumnSpinner() {
        return columnSpinner;
    }

    /**
     * Returns the JComboBox object used for selecting the strategy in the game window.
     *
     * @return the strategySelect JComboBox object
     */
    public static JComboBox<String> getStrategySelect() {
        return strategySelect;
    }

    /**
     * This method returns the JLabel object for the game's timer. The timer label is used to display the current time elapsed during the game.
     *
     * @return JLabel object for the game's timer.
     */
    public static JLabel getTimerLabel() {
        return timerLabel;
    }


    /**
     * This method returns the JLabel object used to display the current game status. The game status JLabel shows
     * information about the current state of the game, such as whether the game is in progress, paused, or over.
     *
     * @return the game status JLabel
     */
    public static JLabel getGameStatus() {
        return gameStatus;
    }

    /**
     * This method returns the JLabel used to display the points of player 1.
     *
     * @return the player 1 points JLabel
     */
    public static JLabel getPlayer1Points() {
        return player1Points;
    }

    /**
     * This method returns the JLabel used to display the current game status.
     *
     * @return the game status JLabel
     */
    public static void setPlayer1Points(JLabel player1Points) {
        GameWindow.player1Points = player1Points;
    }

    public static void setStartStopButton(JButton startStopButton) {
        GameWindow.startStopButton = startStopButton;
    }

    public static void setPlayPauseButton(JButton playPauseButton) {
        GameWindow.playPauseButton = playPauseButton;
    }

    public static JLabel getPlayer() {
        return player;
    }

    public static void setPlayer(JLabel player) {
        GameWindow.player = player;
    }

    public static void setPlayerSelect(JComboBox<String> playerSelect) {
        GameWindow.playerSelect = playerSelect;
    }

    public static JLabel getColors() {
        return colors;
    }

    public static void setColors(JLabel colors) {
        GameWindow.colors = colors;
    }

    public static void setColorSpinner(JSpinner colorSpinner) {
        GameWindow.colorSpinner = colorSpinner;
    }

    public static JLabel getRows() {
        return rows;
    }

    public static void setRows(JLabel rows) {
        GameWindow.rows = rows;
    }

    public static void setRowSpinner(JSpinner rowSpinner) {
        GameWindow.rowSpinner = rowSpinner;
    }

    public static JLabel getCols() {
        return cols;
    }

    public static void setCols(JLabel cols) {
        GameWindow.cols = cols;
    }

    public static void setColumnSpinner(JSpinner columnSpinner) {
        GameWindow.columnSpinner = columnSpinner;
    }

    public static int getColPrev() {
        return colPrev;
    }

    public static void setColPrev(int colPrev) {
        GameWindow.colPrev = colPrev;
    }

    public static int getColuPrev() {
        return coluPrev;
    }

    public static void setColuPrev(int coluPrev) {
        GameWindow.coluPrev = coluPrev;
    }

    public static int getRowPrev() {
        return rowPrev;
    }

    public static void setRowPrev(int rowPrev) {
        GameWindow.rowPrev = rowPrev;
    }

    public static JLabel getStrategy() {
        return strategy;
    }

    public static void setStrategy(JLabel strategy) {
        GameWindow.strategy = strategy;
    }

    public static void setStrategySelect(JComboBox<String> strategySelect) {
        GameWindow.strategySelect = strategySelect;
    }

    public static void setTimerLabel(JLabel timerLabel) {
        GameWindow.timerLabel = timerLabel;
    }

    public static void setPlayer2Points(JLabel player2Points) {
        GameWindow.player2Points = player2Points;
    }

    public static void setGameStatus(JLabel gameStatus) {
        GameWindow.gameStatus = gameStatus;
    }

    /**
     * This method retrieves the value of player2Points JLabel. This is used to get the points
     * displayed for player 2 during the game.
     *
     * @return the JLabel representing the points of player 2.
     */
    public static JLabel getPlayer2Points() {
        return player2Points;
    }
}

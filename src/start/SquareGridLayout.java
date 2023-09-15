//Niklas Nesseler 7367375
package start;

import java.awt.*;

class SquareGridLayout implements LayoutManager {
    /**
     * The number of rows in a data structure.
     */
    private final int rows;
    /**
     * Represents the number of columns in a grid or matrix.
     * This variable is private, meaning it can only be accessed or modified
     * within the class it is declared in.
     * <p>
     * Usage:
     * The value of 'cols' indicates the number of columns in a two-dimensional
     * data structure, such as a grid or a matrix. It can be used to determine the
     * size, width, or position of elements within the data structure.
     * <p>
     * Note: It is advisable to always verify the validity of the 'cols' value
     * before using it in any calculations or operations, to ensure the expected
     * behavior of the program and to avoid any out-of-bounds errors or unexpected
     * results.
     */
    private final int cols;

    /**
     * Initializes a new instance of the SquareGridLayout class with the specified number of rows and columns.
     *
     * @param rows The number of rows in the grid layout.
     * @param cols The number of columns in the grid layout.
     */
    public SquareGridLayout(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Arranges the components of the parent container in a grid layout.
     * Each component is given equal size based on the number of columns and rows specified.
     *
     * @param parent the container whose components should be arranged
     */
    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int nComps = parent.getComponentCount();
        int parentWidth = parent.getWidth() - (insets.left + insets.right);
        int parentHeight = parent.getHeight() - (insets.top + insets.bottom);
        int size = Math.min(parentWidth / cols, parentHeight / rows);

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            int row = i / cols;
            int col = i % cols;

            int x = insets.left + col * size;
            int y = insets.top + row * size;

            c.setBounds(x, y, size, size);
        }
    }

    /**
     * Calculates the preferred size of the container based on the number of columns and rows.
     *
     * @param parent the container for which to calculate the preferred size
     * @return the preferred size of the container
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Insets insets = parent.getInsets();
        int width = cols * 50 + insets.left + insets.right; // 50 is just an example
        int height = rows * 50 + insets.top + insets.bottom; // adjust to suit
        return new Dimension(width, height);
    }

    /**
     * Returns the minimum dimensions needed to layout the specified container.
     *
     * @param parent the container to be laid out
     * @return a Dimension object representing the minimum layout size
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    /**
     * Adds the specified component to the layout.
     *
     * This method is a no-op, meaning it does nothing when called. It is implemented because it is required by the LayoutManager interface.
     *
     * @param name the name of the component (unused)
     * @param comp the component to be added to the layout
     *
     * @see LayoutManager
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        // no-op
    }

    /**
     * Removes the specified component from this layout.
     *
     * @param comp the component to be removed from the layout
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        // no-op
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}

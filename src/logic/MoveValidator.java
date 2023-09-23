
package logic;


public interface MoveValidator {


    /**
     * Validates if the move is valid on the given field.
     *
     * @param field the field on which to validate the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    boolean validateMove(Field field);

}

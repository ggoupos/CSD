package mvc.model.piece;

/**
 * The interface of our Pieces
 */
public interface Piece {

    /**
     * <p>Accessor: Returns the power of the piece</p>
     * <p>PostCondition: The power of the piece has been returned</p>
     * @return The power of the Piece
     */
    public int getPower();

    /**
     * <p>Accessor: Returns the name of the piece</p>
     * <p>PostCondition: The name of the piece has been returned</p>
     * @return The name of the Piece
     */
    public String getPieceName();

    /**
     * <p>Transformer: Sets the power of the piece</p>
     * <p>PostCondition: The power of the piece has been set</p>
     * @param value The value we want to set the power of the Piece
     */
    public void setPower(int value);

    /**
     * <p>Transformer: Sets the name of the piece</p>
     * <p>PostCondition: The name of the piece has been set</p>
     * @param pieceName The name we want to give to the Piece
     */
    public void setPieceName(String pieceName);

}

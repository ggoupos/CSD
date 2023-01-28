package mvc.model.piece;

/**
 * This class extends the class MoveablePiece and refers to the special pieces of the game.
 */
public class SpecialMoveablePiece extends MoveablePiece {

    private String specialAttribute;

    /**
     * <p>Constructor: Creates a new instance of a SpecialMoveablePiece and initializes the
     * necessary attributes</p>
     * <p>PostCondition: A new instance of a SpecialMoveablePiece object has been created</p>
     * @param pieceName The name of the ImmovablePiece
     * @param value The power of the ImmovablePiece
     * @param special The special power of the Piece
     */
    public SpecialMoveablePiece(int value, String pieceName, String special){
        super(value,pieceName);
        specialAttribute = special;
    }

    /**
     * <p>Transformer: Sets the variable specialAttribute (the special power the piece has)</p>
     * <p>PostCondition: specialAttribute var has been set</p>
     * @param special The special power of the piece that we want to assign
     */
    public void setSpecial(String special){
        specialAttribute = special;
    }

    /**
     * <p>Accessor: Returns the special power of the piece</p>
     * <p>PostCondition: The special power of the piece has been returned</p>
     * @return The special power of the Piece
     */
    public String getSpecial(){
        return specialAttribute;
    }

}

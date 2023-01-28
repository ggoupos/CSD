package mvc.model.piece;

/**
 * This class also implements the interface Piece (like the ImmovablePiece class). This class
 * refers to pieces that can actually move. It also implements all the methods from the Piece interface.
 */
public class MoveablePiece implements Piece{

    private int power;
    private String name;

    /**
     * <p>Constructor: Creates a new MoveablePiece, and initialize all the attributes that
     * are necessary</p>
     * <p>PostCondition: A new MoveablePiece object is created</p>
     * @param pieceName The name of the ImmovablePiece
     * @param value The power of the ImmovablePiece
     */
    public MoveablePiece(int value, String pieceName){
        power = value;
        name = pieceName;
    }
    public int getPower() {
        return power;
    }

    public String getPieceName() {
        return name;
    }

    public void setPower(int value){
        power = value;
    }

    public void setPieceName(String pieceName){
        name = pieceName;
    }
}

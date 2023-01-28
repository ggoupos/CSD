package mvc.model.piece;

/**
 * This class implements the interface Piece. This class refers to only pieces that cannot move
 * and implements all the methods from the Piece interface.
 */
public class ImmovablePiece implements Piece {

    private int power;
    private String name;


    /**
     * <p>Constructor: Creates a new ImmovablePiece, and initialize all the attributes that
     * are necessary</p>
     * <p>PostCondition: A new ImmovablePiece object is created</p>
     * @param pieceName The name of the ImmovablePiece
     * @param value The power of the ImmovablePiece
     */
    public ImmovablePiece(int value, String pieceName){
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


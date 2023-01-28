package mvc.model.army;

import mvc.model.piece.Piece;

import java.util.ArrayList;

/**
 * The Army class represents the total pieces each player has
 */
public class Army {

    private ArrayList<Piece> army;

    /**
     * <p>Constructor: Creates a new Army (a new ArrayList of pieces)</p>
     * <p>PostCondition: A new Army has been created</p>
     */
    public Army(){
        army = new ArrayList<>();
    }

    /**
     * <p>Transformer: Adds a troop (a new Piece) to the Army</p>
     * <p>PostCondition: The number of Army has been increased by 1 and a new Piece is added to
     * the ArrayList</p>
     * @param newPiece The new Piece that we want to add to the ArrayList
     */
    public void addPiece(Piece newPiece){
        army.add(newPiece);
    }

    /**
     * <p>Transformer: Removes a piece from the Army</p>
     * <p>PreCondition: The piece must exists in the Army</p>
     * <p>PostCondition: The piece has been removed from the ArrayList and the number of Army
     * has been decreased by 1</p>
     * @param rmPieceName The Piece that we want to remove from the ArrayList
     */
    public void removePiece(String rmPieceName){
        for(int i =0; i<army.size(); i++){
            if(army.get(i).getPieceName().equals(rmPieceName)){
                army.remove(i);
                return;
            }
        }
    }

    /**
     * <p>Accessor: Returns the Piece asked from the Army</p>
     * <p>PostCondition: The Piece asked has been returned</p>
     * @param pos position of the Piece asked in the ArrayList
     */
    public Piece getPiece(int pos){
        return army.get(pos);
    }

    /**
     * <p>Accessor: Returns the size of the Army (number of Pieces)</p>
     * <p>PostCondition: The number of the Army has been returned </p>
     * @return The size of the army
     */
    public int getSizeofArmy(){
        return army.size();
    }
}

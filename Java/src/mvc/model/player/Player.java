package mvc.model.player;

import mvc.model.army.Army;
import mvc.model.piece.Piece;
import mvc.model.playerTeam.playerTeam;

import static mvc.model.playerTeam.playerTeam.ICE;

/**
 * A class that represents a player.
 */
public class Player {

    private playerTeam team;
    private Army aliveTroops;
    private Army deadTroops;
    private int rescues;
    private float successAttacks;
    private float numberOfAttacks;

    /**
     * <p>Constructor: Creates a new Player instance and initialize the appropriate attributes</p>
     * <p>PostCondition: A new Player object has been created</p>
     * @param Team The team the player belongs to (ice or fire)
     */
    public Player(playerTeam Team){
        team = Team;
        aliveTroops = new Army();
        deadTroops = new Army();
        rescues = 0;
        successAttacks= 0;
        numberOfAttacks = 0;
    }

    /**
     * <p>Accessor: Returns the number of alive troops (alive pieces of the player's army)</p>
     * <p>PostCondition: The number of alive troops has been returned</p>
     * @return The number of alive troops
     */
    public int getAliveTroopsSize(){
        return aliveTroops.getSizeofArmy();
    }
    /**
     * <p>Accessor: Returns the number of dead troops (dead pieces of the player's army)</p>
     * <p>PostCondition: The number of dead troops has been returned</p>
     * @return The number of dead troops
     */
    public int getDeadTroopSize(){
        return deadTroops.getSizeofArmy();
    }

    /**
     * <p>Transformer: Adds a dead piece from the dead troops to the alive troops army</p>
     * <p>PostCondition: The number of the deadTroops army is reduced by 1 and the number of aliveTroops army is increased by 1</p>
     * @param deadToAlive The piece that will be moved from the dead troops to the alive troops
     */
    public void addDeadFromAlive(Piece aliveToDead){
        aliveTroops.removePiece(aliveToDead.getPieceName());
        addDeadTroop(aliveToDead);
    }
    /**
     * <p>Transformer: Adds an alive piece from the alive troops to the dead troops army</p>
     * <p>PostCondition: The number of the deadTroops army is increased by 1 and the number of aliveTroops army is decreased by 1</p>
     * @param aliveToDead The piece that will be moved from the alive troops to the dead troops
     */
    public void addAliveFromDead(Piece deadToAlive){
        deadTroops.removePiece(deadToAlive.getPieceName());
        addAliveTroop(deadToAlive);

    }

    /**
     * <p>Transformer: Adds a troop ( a new piece) to the alive troops army</p>
     * <p>PostCondition: The number of the alive troops army is increased by 1</p>
     * @param aliveTroop The troop that will be added to the alive troops army
     */
    public void addAliveTroop(Piece aliveTroop){
        aliveTroops.addPiece(aliveTroop);
    }
    /**
     * <p>Transformer: Adds a troop ( a new piece) to the dead troops army</p>
     * <p>PostCondition: The number of the dead troops army is increased by 1</p>
     * @param deadTroop The troop that will be added to the dead troops army
     */
    public void addDeadTroop(Piece deadTroop) {
        deadTroops.addPiece(deadTroop);
    }

    /**
     * <p>Accessor: Returns the appropriate troop from the alive troops army</p>
     * <p>PostCondition: The appropriate troop has been returned</p>
     * @param i The position of the troop in the aliveTroops ArrayList
     */
    public Piece getAliveTroop(int i) {
        return aliveTroops.getPiece(i);
    }
    /**
     * <p>Accessor: Returns the appropriate troop from the dead troops army</p>
     * <p>PostCondition: The appropriate troop has been returned</p>
     * @param i The position of the troop in the deadTroops ArrayList
     */
    public Piece getDeadTroop(int i){
        return deadTroops.getPiece(i);
    }

    /**
     * <p>Accessor: Returns the number of the total rescues the player has made</p>
     * <p>PostCondition: The number of total rescues has been returned</p>
     * @return The number of total rescues
     */
    public int getRescues(){
        return rescues;
    }

    /**
     * <p>Transformer: Increase the total number of rescues by 1</p>
     * <p>PostCondition: The total number of rescues has been increased by 1</p>
     */
    public void increaseRescues(){
        rescues++;
    }
    public void increaseAttacks(){
        numberOfAttacks++;
    }
    public void increasesSuccessAttacks(){
        successAttacks++;
    }

    /**
     * <p>Transformer: Calculate the percentage of the successful attacks the player has made</p>
     * <p>PostCondition: The above percentage is returned</p>
     * @return The percentage of the successful attacks the player has made
     */
    public float calculateSuccessAttacks(){
        if(numberOfAttacks == 0) return 0;
        return successAttacks/numberOfAttacks;
    }

}

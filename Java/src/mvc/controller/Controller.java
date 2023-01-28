package mvc.controller;

import mvc.model.piece.ImmovablePiece;
import mvc.model.piece.MoveablePiece;
import mvc.model.piece.SpecialMoveablePiece;
import mvc.model.player.Player;
import mvc.model.playerTeam.playerTeam;
import mvc.model.turn.Turn;
import mvc.view.GraphicsUI;
import mvc.view.PieceButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static mvc.model.playerTeam.playerTeam.*;

public class Controller {

    public Player IcePlayer;
    public Player FirePlayer;
    private GraphicsUI gui;
    public Turn turn;
    private boolean noRetreat;
    private boolean reducedArmy;
    private final Image redFaceDown=ImageIO.read(getClass().getResource("images/RedPieces/redHidden.png"));
    private final Image blueFaceDown=ImageIO.read(Objects.requireNonNull(getClass().getResource("images/bluePieces/blueHidden.png")));

    /**
     * <p>Constructor: Initialize the Controller and the appropriate attributes</p>
     * @param graphics The GraphicsUI object of the game
     */
    public Controller(GraphicsUI graphics) throws IOException {
        IcePlayer = new Player(ICE);
        FirePlayer = new Player(FIRE);
        turn = new Turn();
        noRetreat = false;
        reducedArmy = false;
        gui = graphics;
    }

    public boolean getReducedArmy(){
        return reducedArmy;
    }
    /**
     * <p> Initialize a PieceButton object with the appropriate attributes</p>
     * @param button The PieceButton that we want to initialize
     * @param buttonName The name of the piece the button represents
     * @param x The X coordinate of the piece
     * @param y The y coordinate of the piece
     * @param team The team that the PieceButton belongs to (ice or fire)
     */
    public void initPieceButton(PieceButton button, String buttonName, int x, int y, playerTeam team) throws IOException {
        Player help_player;
        if(team == ICE) help_player = IcePlayer;
        else help_player = FirePlayer;

        button.setXpos(x);
        button.setYpos(y);
        button.setCanBeAttacked(true);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        switch (buttonName){
            case "empty":
                button.setButtonPiece(new ImmovablePiece(-10,buttonName));
                button.setCanBePressed(false);
                button.setCanBeAttacked(true);
                button.setFaceUpImg(ImageIO.read(Objects.requireNonNull(getClass().
                        getResource("images/"+ buttonName + ".png"))));
                button.setFaceDownImg(button.getFaceUpImg());
                button.setTurnImg(button.getFaceUpImg());
                button.setPlayerTeam(NONE);
                break;

            case "no":
                button.setButtonPiece(new ImmovablePiece(-20,buttonName));
                button.setCanBePressed(false);
                button.setCanBeAttacked(false);
                button.setFaceUpImg(ImageIO.read(Objects.requireNonNull(getClass().
                        getResource("images/"+ buttonName + ".jpg"))));
                button.setFaceDownImg(button.getFaceUpImg());
                button.setTurnImg(button.getFaceUpImg());
                button.setPlayerTeam(NONE);
                button.setBorderPainted( false );
                button.setFocusPainted( false );
                break;
            case "beastRider":
                help_player.addAliveTroop(new MoveablePiece(7,buttonName));
                button.setButtonPiece(new MoveablePiece(7,buttonName));
                button.setCanBePressed(true);
                break;
            case "dragon":
                help_player.addAliveTroop(new MoveablePiece(10,buttonName));
                button.setButtonPiece(new MoveablePiece(10,buttonName));
                button.setCanBePressed(true);
                break;
            case "dwarf":
                help_player.addAliveTroop(new SpecialMoveablePiece(3,buttonName,"Diffusion"));
                button.setButtonPiece(new SpecialMoveablePiece(3,buttonName,"Diffusion"));
                button.setCanBePressed(true);
                break;
            case "elf":
                help_player.addAliveTroop(new MoveablePiece(4,buttonName));
                button.setButtonPiece(new MoveablePiece(4,buttonName));
                button.setCanBePressed(true);
                break;
            case "knight":
                help_player.addAliveTroop(new MoveablePiece(8,buttonName));
                button.setButtonPiece(new MoveablePiece(8,buttonName));
                button.setCanBePressed(true);
                break;
            case "mage":
                help_player.addAliveTroop(new MoveablePiece(9,buttonName));
                button.setButtonPiece(new MoveablePiece(9,buttonName));
                button.setCanBePressed(true);
                break;
            case "scout":
                help_player.addAliveTroop(new SpecialMoveablePiece(2,buttonName,"Movement"));
                button.setButtonPiece(new SpecialMoveablePiece(2,buttonName,"Movement"));
                button.setCanBePressed(true);
                break;
            case "slayer":
                help_player.addAliveTroop(new SpecialMoveablePiece(0,buttonName,"KillsDragon"));
                button.setButtonPiece(new SpecialMoveablePiece(0,buttonName,"KillsDragon"));
                button.setCanBePressed(true);
                break;
            case "sorceress":
                help_player.addAliveTroop(new MoveablePiece(6,buttonName));
                button.setButtonPiece(new MoveablePiece(6,buttonName));
                button.setCanBePressed(true);
                break;
            case "yeti":
                help_player.addAliveTroop(new MoveablePiece(5,buttonName));
                button.setButtonPiece(new MoveablePiece(5,buttonName));
                button.setCanBePressed(true);
                break;
            case "lavaBeast":
                help_player.addAliveTroop(new MoveablePiece(5,buttonName));
                button.setButtonPiece(new MoveablePiece(5,buttonName));
                button.setCanBePressed(true);
                break;
            case "flag":
                help_player.addAliveTroop(new ImmovablePiece(-1,buttonName));
                button.setButtonPiece(new ImmovablePiece(-1,buttonName));
                button.setCanBePressed(true);
                break;
            case "trap":
                help_player.addAliveTroop(new ImmovablePiece(20,buttonName));
                button.setButtonPiece(new ImmovablePiece(20,buttonName));
                button.setCanBePressed(true);
                break;
        }
        if(team == ICE ){
            button.setPlayerTeam(ICE);
            button.setFaceDownImg(blueFaceDown);
            button.setFaceUpImg(ImageIO.read(Objects.requireNonNull(getClass().
                   getResource("images/bluePieces/" + buttonName + "B.png"))));
            button.setTurnImg(button.getFaceUpImg());

        }
        else if(team == FIRE ){
            button.setPlayerTeam(FIRE);
            button.setFaceDownImg(redFaceDown);
            button.setFaceUpImg(ImageIO.read(Objects.requireNonNull(getClass().
                    getResource("images/RedPieces/" + buttonName + "R.png"))));
            button.setTurnImg(redFaceDown);
            button.setCanBePressed(false);
        }
    }

    /**
     * <p>After each move, the highlighted button must be go back to their previous state, where the were not highlighted</p>
     * @param pieceArray All the pieceButtons of the game
     */
    public void de_highlightButtons(PieceButton[][] pieceArray){
       for(int i =0;i<8;i++){
           for(int j =0;j<10;j++){
                   pieceArray[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
           }
       }
    }

    /**
     * <p>When a player click a pieceButton, the same button and perhaps some other buttons must be highlighted
     * to show the correct moves. This function is responsible for that</p>
     * @param buttonPressed The pieceButton that one of the players pressed
     * @param pieceArray All the pieceButtons of the game
     */
    public void highlightButtons(PieceButton buttonPressed, PieceButton[][] pieceArray){
        int pressedX = buttonPressed.getXpos();
        int pressedY = buttonPressed.getYpos();
        if(buttonPressed.getButtonPiece().getPieceName() == "trap" || buttonPressed.getButtonPiece().getPieceName() == "flag"){
            return;
        }
        if(buttonPressed.getButtonPiece().getPieceName() == "scout"){
            int j = buttonPressed.getYpos();
            for(int i =0;i<8;i++){
                if (pressedX == i) {
                   //to the left first
                   j--;
                   while(j>=0){
                       if(pieceArray[i][j].getPlayerTeam() == buttonPressed.getPlayerTeam()) break;
                       if(pieceArray[i][j].getButtonPiece().getPieceName() == "empty" || pieceArray[i][j].getPlayerTeam() != buttonPressed.getPlayerTeam()){
                           pieceArray[i][j].setBorder(BorderFactory.createLineBorder(Color.green, 5));
                           if(pieceArray[i][j].getButtonPiece().getPieceName() !="empty" ){
                               break;
                           }
                       }
                       j--;
                   }
                   //to the right
                   j = buttonPressed.getYpos() +1;
                    while(j<10){
                        if(pieceArray[i][j].getPlayerTeam() == buttonPressed.getPlayerTeam()) break;
                        if(pieceArray[i][j].getButtonPiece().getPieceName() == "empty" || pieceArray[i][j].getPlayerTeam() != buttonPressed.getPlayerTeam()){
                            pieceArray[i][j].setBorder(BorderFactory.createLineBorder(Color.green, 5));
                            if(pieceArray[i][j].getButtonPiece().getPieceName() !="empty"){
                                break;
                            }
                        }
                        j++;
                    }
                }
            }
            for(int y =0; y<10;y++){
                int k = buttonPressed.getXpos();
                if(pressedY == y){
                   //to the up first
                    k--;
                    while(k>=0){
                        if(pieceArray[k][y].getPlayerTeam() == buttonPressed.getPlayerTeam()) break;
                        if(pieceArray[k][y].getButtonPiece().getPieceName() == "empty" || pieceArray[k][y].getPlayerTeam() != buttonPressed.getPlayerTeam()){
                            pieceArray[k][y].setBorder(BorderFactory.createLineBorder(Color.green, 5));
                            if(noRetreat){
                                if(buttonPressed.getPlayerTeam() == ICE){
                                    if(buttonPressed.getXpos() > k){
                                        pieceArray[k][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                                    }
                                }
                            }
                            if(pieceArray[k][y].getButtonPiece().getPieceName() !="empty"){
                                break;
                            }
                        }
                        k--;
                    }
                    //to the down now
                    k = buttonPressed.getXpos() + 1;
                    while(k<8){
                        if(pieceArray[k][y].getPlayerTeam() == buttonPressed.getPlayerTeam()) break;
                        if(pieceArray[k][y].getButtonPiece().getPieceName() == "empty" || pieceArray[k][y].getPlayerTeam() != buttonPressed.getPlayerTeam()){
                            pieceArray[k][y].setBorder(BorderFactory.createLineBorder(Color.green, 5));
                            if(noRetreat){
                                if(buttonPressed.getPlayerTeam() == FIRE){
                                    if(buttonPressed.getXpos() < k){
                                        pieceArray[k][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                                    }
                                }
                            }
                            if(pieceArray[k][y].getButtonPiece().getPieceName() !="empty"){
                                break;
                            }
                        }
                        k++;
                    }
                }
            }
            return;
        }

        for(int i =0;i<8;i++){
            for(int j =0;j<10;j++){
                if(i == pressedX){
                    if(j-1 == pressedY || j+1 == pressedY){
                        if(pieceArray[i][j].getPlayerTeam() != buttonPressed.getPlayerTeam()) {
                            pieceArray[i][j].setBorder(BorderFactory.createLineBorder(Color.green, 5));
                        }
                    }
                }
                else if( j == pressedY){
                    if(i - 1 == pressedX || i + 1 == pressedX){

                        if(pieceArray[i][j].getPlayerTeam() != buttonPressed.getPlayerTeam()) {
                            pieceArray[i][j].setBorder(BorderFactory.createLineBorder(Color.green, 5));
                        }
                        if (noRetreat) {
                            if(buttonPressed.getPlayerTeam() == ICE){
                                if(i + 1 == pressedX) pieceArray[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK ));
                            }
                            else if (buttonPressed.getPlayerTeam() == FIRE){
                                if(i-1 == pressedX) pieceArray[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK ));
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    /**
     * <p>Observer: Checks if the player move is valid or not</p>
     * <p>PreCondition: Both argument must be != null </p>
     * <p>PostCondition: The move has been checked</p>
     * @param startButton The button(piece) that the player wants to move
     * @param destButton The button that the player wants to move TO
     * @param pieceArray All the pieceButtons that are in the board
     * @return true if move is valid, false otherwise
     */
    public boolean check_move(PieceButton startButton, PieceButton destButton, PieceButton[][] pieceArray){
        int startX = startButton.getXpos();
        int startY = startButton.getYpos();
        int destX = destButton.getXpos();
        int destY = destButton.getYpos();
        int i = destX;
        int j = destY;
        System.out.println("StartX: " + startX + " AND StartY: " + startY);
        System.out.println("DestX: " + destX + " AND DestY: " + destY);
        if(startButton.getButtonPiece().getPieceName() == "flag" || startButton.getButtonPiece().getPieceName() == "trap")
            return false;
        if(!destButton.getCanBeAttacked())
            return false;
        if(startButton.getButtonPiece().getPieceName() == "scout"){
            if(startX == destX && startY != destY){
                if(destButton.getButtonPiece().getPieceName() == "empty" || destButton.getPlayerTeam()!= startButton.getPlayerTeam()){
                    j--;
                    while(j > startY){
                        if(pieceArray[startX][j].getButtonPiece().getPieceName()!="empty"){
                            System.out.println("Move Incorrect");
                            return false;
                        }
                        j--;
                    }
                    j = destY +1;
                    while(j < startY){
                        if(pieceArray[startX][j].getButtonPiece().getPieceName()!="empty"){
                            System.out.println("Move Incorrect");
                            return false;
                        }
                        j++;
                    }
                    System.out.println("Move correct");
                    return true;
                }
            }
            if(startY == destY && startX != destX){
                if(noRetreat){
                    if(startButton.getPlayerTeam() == ICE){
                        if(destX < startX){
                            System.out.println("Move Incorrect");
                            return false;
                        }
                    }
                    else if(startButton.getPlayerTeam() == FIRE){
                        if(destX > startX){
                            System.out.println("Move Incorrect");
                            return false;
                        }
                    }
                }
                if(destButton.getButtonPiece().getPieceName() == "empty" || destButton.getPlayerTeam()!=startButton.getPlayerTeam()){
                    i--;
                    while(i > startX){
                        if(pieceArray[i][startY].getButtonPiece().getPieceName()!="empty"){
                            System.out.println("Move Incorrect");
                            return false;
                        }
                        i--;
                    }
                    i = destX+1;
                    while(i < startX){
                        if(pieceArray[i][startY].getButtonPiece().getPieceName()!="empty"){
                            System.out.println("Move Incorrect");
                            return false;
                        }
                        i++;
                    }
                    System.out.println("Move correct");
                    return true;
                }
            }
            return false;
        }
        System.out.println("Dest team : " + destButton.getPlayerTeam()) ;
        System.out.println("Start team : " + startButton.getPlayerTeam()) ;
        if(destX - 1 == startX || destX + 1 == startX){
            if(noRetreat){
                if(startButton.getPlayerTeam() == ICE){
                    if(destX+1 == startX){
                        System.out.println("Move Incorrect");
                        return false;
                    }
                }
                else if(startButton.getPlayerTeam() == FIRE){
                    if(destX-1 == startX){
                        System.out.println("Move Incorrect");
                        return false;
                    }
                }
            }
            if(destY == startY) {
                if(destButton.getButtonPiece().getPieceName() == "empty" || destButton.getPlayerTeam() != startButton.getPlayerTeam()){
                    System.out.println("Move correct");
                    return true;
                }
            }
        }
        else if(destX == startX){
            if(destY - 1 == startY || destY + 1 == startY){

                if(destButton.getButtonPiece().getPieceName() == "empty" || destButton.getPlayerTeam() != startButton.getPlayerTeam()){
                    System.out.println("Move correct");
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * <p>Transformer: Makes the attack</p>
     * <p>PreCondition: Both arguments must be != null</p>
     * <p>PostCondition: The attacked has been made</p>
     * @param attackerButton The button (piece) that wants to attack
     * @param defenderButton The button (piece) that defends
     * @return 1 if the attackerButton succeeds, -1 if not, 2 if the defender is the flag, 0 otherwise
     */
    public int makeAttack(PieceButton attackerButton, PieceButton defenderButton){
        int attack_power = attackerButton.getButtonPiece().getPower();
        int defend_power = defenderButton.getButtonPiece().getPower();
        String attackerName = attackerButton.getButtonPiece().getPieceName();
        String defenderName = defenderButton.getButtonPiece().getPieceName();
        Player attackingPlayer = null;
        Player defenderPlayer = null;
        if(attackerButton.getPlayerTeam() == ICE) {
            attackingPlayer = IcePlayer;
            defenderPlayer = FirePlayer;
        }
        else {
            attackingPlayer = FirePlayer;
            defenderPlayer = IcePlayer;
        }
        System.out.println("Attacker: " + attackerName + "(" +attack_power +") , Defender: " + defenderName + "(" + defend_power + ")");
        if(defenderButton.getButtonPiece().getPieceName() == "flag"){
            return 2;
        }
        if(attackerName == "dwarf" && defenderName == "trap"){
            attackingPlayer.increasesSuccessAttacks();
            attackingPlayer.increaseAttacks();
            defenderPlayer.addDeadFromAlive(defenderButton.getButtonPiece());
            return 1;
        }
        if(attackerName == "slayer" && defenderName == "dragon"){
            attackingPlayer.increasesSuccessAttacks();
            attackingPlayer.increaseAttacks();
            defenderPlayer.addDeadFromAlive(defenderButton.getButtonPiece());
            return 1;
        }
        if(attack_power > defend_power){
            if(defenderButton.getButtonPiece().getPieceName() != "empty"){
                attackingPlayer.increasesSuccessAttacks();
                attackingPlayer.increaseAttacks();
                defenderPlayer.addDeadFromAlive(defenderButton.getButtonPiece());
            }
            return 1;
        }
        else if (attack_power < defend_power){
            attackingPlayer.increaseAttacks();
            attackingPlayer.addDeadFromAlive(attackerButton.getButtonPiece());
            return -1;
        }
        else if (attack_power == defend_power){
            attackingPlayer.increasesSuccessAttacks();
            attackingPlayer.increaseAttacks();
            attackingPlayer.addDeadFromAlive(attackerButton.getButtonPiece());
            defenderPlayer.addDeadFromAlive(defenderButton.getButtonPiece());
            return 0;
        }

        return 5;
    }

    /**
     * <p>Transformer: Swaps all the information of the buttons</p>
     * <p>PreCondition: Both arguments must be != null</p>
     * <p>PostsCondition: Info of the buttons has been swapped</p>
     * @param butFirst The first button we want to swap
     * @param butSecond The second button we want to swap
     */
    public void swapPiecesButtons(PieceButton butFirst, PieceButton butSecond){
        PieceButton temp = new PieceButton();

        temp.setButtonPiece(butFirst.getButtonPiece());
        temp.setFaceUpImg(butFirst.getFaceUpImg());
        temp.setFaceDownImg(butFirst.getFaceDownImg());
        temp.setTurnImg(butFirst.getTurnImg());
        temp.setCanBePressed(butFirst.getCanBePressed());
        temp.setPlayerTeam(butFirst.getPlayerTeam());
        temp.setRescues(butFirst.getRescues());

        butFirst.setButtonPiece(butSecond.getButtonPiece());
        butFirst.setFaceUpImg(butSecond.getFaceUpImg());
        butFirst.setFaceDownImg(butSecond.getFaceDownImg());
        butFirst.setTurnImg(butSecond.getTurnImg());
        butFirst.setCanBePressed(butSecond.getCanBePressed());
        butFirst.setPlayerTeam(butSecond.getPlayerTeam());
        butFirst.setRescues(butSecond.getRescues());

        butSecond.setButtonPiece(temp.getButtonPiece());
        butSecond.setFaceUpImg(temp.getFaceUpImg());
        butSecond.setFaceDownImg(temp.getFaceDownImg());
        butSecond.setTurnImg(temp.getTurnImg());
        butSecond.setCanBePressed(temp.getCanBePressed());
        butSecond.setPlayerTeam(temp.getPlayerTeam());
        butSecond.setRescues(temp.getRescues());
    }

    /**
     * <p>Transformer: Makes a button empty</p>
     * <p>PreCondition: Argument must be != null </p>
     * <p>PostCondition: The button become the EmptyButton</p>
     * @param button The button we want to make empty
     */
    public void createEmptyButton(PieceButton button) throws IOException {
        button.setPlayerTeam(NONE);
        button.setButtonPiece(new ImmovablePiece(-10,"empty"));
        button.setCanBeAttacked(true);
        button.setCanBePressed(false);
        button.setFaceUpImg(ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/"+ "empty" + ".png"))));
        button.setFaceDownImg(button.getFaceUpImg());
        button.setTurnImg(button.getFaceUpImg());
    }

    /**
     * <p>Observer: Checks to see if the player is out of moves</p>
     * <p>PreCondition: button must be != null</p>
     * <p>PostCondition: The check has been made</p>
     * @param button The button (piece) that is checked to see if it is out of moves
     * @param pieceArray All the pieceButtons in the board
     * @return true, if it is indeed out of moves, false otherwise
     */
    public boolean isOutOfMoves(PieceButton button,PieceButton[][] pieceArray){
        boolean flag = false;
        for(int i =0;i<8;i++){
            for(int j = 0;j<10;j++) {
                flag = check_move(button, pieceArray[i][j], pieceArray);
                if (flag)
                    return !flag;
           }
        }
        return true;
    }

    /**
     * <p>Observer: Checks if a rescue is possible</p>
     * <p>PreCondition: button must be != null</p>
     * <p>PostCondition: The check has been done</p>
     * @param button The button that wants to make a rescue
     * @return true, if the rescue is possible, false otherwise
     */
    public boolean canRescue(PieceButton button){

        int countTraps=0;

        if(button.getPlayerTeam() == ICE){
            if(button.getXpos() == 7){
                if(button.getButtonPiece().getPieceName() == "scout") {
                    JOptionPane.showMessageDialog(null,"Scout cannot make a rescue","Scout rescue",1);
                    return false;
                }
                for(int i =0;i<IcePlayer.getDeadTroopSize();i++){
                    if(IcePlayer.getDeadTroop(i).getPieceName() == "trap") countTraps++;
                }
                if((IcePlayer.getDeadTroopSize()-countTraps)==0 ){
                    JOptionPane.showMessageDialog(null,"Your dead troops are only Traps. Cannot rescue Traps.","Traps rescue",1);
                    return false;
                }
                else{
                    if(IcePlayer.getRescues()>=2){
                        JOptionPane.showMessageDialog(null,"Player " + button.getPlayerTeam() + " has already completed" +
                                " 2 rescues","Rescues completed",1);
                        return false;
                    }
                    else{
                        if(button.getRescues()>=1){
                            JOptionPane.showMessageDialog(null,"This troop has already make a rescue.","Troop rescue",1);
                            return false;
                        }
                        else return true;
                    }
                }
            }
        }
        else if(button.getPlayerTeam() == FIRE){
            if(button.getXpos() == 0){
                if(button.getButtonPiece().getPieceName() == "scout") {
                    JOptionPane.showMessageDialog(null,"Scout cannot make a rescue","Scout rescue",1);
                    return false;
                }
                for(int i =0;i<FirePlayer.getDeadTroopSize();i++){
                    if(FirePlayer.getDeadTroop(i).getPieceName() == "trap") countTraps++;
                }
                if((FirePlayer.getDeadTroopSize()-countTraps)==0 ){
                    JOptionPane.showMessageDialog(null,"Your dead troops are only Traps. Cannot rescue Traps.","Traps rescue",1);
                    return false;
                }
                else{
                    if(FirePlayer.getRescues()>=2){
                        JOptionPane.showMessageDialog(null,"Player " + button.getPlayerTeam() + " has already completed" +
                                " 2 rescues","Rescues completed",1);
                        return false;
                    }
                    else{
                        if(button.getRescues()>=1){
                            JOptionPane.showMessageDialog(null,"This troop has already make a rescue.","Troop rescue",1);
                            return false;
                        }
                        else return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * <p>Transformer: Brings back a dead troop to life</p>
     * <p>PreCondition: button must be != null</p>
     * <p>PostCondition: The rescue has been made</p>
     * @param button The button(piece) that can make a rescue
     * @param pieceArray All the pieceButtons that exist in the game
     */
    public void makeRescue(PieceButton button, PieceButton[][] pieceArray) throws IOException {
       ArrayList<String> deadArrayList = new ArrayList<>();
       String rescuedPiece = "";
       if(button.getPlayerTeam() == ICE){
           for(int i =0;i<IcePlayer.getDeadTroopSize();i++){
               if(IcePlayer.getDeadTroop(i).getPieceName()!="trap")
                   deadArrayList.add(IcePlayer.getDeadTroop(i).getPieceName());
           }
           String[] deadArray = deadArrayList.toArray(new String[0]);
           int selection = JOptionPane.showOptionDialog(null, "Διάλεξε ποιο θα σώσεις:","Διάσωση",
                   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, deadArray,deadArray[0]);
           rescuedPiece = deadArray[selection];
           for(int i =0;i<3;i++){
               for(int j =0;j<10;j++){
                   if(pieceArray[i][j].getButtonPiece().getPieceName() == "empty") {
                       initPieceButton(pieceArray[i][j], rescuedPiece, i, j, ICE);
                       IcePlayer.addAliveFromDead(pieceArray[i][j].getButtonPiece());
                       IcePlayer.increaseRescues();
                       button.increaseRescues();
                       return;
                   }
               }
           }
       }
       else if(button.getPlayerTeam() == FIRE){
            for(int i =0;i<FirePlayer.getDeadTroopSize();i++){
                if(FirePlayer.getDeadTroop(i).getPieceName()!="trap")
                    deadArrayList.add(FirePlayer.getDeadTroop(i).getPieceName());
            }
            String[] deadArray = deadArrayList.toArray(new String[0]);
            int selection = JOptionPane.showOptionDialog(null, "Διάλεξε ποιο θα σώσεις:","Διάσωση",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, deadArray,deadArray[0]);
            rescuedPiece = deadArray[selection];
            for(int i =7 ;i>4;i--){
                for(int j =0 ;j<10;j++) {
                    if (pieceArray[i][j].getButtonPiece().getPieceName() == "empty"){
                        initPieceButton(pieceArray[i][j], rescuedPiece, i, j, FIRE);
                        FirePlayer.addAliveFromDead(pieceArray[i][j].getButtonPiece());
                        FirePlayer.increaseRescues();
                        button.increaseRescues();
                        return;
                   }
                }
            }
        }
       //show up error
       return;
    }

    /**
     * <p>Transformer: Change the face of the Pieces</p>
     * <p>PreCondition: graphics must be != null</p>
     * <p>PostCondition: The change has been made</p>
     * @param pieceArray The button pieces of the game
     */
    public void changeFaceOfButtons(PieceButton[][] pieceArray){
        if(turn.whoIsPlaying() == ICE){
            for(int i =0; i<8;i++){
                for(int j =0; j<10;j++){
                    if(pieceArray[i][j].getPlayerTeam() == ICE){
                        pieceArray[i][j].setTurnImg(pieceArray[i][j].getFaceDownImg());
                        pieceArray[i][j].setCanBePressed(false);
                    }
                    if(pieceArray[i][j].getPlayerTeam() == FIRE){
                        pieceArray[i][j].setTurnImg(pieceArray[i][j].getFaceUpImg());
                        pieceArray[i][j].setCanBePressed(true);
                    }
                }
            }
        }
        if(turn.whoIsPlaying() == FIRE){
            for(int i =0; i<8;i++){
                for(int j =0; j<10;j++){
                    if(pieceArray[i][j].getPlayerTeam() == FIRE){
                        pieceArray[i][j].setTurnImg(pieceArray[i][j].getFaceDownImg());
                        pieceArray[i][j].setCanBePressed(false);
                    }
                    if(pieceArray[i][j].getPlayerTeam() == ICE) {
                        pieceArray[i][j].setTurnImg(pieceArray[i][j].getFaceUpImg());
                        pieceArray[i][j].setCanBePressed(true);
                    }
                }
            }
        }
    }

    /**
     * <p>Transformer: Write to the panel which special rules are applied (the top panel in info panel)</p>
     * <p>PreCondition: graphics must be != null</p>
     * <p>PostCondition: The write has been made and also the specialRules variables in the controller have been assigned</p>
     * @param graphics The graphics object of the game
     */
    public void write_specialRules(GraphicsUI graphics){
        JCheckBox checkbox1 = null;
        JCheckBox checkbox2 = null;
        String[] options = { "Μειωμένος Στρατός", "Καμία Υποχώρηση", "Και τα δύο","Κανένα" };
        int selection = JOptionPane.showOptionDialog(null, "Διάλεξε ένα:","Ειδικοί Κανόνες",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selection == 0) {
             checkbox1 = new JCheckBox("Μειωμένος στρατός",true);
             checkbox2 = new JCheckBox("Καμία υποχώρηση",false);
             reducedArmy = true;
             noRetreat = false;
        }
        if (selection == 1) {
             checkbox1 = new JCheckBox("Μειωμένος στρατός",false);
             checkbox2 = new JCheckBox("Καμία υποχώρηση",true);
            reducedArmy = false;
            noRetreat = true;
        }
        if (selection == 2) {
             checkbox1 = new JCheckBox("Μειωμένος στρατός",true);
             checkbox2 = new JCheckBox("Καμία υποχώρηση",true);
            reducedArmy = true;
            noRetreat = true;
        }
        if (selection == 3) {
             checkbox1 = new JCheckBox("Μειωμένος στρατός",false);
             checkbox2 = new JCheckBox("Καμία υποχώρηση",false);
            reducedArmy = false;
            noRetreat = false;
        }
        JPanel activeRules = new JPanel(new GridLayout(2,0));
        JPanel checkPanel = new JPanel(new GridLayout(2,0));

        JLabel title = new JLabel("     Ενεργοί Κανόνες     ");
        title.setFont(new Font("Verdana",Font.BOLD,45));
        activeRules.add(title);
        checkbox1.setFont(new Font("Verdana",Font.BOLD,30));
        checkbox2.setFont(new Font("Verdana",Font.BOLD,30));
        checkbox1.setEnabled(false);
        checkbox2.setEnabled(false);
        checkPanel.setBackground(Color.lightGray);
        checkPanel.add(checkbox1);
        checkPanel.add(checkbox2);
        activeRules.add(checkPanel);
        graphics.info_panel.add(activeRules);


    }
    /**
     * <p>Transformer: Write to the panel which turn is it,the rescues the successAttackRate (responsible the medium panel in the info_panel)</p>
     * <p>PreCondition: graphics must be != null</p>
     * <p>PostCondition: The write has been made</p>
     * @param graphics The graphics object of the game
     */
    public void write_turn(GraphicsUI graphics){
        Player playingPlayer = null;
        if(turn.whoIsPlaying() == ICE){
           playingPlayer = IcePlayer;
        }
        else playingPlayer = FirePlayer;
        JLabel playerTurn = (JLabel) graphics.medium_panel.getComponent(1);
        JLabel successAttack = (JLabel) graphics.medium_panel.getComponent(2);
        JLabel rescuesTurn = (JLabel) graphics.medium_panel.getComponent(3);
        playerTurn.setText("Player " + turn.whoIsPlaying() + " turn");
        playerTurn.setFont(new Font("Verdana", Font.PLAIN,30));
        successAttack.setText("Ποσοστό επιτ. επίθεσης: " + playingPlayer.calculateSuccessAttacks()*100  + "%");
        successAttack.setFont(new Font("Verdana", Font.PLAIN,30));
        rescuesTurn.setText("Διασώσεις: " + playingPlayer.getRescues() +"                Γύρος: "+ turn.getNumberofRounds());
        rescuesTurn.setFont(new Font("Verdana", Font.PLAIN,30));

    }

    /**
     * <p> Helper function needed only in the controller class.
     *  Counts how many instances of the specific piece has the player in his graveyard(dead)</p>
     * @param deadPiece The dead piece that we want to find how many instances of this are dead
     * @param playerLost The player that the piece belongs to
     * @return The number of the deadPieces that exists in the player deadArmy
     */
    private int countDEAD(String deadPiece,Player playerLost){
        int counter = 0;
        for(int i =0; i<playerLost.getDeadTroopSize();i++){
            if(deadPiece.equals(playerLost.getDeadTroop(i).getPieceName())){
                counter++;
            }
        }
        return counter;
    }

    /**
     * <p>Transformer: Write to the panel the captures of the appropriate player (responsible for the captures_panel)</p>
     * <p>PreCondition: graphics must be != null</p>
     * <p>PostCondition: The write has been made</p>
     * @param graphics The graphics object of the game
     */
    public void write_captures(GraphicsUI graphics) throws IOException {
        Image beastRiderf = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/beastRiderR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image dragonf = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/dragonR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image dwarff = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/dwarfR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image elff = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/elfR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image knightf = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/knightR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image lavaBeast = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/lavaBeastR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image magef = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/mageR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image scoutf = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/scoutR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image slayerf = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/slayerR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image sorceressf = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/sorceressR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);

        Image yeti = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/yetiB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image beastRideri = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/beastRiderB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image dragoni = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/dragonB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image dwarfi = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/dwarfB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image elfi = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/elfB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image knighti = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/knightB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image magei = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/mageB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image scouti = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/scoutB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image slayeri = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/slayerB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        Image sorceressi = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/bluePieces/sorceressB.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon1 =  (JLabel) graphics.captures_panel.getComponent(0);
        JLabel icon2 =  (JLabel) graphics.captures_panel.getComponent(1);
        JLabel icon3 =  (JLabel) graphics.captures_panel.getComponent(2);
        JLabel icon4 =  (JLabel) graphics.captures_panel.getComponent(3);
        JLabel icon5 =  (JLabel) graphics.captures_panel.getComponent(4);
        JLabel icon6 =  (JLabel) graphics.captures_panel.getComponent(5);
        JLabel icon7 =  (JLabel) graphics.captures_panel.getComponent(6);
        JLabel icon8 =  (JLabel) graphics.captures_panel.getComponent(7);
        JLabel icon9 =  (JLabel) graphics.captures_panel.getComponent(8);
        JLabel icon10 =  (JLabel) graphics.captures_panel.getComponent(9);

        JLabel totalNum = (JLabel) graphics.captures_panel.getComponent(11);
        switch (turn.whoIsPlaying()){
            case ICE:
                JLabel newicon1 = new JLabel(new ImageIcon(beastRiderf));
                newicon1.setText(Integer.toString(countDEAD("beastRider",FirePlayer)));
                newicon1.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon2 = new JLabel(new ImageIcon(dragonf));
                newicon2.setText(Integer.toString(countDEAD("dragon",FirePlayer)));
                newicon2.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon3 = new JLabel(new ImageIcon(dwarff));
                newicon3.setText(Integer.toString(countDEAD("dwarf",FirePlayer)));
                newicon3.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon4 = new JLabel(new ImageIcon(elff));
                newicon4.setText(Integer.toString(countDEAD("elf",FirePlayer)));
                newicon4.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon5 = new JLabel(new ImageIcon(knightf));
                newicon5.setText(Integer.toString(countDEAD("knight",FirePlayer)));
                newicon5.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon6 = new JLabel(new ImageIcon(lavaBeast));
                newicon6.setText(Integer.toString(countDEAD("lavabeast",FirePlayer)));
                newicon6.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon7 = new JLabel(new ImageIcon(magef));
                newicon7.setText(Integer.toString(countDEAD("mage",FirePlayer)));
                newicon7.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon8 = new JLabel(new ImageIcon(scoutf));
                newicon8.setText(Integer.toString(countDEAD("scout",FirePlayer)));
                newicon8.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon9 = new JLabel(new ImageIcon(slayerf));
                newicon9.setText(Integer.toString(countDEAD("slayer",FirePlayer)));
                newicon9.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon10 = new JLabel(new ImageIcon(sorceressf));
                newicon10.setText(Integer.toString(countDEAD("sorceress",FirePlayer)));
                newicon10.setFont(new Font("Verdana", Font.BOLD,40));
                graphics.captures_panel.remove(icon1);
                graphics.captures_panel.remove(icon2);
                graphics.captures_panel.remove(icon3);
                graphics.captures_panel.remove(icon4);
                graphics.captures_panel.remove(icon5);
                graphics.captures_panel.remove(icon6);
                graphics.captures_panel.remove(icon7);
                graphics.captures_panel.remove(icon8);
                graphics.captures_panel.remove(icon9);
                graphics.captures_panel.remove(icon10);
                graphics.captures_panel.add(newicon1,0);
                graphics.captures_panel.add(newicon2,1);
                graphics.captures_panel.add(newicon3,2);
                graphics.captures_panel.add(newicon4,3);
                graphics.captures_panel.add(newicon5,4);
                graphics.captures_panel.add(newicon6,5);
                graphics.captures_panel.add(newicon7,6);
                graphics.captures_panel.add(newicon8,7);
                graphics.captures_panel.add(newicon9,8);
                graphics.captures_panel.add(newicon10,9);
                totalNum.setText("  "+Integer.toString(FirePlayer.getDeadTroopSize()));
                break;
            case FIRE:
                JLabel newicon11 = new JLabel(new ImageIcon(beastRideri));
                newicon11.setText(Integer.toString(countDEAD("beastRider",IcePlayer)));
                newicon11.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon22 = new JLabel(new ImageIcon(dragoni));
                newicon22.setText(Integer.toString(countDEAD("dragon",IcePlayer)));
                newicon22.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon33 = new JLabel(new ImageIcon(dwarfi));
                newicon33.setText(Integer.toString(countDEAD("dwarf",IcePlayer)));
                newicon33.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon44 = new JLabel(new ImageIcon(elfi));
                newicon44.setText(Integer.toString(countDEAD("elf",IcePlayer)));
                newicon44.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon55 = new JLabel(new ImageIcon(knighti));
                newicon55.setText(Integer.toString(countDEAD("knight",IcePlayer)));
                newicon55.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon66 = new JLabel(new ImageIcon(yeti));
                newicon66.setText(Integer.toString(countDEAD("yeti",IcePlayer)));
                newicon66.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon77 = new JLabel(new ImageIcon(magei));
                newicon77.setText(Integer.toString(countDEAD("mage",IcePlayer)));
                newicon77.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon88 = new JLabel(new ImageIcon(scouti));
                newicon88.setText(Integer.toString(countDEAD("scout",IcePlayer)));
                newicon88.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon99 = new JLabel(new ImageIcon(slayeri));
                newicon99.setText(Integer.toString(countDEAD("slayer",IcePlayer)));
                newicon99.setFont(new Font("Verdana", Font.BOLD,40));
                JLabel newicon100 = new JLabel(new ImageIcon(sorceressi));
                newicon100.setText(Integer.toString(countDEAD("sorceress",IcePlayer)));
                newicon100.setFont(new Font("Verdana", Font.BOLD,40));
                graphics.captures_panel.remove(icon1);
                graphics.captures_panel.remove(icon2);
                graphics.captures_panel.remove(icon3);
                graphics.captures_panel.remove(icon4);
                graphics.captures_panel.remove(icon5);
                graphics.captures_panel.remove(icon6);
                graphics.captures_panel.remove(icon7);
                graphics.captures_panel.remove(icon8);
                graphics.captures_panel.remove(icon9);
                graphics.captures_panel.remove(icon10);
                graphics.captures_panel.add(newicon11,0);
                graphics.captures_panel.add(newicon22,1);
                graphics.captures_panel.add(newicon33,2);
                graphics.captures_panel.add(newicon44,3);
                graphics.captures_panel.add(newicon55,4);
                graphics.captures_panel.add(newicon66,5);
                graphics.captures_panel.add(newicon77,6);
                graphics.captures_panel.add(newicon88,7);
                graphics.captures_panel.add(newicon99,8);
                graphics.captures_panel.add(newicon100,9);
                totalNum.setText("    " + Integer.toString(IcePlayer.getDeadTroopSize()));
                break;

        }

    }
}

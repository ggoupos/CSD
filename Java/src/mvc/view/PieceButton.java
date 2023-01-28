package mvc.view;

import mvc.model.piece.ImmovablePiece;
import mvc.model.piece.Piece;
import mvc.model.playerTeam.playerTeam;

import javax.swing.*;
import java.awt.*;

import static mvc.model.playerTeam.playerTeam.NONE;

public class PieceButton extends JButton {
    private int posX;
    private int posY;
    private int rescues;
    private Piece ButtonPiece;
    private playerTeam team;
    private boolean canBePressed;
    private boolean canBeAttacked;
    private Image faceUpImg,faceDownImg,turnImage;

    /**
     * <p>Constructor: Creates a new instance of a PieceButton</p>
     */
    public PieceButton(){
        super();
        ButtonPiece = new ImmovablePiece(-1,"empty");
        team = NONE;
        rescues = 0;
    }

    /**
     * <p>Accessor: Return the number of rescues of the piece button</p>
     * @return The number of rescues of the piece Button
     */
    public int getRescues(){
        return rescues;
    }

    /**
     * <p>Transformer: Increase the number of rescues by 1</p>
     * <p>PostCondition: The number of rescues is increased by 1</p>
     */
    public void increaseRescues(){
        rescues++;
    }

    /**
     * <p>Transformer: Set the number of rescues</p>
     * <p>PostCondition: The number of rescues are set according to the param value</p>
     * @param value The number of rescues
     */
    public void setRescues(int value){
        rescues = value;
    }

    /**
     * <p>Transformer: Set the faceup Image of the piece Button</p>
     * <p>PostCondition: The faceUpImg has been set</p>
     * @param faceup The image we want for the faceUpImg
     */
    public void setFaceUpImg(Image faceup){
        faceUpImg = faceup;
    }

    /**
     * <p>Accessor: Returns the faceUpImg</p>
     * @return The faceUpImg
     */
    public Image getFaceUpImg(){
        return faceUpImg;
    }
    /**
     * <p>Accessor: Returns the faceDownImg</p>
     * @return The faceDownImg
     */
    public Image getFaceDownImg(){
        return faceDownImg;
    }
    /**
     * <p>Accessor: Returns the TurnImg</p>
     * @return The TurnImg
     */
    public Image getTurnImg(){
        return turnImage;
    }

    /**
     * <p>Transformer: Set the faceDown Image of the piece Button</p>
     * <p>PostCondition: The faceDownImg has been set</p>
     * @param facedown The image we want for the faceDownImg
     */
    public void setFaceDownImg(Image facedown){
        faceDownImg = facedown;
    }
    /**
     * <p>Transformer: Set the TurnImage Image of the piece Button and sets the icon of the image to the button</p>
     * @param img The image we want for the TurnImage
     */
    public void setTurnImg(Image img){
        turnImage = img;
        Image newimg = turnImage.getScaledInstance( 150, 180,  java.awt.Image.SCALE_SMOOTH ) ;
        this.setIcon(new ImageIcon(newimg));
    }
    /**
     * <p>Transformer: Sets the button's X coordinate</p>
     * <p>PostCondition: The coordinate has been set</p>
     * @param value X Coordinate of the button
     */
    public void setXpos(int value){
        posX = value;
    }
    /**
     * <p>Transformer: Sets the button's Y coordinate</p>
     * <p>PostCondition: The coordinate has been set</p>
     * @param value Y Coordinate of the button
     */
    public void setYpos(int value){
        posY = value;
    }

    /**
     * <p>Accessor: Return the button's X coordinate</p>
     * <p>PostCondition: The coordinate has been returned</p>
     * @return The X coordinate
     */
    public int getXpos(){
        return posX;
    }
    /**
     * <p>Accessor: Return the button's Y coordinate</p>
     * <p>PostCondition: The coordinate has been returned</p>
     * @return The Y coordinate
     */
    public int getYpos(){
        return posY;
    }

    /**
     * <p>Transformer: Sets the ButtonPiece variable</p>
     * <p>PreCondition: piece must be != null</p>
     * <p>PostCondition: The ButtonPiece variable has been set</p>
     * @param piece The piece that we want to assign to our button
     */
    public void setButtonPiece(Piece piece){
        ButtonPiece = piece;
    }

    /**
     * <p>Accessor: Get the button's piece</p>
     * <p>PostCondition: The button's piece has been returned</p>
     * @return The button's piece
     */
    public Piece getButtonPiece(){
        return ButtonPiece;
    }

    /**
     * <p>Transformer: Sets the team of the button (ice or fire)</p>
     * <p>PostCondition: The team of the button has been set</p>
     * @param Team The team of the button
     */
    public void setPlayerTeam(playerTeam Team){
        team = Team;
    }

    /**
     * <p>Accessor: Returns the team that the button belongs to (ice or fire)</p>
     * <p>PostCondition: The team of the button has been returned</p>
     * @return the team of the button (ice or fire)
     */
    public playerTeam getPlayerTeam(){
        return team;
    }

    /**
     * <p>Transformer: Set the CanBeAttacked variable</p>
     * <p>PostCondition: The CanBeAttacked variable has been set</p>
     * @param value The value of the CanBeAttacked variable
     */
    public void setCanBeAttacked(boolean value){
        canBeAttacked = value;
    }
    /**
     * <p>Transformer: Set the CanBePressed variable</p>
     * <p>PostCondition: The CanBePressed variable has been set</p>
     * @param value The value of the CanBePressed variable
     */
    public void setCanBePressed(boolean value){
       canBePressed = value;
    }

    /**
     * <p>Observer: Checks if the button can be attacked</p>
     * <p>PostCondition: The check has been made</p>
     * @return true, if it can be attacked, false otherwise
     */
    public boolean getCanBeAttacked(){
        return canBeAttacked;
    }
    /**
     * <p>Observer: Checks if the button can be pressed</p>
     * <p>PostCondition: The check has been made</p>
     * @return true, if it can be pressed, false otherwise
     */
    public boolean getCanBePressed(){
        return canBePressed;
    }

}


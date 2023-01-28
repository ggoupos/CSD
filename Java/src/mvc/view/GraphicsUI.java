package mvc.view;

import mvc.controller.Controller;
import mvc.model.playerTeam.playerTeam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static mvc.model.playerTeam.playerTeam.*;

/**
 * This class represents the main screen of the game
 */
public class GraphicsUI extends JFrame {


    public PieceButton[][] piece_but = new PieceButton[8][10];

    private final JPanel piece_panel = new JPanel();
    public JPanel info_panel = new JPanel();
    public JPanel medium_panel = new JPanel();
    public JPanel bottom_panel = new JPanel();
    public JPanel captures_panel = new JPanel();

    private final Controller controller;

    private final PieceListener piece_action;
    private boolean isPieceSelected;
    private PieceButton initial_button;
    /**
     * <p>Constructor: Creates a new windows and initializes the appropriate buttons and panels</p>
     */
    public GraphicsUI() throws IOException {
        super("Stratego");
        setSize(2000,1500);
        setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.piece_panel.setLayout(new GridLayout(8,10));
        this.info_panel.setLayout(new GridLayout(3,1));
        info_panel.setBorder(BorderFactory.createLineBorder(Color.black));

        isPieceSelected = false;
        piece_action = new PieceListener();
        controller = new Controller(this);
        startingScreen();
    }

    /**
     * <p>Transformer: Creates the starting screen of the game(with all the buttons and panels done)</p>
     */
    private void startingScreen() throws IOException {
        String[] bluearmy = {"scout","dragon","yeti","mage","trap","knight","beastRider","knight","elf","beastRider","sorceress","beastRider",
                "sorceress","yeti","elf","dwarf","trap","dwarf","dwarf","scout","trap","scout",
                 "slayer","trap","scout","trap","dwarf","trap","flag","dwarf"};
        String[] redarmy = {"dragon","trap","knight","beastRider","sorceress","trap","beastRider","scout",
                "sorceress","lavaBeast","beastRider","lavaBeast","trap","elf","scout","elf","dwarf","trap","dwarf"
                ,"mage","dwarf","trap","dwarf","scout","scout","slayer","trap","flag","dwarf","knight"};
        //int r = 0;
        Random random = new Random(System.currentTimeMillis());
        for(int i =0; i< 8;i++){
          for(int j =0; j<10;j++){
              int r = Math.abs((random.nextInt()) % 30);

              piece_but[i][j] = new PieceButton();
              piece_but[i][j].addMouseListener(piece_action);

              if(i <3) {
                  if(Objects.equals(bluearmy[r], "error")){
                      int k = 0;
                      while(Objects.equals(bluearmy[k], "error")){
                          k++;
                      }
                      r = k;
                  }
                  controller.initPieceButton(piece_but[i][j], bluearmy[r], i, j, ICE);
                  piece_but[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                  piece_panel.add(piece_but[i][j]);
                  bluearmy[r] = "error";
              }
              else if (i>4){
                  if(Objects.equals(redarmy[r], "error")){
                      int k = 0;
                      while(Objects.equals(redarmy[k], "error")){
                          k++;
                      }
                      r = k;
                  }
                  controller.initPieceButton(piece_but[i][j], redarmy[r], i, j, FIRE);

                  piece_but[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                  piece_panel.add(piece_but[i][j]);
                  redarmy[r] = "error";
              }
              else {
                  controller.initPieceButton(piece_but[i][j], "empty", i, j, NONE);
                  piece_panel.add(piece_but[i][j]);
                  piece_but[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
              }
              if(i == 3){
                  if(j == 2 || j == 3 || j ==6 || j==7){
                      controller.initPieceButton(piece_but[i][j], "no", i, j, NONE);
                      piece_panel.add(piece_but[i][j]);
                  }
              }
              else if( i == 4) {
                  if (j == 2 || j == 3 || j == 6 || j == 7) {
                      controller.initPieceButton(piece_but[i][j], "no", i, j, NONE);
                      piece_panel.add(piece_but[i][j]);
                  }
              }
          }
        }
        //
        //

        int knightFlag = 0;
        int dwarfFlag = 0;
        int scoutFlag = 0;
        int trapFlag = 0;
        int sorceressFlag = 0;
        int yetiFlag = 0;
        int lavabeastFlag = 0;
        int elfFlag = 0;
        int beastRiderFlag = 0;

        controller.write_specialRules(this);

        if(controller.getReducedArmy()){
            for(int i = 0;i<3;i++){
                for(int j =0;j<10;j++){
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "knight" && knightFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        knightFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "beastRider" && beastRiderFlag < 2){
                        controller.createEmptyButton(piece_but[i][j]);
                        beastRiderFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "sorceress" && sorceressFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        sorceressFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "yeti" && yetiFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        yetiFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "elf" && elfFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        elfFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "dwarf" && dwarfFlag < 3){
                        controller.createEmptyButton(piece_but[i][j]);
                        dwarfFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "scout" && scoutFlag < 2){
                        controller.createEmptyButton(piece_but[i][j]);
                        scoutFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "trap" && trapFlag < 3){
                        controller.createEmptyButton(piece_but[i][j]);
                        trapFlag++;
                    }
                }
            }
            knightFlag = 0;
            dwarfFlag = 0;
            scoutFlag = 0;
            trapFlag = 0;
            sorceressFlag = 0;
            elfFlag = 0;
            beastRiderFlag = 0;

            for(int i = 5;i<8;i++){
                for(int j =0;j<10;j++){
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "knight" && knightFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        knightFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "beastRider" && beastRiderFlag < 2){
                        controller.createEmptyButton(piece_but[i][j]);
                        beastRiderFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "sorceress" && sorceressFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        sorceressFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "lavaBeast" && lavabeastFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        lavabeastFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "elf" && elfFlag < 1){
                        controller.createEmptyButton(piece_but[i][j]);
                        elfFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "dwarf" && dwarfFlag < 3){
                        controller.createEmptyButton(piece_but[i][j]);
                        dwarfFlag++;
                    }
                    if(piece_but[i][j].getButtonPiece().getPieceName() == "scout" && scoutFlag < 2){
                        controller.createEmptyButton(piece_but[i][j]);
                        scoutFlag++;
                    }
                        if(piece_but[i][j].getButtonPiece().getPieceName() == "trap" && trapFlag < 3){
                        controller.createEmptyButton(piece_but[i][j]);
                        trapFlag++;
                    }
                }
            }
        }

        controller.turn.setPlayingTeam(ICE);
        initInfoPanel();
        //info_panel.add(top,BorderLayout.NORTH);
        //info_panel.add(mid,BorderLayout.AFTER_LAST_LINE);
        this.add(piece_panel);
        this.add(info_panel,BorderLayout.EAST);
        setVisible(true);
    }

    private void initInfoPanel() throws IOException {
        JLabel titleStats = new JLabel("     Στατιστικά                      ");
        titleStats.setFont(new Font("Verdana",Font.BOLD,45));
        JLabel playerTurn = new JLabel("Player ICE turn");
        playerTurn.setFont(new Font("Verdana",Font.PLAIN,30));
        JLabel successAttacks = new JLabel("Ποσοστό επιτ. επίθεσης: -");
        successAttacks.setFont(new Font("Verdana",Font.PLAIN,30));
        JLabel rescuesTurn = new JLabel("Διασώσεις: 0                        Γύρος: 1");
        rescuesTurn.setFont(new Font("Verdana",Font.PLAIN,30));
        medium_panel.setLayout(new GridLayout(4,1));
        medium_panel.add(titleStats);
        medium_panel.add(playerTurn);
        medium_panel.add(successAttacks);
        medium_panel.add(rescuesTurn);
        info_panel.add(medium_panel);


        JLabel captures = new JLabel("     Aιχμαλωτίσεις     ");
        captures.setFont(new Font("Verdana",Font.BOLD,45));
        bottom_panel.setLayout(new BorderLayout());
        bottom_panel.add(captures,BorderLayout.PAGE_START);

        captures_panel.setLayout(new GridLayout(4,3,20,20));

        Image image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/beastRiderR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon1 = new JLabel(new ImageIcon(image));
        icon1.setFont(new Font("Verdana", Font.BOLD,40));
        icon1.setText("0");
        captures_panel.add(icon1);

        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/dragonR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon2 = new JLabel(new ImageIcon(image));
        icon2.setFont(new Font("Verdana", Font.BOLD,40));
        icon2.setText("0");
        captures_panel.add(icon2);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/elfR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon3 = new JLabel(new ImageIcon(image));
        icon3.setText("0");
        icon3.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon3);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/dwarfR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon4 = new JLabel(new ImageIcon(image));
        icon4.setText("0");
        icon4.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon4);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/knightR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon5 = new JLabel(new ImageIcon(image));
        icon5.setText("0");
        icon5.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon5);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/lavaBeastR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon6 = new JLabel(new ImageIcon(image));
        icon6.setText("0");
        icon6.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon6);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/mageR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon7 = new JLabel(new ImageIcon(image));
        icon7.setText("0");
        icon7.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon7);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/scoutR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon8 = new JLabel(new ImageIcon(image));
        icon8.setText("0");
        icon8.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon8);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/slayerR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon9 = new JLabel(new ImageIcon(image));
        icon9.setText("0");
        icon9.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon9);
        image = ImageIO.read(Objects.requireNonNull(getClass().
                getResource("images/RedPieces/sorceressR.png"))).getScaledInstance(90,103,Image.SCALE_SMOOTH);
        JLabel icon10 = new JLabel(new ImageIcon(image));
        icon10.setText("0");
        icon10.setFont(new Font("Verdana", Font.BOLD,40));
        captures_panel.add(icon10);


        JLabel total = new JLabel("   Total: ");
        total.setFont(new Font("Verdana",Font.BOLD,35));
        captures_panel.add(total);
        JLabel totalNumber = new JLabel("   0  ");
        totalNumber.setFont(new Font("Verdana",Font.BOLD,55));
        captures_panel.add(totalNumber);

        bottom_panel.add(captures_panel);
        info_panel.add(bottom_panel);

    }


    /**
     * <p>This class checks if move or attack is possible, and if it is, make the necessary changes</p>
     */
    private class PieceListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            PieceButton destination_button = (PieceButton) mouseEvent.getSource();
            int option = 0;
            if(isPieceSelected){
                if(controller.check_move(initial_button,destination_button,piece_but)) {
                    initial_button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    option = controller.makeAttack(initial_button,destination_button);
                    switch (option){
                        case 0:
                            try {
                                controller.createEmptyButton(initial_button);
                                controller.createEmptyButton(destination_button);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 1:

                            controller.swapPiecesButtons(initial_button,destination_button);
                            if(controller.canRescue(destination_button)){
                                try {
                                    controller.makeRescue(destination_button,piece_but);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            try {
                                controller.createEmptyButton(initial_button);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case -1:
                            try {
                                controller.createEmptyButton(initial_button);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 2:
                            //finish the game
                            JOptionPane.showMessageDialog(null,initial_button.getPlayerTeam() + " WON","End",1);
                            System.exit(0);
                            break;

                    }
                    controller.de_highlightButtons(piece_but);
                    System.out.println("yes");
                    isPieceSelected = false;
                    //change turns
                    controller.changeFaceOfButtons(piece_but);
                    controller.turn.changeTurn();
                    //CHECK FOR MOVES
                    int counter = 0;
                    for(int i =0;i<8;i++){
                        for(int j = 0;j<10;j++){
                            if(piece_but[i][j].getButtonPiece().getPieceName()!="empty" && piece_but[i][j].getButtonPiece().getPieceName()!="no"
                             && piece_but[i][j].getButtonPiece().getPieceName()!="trap" && piece_but[i][j].getButtonPiece().getPieceName() !="flag"
                             && piece_but[i][j].getPlayerTeam() == controller.turn.whoIsPlaying()){
                                if(!controller.isOutOfMoves(piece_but[i][j],piece_but)) {
                                    counter++;
                                }
                            }
                        }
                    }
                    if(counter==0) {
                        JOptionPane.showMessageDialog(null,controller.turn.whoIsPlaying() + " LOST (no valid moves left) ","End",1);
                        System.exit(0);
                    }

                    controller.write_turn(GraphicsUI.this);
                    try {
                        controller.write_captures(GraphicsUI.this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    isPieceSelected = false;
                    destination_button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    controller.de_highlightButtons(piece_but);
                }
            }
            else if(!isPieceSelected && destination_button.getCanBePressed()){
                isPieceSelected = true;
                initial_button = destination_button;
                destination_button.setBorder(BorderFactory.createLineBorder(Color.GREEN,3));
                controller.highlightButtons(initial_button,piece_but);
            }
        }
        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
}

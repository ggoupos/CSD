package mvc.model.turn;

import mvc.model.playerTeam.playerTeam;

import static mvc.model.playerTeam.playerTeam.FIRE;
import static mvc.model.playerTeam.playerTeam.ICE;

public class Turn {
    private int numberRounds = 1;
    private playerTeam playingTeam;

    /**
     * <p>Accessor: Returns the number of total rounds</p>
     * <p>PostCondition: The number of total rounds has been returned</p>
     * @return The number of total rounds
     */
    public int getNumberofRounds(){
        return numberRounds;
    }

    /**
     * <p>Transformer: Increased the total number of rounds by 1</p>
     * <p>PostCondition: The variable numberRounds will be increased by 1</p>
     */
    public void increaseRounds(){
        numberRounds++;
    }

    /**
     * <p>Transformer: Change the turn of the players</p>
     * <p>PostCondition: The turn of the players has been changed</p>
     */
    public void changeTurn(){
        if(playingTeam == ICE){
            playingTeam = FIRE;
        }
        else{
            playingTeam = ICE;
        }
        increaseRounds();
    }

    /**
     * <p>Transformer: Sets the variable playingTeam according to who is playing</p>
     * <p>PostCondition: The playingTeam variable has been set</p>
     */
    public void setPlayingTeam(playerTeam team){
        playingTeam = team;
    }

    /**
     * <p>Accessor: Returns who player is playing</p>
     * @return The player who is playing
     */
    public playerTeam whoIsPlaying(){
        return playingTeam;
    }

}

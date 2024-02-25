package application;

import java.time.LocalDate;

public class Game {
	
	public Player winner;
	
	public Player loser;
	
	public LocalDate date;

	public int winnerScore;
	
	public int loserScore;
	
	//Whose turn is it
	public int turnPlayerNumber = 1;
	
	public int playerOneTurnScore = 0;

	public int playerTwoTurnScore = 0;

	public int playerOneScore = 0;

	public int playerTwoScore = 0;
	
    public boolean isPlayerOneWinner() {
        if (playerOneScore > playerTwoScore) {
        	return true;
        } else {
        	return false;
        }
    }
    
	public Player getWinner() {
		return winner;
	}

	public Player getLoser() {
		return loser;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public int getPlayerOneScore() {
		return playerOneScore;
	}
	
	public int getPlayerTwoScore() {
		return playerTwoScore;
	}
	
	public int getWinnerScore() {
		return winnerScore;
	}
	
	public int getLoserScore() {
		return loserScore;
	}
		
}

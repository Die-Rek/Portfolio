package gui;

import domain.GameBoard;

/**
 * Interface for Gameboard Observers
 * 
 * @author micha
 *
 */
public interface GameboardObserver {
	/**
	 * Updates the gameboard
	 * @param gb the game state as gameboard
	 */
	public void update(GameBoard gb);
}

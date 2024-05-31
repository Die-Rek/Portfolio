package domain;

import gui.GameboardObserver;

/**
 * Implementation of a Viewing strategy. Uses The Gui.
 * @author micha
 *
 */
public class GuiView implements ViewStrategy {

	@Override
	/**
	 * Notifies the Gameboard observers in the gui to update the view
	 * 
	 * @param c the controller corresponding to the game
	 */
	public void updateView(Controller c) {
		GameBoard current_state = c.getGameBoard().clone();
		for(GameboardObserver gbo : c.getGameboardObservers()) {
			gbo.update(current_state);
		}
	}

}

package domain;

/**
 * View Strategy Interface for selecting different viewing methods for the gameboard
 * 
 * @author micha
 *
 */
interface ViewStrategy {
	/**
	 * updates the view
	 * @param c the controller corresponding to this game
	 */
	void updateView(Controller c);
}

package domain;

/**
 * ConsoleView-Strategy is used to print the Game State to the console.
 * @author micha
 *
 */
public class ConsoleView implements ViewStrategy {
	
	@Override
	/**
	 * This Viewing strategy creates a clone of the current gameboad and prints it to the console.
	 * 
	 * @param c reference to the controller to access gameboard.
	 */
	public void updateView(Controller c) {
		GameBoard current_state = c.getGameBoard().clone();
		System.out.print(current_state.toString());
		System.out.print(System.lineSeparator());
	}

}

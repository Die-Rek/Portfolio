package domain;

import util.Tuple;

/**
 * Crate is a class that extends Box. Crate is an object in the Sokoban game that the player is supposed to put in the right spot on the board.
 * 
 * @author Senne Dierick
 * @author michael unterberger
 *
 */
public class Crate{
	
	private Tuple<Integer> pos;
	private boolean in_position;
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -9028642214441364470L;


	/**
	 * Constructor of the Class
	 * Creates a new crate with a given position and information if the crate is on a marked space.
	 * 
	 * @param pos position of the crate
	 * @param in_position specifies if crate is on a marked tile
	 */
	public Crate(Tuple<Integer> pos, boolean in_position) {
		this.pos = pos;
		this.in_position = in_position;
	}

	/**
	 * @return the position of the crate
	 */
	public Tuple<Integer> getPos() {
		return pos;
	}

	/**
	 * @param pos the position to set
	 */
	public void setPos(Tuple<Integer> pos) {
		this.pos = pos;
	}

	/**
	 * @return the in_position
	 */
	public boolean isIn_position() {
		return in_position;
	}

	/**
	 * @param in_position the in_position to set
	 */
	public void setIn_position(boolean in_position) {
		this.in_position = in_position;
	}
	
}

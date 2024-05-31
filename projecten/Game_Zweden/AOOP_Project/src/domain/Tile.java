package domain;

import java.io.Serializable;

import util.InvalidTileConfigException;

/**
 * The Tiles of which the gameboard consists of. Is kept very lightweight it only contains boolean fields
 * to identify the type of tile.
 * 
 * @author micha
 *
 */
public class Tile implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4915648115261390934L;
	private boolean is_wall;
	private boolean is_ppos;
	private boolean is_cpos;
	private boolean is_marked;

	/**
	 * 
	 * @return if the tile is the player-position
	 */
	public boolean is_ppos(){
		return is_ppos;
	}

	/**
	 * Sets the player-position flag
	 * 
	 * @param is_ppos if tile is player position
	 * @throws InvalidTileConfigException thrown if tile config is invalid
	 */
	public void set_ppos(boolean is_ppos) throws InvalidTileConfigException {
		verifyConfig();
		this.is_ppos = is_ppos;
	}
	
	/**
	 * 
	 * @return if the tile is a crate-positions
	 */
	public boolean is_cpos() {
		return is_cpos;
	}

	/**
	 * Sets the crate-position flag.
	 * 
	 * @param is_cpos if tile is crate position
	 * @throws InvalidTileConfigException thrown if tile config is invalid
	 */
	public void set_cpos(boolean is_cpos) throws InvalidTileConfigException {
		verifyConfig();
		this.is_cpos = is_cpos;
	}

	/**
	 * 
	 * @return if the tile is a wall
	 */
	public boolean is_wall() {
		return is_wall;
	}

	/**
	 * 
	 * @return if the tile is a marked space
	 */
	public boolean is_marked() {
		return is_marked;
	}
	
	/**
	 * Class Constructor
	 * sets the flags as passed and checks the validity of the configuration.
	 * 
	 * @param is_wall if tile is wall
	 * @param is_ppos if tile is player position
	 * @param is_cpos if tile is crate position
	 * @param is_marked if tile is marked
	 * @throws InvalidTileConfigException thrown if config of tile is invalid
	 */
	public Tile(boolean is_wall, boolean is_ppos, boolean is_cpos, boolean is_marked)
			throws InvalidTileConfigException {
		this.is_wall = is_wall;
		this.is_ppos = is_ppos;
		this.is_cpos = is_cpos;
		this.is_marked = is_marked;

		verifyConfig();
	}
	
	/**
	 * Checks if the config of this is valid, throws an exception if the configuration is faulty.
	 * @throws InvalidTileConfigException thrown if tile config is invalid
	 */
	private void verifyConfig() throws InvalidTileConfigException {
		if (is_wall && (is_ppos || is_cpos || is_marked)) {
			throw new InvalidTileConfigException(
					"Invalid Tile Configuration: Tile cannot be Wall and PPOS/CPOS/MARKED!");
		}
		if (is_ppos && is_cpos) {
			throw new InvalidTileConfigException("Invalid Tile Configuration: Tile cannot be PPOS and CPOS!");
		}

	}

	
	@Override
	/**
	 * Clones the tile.
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @return the string representation of the tile
	 */
	public String toString() {
		if (is_wall) {
			return "#";
		} else if (is_cpos) {
			if (is_marked)
				return "X";
			return "x";
		} else if (is_ppos) {
			return "@";
		} else {
			if (is_marked)
				return "O";
			else
				return " ";
		}
	}
}

package domain;

/**
 * Enum for possible actions the user can input into the program.
 * 
 * @author micha
 *
 */
public enum MoveType {
	/**
	 * Player walking on a path.
	 */
	WALK,
	/**
	 * Player pushing a Crate.
	 */
	PUSH,
	/**
	 * Player tries to push a blocked crate or tries to walk into a wall
	 */
	BLOCKED,
	/**
	 * Player pushes a crate on a marked tile
	 */
	PLACE,
	/**
	 * Player makes the winning move
	 */
	WIN,
	/**
	 * User clicks a button
	 */
	CLICK,
	/**
	 * No movement placeholder.
	 */
	NONE
}

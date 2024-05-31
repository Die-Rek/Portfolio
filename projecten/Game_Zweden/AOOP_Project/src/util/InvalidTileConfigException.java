package util;

/**
 * Exception for Tile Class when the tile is configured in a faulty way.
 * 
 * @author micha
 *
 */
public class InvalidTileConfigException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7980630687477589801L;

	/**
	 * Class Constructor
	 * Creates the Exception with passed String as message.
	 * 
	 * @param errMessage the error message that can be printed
	 */
	public InvalidTileConfigException(String errMessage) {
		super(errMessage);
	}
}

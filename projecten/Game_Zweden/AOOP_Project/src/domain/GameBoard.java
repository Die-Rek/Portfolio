package domain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import util.Tuple;

/**
 * game board is the main class of the game holding the board. This board can be
 * saved and loaded referred to as levels. .
 * 
 * @author Senne Dierick
 *
 */
public class GameBoard implements Serializable, Cloneable { // Will support saving of levels

	private static final long serialVersionUID = 1L;
	private Tile[][] board;
	private Tuple<Integer> dimensions;
	private static String sennePathPrefix;

	/**
	 * 
	 */
	public static final int AMOUNTOFLEVELS = 16;

	/**
	 * Constructor Initializes the Object's fields to null.
	 */
	public GameBoard() {
		board = null;
		dimensions = null;
	}

	/**
	 * Initializes the board. This method sets the dimensions of the board and
	 * initializes the Tile-Array
	 * 
	 * @param columns number of columns of gameBoard
	 * @param rows    number of rows of gameBoard
	 */
	public void createBoard(int columns, int rows) {
		this.dimensions = new Tuple<Integer>(rows, columns);
		board = new Tile[dimensions.getKey()][this.dimensions.getVal()];
	}

	/**
	 * Returns the state of the board as a 2D-Array of tiles.
	 * 
	 * @return board state of board
	 */
	public Tile[][] getBoard() {
		return this.board;
	}

	/**
	 * Sets a tile adressed by the pos parameter
	 * 
	 * @param tile the pre-configured tile
	 * @param pos the position of the tile
	 */
	public void setTile(Tile tile, Tuple<Integer> pos) {
		board[pos.getKey()][pos.getVal()] = tile;
	}

	/**
	 * Gets a tile corresponding to the pos parameter (coordinates)
	 * 
	 * @param pos the position of the tile
	 * @return the specified tile
	 */
	public Tile getTile(Tuple<Integer> pos) {
		if(pos.getKey() < 0 || pos.getKey() > dimensions.getKey() - 1) {
			return null;
		} else if(pos.getVal() < 0 || pos.getVal() > dimensions.getVal() - 1){
			return null;
		}
		return board[pos.getKey()][pos.getVal()];
	}

	/**
	 * Function to print gameBoard to console in a slightly fancier way.
	 * 
	 * @return string that represents the board
	 */
	public String toString() {
		String lineSeparator = System.lineSeparator();
		StringBuilder sb = new StringBuilder();
		int width = dimensions.getKey();

		sb.append("▛");
		for (int i = 1; i < width; i++) {
			sb.append("-");
		}
		sb.append("▜").append(lineSeparator);

		for (Tile[] row : board) {
		    sb.append("|");
		    for(Tile t : row) {
		    	sb.append(t.toString());
		    }
		    sb.append("▕").append(lineSeparator);
		}

		sb.append("▙");
		for (int i = 1; i < width; i++) {
			sb.append("-");
		}
		sb.append("▟").append(lineSeparator);

		return sb.toString();
	}

	/**
	 * A way to write the gameBoard to a textFile corresponding to it's level.
	 * 
	 * @param level the level number
	 * @param prefix set for special pathing on different machines
	 * @throws IOException thrown if writing fails
	 * @throws ClassNotFoundException thrown if writing fails
	 */
	public final void writeObject(int level, boolean prefix) throws IOException, ClassNotFoundException {
		if(prefix)
			sennePathPrefix = "AOOP_Project/";
		else
			sennePathPrefix = "";
		FileOutputStream fileOutputStream = new FileOutputStream(String.format(sennePathPrefix + "src/Levels/level%d.txt", level));
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(this);
		objectOutputStream.flush();
		objectOutputStream.close();
	}

	/**
	 * A way to load a gameBoard from a text File
	 * 
	 * @param level the chosen level
	 * @param prefix set for special pathing on different machines
	 * @return board a gameBoard loaded from the file
	 * @throws IOException thrown if reading fails
	 * @throws ClassNotFoundException thrown if reading fails
	 */
	public static final GameBoard readObject(int level, boolean prefix) throws IOException, ClassNotFoundException {
		if(prefix)
			sennePathPrefix = "AOOP_Project/";
		else
			sennePathPrefix = "";
		FileInputStream fileInputStream = new FileInputStream(String.format(sennePathPrefix + "src/Levels/level%d.txt", level));
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		GameBoard board = (GameBoard) objectInputStream.readObject();
		return board;
	}
	
	/**
	 * Is the clonig method of Gameboard, mainly used when passing around a gameboard so that
	 * the receiving end cannot interfere with a running game. Creates a DeepCopy.
	 * 
	 * @return GameBoard a clone of this gameboard
	 */
	public GameBoard clone() {
		try {
			GameBoard cloned = (GameBoard) super.clone();
			if (board == null)
				cloned.board = null;
			else {
				Tile[][] clonedBoard = new Tile[board.length][board[0].length];
				int r = 0, c = 0;
				for (Tile[] row : board) {
					for (Tile tile : row) {
						clonedBoard[r][c] = (Tile) tile.clone();
						c++;
					}
					c = 0;
					r++;
				}
				cloned.board = clonedBoard;
			}

			if (dimensions == null)
				cloned.dimensions = null;
			else
				cloned.dimensions = dimensions.clone();

			return cloned;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}

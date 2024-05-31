package LevelCreater;

import java.io.IOException;

import domain.GameBoard;
import domain.Tile;
import util.InvalidTileConfigException;
import util.Tuple;

/**
 * This class is made for creating boards and writing them to a file so we can
 * load in levels easily.
 * 
 * 
 * @author Senne Dierick
 *
 */
public class Creater {
	
	/**
	 * Main method for creating level 1
	 * @param args command line arguments
	 */
	public static void main(String[] args) {

		boolean sennePrefix = false;
		GameBoard gameBoard = new GameBoard();
		gameBoard.createBoard(8, 9);
		
		try {
			Tile a_wall = new Tile(true, false, false, false);
			Tile a_blank = new Tile(false, false, false, false);
			Tile a_mark = new Tile(false, false, false, true);
			Tile a_crate = new Tile(false, false, true, false);
			Tile a_crate_in_pos = new Tile(false, false, true, true);

			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(0, 0));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(0, 1));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(0, 2));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(0, 3));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(0, 4));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(0, 5));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(0, 6));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(0, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(1, 0));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(1, 1));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(1, 2));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(1, 3));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(1, 4));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(1, 5));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(1, 6));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(1, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(2, 0));
			gameBoard.setTile((Tile) a_mark.clone(), new Tuple<Integer>(2, 1));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(2, 2));
			gameBoard.getTile(new Tuple<Integer>(2, 2)).set_ppos(true);
			gameBoard.setTile((Tile) a_crate.clone(), new Tuple<Integer>(2, 3));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(2, 4));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(2, 5));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(2, 6));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(2, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(3, 0));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(3, 1));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(3, 2));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(3, 3));
			gameBoard.setTile((Tile) a_crate.clone(), new Tuple<Integer>(3, 4));
			gameBoard.setTile((Tile) a_mark.clone(), new Tuple<Integer>(3, 5));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(3, 6));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(3, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(4, 0));
			gameBoard.setTile((Tile) a_mark.clone(), new Tuple<Integer>(4, 1));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(4, 2));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(4, 3));
			gameBoard.setTile((Tile) a_crate.clone(), new Tuple<Integer>(4, 4));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(4, 5));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(4, 6));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(4, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(5, 0));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(5, 1));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(5, 2));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(5, 3));
			gameBoard.setTile((Tile) a_mark.clone(), new Tuple<Integer>(5, 4));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(5, 5));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(5, 6));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(5, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(6, 0));
			gameBoard.setTile((Tile) a_crate.clone(), new Tuple<Integer>(6, 1));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(6, 2));
			gameBoard.setTile((Tile) a_crate_in_pos.clone(), new Tuple<Integer>(6, 3));
			gameBoard.setTile((Tile) a_crate.clone(), new Tuple<Integer>(6, 4));
			gameBoard.setTile((Tile) a_crate.clone(), new Tuple<Integer>(6, 5));
			gameBoard.setTile((Tile) a_mark.clone(), new Tuple<Integer>(6, 6));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(6, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(7, 0));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(7, 1));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(7, 2));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(7, 3));
			gameBoard.setTile((Tile) a_mark.clone(), new Tuple<Integer>(7, 4));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(7, 5));
			gameBoard.setTile((Tile) a_blank.clone(), new Tuple<Integer>(7, 6));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(7, 7));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 0));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 1));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 2));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 3));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 4));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 5));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 6));
			gameBoard.setTile((Tile) a_wall.clone(), new Tuple<Integer>(8, 7));

		} catch (InvalidTileConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Invalid Tile Configuration!");
		}
		try {
			gameBoard.writeObject(1, sennePrefix);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

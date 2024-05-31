package domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gui.GameboardObserver;
import sound.ActionObserver;
import util.Tuple;
import util.InvalidTileConfigException;

/**
 * Controller is used to control every class. Keeps track of the Gameboard,
 * Player position, Crate Positions and the winning condition.
 * 
 * @author Senne Dierick, Michael Unterberger
 *
 */
public class Controller implements Serializable {

	private static final long serialVersionUID = -3140442330676441347L;
	private GameBoard gameBoard;
	private GameBoard initialSetup;
	private ArrayList<Crate> crates;
	private List<GameboardObserver> observers = new ArrayList<GameboardObserver>();
	private List<ActionObserver> moveObservers = new ArrayList<ActionObserver>();
	private ViewStrategy viewer;

	private int px;
	private int py;

	private int selectedLevel = 1;
	private boolean keyboardOn = true;
	private boolean gameWon;

	/**
	 * Class Constructor
	 */
	public Controller() {
		crates = new ArrayList<Crate>();
		this.viewer = new GuiView();
	}

	/**
	 * Sets the Viewing Strategy of the current State of the game.
	 * 
	 * @param vs the desired viewing strategy
	 */
	public void setViewStrategy(ViewStrategy vs) {
		this.viewer = vs;
		updateView();
	}

	/**
	 * This returns the Class type of the current ViewStrategy, it is used by other
	 * classes to determine which viewing strategy is in use at the moment.
	 * 
	 * @return Class type of the current viewerStrategy
	 */
	public Class<?> getViewerType() {
		return this.viewer.getClass();
	}

	/**
	 * Checks if the endConditions are met. It traverses through the crates array
	 * and checks if all the Crates are in position.
	 * 
	 * @return if game has been won
	 */
	public boolean checkEndConditions() {
		for (Crate crate : crates) {
			if (crate.isIn_position() == false) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * This function is called after the user chooses the level on a BeginScreen to
	 * create the level.
	 * 
	 * @param prefix set for special pathing on different machines
	 */
	public void loadBoard(boolean prefix) {
		gameBoard = new GameBoard();
		initialSetup = new GameBoard();
		gameWon = false;
		loadLevel(selectedLevel, prefix);
		updateView();
	}

	/**
	 * This function attaches a new GameboardObserver to the ObserverList. It will get notified when
	 * the Gameboard changes.
	 * 
	 * @param gb the gameboard observer to attach
	 */
	public void attachGameboardObserver(GameboardObserver gb) {
		observers.add(gb);
	}
	
	/**
	 * This function attaches an observer to the observer list, which get notified
	 * when the user inputs something.
	 * 
	 * @param ao ActionObserver that gets notified when the user inputs something
	 */
	public void attachMoveObserver(ActionObserver ao) {
		moveObservers.add(ao);
	}

	/**
	 * This function causes the currently used viewer to update the Game-View.
	 */
	public void updateView() {
		viewer.updateView(this);
	}

	/**
	 * Gets the gameBoard as a 2D-Array of tiles.
	 * 
	 * @return the gameBoard
	 */
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	/**
	 * Gets a list of the GameboardObservers. Is used by the GUI-viewing strategy.
	 * 
	 * @return the observers
	 */
	public List<GameboardObserver> getGameboardObservers() {
		return observers;
	}
	
	/**
	 * Notifies Action observers to output sounds
	 * 
	 * @param t the direction of movement
	 */
	private void playSound(MoveType t) {
		for(ActionObserver a : moveObservers) {
			a.playSound(t);
		}
	}
	
	/**
	 * This function moves the player to the right. It checks if there is a Crate to
	 * the right that he can push. Also if there is a wall the player can't move. 
	 * The crate and character position and view will be updated if the gamestate changes
	 * and it will be checked if the player won the game.
	 * 
	 * @param d direction of movement
	 */
	public void movePlayer(MoveDirection d) {
		MoveType sound = MoveType.NONE;
		if(gameWon)
			return;
		Tuple<Integer> currentTilePos = new Tuple<Integer>(py, px);
		int nextRow, nextNextRow;
		int nextColumn, nextNextColumn;
		
		switch(d) {
		case DOWN:
			nextRow = py + 1;
			nextColumn = px;
			nextNextRow = nextRow + 1;
			nextNextColumn = nextColumn;
			break;
		case LEFT:
			nextRow = py;
			nextColumn = px - 1;
			nextNextRow = nextRow;
			nextNextColumn = nextColumn - 1;
			break;
		case RIGHT:
			nextRow = py;
			nextColumn = px + 1;
			nextNextRow = nextRow;
			nextNextColumn = nextColumn + 1;
			break;
		case UP:
			nextRow = py - 1;
			nextColumn = px;
			nextNextRow = nextRow - 1;
			nextNextColumn = nextColumn;
			break;
		default:
			return;	
		}

		Tuple<Integer> nextTilePos = new Tuple<Integer>(nextRow, nextColumn);

		Tile currentTile = gameBoard.getTile(currentTilePos);
		Tile nextTile = gameBoard.getTile(nextTilePos);
		if (nextTile == null || nextTile.is_wall()) {
			playSound(MoveType.BLOCKED);
			return;
		} else if (nextTile.is_cpos()) {

			Tuple<Integer> nextNextTilePos = new Tuple<Integer>(nextNextRow, nextNextColumn);

			Tile nextNextTile = gameBoard.getTile(nextNextTilePos);
			if (nextNextTile == null || nextNextTile.is_wall() || nextNextTile.is_cpos()) {
				playSound(MoveType.BLOCKED);
				return;
			} else {
				for (Crate c : crates) {
					if (c.getPos().equal(nextTilePos)) {
						c.setPos(nextNextTilePos);
						if (nextNextTile.is_marked()) {
							c.setIn_position(true);
							sound = MoveType.CLICK;
						}
						else {
							c.setIn_position(false);
							sound = MoveType.PUSH;
						}
					}
				}
				try {
					nextNextTile.set_cpos(true);
					nextTile.set_cpos(false);
					nextTile.set_ppos(true);
					currentTile.set_ppos(false);
				} catch (InvalidTileConfigException e) {
					e.printStackTrace();
					System.out.print(e.getMessage() + System.lineSeparator());
				}

				px = nextColumn;
				py = nextRow;
			}
		} else {
			try {
				currentTile.set_ppos(false);
				nextTile.set_ppos(true);
				sound = MoveType.WALK;
			} catch (InvalidTileConfigException e) {
				e.printStackTrace();
				System.out.print(e.getMessage() + System.lineSeparator());
			}
			px = nextColumn;
			py = nextRow;
		}
		
		playSound(sound);
		updateView();
		if (checkEndConditions()) {
			System.out.println("You Won!");
			gameWon = true;
			playSound(MoveType.WIN);
		}
	}

	/**
	 * sets the currently selected level
	 * 
	 * @param selectedLevel currently selected level
	 */
	public void setSelectedLevel(int selectedLevel) {
		this.selectedLevel = selectedLevel;
	}

	/**
	 * returns the value representing if the user has selected if the keyboard
	 * should be used
	 * 
	 * @return if keybpoard is on
	 */
	public boolean isKeyboardOn() {
		return keyboardOn;
	}

	/**
	 * sets the variable for the use of keyboard
	 * 
	 * @param keyboardOn boolean that holds value for keyboard use
	 */
	public void setKeyboardOn(boolean keyboardOn) {
		this.keyboardOn = keyboardOn;
	}

	/**
	 * Loads a GameBoard referred to as level and loads it into gameBoard so the
	 * choosen level can be played. There is also a check for winning condition
	 * if the player safed a won game.
	 * 
	 * @param level choosen level
	 * @param prefix set for special pathing on different machines
	 */
	private void loadLevel(int level, boolean prefix) {
		try {
			gameBoard = GameBoard.readObject(level, prefix);
			readCratesAndPlayer(gameBoard);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialSetup = gameBoard.clone();
		
		if (checkEndConditions()) {
			System.out.println("You Won!");
			gameWon = true;
		}

	}

	/**
	 * Saves the current game state to a file
	 * 
	 * @param prefix set for special pathing on different machines
	 */
	public void saveGame(boolean prefix) {
		try {
			gameBoard.writeObject(0, prefix);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function reads player position and the position of crates from the Gameboard.
	 * This function is used when loading a level so that the controller can properly keep
	 * track of crates and player.
	 * 
	 * @param gb the gameboard to read the information from
	 */
	private void readCratesAndPlayer(GameBoard gb) {
		crates.clear();
		int r = 0;
		int c = 0;
		for (Tile[] row : gameBoard.getBoard()) {
			for (Tile tile : row) {
				if (tile.is_cpos() && tile.is_marked()) {
					crates.add(new Crate(new Tuple<Integer>(r, c), true));
				} else if (tile.is_cpos()) {
					crates.add(new Crate(new Tuple<Integer>(r, c), false));
				} else if (tile.is_ppos()) {
					px = c;
					py = r;
				}
				c++;
			}
			c = 0;
			r++;
		}
	}

	/**
	 * this function resets the Current games gameboard back to starting conditions. This
	 * is called when the user presses "Space" or when he uses the reset button.
	 * The view gets updated when calling this function.
	 */
	public void resetGameboard() {
		gameBoard = initialSetup.clone();
		readCratesAndPlayer(gameBoard);
		gameWon = false;
		updateView();
	}

}

package gui;

import java.awt.Dimension;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.GameBoard;
import domain.Tile;

/**
 * gameBoard uses a gridbagLayout to show the user the situation of the board
 * via the GUI.
 * 
 * @author Senne Dierick
 * @author Michael Unterberger
 *
 */
public class GameBoardPanel extends JPanel{

	private static final long serialVersionUID = -1821679477549717216L;
	private String sennePrefix;

	private final static int IMAGESIZE = 32;
	private final static String BLANK = "src/images/blank.jpg";
	private final static String BLANKMARKED = "src/images/blankmarked.jpg";
	private final static String CRATE = "src/images/crate.jpg";
	private final static String CRATEMARKED = "src/images/cratemarked.jpg";
	private final static String PLAYER = "src/images/player.jpg";
	private final static String WALL = "src/images/wall.jpg";

	/**
	 * Constructor of the class. Makes a panel with gridbaglayout.
	 * 
	 * @param prefix set for special pathing on different machines
	 */
	public GameBoardPanel(boolean prefix) {
		if(prefix)
			sennePrefix = "AOOP_Project/";
		else
			sennePrefix = "";
		setLayout(new GridBagLayout());	
	}

	/**
	 * Updates the board after a move is done. called by GameBoardScreen.
	 * 
	 * @param gb updated game state to which the view will be updated to.
	 */
	public void update(GameBoard gb) {
		removeAll();
		GridBagConstraints c = new GridBagConstraints();

		String path;
		int i = 0;
		int j = 0;
		for (Tile[] tiles : gb.getBoard()) {
			j = 0;
			for (Tile tile : tiles) {
				if (tile.is_wall()) {
					
					path = sennePrefix + WALL;
				} else if (tile.is_ppos()) {
					path = sennePrefix + PLAYER;
				} else if (tile.is_cpos()) {
					path = tile.is_marked() ? sennePrefix + CRATEMARKED : sennePrefix + CRATE;
				} else {
					path = tile.is_marked() ? sennePrefix + BLANKMARKED : sennePrefix + BLANK;
				}
				// System.out.println(path);
				ImageIcon image = new ImageIcon(path);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.CENTER;
				c.gridx = j;
				c.gridy = i;
				c.weightx = 0;
				c.weighty = 0;
				this.add(new JLabel(image), c);
				j++;

			}
			i++;
		}
		setSize(new Dimension(j * IMAGESIZE, i * IMAGESIZE));
	}
}

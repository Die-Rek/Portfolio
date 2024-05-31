package gui;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import domain.Controller;

/**
 * Screen to select level and the way you want to control the player and the level.
 * It uses a LevelSelector and several buttons to control the selection.
 * 
 * @author Senne Dierick
 *
 */
public class BeginScreen extends JFrame{
	
	private static final long serialVersionUID = 3913784850414167671L;
	private LevelSelector levelSelector;
	
	/**
	 * Constructor of the class.
	 * BeginsScreen is a frame with a button to select the if the player wants to use a keyboard or not.
	 * It also implements a levelSelector to select a level and a button to continue to start the level
	 * and attach the GameBoard observer to the Controller.
	 * 
	 * @param controller the referenced controller for this game
	 * @param debug set to enable debug messages
	 * @param prefix set for special pathing on different machines
	 */
	public BeginScreen(Controller controller, boolean debug, boolean prefix) {
		this.levelSelector = new LevelSelector(controller);
		
		JButton keyBoardButton = new JButton("Keyboard: on");
		keyBoardButton.setBounds(50, 100, 200, 30);
		keyBoardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean keyBoardOn = !controller.isKeyboardOn();
				controller.setKeyboardOn(keyBoardOn);
				keyBoardButton.setText(keyBoardOn ? "Keyboard: on" : "Keyboard: off");
			}
		});
		add(keyBoardButton);
		
		levelSelector.setBounds(300, 100, 200, 100);
		add(levelSelector);
		
		JButton loadButton = new JButton("Load last saved game");
		loadButton.setBounds(300, 225, 200, 30);
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setSelectedLevel(0);
			}
		});
		add(loadButton);
		
		JButton continueButton = new JButton("Start level");
		continueButton.setBounds(50, 300, 450, 40);
		continueButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.attachGameboardObserver(new GameBoardScreen(controller, debug, prefix));
				controller.loadBoard(prefix);
				
				setVisible(false);
			}
		});
		add(continueButton);
		
		JLabel title = new JLabel("Sokoban", SwingConstants.CENTER);
		title.setBounds(200, 20, 150, 40);
		title.setFont(new Font("Serif", Font.PLAIN, 20));
		add(title);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(550, 400);
		setLayout(null);
		setResizable(false);
		setVisible(true);
	}
}

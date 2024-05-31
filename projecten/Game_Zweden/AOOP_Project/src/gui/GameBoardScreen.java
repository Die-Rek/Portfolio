package gui;

import java.awt.BorderLayout;
import java.awt.ContainerOrderFocusTraversalPolicy;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import domain.Controller;
import domain.GameBoard;
import domain.MoveDirection;

/**
 * This holds the GameBoardPanel and the OptionsPanel and packs them into a
 * frame to show it to the user. If the user chose to play without keyboard
 * There will be arrows on the screen too.
 * 
 * @author Senne Dierick
 * @author Michael Unterberger
 */
public class GameBoardScreen extends JFrame implements GameboardObserver {

	private static final long serialVersionUID = 1183585741916594752L;
	JFrame frame;
	GameBoardPanel gamePanel;
	Controller controller;
	OptionsPanel options;
	ControlSavePanel control;
	ButtonsPanel buttons;
	GameBoard lastState;

	/**
	 * A frame that includes a GameBoardPanel to show the user the board.
	 * Has the keyboardListener to move the character.
	 * 
	 * @param controller reference to the controller
	 * @param debug      prints debug info if set
	 * @param prefix set for special pathing on different machines
	 */
	public GameBoardScreen(Controller controller, boolean debug, boolean prefix) {
		this.options = new OptionsPanel(controller);
		this.controller = controller;
		this.gamePanel = new GameBoardPanel(prefix);
		this.control = new ControlSavePanel(controller, prefix);
		this.buttons = new ButtonsPanel(controller);
		
		frame = new JFrame();
		frame.setFocusTraversalPolicy(new ContainerOrderFocusTraversalPolicy());
		frame.setFocusTraversalKeysEnabled(true);
		frame.setLayout(new BorderLayout());

		this.control = new ControlSavePanel(controller, prefix);
		control.setPanelListener(new PanelListener() {
			@Override
			public void onButtonClick() {
				updateControls();
			}
		});
		// an action listener for keyboard inputs
		frame.addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (controller.isKeyboardOn()) {
					if (keyCode == KeyEvent.VK_UP) {
						controller.movePlayer(MoveDirection.UP);
					} else if (keyCode == KeyEvent.VK_DOWN) {
						controller.movePlayer(MoveDirection.DOWN);
					} else if (keyCode == KeyEvent.VK_LEFT) {
						controller.movePlayer(MoveDirection.LEFT);
					} else if (keyCode == KeyEvent.VK_RIGHT) {
						controller.movePlayer(MoveDirection.RIGHT);
					} else if (keyCode == KeyEvent.VK_SPACE) {
						controller.resetGameboard();
					}
				}
			}
		});

		frame.setSize(new Dimension(400, 400));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(options, BorderLayout.PAGE_START);
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(control, BorderLayout.SOUTH);
		if (!controller.isKeyboardOn()) {
			frame.add(buttons, BorderLayout.EAST);
		}
		frame.setVisible(true);
	}

	/**
	 * Updates the frame after a move called by the controller
	 * 
	 * @param gb the gameboard to be updated
	 */
	@Override
	public void update(GameBoard gb) {
		lastState = gb;
		gamePanel.update(gb);
		frame.getContentPane().removeAll();
		frame.add(options, BorderLayout.NORTH);
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(control, BorderLayout.SOUTH);
		if (!controller.isKeyboardOn()) {
			frame.add(buttons, BorderLayout.EAST);
		}
		frame.revalidate();
		frame.repaint();
		frame.setVisible(true);
	}
	
	/**
	 * This function is used when the control buttons are activated while console view is on
	 * to reload the main frame without updating the game state. This will cause the buttons
	 * to appear
	 */
	private void updateControls() {
		gamePanel.update(lastState);
		frame.getContentPane().removeAll();
		frame.add(options, BorderLayout.NORTH);
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(control, BorderLayout.SOUTH);
		if (!controller.isKeyboardOn()) {
			frame.add(buttons, BorderLayout.EAST);
		}
		frame.revalidate();
		frame.repaint();
		frame.setVisible(true);
	}

}

package gui;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Controller;
import domain.GameBoard;

/**
 * This panel represents a grid of the levels you are able to select.
 * 
 * @author Senne Dierick
 *
 */
public class LevelSelector extends JPanel{
	
	private static final long serialVersionUID = 2590600005864052442L;
	Controller controller;
	
	/**
	 * Constructor of the class
	 * makes a gridLayout from the amount of levels possible to choose from.
	 * 
	 * @param controller the controller to be modified
	 */
	public LevelSelector(Controller controller) {
		setLayout(new GridLayout(4, 4));
		
		for (int i = 1; i <= GameBoard.AMOUNTOFLEVELS; i++) {
			final int final_i = i;
			JButton button = new JButton(String.format("%d", i));
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.setSelectedLevel(final_i);
				}
				
			});
			add(button);
		}
	}
}

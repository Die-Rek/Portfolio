package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Controller;
import domain.MoveDirection;

/**
 * Panel for the GameScreen that contains the control Buttons.
 * @author micha
 *
 */
public class ButtonsPanel extends JPanel{
	private static final long serialVersionUID = 4378876949373506575L;
	Controller controller;
	
	/**
	 * Class Constructor
	 * Creates the Panel and adds the control buttons.
	 * 
	 * @param Controller the controller that receives the button inputs
	 */
	public ButtonsPanel(Controller Controller) {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		
		
		gbc.gridx = 1;
		//gbc.gridy = 0;
		JButton up = new JButton("🠉");
		up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.movePlayer(MoveDirection.UP);
			}});
        add(up, gbc);
        gbc.gridx = 0;
        //gbc.gridy = 1;
        JButton left = new JButton("🠈");
		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.movePlayer(MoveDirection.LEFT);
			}});
        add(left, gbc);
        gbc.gridx = 2;
        //gbc.gridy = 1;
        JButton right = new JButton("🠊");
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.movePlayer(MoveDirection.RIGHT);
			}});
        add(right, gbc);
        gbc.gridx = 1;
        //gbc.gridy = 2;
        JButton down = new JButton("🠋");
		down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.movePlayer(MoveDirection.DOWN);
			}});
        add(down, gbc);
        
        gbc.gridx = 1;
        //gbc.gridy = 4;
        JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.resetGameboard();
			}});
		gbc.insets = new Insets(20, 0, 0, 0);
        add(reset, gbc);
	}

}

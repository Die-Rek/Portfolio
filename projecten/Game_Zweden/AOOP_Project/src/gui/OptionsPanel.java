package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Controller;
import domain.GuiView;
import domain.ConsoleView;

/**
 * Is the Options Panel on the top of the GameScreen, it contains buttons for conrolling view,
 * changing the Control Input option, an option for saving/loading the gamestate and a button
 * to return to the begin screen.
 * 
 * @author Michael Unterberger
 *
 */
public class OptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 815519757681214975L;

	/**
	 * Class constructor
	 * Creates the buttons aswell as Action listeners and adds them to the panel.
	 * 
	 * @param controller the controller of the game, is used to for accessing viewer information
	 */
	public OptionsPanel(Controller controller) {
		setLayout(new FlowLayout());
		JButton Gui_B = new JButton("GUI-View");
		JButton Console_B = new JButton("Console-View");
		
		if(controller.getViewerType() == GuiView.class) {
			Gui_B.setEnabled(false);
			Console_B.setEnabled(true);
		} else if(controller.getViewerType() == GuiView.class) {
			Gui_B.setEnabled(true);
			Console_B.setEnabled(false);
		}
		Gui_B.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Gui_B.setEnabled(false);
				Console_B.setEnabled(true);
				controller.setViewStrategy(new GuiView());
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(OptionsPanel.this);
		        topFrame.requestFocusInWindow();
			}
		});
		Console_B.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Gui_B.setEnabled(true);
				Console_B.setEnabled(false);
				controller.setViewStrategy(new ConsoleView());
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(OptionsPanel.this);
		        topFrame.requestFocusInWindow();
			}
		});
		this.add(Gui_B);
		this.add(Console_B);
	}

}

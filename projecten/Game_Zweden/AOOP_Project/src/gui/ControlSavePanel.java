package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Controller;

/**
 * Panel for the GameScreen containing a button to change control input and a save game state button.
 * @author micha
 *
 */
public class ControlSavePanel extends JPanel {
	private static final long serialVersionUID = 3151851676541567963L;
	private PanelListener controlListener;

	/**
	 * Class Constructor
	 * creates a panel for the gamescreen containing buttons to change control input and to 
	 * save game state
	 * 
	 * @param controller controller corresponding to the game
	 * @param prefix set for special pathing on different machines
	 */
	public ControlSavePanel(Controller controller, boolean prefix) {
		setLayout(new FlowLayout());
		JButton Controls = new JButton();
		JButton Save = new JButton("Save Game");

		if (controller.isKeyboardOn()) {
			Controls.setText("Toggle buttoncontrols");
		} else {
			Controls.setText("Toggle keyboardcontrols");
		}
		
		Controls.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                if (controlListener != null) {
                    // Notify the listener when the button is clicked
                    controller.setKeyboardOn(!controller.isKeyboardOn());
    				controller.updateView();
    				if (controller.isKeyboardOn()) {
    					Controls.setText("Toggle buttoncontrols");
    				} else {
    					Controls.setText("Toggle keyboardcontrols");
    				}
    				controlListener.onButtonClick();
    				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ControlSavePanel.this);
    		        topFrame.requestFocusInWindow();
                }
            }
		});
		
		Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveGame(prefix);
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ControlSavePanel.this);
		        topFrame.requestFocusInWindow();
			}
		});
		add(Controls);
		add(Save);

	}
	
	/**
	 * This function allows a parent container to set a function for this listener.
	 * 
	 * @param listener the listener to set
	 */
	public void setPanelListener(PanelListener listener) {
        this.controlListener = listener;
    }
}

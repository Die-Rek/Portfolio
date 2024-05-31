package main;

import domain.Controller;
import gui.BeginScreen;
import sound.SoundController;

/**
 * This is the class that initiates the whole application. It creates the controller and the begin screen.
 * 
 * @author Senne Dierick
 *
 */
public class StartUp {

	/**
	 * main method for properly starting the application
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		//launch the application via a welcomeScreen
		boolean DEBUG = false;
		boolean SennesPaths = false;
		Controller controller = new Controller();
		controller.attachMoveObserver(new SoundController(SennesPaths));
		new BeginScreen(controller, DEBUG, SennesPaths);
	}
}

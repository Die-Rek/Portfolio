package gui;

/**
 * Panel listener interface is used to communicate from a sub class to a parent class in an 
 * uncoupled manner.
 * 
 * @author micha
 *
 */
public interface PanelListener {
	/**
	 * This function needs to be overridden in the parent container to make parent methods available
	 * to contained class.
	 */
	void onButtonClick();
}

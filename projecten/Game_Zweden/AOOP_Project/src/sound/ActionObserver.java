package sound;

import domain.MoveType;

/**
 * Inteface for Action Observers, used to notify the sound engine to play a sound.
 * 
 * @author micha
 *
 */
public interface ActionObserver {
	/**
	 * Method prototype for playing a sound corresponding to the movement.
	 * 
	 * @param t type of movement.
	 */
	public void playSound(MoveType t);
}

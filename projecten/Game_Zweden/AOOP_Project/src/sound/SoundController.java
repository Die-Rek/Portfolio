package sound;

import domain.MoveType;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

/**
 * SoundController for the Game
 * 
 * Under certain Conditions/ if the user inputs certain commands (like move
 * player) the sound controller will be invoked per ObserverPattern and play a
 * sound.
 * 
 * @author micha
 *
 */
public class SoundController implements ActionObserver {

	private String pathPrefix;

	private final static String WALK = "src/Sounds/walk.wav";
	private final static String PUSH = "src/Sounds/push.wav";
	private final static String BLOCKED = "src/Sounds/blocked.wav";
	private final static String DING = "src/Sounds/ding.wav";
	private final static String WIN = "src/Sounds/win.wav";

	private Clip walk_sound;
	private Clip push_sound;
	private Clip blocked_sound;
	private Clip ding_sound;
	private Clip win_sound;

	/**
	 * Class Constructor Loads all the sounds from filesystem and saves them to
	 * playable Clip-Objects
	 * 
	 * @param prefix for special pathing on other machines
	 */
	public SoundController(boolean prefix) {
		try {
			if (prefix)
				pathPrefix = "AOOP_Project/";
			else
				pathPrefix = "";
			walk_sound = AudioSystem.getClip();
			walk_sound.open(AudioSystem.getAudioInputStream(new File(pathPrefix + WALK)));

			push_sound = AudioSystem.getClip();
			push_sound.open(AudioSystem.getAudioInputStream(new File(pathPrefix + PUSH)));

			blocked_sound = AudioSystem.getClip();
			blocked_sound.open(AudioSystem.getAudioInputStream(new File(pathPrefix + BLOCKED)));

			ding_sound = AudioSystem.getClip();
			ding_sound.open(AudioSystem.getAudioInputStream(new File(pathPrefix + DING)));

			win_sound = AudioSystem.getClip();
			win_sound.open(AudioSystem.getAudioInputStream(new File(pathPrefix + WIN)));

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			System.out.println("A path is wrong or audiofile unsupported!");
		}
	}

	@Override
	/**
	 * plays the sound corresponding to the move type
	 * 
	 * @param t type of movement
	 */
	public void playSound(MoveType t) {
		switch (t) {
		case BLOCKED:
			if (blocked_sound.isActive()) {
				blocked_sound.stop();
			}
			blocked_sound.setFramePosition(0);
			blocked_sound.start();
			break;
		case CLICK:
			if (ding_sound.isActive()) {
				ding_sound.stop();
			}
			ding_sound.setFramePosition(0);
			ding_sound.start();
			break;
		case PLACE:
			if (ding_sound.isActive()) {
				ding_sound.stop();
			}
			ding_sound.setFramePosition(0);
			ding_sound.start();
			break;
		case PUSH:
			if (push_sound.isActive()) {
				push_sound.stop();
			}
			push_sound.setFramePosition(0);
			push_sound.start();
			break;
		case WALK:
			if (walk_sound.isActive()) {
				walk_sound.flush();
			}
			walk_sound.setFramePosition(0);
			walk_sound.start();
			break;
		case WIN:
			if (win_sound.isActive()) {
				win_sound.stop();
			}
			win_sound.setFramePosition(0);
			win_sound.start();
			break;
		default:
			break;
		}

	}

}

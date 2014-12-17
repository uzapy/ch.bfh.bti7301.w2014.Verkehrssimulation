/**
 * 
 */
package util;


/**
 * @author bublm1
 */
public class ParameterPool {

	public static int FRAMES_PER_SECOND = 30;   // Simulationsgeschwindigkeit in Frames pro Sekunde
	public static double SPAWN_RATE = 1;
	public static int VIEW_OFFSET = 0;
	public static TrackPreset TRACK_PRESET = TrackPreset.Default;

	/**
	 * @author bublm1
	 */
	public static void moveTrackRight() {
		VIEW_OFFSET -= 25;
	}

	/**
	 * @author bublm1
	 */
	public static void moveTrackLeft() {
		VIEW_OFFSET += 25;
	}
}

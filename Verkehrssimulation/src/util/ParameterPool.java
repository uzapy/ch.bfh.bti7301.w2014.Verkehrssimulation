package util;


/**
 * Speichert global definierte Parameter
 * @author bublm1
 */
public class ParameterPool {

	public static int FRAMES_PER_SECOND = 30;   					// Simulationsgeschwindigkeit in Frames pro Sekunde
	public static double SPAWN_RATE = 1;							// Rate in velcher die Autos hinzugefügt werden
	public static int VIEW_OFFSET = 0;								// Verschiebung der Strecken-Position für den Betrachter
	public static TrackPreset TRACK_PRESET = TrackPreset.Default;	// Situations-Vorlage

	public static void moveTrackRight() {
		VIEW_OFFSET -= 25;
	}

	public static void moveTrackLeft() {
		VIEW_OFFSET += 25;
	}
}

package util;

import model.Lane;
import model.Track;
import segment.DoomSegment;
import segment.MeasuringSegment;
import segment.OpenToTrafficSegment;
import segment.PassableSegment;
import segment.Segment;
import segment.SpawnSegment;
import segment.VelocitySegment;

/**
 * Alle Strecken-konfigurationen
 * @author bublm1
 */
public class PresetPool {

	/**
	 * Standard: Dreispurige Autobahn ohne Hindernisse
	 * @author bublm1, burkt4, stahr2
	 * @return Autobahn
	 */
	public static Track getDefault() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);
		
		Segment measureSegment0 = new MeasuringSegment(50, 450);

		lane0.addSegment(measureSegment0);
		lane1.addSegment(measureSegment0);
		lane2.addSegment(measureSegment0);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

	/**
	 * Baustelle: Zunächst wird die Geschwindigkeit auf 22 m/s reduziert. Anschliessend wird die dritte (oberste) Spur geschlossen.
	 * Autos auf der obersten Spur müssen sich auf die mittlere Spur einspuren. Nach 150m werden alle 3 spuren wieder freigegeben.
	 * @author bublm1, burkt4, stahr2
	 * @return Autobahn
	 */
	public static Track getRoadWorks() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);
		
		Segment velocitySegment0 = new VelocitySegment(100, 500, 22);
		Segment velocitySegment1 = new VelocitySegment(100, 500, 22);
		Segment velocitySegment2 = new VelocitySegment(100, 500, 22);
		
		Segment notOpenToTrafficSegment1 = new OpenToTrafficSegment(250, 500, false);
		
		Segment measureSegment0 = new MeasuringSegment(50, 400);
		
		lane0.addSegment(velocitySegment0);
		lane1.addSegment(velocitySegment1);
		lane2.addSegment(velocitySegment2);

		lane0.addSegment(measureSegment0);
		lane1.addSegment(measureSegment0);
		lane2.addSegment(measureSegment0);

		lane2.addSegment(notOpenToTrafficSegment1);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

	/**
	 * Spurverengung: Zunächst wird die Geschwindigkeit auf 22 m/s dann noch auf 17 m/s reduziert.
	 * Anschliessend wird die dritte (oberste) und schliesslich die mittlere Spur geschlossen.
	 * Alle Autos müssen auf eine Spur wechseln.
	 * @author bublm1, burkt4, stahr2
	 * @return Autobahn
	 */
	public static Track getBottleneck() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);
		
		Segment velocitySegment0 = new VelocitySegment(100, 200, 22);
		Segment velocitySegment1 = new VelocitySegment(100, 200, 22);
		Segment velocitySegment2 = new VelocitySegment(100, 200, 22);

		Segment velocitySegment3 = new VelocitySegment(200, 500, 17);
		Segment velocitySegment4 = new VelocitySegment(200, 500, 17);
		Segment velocitySegment5 = new VelocitySegment(200, 500, 17);
		
		Segment notOpenToTrafficSegment0 = new OpenToTrafficSegment(300, 500, false);
		Segment notOpenToTrafficSegment1 = new OpenToTrafficSegment(200, 500, false);
		
		Segment measureSegment0 = new MeasuringSegment(50, 400);
		
		lane0.addSegment(velocitySegment0);
		lane1.addSegment(velocitySegment1);
		lane2.addSegment(velocitySegment2);

		lane0.addSegment(velocitySegment3);
		lane1.addSegment(velocitySegment4);
		lane2.addSegment(velocitySegment5);

		lane0.addSegment(measureSegment0);
		lane1.addSegment(measureSegment0);
		lane2.addSegment(measureSegment0);

		lane1.addSegment(notOpenToTrafficSegment0);
		lane2.addSegment(notOpenToTrafficSegment1);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

	/**
	 * Geschwindigkeitsbegrenzung: Zunächst wird die Geschwindigkeit auf 22 m/s dann noch auf 17 m/s reduziert.
	 * Nach 400m wird die Begrenzung wieder aufgehoben.
	 * @author bublm1, burkt4, stahr2
	 * @return Autobahn
	 */
	public static Track getSpeedLimit() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);
		
		Segment velocitySegment0 = new VelocitySegment(100, 200, 22);
		Segment velocitySegment1 = new VelocitySegment(100, 200, 22);
		Segment velocitySegment2 = new VelocitySegment(100, 200, 22);

		Segment velocitySegment3 = new VelocitySegment(200, 500, 17);
		Segment velocitySegment4 = new VelocitySegment(200, 500, 17);
		Segment velocitySegment5 = new VelocitySegment(200, 500, 17);
		
		Segment measureSegment0 = new MeasuringSegment(50, 400);
		
		lane0.addSegment(velocitySegment0);
		lane1.addSegment(velocitySegment1);
		lane2.addSegment(velocitySegment2);

		lane0.addSegment(velocitySegment3);
		lane1.addSegment(velocitySegment4);
		lane2.addSegment(velocitySegment5);

		lane0.addSegment(measureSegment0);
		lane1.addSegment(measureSegment0);
		lane2.addSegment(measureSegment0);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

	/**
	 * Überholverbot: Auf einem 300m langen Teilstück ist Spurwechselverbot für alle Autos
	 * @author bublm1, burkt4, stahr2
	 * @return Autobahn
	 */
	public static Track getBanOnPassing() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);

		Segment notPassableSegment0 = new PassableSegment(100, 400, false, false);
		Segment notPassableSegment1 = new PassableSegment(100, 400, false, false);
		Segment notPassableSegment2 = new PassableSegment(100, 400, false, false);
		
		Segment measureSegment0 = new MeasuringSegment(50, 450);

		lane0.addSegment(notPassableSegment0);
		lane1.addSegment(notPassableSegment1);
		lane2.addSegment(notPassableSegment2);

		lane0.addSegment(measureSegment0);
		lane1.addSegment(measureSegment0);
		lane2.addSegment(measureSegment0);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

	/**
	 * Ein- und Ausfahrt: Zweispurige Autobahn mit einer Ausfahrt und darauffolgender Auffahrtsspur
	 * @author bublm1
	 * @return
	 */
	public static Track getOnAndExitRamp() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);

		Segment doomSegment0 = new DoomSegment(50,150);
		Segment spawnSegment0 = new SpawnSegment(200,300);
		
		Segment notPassableSegment0 = new PassableSegment(50, 150, false, false);
		Segment notPassableSegment1 = new PassableSegment(200, 300, true, false);
		
		Segment notOpenToTrafficSegment0 = new OpenToTrafficSegment(0, 50, false);
		Segment notOpenToTrafficSegment1 = new OpenToTrafficSegment(100, 200, false);
		Segment notOpenToTrafficSegment2 = new OpenToTrafficSegment(300, 500, false);
		
		Segment measureSegment0 = new MeasuringSegment(50, 450);
		
		lane0.addSegment(doomSegment0);
		lane0.addSegment(spawnSegment0);
		
		lane0.addSegment(notPassableSegment0);
		lane1.addSegment(notPassableSegment1);
		
		lane0.addSegment(notOpenToTrafficSegment0);
		lane0.addSegment(notOpenToTrafficSegment1);
		lane0.addSegment(notOpenToTrafficSegment2);

		lane0.addSegment(measureSegment0);
		lane1.addSegment(measureSegment0);
		lane2.addSegment(measureSegment0);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

	/**
	 * @author bublm1
	 * @return
	 */
	public static Track getExperimental() {
		Lane lane0 = new Lane(33, 500, 0);
		Lane lane1 = new Lane(33, 500, 1);
		Lane lane2 = new Lane(33, 500, 2);

		lane0.setAdjacentLanes(lane1, null);
		lane1.setAdjacentLanes(lane2, lane0);
		lane2.setAdjacentLanes(null, lane1);
		
		Segment spawnSegment0 = new SpawnSegment(50,100);
		Segment doomSegment0 = new DoomSegment(100,150);
		
		lane0.addSegment(spawnSegment0);
		lane2.addSegment(doomSegment0);
		
		Track track = new Track();
		track.addLane(lane0);
		track.addLane(lane1);
		track.addLane(lane2);
		
		return track;
	}

}

package view.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Optional;

import javax.swing.JPanel;

import model.Car;
import model.Lane;
import model.Track;
import segment.DoomSegment;
import segment.MeasuringSegment;
import segment.OpenToTrafficSegment;
import segment.PassableSegment;
import segment.Segment;
import segment.SpawnSegment;
import segment.VelocitySegment;
import skiplist.Locator;
import util.MetricToPixel;
import util.ParameterPool;

@SuppressWarnings("serial")
public class TrackPanel extends JPanel {
	private Track track;																// Die Autobahn
	private LinkedList<LanePanel> lanePanels = new LinkedList<LanePanel>();				// Alle Spuren
	private LinkedList<SegmentPanel> segmentPanels = new LinkedList<SegmentPanel>();	// Alle Segmente
	private LinkedList<CarPanel> carPanels = new LinkedList<CarPanel>();				// Alle Autos
	
	private int trackOffset = 2;		// Dicke von den schwarzen Begrenzungsstreifen (oben und unten)	
	private int markerInterval = 50;	// Abstand der gelben Distanz-Markierungen

	/**
	 * Visuelle Repräsentation der Autobahn
	 * @author burkt4
	 * @param track
	 */
	public TrackPanel(Track track) {
		super();
		this.setTrack(track);
	}
	
	/**
	 * Zusammenstellung der Kollekionen von Objekten, die gezeichnet werden
	 * @author burkt4
	 * @param track
	 */
	public void setTrack(Track track) {
		this.lanePanels.clear();
		this.segmentPanels.clear();
		this.carPanels.clear();
		
		this.track = track;

		for (Lane lane : this.track.getLanes()) {
			int fastLaneOffset = (this.track.getLanes().size() - 1) - lane.getFastLaneIndex();
			
			this.lanePanels.add(new LanePanel(lane, fastLaneOffset, trackOffset));
			
			for (Segment segment : lane.getSegments(VelocitySegment.class)) {
				this.segmentPanels.add(new VelocitySegmentPanel(segment, fastLaneOffset, trackOffset));
			}
			
			for (Segment segment : lane.getSegments(PassableSegment.class)) {
				this.segmentPanels.add(new PassableSegmentPanel(segment, fastLaneOffset, trackOffset));
			}
			
			for (Segment segment : lane.getSegments(MeasuringSegment.class)) {
				this.segmentPanels.add(new MeasuringSegmentPanel(segment, fastLaneOffset, trackOffset));
			}
			
			for (Segment segment : lane.getSegments(OpenToTrafficSegment.class)) {
				this.segmentPanels.add(new OpenToTrafficSegmentPanel(segment, fastLaneOffset, trackOffset));
			}
			
			for (Segment segment : lane.getSegments(SpawnSegment.class)) {
				this.segmentPanels.add(new SpawnSegmentPanel(segment, fastLaneOffset, trackOffset));
			}
			
			for (Segment segment : lane.getSegments(DoomSegment.class)) {
				this.segmentPanels.add(new DoomSegmentPanel(segment, fastLaneOffset, trackOffset));
			}
			
			for (Locator<Integer, Car> carLocator : lane) {
				this.carPanels.add(new CarPanel(carLocator.element(), fastLaneOffset, trackOffset, this.track.getLanes().size() - 1));
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Schrifgrösse global setzen
		g.setFont(new Font("Arial", Font.PLAIN, MetricToPixel.getFontSize()));
		// Perspektive anpassen
		g.translate(MetricToPixel.scale(ParameterPool.VIEW_OFFSET), 0);
		
		// Schwarze Begrenzungsstreifen zeichnen oben
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MetricToPixel.scale(track.getLane(0).getLength()), MetricToPixel.scale(trackOffset));
		
		// Distanz-Markierungen zeichnen
		drawMarkers(g);	
		
		for (LanePanel lanePanel : this.lanePanels) {
			lanePanel.paintComponent(g);
		}
		
		for (SegmentPanel segmentPanel : this.segmentPanels) {
			segmentPanel.paintComponent(g);
		}
		
		for (CarPanel carPanel : carPanels) {
			carPanel.paintComponent(g);
		}

		int xPosition = 0;
		int yPosition = this.lanePanels.size() * MetricToPixel.scale(Lane.WIDTH) + MetricToPixel.scale(trackOffset);
		int length = MetricToPixel.scale(track.getLane(0).getLength());
		int width = MetricToPixel.scale(trackOffset);
		
		// Schwarze Begrenzungsstreifen zeichnen unten
		g.setColor(Color.BLACK);
		g.fillRect(xPosition, yPosition, length, width);
	}

	/**
	 * Zeichent die Distanz-Markierungen
	 * @author bublm1
	 * @param g
	 */
	private void drawMarkers(Graphics g) {
		g.setColor(Color.YELLOW);
		int xStart = 0;
		int yStart = 0;
		int xEnd = 0;
		int yEnd = MetricToPixel.scale(trackOffset);
		
		int numberOfMarkers = track.getLane(0).getLength() / markerInterval;
		for (int i = 0; i < numberOfMarkers; i++) {
			xStart = MetricToPixel.scale(i * markerInterval);
			xEnd = MetricToPixel.scale(i * markerInterval);
			
			g.drawLine(xStart, yStart, xEnd, yEnd);
			
			int xStringPosition = MetricToPixel.scale(i * markerInterval) + 1;
			int yStringPosition = MetricToPixel.scale(trackOffset) - 1;
			
			g.drawString(Integer.toString(i * markerInterval), xStringPosition, yStringPosition);
		}
	}

	/**
	 * Autos entfernen, die die Strecke verlassen haben. Neu generierte Autos hinzufügen. Alle Autos neu zeichnen
	 * @author stahr2
	 */
	public void updateTrack() {
		for (Car oldCar : this.track.getOldCars()) {
			Optional<CarPanel> foundCar = this.carPanels.stream().filter(cp -> cp.getId() == oldCar.getId()).findFirst();
			if (foundCar.isPresent()) {
				this.carPanels.remove(foundCar.get());
			}
		}

		for (Car newCar : this.track.getNewCars()) {
			this.carPanels.add(new CarPanel(newCar, this.track.getLanes().size(), trackOffset, this.track.getLanes().size() - 1));
		}

		this.track.clearNewCars();
		this.track.clearOldCars();
		this.repaint();
	}

	/**
	 * Simulationsschritt zeichen
	 * @author bublm1
	 * @param simStep  Nummer des Zwischenschritts
	 */
	public void performSimStep(int simStep) {
		for (CarPanel carPanel : this.carPanels) {
			carPanel.performSimStep(simStep);
		}
		
		this.repaint();
	}
}

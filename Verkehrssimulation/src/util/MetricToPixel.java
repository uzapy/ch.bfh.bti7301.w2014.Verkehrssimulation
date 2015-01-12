package util;

/**
 * Rechnet Metrische einheiten in Pixel um. Mit einem Skalier-Faktor
 * @author bublm1
 */
public class MetricToPixel {
	public static int SCALING_FACTOR = 8;

	public static int scale(int distance) {
		return distance * MetricToPixel.SCALING_FACTOR;
	}

	public static int scale(float distance) {
		return (int) (distance * (float)MetricToPixel.SCALING_FACTOR);
	}

	public static int getImageSize() {
		return 4 * MetricToPixel.SCALING_FACTOR;
	}

	public static int getFontSize() {
		return 2 * MetricToPixel.SCALING_FACTOR;
	}

	public static int getTurnSignalSize() {
		double turnSignalSize = (double) MetricToPixel.SCALING_FACTOR / 3;
		return (int) Math.ceil(turnSignalSize);
	}

	public static void zoomIn() {
		SCALING_FACTOR++;
	}

	public static void zoomOut() {
		SCALING_FACTOR--;
	}
}

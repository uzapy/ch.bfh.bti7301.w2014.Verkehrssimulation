/**
 * 
 */
package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Kann Bilder vom File-System laden als BufferedImage
 * @author bublm1
 */
public class Resources {

	/**
	 * Liest Bilder vom File-System und gibt sie als BufferedImage zurück
	 * @author bublm1
	 * @param name		Name der Bilddatei
	 * @return			Das Bild als BufferedImage
	 */
	public static BufferedImage getImage(String name) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("src/resources/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}

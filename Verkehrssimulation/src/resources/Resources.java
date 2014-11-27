/**
 * 
 */
package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author bublm1
 */
public class Resources {

	/**
	 * @author bublm1
	 * @param name
	 * @return
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

/**
 * 
 */
package view;

import javax.swing.JPanel;

/**
 * @author bublm1
 */
@SuppressWarnings("serial")
public abstract class AbstractPanel<T> extends JPanel {

	protected T object;
	protected int trackOffset;
	protected int fastLaneOffset; 
	
	/**
	 * @author bublm1
	 * @param car
	 * @param fastLaneOffset
	 * @param trackOffset
	 */
	public AbstractPanel(T object, int fastLaneOffset, int trackOffset) {
		this.object = object;
		this.fastLaneOffset = fastLaneOffset;
		this.trackOffset = trackOffset;
	}
}

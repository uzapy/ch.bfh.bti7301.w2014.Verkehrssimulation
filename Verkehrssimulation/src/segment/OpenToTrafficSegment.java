/**
 * 
 */
package segment;

/**
 * @author burkt4
 */
public class OpenToTrafficSegment implements Segment {

	private int start;
	private int end;
	private boolean openToTraffic;
	
	public OpenToTrafficSegment(int start, int end, boolean openToTraffic){
		this.start = start;
		this.end = end;
		this.openToTraffic = openToTraffic;
	}

	@Override
	public int start() {
		return this.start;
	}

	@Override
	public int end() {
		return this.end;
	}
	
	public boolean isOpenToTraffic(){
		return this.openToTraffic;
	}

}

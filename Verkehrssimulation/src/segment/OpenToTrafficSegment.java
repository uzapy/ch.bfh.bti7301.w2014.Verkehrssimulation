/**
 * 
 */
package segment;

/**
 * @author burkt4
 */
public class OpenToTrafficSegment implements Segment {

	int start;
	int end;
	boolean openToTraffic;
	
	public OpenToTrafficSegment(int start, int end, boolean openToTraffic){
		this.start = start;
		this.end = end;
		this.openToTraffic = openToTraffic;
	}


	public int start() {

		return this.start;
	}

	public int end() {
		return this.end;
	}
	
	public boolean isOpenToTraffic(){
		return this.openToTraffic;
	}

}

package ihpc.maritime.transportation.intermodel;

/**
 * The class store trajectory points, note that should be sorted according to ts by acs
 * 
 * @author Zhe
 *
 */
public class TrajectoryPoint {
	
	private double cog;

	private double lat;

	private double lng;

	private double sog;

	private long ts;

	public double getCog() {
		return cog;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public double getSog() {
		return sog;
	}

	public long getTs() {
		return ts;
	}

	public void setCog(double cog) {
		this.cog = cog;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public void setSog(double sog) {
		this.sog = sog;
	}
	
	public void setTs(long ts) {
		this.ts = ts;
	}
	


}

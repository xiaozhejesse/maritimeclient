package ihpc.maritime.transportation.intermodel;

public class LocationCogSogProbValue {
	
	private double lat;
	private double lng;
	private double cog;
	private double sog;
	private double prob;
	private double dist;
	
	public LocationCogSogProbValue(){
		
	}

	public LocationCogSogProbValue(double lat, double lng, double cog, double sog, double dist, double prob){
		this.lat = lat;
		this.lng = lng;
		this.cog = cog;
		this.sog = sog;
		this.dist = dist;
		this.prob = prob;
	}

	public double getCog() {
		return cog;
	}

	public double getDist() {
		return dist;
	}

	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public double getProb() {
		return prob;
	}
	public double getSog() {
		return sog;
	}
	public void setCog(double cog) {
		this.cog = cog;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	public void setSog(double sog) {
		this.sog = sog;
	}
	
	@Override 
	public LocationCogSogProbValue clone(){ 
		LocationCogSogProbValue clone = null; 
		try{ 
			clone = (LocationCogSogProbValue) super.clone(); 
		}catch(CloneNotSupportedException e)
		{ 
			throw new RuntimeException(e); // won't happen } return clone; }
		}
		return clone;
	}


}

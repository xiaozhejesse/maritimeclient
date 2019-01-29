package ihpc.maritime.transportation.intermodel;

/**
 * This class is just with the prediction results, compared with STPTrajectoryWebModel
 * 
 * @author Zhe
 *
 */
public class STPTrajectoryModel {
	
	private String errorCode;

	private int methodLabel;

	private String mmsi;

	private TrajectoryPoint[] trajectory;
	
	public STPTrajectoryModel(){
		
	}
	
	public STPTrajectoryModel(TrajectoryPoint[] trajectory, String mmsi, String errorCode, int methodLabel){
		
		this.trajectory = trajectory;
		this.mmsi = mmsi;
		this.methodLabel = methodLabel;
		this.errorCode = errorCode;

	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public int getMethodLabel() {
		return methodLabel;
	}

	public String getMmsi() {
		return mmsi;
	}

	public TrajectoryPoint[] getTrajectory() {
		return trajectory;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public void setMethodLabel(int methodLabel) {
		this.methodLabel = methodLabel;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}
	
	public void setTrajectory(TrajectoryPoint[] trajectory) {
		this.trajectory = trajectory;
	}
	
}

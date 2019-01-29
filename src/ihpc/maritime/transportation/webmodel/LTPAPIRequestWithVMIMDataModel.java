package ihpc.maritime.transportation.webmodel;

import java.util.HashMap;

import ihpc.maritime.transportation.intermodel.TrajectoryPoint;

public class LTPAPIRequestWithVMIMDataModel {
	
	private HashMap<String, TrajectoryPoint[]> inputVMIMData;

	private long predictionTs;
	
	/**
	 * in hour
	 */
	private double predictionTw;

	public HashMap<String, TrajectoryPoint[]> getInputVMIMData() {
		return inputVMIMData;
	}


	public long getPredictionTs() {
		return predictionTs;
	}

	public double getPredictionTw() {
		return predictionTw;
	}

	public void setInputVMIMData(HashMap<String, TrajectoryPoint[]> inputVMIMData) {
		this.inputVMIMData = inputVMIMData;
	}

	public void setPredictionTs(long predictionTs) {
		this.predictionTs = predictionTs;
	}
	
    public void setPredictionTw(double predictionTw) {
		this.predictionTw = predictionTw;
	}


}

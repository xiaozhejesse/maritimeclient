package ihpc.maritime.transportation.webmodel;

import java.util.HashMap;

import ihpc.maritime.transportation.intermodel.TrajectoryPoint;

public class STPAPIRequestWithVMIMDataModel {
	
	private HashMap<String, TrajectoryPoint[]> inputVMIMData;

	private int method;

	private long predictionTs;
	
	private double predictionTw;

	public HashMap<String, TrajectoryPoint[]> getInputVMIMData() {
		return inputVMIMData;
	}

	public int getMethod() {
		return method;
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

	public void setMethod(int method) {
		this.method = method;
	}

	public void setPredictionTs(long predictionTs) {
		this.predictionTs = predictionTs;
	}
	
    public void setPredictionTw(double predictionTw) {
		this.predictionTw = predictionTw;
	}


}

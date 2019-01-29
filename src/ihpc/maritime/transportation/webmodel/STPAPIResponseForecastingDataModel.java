package ihpc.maritime.transportation.webmodel;

import ihpc.maritime.transportation.intermodel.STPTrajectoryModel;
import java.util.List;

public class STPAPIResponseForecastingDataModel {
	
	List<STPTrajectoryModel> forecastingData;

	private long predictionTs;

	private double predictionTw;

	public List<STPTrajectoryModel> getForecastingData() {
		return forecastingData;
	}

	public long getPredictionTs() {
		return predictionTs;
	}

	public double getPredictionTw() {
		return predictionTw;
	}

	public void setForecastingData(List<STPTrajectoryModel> forecastingData) {
		this.forecastingData = forecastingData;
	}
	
	public void setPredictionTs(long predictionTs) {
		this.predictionTs = predictionTs;
	}

	public void setPredictionTw(double predictionTw) {
		this.predictionTw = predictionTw;
	}

}

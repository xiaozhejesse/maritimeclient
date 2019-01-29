package ihpc.maritime.transportation.webmodel;

import ihpc.maritime.transportation.intermodel.LTPProbLocModel;

import java.util.List;

public class LTPAPIResponseForecastingDataModel {
	
	List<LTPProbLocModel> forecastingData;

	private long predictionTs;

	private double predictionTw;

	public List<LTPProbLocModel> getForecastingData() {
		return forecastingData;
	}

	public long getPredictionTs() {
		return predictionTs;
	}

	public double getPredictionTw() {
		return predictionTw;
	}

	public void setForecastingData(List<LTPProbLocModel> forecastingData) {
		this.forecastingData = forecastingData;
	}
	
	public void setPredictionTs(long predictionTs) {
		this.predictionTs = predictionTs;
	}

	public void setPredictionTw(double predictionTw) {
		this.predictionTw = predictionTw;
	}

}

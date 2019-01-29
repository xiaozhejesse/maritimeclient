package ihpc.maritime.transportation.clienttest;

import ihpc.maritime.transportation.intermodel.LTPProbLocModel;
import ihpc.maritime.transportation.intermodel.LocationCogSogProbValue;
import ihpc.maritime.transportation.intermodel.TrajectoryPoint;
import ihpc.maritime.transportation.system.common.SystemConstant;
import ihpc.maritime.transportation.util.AISLoader;
import ihpc.maritime.transportation.vessel.entity.ATempInterpolationDataUnit;
import ihpc.maritime.transportation.webmodel.LTPAPIRequestWithVMIMDataModel;
import ihpc.maritime.transportation.webmodel.LTPAPIResponseForecastingDataModel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LTPClientSampleTest {
	
	final static Logger logger = Logger.getLogger(LTPClientSampleTest.class);

	@Test
	public void test(){
		
		long ts = 1504058706000l;
		double hour = 0.5;
		
		ArrayList<String> vsls = new ArrayList<String>();
		vsls.add("210138000");
		vsls.add("413000000");
		vsls.add("563015950");
		vsls.add("538003093");
		vsls.add("538005371");
		vsls.add("009821964");
		vsls.add("563036650");
		vsls.add("373455000");
		vsls.add("210208000");
		vsls.add("566500000");
		vsls.add("565220000");
		vsls.add("412420809");
		
		LTPAPIRequestWithVMIMDataModel dataModel = prepareDataForAPICall(vsls, ts, hour);
		long tic = System.currentTimeMillis();
		LTPAPIResponseForecastingDataModel rst = callAPI(dataModel, "172.20.2.44", "8080");
		System.out.println(System.currentTimeMillis()-tic);
		
		System.out.println(rst.getForecastingData().size());
		
		List<LTPProbLocModel> list = rst.getForecastingData();
		for(LTPProbLocModel ele:list){
			for(LocationCogSogProbValue loc:ele.getMergedLocs()){
				System.out.println(loc.getLat()+"|"+loc.getLng()+"|"+loc.getProb());
			}
			System.out.println("/////////////////////////////////////");
		}

	}

/*	@Test
	public void test() {
		List<String> vsls;
		long start = 1485794700000l;
		long end = 1485796200000l;
		ATempInterpolationDataUnitDAO dao = new ATempInterpolationDataUnitDAO();
		vsls = dao.queryDistinctVslId();
		errorLog(vsls, start, end, 0.5);
	}*/
	
	
	LTPAPIRequestWithVMIMDataModel prepareDataForAPICall(List<String> vsls, long ts, double hour){
		
		
		LTPAPIRequestWithVMIMDataModel dataModel = new LTPAPIRequestWithVMIMDataModel();
		HashMap<String, TrajectoryPoint[]> inputVMIMData = new HashMap<String, TrajectoryPoint[]>();

		
		// get the AIS data of last 10 minutes
		long tperiod = (long) (hour*3600000);
		long beforeStart = - tperiod + ts;
	    long start = ts;
	    
	    for(String vslId: vsls){
	    	ArrayList<ATempInterpolationDataUnit> tdata = AISLoader.getAISdata(vslId, beforeStart, start); //this is just loading data from database
	    	int size = tdata.size();
	    	TrajectoryPoint[] tp = new TrajectoryPoint[size];
	    	for(int i=0;i<size;i++){
	    		TrajectoryPoint temp = new TrajectoryPoint();
	    		temp.setLat(tdata.get(i).getLat());
	    		temp.setLng(tdata.get(i).getLng());
	    		temp.setSog(tdata.get(i).getVslSpeed());
	    		temp.setCog(tdata.get(i).getCourse());
	    		temp.setTs(tdata.get(i).getTimestamp());
	    		tp[i] = temp;
	    	}
	    	inputVMIMData.put(vslId, tp);
	    }
	    
	    dataModel.setPredictionTs(ts);
	    dataModel.setPredictionTw(hour);
	    dataModel.setInputVMIMData(inputVMIMData);
	    
		return dataModel;
	}
	
	public LTPAPIResponseForecastingDataModel callAPI(LTPAPIRequestWithVMIMDataModel input, String ip, String port){
	     
		LTPAPIResponseForecastingDataModel rst = null;
		
		try{
	    Gson gson = new Gson();
	    String json = gson.toJson(input);
		
		String USER_AGENT = "Mozilla/5.0";
	    String query = "http://"+ip+":"+port+"/maritime/akkaltp";
	    //query = "https://modstore.org/maritime/akkaltp";
	    System.out.println(query);

	    URL url = new URL(query);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setConnectTimeout(15000);
	    conn.setRequestProperty("User-Agent", USER_AGENT);
	    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	    conn.setRequestMethod("POST");

	    OutputStream os = conn.getOutputStream();
	    os.write(json.getBytes("UTF-8"));
	    os.close();

	    // read the response
	    int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
	    InputStream in = new BufferedInputStream(conn.getInputStream());
	    String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

	    rst = gson.fromJson(result,  new TypeToken<LTPAPIResponseForecastingDataModel>() {
	    }.getType());

	    in.close();
	    conn.disconnect();
	    

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rst;

	}
	
	
	public double getErrorsMoreInfo(ArrayList<LocationCogSogProbValue> probs, double actualLat, double actualLng){
		double rst = 0;
		
		double probSum = 0;
		for(LocationCogSogProbValue unit:probs){
			probSum+=unit.getProb();
		}
		
		for(LocationCogSogProbValue unit:probs){
			rst+=unit.getProb()/probSum*distance(unit.getLat(), unit.getLng(), actualLat, actualLng,"K");
		}
		
		return rst;
		
	}
	
	
	/**
	 * "M" Miles; "K" Kilometers; "N" Nautical Miles.
	 */
	private double distance(double lat1, double lon1, double lat2, double lon2,
			String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
        if(dist>1){
        	dist = 1;
        }
        if(dist<-1){
        	dist=-1;
        }
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}
		
		return (dist);
	}

	/**
	 * This function converts decimal degrees to radians
	 */
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/**
	 * This function converts radians to decimal degrees
	 */
	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	private void errorLog(List<String> vsls, long start, long end, double hour) {

		for (; start <= end; start += 300000) {
			
			try{
				
				LTPAPIRequestWithVMIMDataModel dataModel = prepareDataForAPICall(vsls, start, hour);
				LTPAPIResponseForecastingDataModel rst = callAPI(dataModel, "172.20.2.44", "8080");
				ArrayList<LTPProbLocModel> rstList = (ArrayList<LTPProbLocModel>) rst.getForecastingData();
				
				for(LTPProbLocModel ele:rstList){
					if(ele!=null){
						ATempInterpolationDataUnit realData = AISLoader.getAISdata(ele.getVslid(), start+(long)(hour*3600000));
						double error = -1;
						if(realData!=null){
							error = this.getErrorsMoreInfo(ele.getMergedLocs(), realData.getLat(), realData.getLng());
						}
						saveForecastingResult(ele,ele.getVslid(),start,realData,error);
					}
				}
		
				System.out.println(start);
				
			}catch(Exception e){
				logger.error("error info", e);
				logger.error(start+":"+vsls.toString());
			}
		}
	}
	
	private void saveForecastingResult(LTPProbLocModel rst, String vslid, long start, ATempInterpolationDataUnit realData, double error) {
		
		String path = SystemConstant.LTPForecastingResultFileDir;
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();

		String filePath = path + SystemConstant.LTPForecastingResultFile;
		


		boolean headerAdding = true;
		if (new File(filePath).exists())
			headerAdding = false;

		FileWriter fileWriter = null;

		String FILE_HEADER = "vslid,start,pCog,pLat,pLng,pSog,prob, rCog,rLat,rLng,rSog,error";
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";

		try {
			fileWriter = new FileWriter(filePath, true);

			if (headerAdding) {
				// Write the CSV file header
				fileWriter.append(FILE_HEADER.toString());

				// Add a new line separator after the header
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
			ArrayList<LocationCogSogProbValue> list = rst.getMergedLocs();
            int size = list.size();

			for (int i = 0; i < size; i++) {
				// "vslid,start,pCog,pLat,pLng,pSog,rCog,rLat,rLng,rSog";
				fileWriter.append(vslid);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(convertTs2Str(start));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(list.get(i).getCog()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(list.get(i).getLat()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(list.get(i).getLng()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(list.get(i).getSog()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(list.get(i).getProb()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(realData==null?"-1":String.valueOf(realData.getCourse()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(realData==null?"-1":String.valueOf(realData.getLat()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(realData==null?"-1":String.valueOf(realData.getLng()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(realData==null?"-1":String.valueOf(realData.getVslSpeed()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(error));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}
	
	private String convertTs2Str(long ts) {
		java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date d = new java.util.Date(ts);
		String rs = sf.format(d);
		return rs;
	}
	
	private long convertStr2Ts2(String time) throws ParseException {
		java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

		java.util.Date d = sf.parse(time);
		long ts = d.getTime();
		return ts;
	}

}

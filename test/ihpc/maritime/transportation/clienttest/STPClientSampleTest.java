package ihpc.maritime.transportation.clienttest;

import ihpc.maritime.transportation.intermodel.STPTrajectoryModel;
import ihpc.maritime.transportation.intermodel.TrajectoryPoint;
import ihpc.maritime.transportation.system.common.SystemConstant;
import ihpc.maritime.transportation.util.AISLoader;
import ihpc.maritime.transportation.util.DistanceUtil;
import ihpc.maritime.transportation.vessel.entity.ATempInterpolationDataUnit;
import ihpc.maritime.transportation.webmodel.STPAPIRequestWithVMIMDataModel;
import ihpc.maritime.transportation.webmodel.STPAPIResponseForecastingDataModel;

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

public class STPClientSampleTest {

	final static Logger logger = Logger.getLogger(STPClientSampleTest.class);
	@Test
	public void test() {
		
		long ts = 1504058706000l;
		double tw = 10;
		
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
		
		STPAPIRequestWithVMIMDataModel dataModel = prepareDataForAPICall(vsls, SystemConstant.PF, ts,tw);
		long tic = System.currentTimeMillis();
		STPAPIResponseForecastingDataModel rst = callAPI(dataModel, "172.20.2.44", "8080");
		System.out.println(System.currentTimeMillis()-tic);
		
		System.out.println(rst.getForecastingData().size());
	}
	
	
/*	@Test
	public void test() {
		
		ArrayList<String> vsls = new ArrayList<String>();

//case 12748		
//		long start = 1506541130000l + 10*60*1000;
//		long end =  1506541130000l + 20*60*1000;
//		
//		vsls.add("525003277");
//		vsls.add("372827000");
//		vsls.add("209911000");
//		vsls.add("563011350");
//		vsls.add("525003080");
//		vsls.add("564294000");
//		vsls.add("525015666");
//		vsls.add("636015474");
//		vsls.add("370706000");
//		vsls.add("565552000");
//		vsls.add("477993800");
//		vsls.add("412014000");
//
//		errorLog(vsls, start, end, 10.0);
		
//case 12754		
//		long start = 1506550260000l + 10*60*1000;
//		long end =  1506550260000l + 20*60*1000;
//		
//		vsls.add("041335570");
//		vsls.add("563884000");
//		vsls.add("351563000");
//		vsls.add("210409000");
//		vsls.add("008674326");
//		vsls.add("564218000");
//		vsls.add("563258000");
//		vsls.add("045731900");
//		vsls.add("210040000");
//		vsls.add("566935000");
//		vsls.add("325860000");
//		
//		errorLog(vsls, start, end, 10.0);

// case 12701
//		long start = 1506446240000l + 10*60*1000;
//		long end =  1506446240000l + 20*60*1000;
//		
//		vsls.add("210138000");
//		vsls.add("525003412");
//		vsls.add("538003093");
//		vsls.add("372922000");
//		vsls.add("229129000");
//		vsls.add("477203800");
//		vsls.add("548827000");
//		errorLog(vsls, start, end, 10.0);

// case 13170
		long start = 1507565440000l + 10*60*1000;
		long end =  1507565440000l + 20*60*1000;
		
		vsls.add("525013024");
		vsls.add("477847200");
		vsls.add("563036620");
		vsls.add("533180146");
		vsls.add("413012320");
		vsls.add("235113778");
		vsls.add("566728000");
		vsls.add("357441000");
		errorLog(vsls, start, end, 10.0);
		
		
	}*/
	
	STPAPIRequestWithVMIMDataModel prepareDataForAPICall(List<String> vsls, int method, long ts, double tw){
		
		
		STPAPIRequestWithVMIMDataModel dataModel = new STPAPIRequestWithVMIMDataModel();
		HashMap<String, TrajectoryPoint[]> inputVMIMData = new HashMap<String, TrajectoryPoint[]>();

		
		// get the AIS data of last 10 minutes
		long tperiod = (long) (tw*60000);
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
	    dataModel.setPredictionTw(tw);
	    dataModel.setMethod(method);
	    dataModel.setInputVMIMData(inputVMIMData);
	    
		return dataModel;
	}
	
	
	public static STPAPIResponseForecastingDataModel callAPI(STPAPIRequestWithVMIMDataModel input, String ip, String port){
	     
		STPAPIResponseForecastingDataModel rst = null;
		
		try{
	    Gson gson = new Gson();
	    String json = gson.toJson(input);
		
		String USER_AGENT = "Mozilla/5.0";
	    String query = "http://"+ip+":"+port+"/maritime/akkastp";
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

	    rst = gson.fromJson(result,  new TypeToken<STPAPIResponseForecastingDataModel>() {
	    }.getType());

	    in.close();
	    conn.disconnect();
	    

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rst;

	}
	
	private void errorLog(List<String> vsls, long start, long end, double tw) {

		for (; start <= end; start += 10000) {
			
			try{
				
				STPAPIRequestWithVMIMDataModel dataModel = prepareDataForAPICall(vsls, SystemConstant.LINEAR, start, tw);
				STPAPIResponseForecastingDataModel rst = callAPI(dataModel, "172.20.2.44", "8080");
				ArrayList<STPTrajectoryModel> trajectoryList = (ArrayList<STPTrajectoryModel>) rst.getForecastingData();
				
				for(STPTrajectoryModel ele:trajectoryList){
					storeForecastingErrorCSV(ele, ele.getMmsi(), start, tw, "linear");
					saveForecastingResult(ele, ele.getMmsi(), start, tw, "linear");
				}
				
				dataModel = prepareDataForAPICall(vsls, SystemConstant.MLNN, start, tw);
				rst = callAPI(dataModel, "172.20.2.44", "8080");
				trajectoryList = (ArrayList<STPTrajectoryModel>) rst.getForecastingData();
				
				for(STPTrajectoryModel ele:trajectoryList){
					storeForecastingErrorCSV(ele, ele.getMmsi(), start, tw, "mlnn");
					saveForecastingResult(ele, ele.getMmsi(), start, tw, "mlnn");
				}
				
				dataModel = prepareDataForAPICall(vsls, SystemConstant.PF, start, tw);
				rst = callAPI(dataModel, "172.20.2.44", "8080");
				trajectoryList = (ArrayList<STPTrajectoryModel>) rst.getForecastingData();
				
				for(STPTrajectoryModel ele:trajectoryList){
					storeForecastingErrorCSV(ele, ele.getMmsi(), start, tw, "KB");
					saveForecastingResult(ele, ele.getMmsi(), start, tw, "KB");
				}
				
				System.out.println(start);
				
			}catch(Exception e){
				logger.error("error info", e);
				logger.error(start+":"+vsls.toString());
			}
			

			
		}

	}

	private void storeForecastingErrorCSV(STPTrajectoryModel rst, String vslid, long start, double min, String method) {

		String path = SystemConstant.STPForecastingResultFileDir + method;
		
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		
		String filePath = SystemConstant.STPForecastingResultFileDir + method + "/" + vslid + ".csv";
		
		ArrayList<ATempInterpolationDataUnit> realData = AISLoader.getAISdata(vslid, start+1000, start+(long)(min*60000));
		TrajectoryPoint[] pData = rst.getTrajectory();

		boolean headerAdding = true;
		if (new File(filePath).exists())
			headerAdding = false;

		FileWriter fileWriter = null;

		String FILE_HEADER = "vslid,ts,ptw,lat_error,lng_error,cog_error,sog_error,dist_error,pm";
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

			int size = realData.size();

			for (int i = 0; i < size; i++) {
				fileWriter.append(vslid);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(start));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(i + 1));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(Math.abs(realData.get(i).getLat() - pData[i].getLat())));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(Math.abs(realData.get(i).getLng() - pData[i].getLng())));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(Math.abs(realData.get(i).getCourse() - pData[i].getCog())<=180?Math.abs(realData.get(i).getCourse() - pData[i].getCog()):360-Math.abs(realData.get(i).getCourse() - pData[i].getCog())));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(Math.abs(realData.get(i).getVslSpeed() - pData[i].getSog())));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(DistanceUtil.distance(realData.get(i).getLat(),
						realData.get(i).getLng(), pData[i].getLat(), pData[i].getLng(), "K")));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(method));
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

	private void saveForecastingResult(STPTrajectoryModel rst, String vslid, long start, double min, String method) {
		String path = SystemConstant.STPForecastingResultFileDir + method;
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();

		String filePath = path + "/result_" + vslid + ".csv";
		
		ArrayList<ATempInterpolationDataUnit> realData = AISLoader.getAISdata(vslid, start+1000, start+(long)(min*60000));
		TrajectoryPoint[] pData = rst.getTrajectory();
		

		boolean headerAdding = true;
		if (new File(filePath).exists())
			headerAdding = false;

		FileWriter fileWriter = null;

		String FILE_HEADER = "vslid,start,ts, pCog,pLat,pLng,pSpeed,rCog,rLat,rLng,rSpeed";
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

			int size = realData.size();

			for (int i = 0; i < size; i++) {
				fileWriter.append(vslid);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(convertTs2Str(start));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(convertTs2Str(start + (i + 1) * 1000));
				fileWriter.append(COMMA_DELIMITER);
				// "vslid,start,ts, pCog,pLat,pLng,pSpeed,rCog,rLat,rLng,rSpeed";
				fileWriter.append(String.valueOf(pData[i].getCog()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(pData[i].getLat()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(pData[i].getLng()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(pData[i].getSog()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(realData.get(i).getCourse()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(realData.get(i).getLat()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(realData.get(i).getLng()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(realData.get(i).getVslSpeed()));
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

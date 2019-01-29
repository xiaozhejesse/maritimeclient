package ihpc.maritime.transportation.util;

import ihpc.maritime.transportation.vessel.dao.ATempInterpolationDataUnitDAO;
import ihpc.maritime.transportation.vessel.entity.ATempInterpolationDataUnit;

import java.util.ArrayList;

public class AISLoader {
	
	/**
	 * if AIS data source is stored in database, call this method
	 * 
	 * @param vslId
	 * @param preStartTime
	 * @param pTimestamp
	 * @return
	 */
	public static ArrayList<ATempInterpolationDataUnit> getAISdata(String vslId, long preStartTime, long pTimestamp){
		ATempInterpolationDataUnitDAO dao = new ATempInterpolationDataUnitDAO();
		return (ArrayList<ATempInterpolationDataUnit>) dao
		.queryDataByIdTimePeriod(vslId, preStartTime, pTimestamp);
	}
	
	public static ATempInterpolationDataUnit getAISdata(String vslId, long ts){
		ATempInterpolationDataUnitDAO dao = new ATempInterpolationDataUnitDAO();
		ArrayList<ATempInterpolationDataUnit> list = (ArrayList<ATempInterpolationDataUnit>) dao.queryDataByIdTimestamp(vslId, ts);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
	}
	

}

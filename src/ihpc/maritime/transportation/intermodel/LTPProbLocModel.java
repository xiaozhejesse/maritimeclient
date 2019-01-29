package ihpc.maritime.transportation.intermodel;

import java.util.ArrayList;

public class LTPProbLocModel {
	
	private String errorCode;

	/**
	 * The predicted locs with state value and probability (if the waterway has branches, all the locs from each branches are not differentiated and merged for storage)
	 */
	private ArrayList<LocationCogSogProbValue> mergedLocs;
	
	private String vslid;
	

	public LTPProbLocModel(){
		mergedLocs = new ArrayList<LocationCogSogProbValue>();
	}
	
	public LTPProbLocModel(String errorCode, String vslid, ArrayList<LocationCogSogProbValue> mergedLocs){
		this.errorCode = errorCode;
		this.vslid = vslid;
		this.mergedLocs = mergedLocs;
	}

	public String getErrorCode() {
		return errorCode;
	}


	public ArrayList<LocationCogSogProbValue> getMergedLocs() {
		return mergedLocs;
	}

	public String getVslid() {
		return vslid;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public void setMergedLocs(ArrayList<LocationCogSogProbValue> mergedLocs) {
		this.mergedLocs = mergedLocs;
	}

	
	public void setVslid(String vslid) {
		this.vslid = vslid;
	}
	

}

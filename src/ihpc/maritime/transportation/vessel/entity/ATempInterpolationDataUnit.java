package ihpc.maritime.transportation.vessel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/*
//this is for web usage
@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "temp_interp_id")
@Table(name = "temp_interpais")
*/


//this is for testing the one round prediction for all vessels(long term prediction)
 
/*@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "temp_interp_test_id")
@Table(name = "temp_interpais_test")*/

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "temp_interp_tits_id")
//@Table(name = "temp_interpais_tits")
//@Table(name = "interp_vmim")
@Table(name = "interp_vmim_19042018")
//@Table(name = "interp_vmim_ltphs03")
public class ATempInterpolationDataUnit extends SequenceObject{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "vslid")
	private String vslId;
	
	@Column(name = "lat")
	private double lat;
	
	@Column(name = "lng")
	private double lng;
	
	@Column(name = "timestamp")
	private long timestamp;
	
	@Column(name = "vslspeed")
	private double vslSpeed;
	
	@Column(name="course")
	private double course;

	@Column(name = "vslty")
	private String vslTy;

	@Column(name = "distance")
	private Double distance;

	public double getCourse() {
		return course;
	}

	public Double getDistance() {
		return distance;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getVslId() {
		return vslId;
	}
	
	public double getVslSpeed() {
		return vslSpeed;
	}


	public String getVslTy() {
		return vslTy;
	}


	public void setCourse(double course) {
		this.course = course;
	}


	public void setDistance(Double distance) {
		this.distance = distance;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}
	

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public void setVslId(String vslId) {
		this.vslId = vslId;
	}


	public void setVslSpeed(double vslSpeed) {
		this.vslSpeed = vslSpeed;
	}
	

	public void setVslTy(String vslTy) {
		this.vslTy = vslTy;
	}
	

}

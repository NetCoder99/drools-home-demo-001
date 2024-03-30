package org.home.demo.x001.models;

public class TaxiRide {
    private Boolean isNightSurcharge;
	private Long    perMileCharge;
    private Long    distanceInMile;
	private String  customerName;
    
	public TaxiRide() {
		super();
	}
    
	public TaxiRide(Boolean isNightSurcharge, Long distanceInMile) {
		super();
		this.isNightSurcharge = isNightSurcharge;
		this.distanceInMile = distanceInMile;
	}

	public Long getPerMileCharge() {
		return perMileCharge;
	}

	public void setPerMileCharge(Long perMileCharge) {
		this.perMileCharge = perMileCharge;
	}

	public Boolean getIsNightSurcharge() {
		return isNightSurcharge;
	}
	public void setIsNightSurcharge(Boolean isNightSurcharge) {
		this.isNightSurcharge = isNightSurcharge;
	}
	public Long getDistanceInMile() {
		return distanceInMile;
	}
	public void setDistanceInMile(Long distanceInMile) {
		this.distanceInMile = distanceInMile;
	}
	public Boolean getNightSurcharge() {
		return isNightSurcharge;
	}
	public void setNightSurcharge(Boolean nightSurcharge) {
		isNightSurcharge = nightSurcharge;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}

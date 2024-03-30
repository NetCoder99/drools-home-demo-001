package org.home.demo.x001.models;

public class TaxiFare {
    private Long nightSurcharge;
    private Long rideFare;
    
	public TaxiFare() {
		super();
	}
    
    
	public TaxiFare(Long nightSurcharge, Long rideFare) {
		super();
		this.nightSurcharge = nightSurcharge;
		this.rideFare = rideFare;
	}
	
	public Long getNightSurcharge() {
		return nightSurcharge;
	}
	public void setNightSurcharge(Long nightSurcharge) {
		this.nightSurcharge = nightSurcharge;
	}
	public Long getRideFare() {
		return rideFare;
	}
	public void setRideFare(Long rideFare) {
		this.rideFare = rideFare;
	}
    
    

}

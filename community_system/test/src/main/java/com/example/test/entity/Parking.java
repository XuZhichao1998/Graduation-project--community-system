package com.example.test.entity;


public class Parking {
	private int parkingId;
	private String parkingPlaceDescription;
	private String parkingCarNumber;

	public Parking() {}
	public Parking(int parkingId, String parkingPlaceDescription, String parkingCarNumber) {
		this.parkingId = parkingId;
		this.parkingPlaceDescription = parkingPlaceDescription;
		this.parkingCarNumber = parkingCarNumber;
	}
	public String showMsg() {
		return "parkingId:["+parkingId+"],parkPlaceDes:["+parkingPlaceDescription+"],parkingCarNumber:["+parkingCarNumber+"]";
	}

	public int getParkingId() {
		return parkingId;
	}
	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
	}
	public String getParkingPlaceDescription() {
		return parkingPlaceDescription;
	}
	public void setParkingPlaceDescription(String parkingPlaceDescription) {
		this.parkingPlaceDescription = parkingPlaceDescription;
	}
	public String getParkingCarNumber() {
		return parkingCarNumber;
	}
	public void setParkingCarNumber(String parkingCarNumber) {
		this.parkingCarNumber = parkingCarNumber;
	}
	
}

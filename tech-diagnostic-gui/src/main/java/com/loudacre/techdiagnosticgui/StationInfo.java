package com.loudacre.techdiagnosticgui;

import java.util.Random;

public class StationInfo {
	String stationId;
	float ambientTemp;
	float voltage;
	float humidity;
	DoorStatus doorStatus;
	Status status;

	Random random = new Random();

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public float getAmbientTemp() {
		return ambientTemp;
	}

	public void setAmbientTemp(float ambientTemp) {
		this.ambientTemp = ambientTemp;
	}

	public float getVoltage() {
		return voltage;
	}

	public void setVoltage(float voltage) {
		this.voltage = voltage;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public DoorStatus getDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(DoorStatus doorStatus) {
		this.doorStatus = doorStatus;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}

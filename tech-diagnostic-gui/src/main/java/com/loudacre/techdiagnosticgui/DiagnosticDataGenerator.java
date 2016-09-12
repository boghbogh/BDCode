package com.loudacre.techdiagnosticgui;

import java.util.HashMap;
import java.util.Random;

/**
 * Generates data to simulate what might be collected from a base station.
 */
public class DiagnosticDataGenerator {
	Random random = new Random();

	HashMap<String, StationInfo> stationIdToStationInfoMap = new HashMap<String, StationInfo>();

	/**
	 * Gets a string representation of a StationInfo for the station id
	 * 
	 * @param stationId
	 *            The station id to get
	 * @return The string representation of a StationInfo for the station id
	 */
	public String getRecord(String stationId) {
		StationInfo stationInfo = stationIdToStationInfoMap.get(stationId);

		if (stationInfo == null) {
			// Station not found, create it
			stationInfo = create(stationId);
			stationIdToStationInfoMap.put(stationId, stationInfo);
		} else {
			// Already exists, update the data in it
			update(stationInfo);
		}

		return stationInfoToString(stationInfo);
	}

	/**
	 * Creates a StationInfo object and initializes it
	 * 
	 * @param stationId
	 *            The id of the object
	 * @return A StationInfo object
	 */
	public StationInfo create(String stationId) {
		StationInfo info = new StationInfo();

		info.setStationId(stationId);

		info.setAmbientTemp(random.nextFloat() * 20 + 10);
		info.setVoltage(random.nextFloat() * 15 + 108);
		info.setHumidity(0.5f - random.nextFloat() * 0.3f);

		updateStatuses(info);

		return info;
	}

	/**
	 * Updates the door and overall status
	 */
	public void updateStatuses(StationInfo info) {
		if (random.nextInt(100) <= 7) {
			info.setDoorStatus(DoorStatus.OPEN);
		} else {
			info.setDoorStatus(DoorStatus.CLOSED);
		}

		if (info.getDoorStatus().equals(DoorStatus.OPEN)) {
			if (random.nextInt(100) <= 2) {
				info.setStatus(Status.FAILED);
			} else {
				info.setStatus(Status.MAINTENANCE_ONGOING);
			}
		} else {
			if (random.nextInt(100) <= 5) {
				info.setStatus(Status.FAILED);
			} else {
				info.setStatus(Status.OK);
			}
		}
	}

	/**
	 * Updates the values based on the previous value
	 */
	public void update(StationInfo info) {
		updateStatuses(info);

		info.setAmbientTemp(info.getAmbientTemp() + getFloat(0.01f));

		info.setVoltage(info.getVoltage() + getFloat(0.01f));

		info.setHumidity(info.getHumidity() + getFloat(0.01f));
	}

	/**
	 * Gets a positive or negative float in the range
	 * 
	 * @param range
	 *            The range
	 * @return A positive or negative float in the range
	 */
	private float getFloat(float range) {
		float nextFloat = random.nextFloat() * range;

		if (random.nextBoolean()) {
			nextFloat *= -1f;
		}

		return nextFloat;
	}

	/**
	 * Takes a StationInfo object and creates a formatted string to be used with Log4J
	 * 
	 * @param info
	 *            The StationInfo object
	 * @return A string representation for Log4J
	 */
	public static String stationInfoToString(StationInfo info) {
		StringBuilder sb = new StringBuilder();

		sb.append("STATION:").append(info.getStationId()).append(";");

		sb.append("AMBIENT_TEMP:");
		sb.append(String.format("%.3f", info.getAmbientTemp()));
		sb.append(";");

		sb.append("VOLTAGE:");
		sb.append(String.format("%.3f", info.getVoltage()));
		sb.append(";");

		sb.append("HUMIDITY:");
		sb.append(String.format("%.2f", info.getHumidity()));
		sb.append(";");

		sb.append("DOOR:").append(info.getDoorStatus().name()).append(";");

		sb.append("STATUS:").append(info.getStatus().name());

		return sb.toString();
	}
}

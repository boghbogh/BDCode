package com.loudacre.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.loudacre.data.DeviceData;

/**
 * Helper class to make it easier to normalize device status
 */
public class DeviceDataHelper {
	private static final Logger logger = Logger.getLogger(DeviceDataHelper.class);

	/** The format of incoming timestamps */
	private final SimpleDateFormat deviceStatusFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

	/**
	 * Takes in a device status line, parses, normalizes and creates a DeviceData object
	 * 
	 * @param statusLine
	 *            The device status line
	 * @return A DeviceData object based on the device status line
	 */
	public DeviceData parseLine(String statusLine) {
		DeviceData deviceData;

		// Create a DeviceData object based on the type of device coming in
		if (statusLine.contains("Sorrento")) {
			deviceData = parseSorrento(statusLine);
		} else if (statusLine.contains("iFruit")) {
			deviceData = parseiFruit(statusLine);
		} else if (statusLine.contains("Ronin")) {
			deviceData = parseRonin(statusLine);
		} else if (statusLine.contains("MeeToo")) {
			deviceData = parseMeeToo(statusLine);
		} else if (statusLine.contains("Titanic")) {
			deviceData = parseTitanic(statusLine);
		} else {
			logger.warn("An unsupported device came in, filtering out the event. The line was:" + statusLine);

			deviceData = null;
		}

		return deviceData;
	}

	/**
	 * Converts the timestamp from a string representation to time since epoch
	 * 
	 * @param timeString
	 *            The string representation of the timestamp
	 * @return The timestamp as time since epoch
	 */
	private long getTimestamp(String timeString) {
		long timestamp;

		try {
			timestamp = deviceStatusFormat.parse(timeString).getTime();
		} catch (ParseException e) {
			logger.error(e);

			timestamp = 0;
		}

		return timestamp;
	}

	/**
	 * Creates a DeviceData object based on the strings passed in. All strings are converted to the proper format like
	 * int or float.
	 * 
	 * @param timestampString
	 *            The timestamp
	 * @param id
	 *            The device's unique id
	 * @param name
	 *            The name of the device
	 * @param device_tempString
	 *            The device's temperature
	 * @param ambient_tempString
	 *            The device's ambient temperature
	 * @param battery_pctString
	 *            The device's battery as a percentage
	 * @param signal_pctString
	 *            The device's signal strength as a percentage
	 * @param cpu_loadString
	 *            The device's CPU load as a percentage
	 * @param ram_usageString
	 *            The device's RAM usage as a percentage
	 * @param gps_status
	 *            The device's GPS status
	 * @param bluetooth_status
	 *            The device's Bluetooth status
	 * @param wifi_status
	 *            The device's Wifi status
	 * @param latitudeString
	 *            The device's current latitude
	 * @param longitudeString
	 *            The device's current longitude
	 * @return The DeviceData object with all data
	 */
	private DeviceData createDeviceData(String timestampString, String id, String name, String device_tempString,
			String ambient_tempString, String battery_pctString, String signal_pctString, String cpu_loadString,
			String ram_usageString, String gps_status, String bluetooth_status, String wifi_status,
			String latitudeString, String longitudeString) {
		// Parse the timestamp
		long timestamp = getTimestamp(timestampString);

		// Parse the percentages
		int device_temp = Integer.parseInt(device_tempString);
		int ambient_temp = Integer.parseInt(ambient_tempString);
		int battery_pct = Integer.parseInt(battery_pctString);
		int signal_pct = Integer.parseInt(signal_pctString);
		int cpu_load = Integer.parseInt(cpu_loadString);
		int ram_usage = Integer.parseInt(ram_usageString);

		// Parse the lat/longs
		float longitude = Float.parseFloat(longitudeString);
		float latitude = Float.parseFloat(latitudeString);

		return new DeviceData(timestamp, name, id, device_temp, ambient_temp, battery_pct, signal_pct, cpu_load,
				ram_usage, gps_status, bluetooth_status, wifi_status, longitude, latitude);
	}

	/**
	 * Parses the Sorrento's status line to create a DeviceData object
	 * 
	 * @param statusLine
	 *            The status line to parse
	 * @return The DeviceData object with all of the data
	 */
	private DeviceData parseSorrento(String statusLine) {
		// Split the line based on its delimiter
		String[] pieces = statusLine.split(",");

		return createDeviceData(pieces[0], pieces[2], pieces[1], pieces[3], pieces[4], pieces[5], pieces[9],
				pieces[10], pieces[11], pieces[6], pieces[7], pieces[8], pieces[12], pieces[13]);
	}

	/**
	 * Parses the iFruit's status line to create a DeviceData object
	 * 
	 * @param statusLine
	 *            The status line to parse
	 * @return The DeviceData object with all of the data
	 */
	private DeviceData parseiFruit(String statusLine) {
		// Split the line based on its delimiter
		String[] pieces = statusLine.split(",");

		return createDeviceData(pieces[0], pieces[2], pieces[1], pieces[3], pieces[4], pieces[5], pieces[9],
				pieces[10], pieces[11], pieces[6], pieces[7], pieces[8], pieces[12], pieces[13]);
	}

	/**
	 * Parses the Ronin's status line to create a DeviceData object
	 * 
	 * @param statusLine
	 *            The status line to parse
	 * @return The DeviceData object with all of the data
	 */
	private DeviceData parseRonin(String statusLine) {
		// Split the line based on its delimiter
		String[] pieces = statusLine.split(",");

		return createDeviceData(pieces[0], pieces[2], pieces[1], pieces[3], pieces[4], pieces[5], pieces[6],
				pieces[10], pieces[11], pieces[7], pieces[8], pieces[9], pieces[12], pieces[13]);
	}

	/**
	 * Parses the MeeToo's status line to create a DeviceData object
	 * 
	 * @param statusLine
	 *            The status line to parse
	 * @return The DeviceData object with all of the data
	 */
	private DeviceData parseMeeToo(String statusLine) {
		// Split the line based on its delimiter
		String[] pieces = statusLine.split("\\|");

		return createDeviceData(pieces[0], pieces[2], pieces[1], pieces[3], pieces[4], pieces[5], pieces[6], pieces[7],
				pieces[8], pieces[9], pieces[10], pieces[11], pieces[12], pieces[13]);
	}

	/**
	 * Parses the Titanic's status line to create a DeviceData object
	 * 
	 * @param statusLine
	 *            The status line to parse
	 * @return The DeviceData object with all of the data
	 */
	private DeviceData parseTitanic(String statusLine) {
		// Split the line based on its delimiter
		String[] pieces = statusLine.split("/");

		return createDeviceData(pieces[0], pieces[2], pieces[1], pieces[8], pieces[7], pieces[3], pieces[4], pieces[5],
				pieces[6], pieces[9], pieces[10], pieces[11], pieces[12], pieces[13]);
	}
}

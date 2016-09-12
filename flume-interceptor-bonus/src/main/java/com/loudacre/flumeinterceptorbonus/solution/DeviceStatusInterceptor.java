package com.loudacre.flumeinterceptorbonus.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.apache.log4j.Logger;

import com.loudacre.data.DeviceData;
import com.loudacre.helper.DeviceDataHelper;

public class DeviceStatusInterceptor implements Interceptor {

	private static Logger logger = Logger.getLogger(DeviceStatusInterceptor.class);
	
	/** Helper class to parse the device status */
	DeviceDataHelper deviceDataHelper;

	/** The delimiter to use for serialization and deserialization */
	String delimiter;

	/** Should the temperature delta be fixed */
	boolean fixDelta;

	/**
	 * Constructor
	 * 
	 * @param delimiter
	 *            The delimiter to use for serialization and deserialization
	 * @param fixDelta
	 *            Should the temperature delta be fixed
	 */
	public DeviceStatusInterceptor(String delimiter, boolean fixDelta) {
		this.delimiter = delimiter;
		this.fixDelta = fixDelta;
		
		logger.info("Created DeviceStatusInterceptor with delimiter \"" + delimiter + "\" and fixDelta " + fixDelta);
	}

	@Override
	public void close() {
		// Do nothing
	}

	@Override
	public void initialize() {
		deviceDataHelper = new DeviceDataHelper();
	}

	@Override
	public Event intercept(Event event) {
		// Create a string representation of the line coming in to Flume
		String statusLine = new String(event.getBody());

		// Parse and normalize the lines
		DeviceData deviceData = deviceDataHelper.parseLine(statusLine);

		if (fixDelta == true) {
			// Make the device temp a whole number and not a delta
			deviceData.setDeviceTemp(deviceData.getAmbientTemp() + deviceData.getDeviceTemp());
		}

		// Get the delimited version of the data
		String delimitedString = deviceData.toDelimitedString(delimiter);

		// Write the bytes to the body
		event.setBody(delimitedString.getBytes());

		return event;
	}

	@Override
	public List<Event> intercept(List<Event> events) {
		ArrayList<Event> eventList = new ArrayList<Event>();

		for (Event event : events) {
			eventList.add(intercept(event));
		}

		return eventList;
	}

	public static class Builder implements Interceptor.Builder {
		/** The delimiter to use for serialization and deserialization */
		String delimiter;

		/** Should the temperature delta be fixed */
		boolean fixDelta;

		@Override
		public void configure(Context context) {
			delimiter = context.getString("delimiter", "\t");
			fixDelta = context.getBoolean("fixDelta", true);
		}

		@Override
		public Interceptor build() {
			return new DeviceStatusInterceptor(delimiter, fixDelta);
		}
	}
}

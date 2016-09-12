package com.loudacre.flumeinterceptor.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import com.loudacre.data.DeviceData;
import com.loudacre.helper.DeviceDataHelper;

public class DeviceStatusInterceptor implements Interceptor {
	/** Helper class to parse the device status */
	DeviceDataHelper deviceDataHelper;
	
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
		
		// Make the device temp a whole number and not a delta
		deviceData.setDeviceTemp(deviceData.getAmbientTemp() + deviceData.getDeviceTemp());
		
		// Get the tab delimited version of the data
		String tabDelimited = deviceData.toTabDelimitedString();
		
		// Write the bytes to the body
		event.setBody(tabDelimited.getBytes());
		
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

		@Override
		public void configure(Context context) {
			// Do nothing
		}

		@Override
		public Interceptor build() {
			return new DeviceStatusInterceptor();
		}

	}
}

package com.loudacre.flumeinterceptorbonus.hints;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.junit.Before;
import org.junit.Test;

import com.loudacre.data.DeviceData;

// TODO: replace 'solution' in the package below with 'hints' so that
// you test the class you've been working on.
import com.loudacre.flumeinterceptorbonus.solution.DeviceStatusInterceptor;

public class InterceptorTest {

	DeviceStatusInterceptor interceptor;
	
	private final SimpleDateFormat deviceStatusFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
	
	private static final String ENABLED = "enabled";
	private static final String CONNECTED = "connected";
	private static final String DISABLED = "disabled";

	@Before
	public void init() {
		interceptor = new DeviceStatusInterceptor("\t", true);
		interceptor.initialize();
	}

	/**
	 * Create an event with the line as the body
	 * 
	 * @param line
	 *            The line to use
	 * @return An event object with the line as the body
	 */
	private Event createEvent(String line) {
		return EventBuilder.withBody(line.getBytes());
	}

	/**
	 * Reads the DeviceData object in the event body
	 * 
	 * @param event
	 *            The event object to use
	 * @return The DeviceData object in the event body
	 * @throws IOException
	 */
	private DeviceData getDeviceData(Event event) throws IOException {
		return getDeviceData(event, "\t");
	}
	
	/**
	 * Reads the DeviceData object in the event body
	 * 
	 * @param event
	 *            The event object to use
	 * @return The DeviceData object in the event body
	 * @throws IOException
	 */
	private DeviceData getDeviceData(Event event, String delimiter) throws IOException {
		String body = new String(event.getBody());
		
		DeviceData result = new DeviceData();
		result.fromDelimitedString(body, delimiter);

		return result;
	}

	@Test
	public void testMeeToo() throws IOException, ParseException {
		Event event = createEvent("2014-03-15:10:10:20|MeeToo 1.0|myid|1|26|88|96|37|43|enabled|disabled|connected|33.8240128449|-117.74018268");

		Event interceptedEvent = interceptor.intercept(event);
		DeviceData deviceData = getDeviceData(interceptedEvent);

		long inputTime = deviceStatusFormat.parse("2014-03-15:10:10:20").getTime();
		assertEquals("Time did not match", inputTime, deviceData.getTime());

		// TODO write assertions to verify the device name and ID

		assertEquals("Device temp did not match", 27, deviceData.getDeviceTemp());
		assertEquals("Ambient temp did not match", 26, deviceData.getAmbientTemp());
		assertEquals("Battery percent did not match", 88, deviceData.getBatteryPct());
		assertEquals("Signal percent did not match", 96, deviceData.getSignalPct());
		assertEquals("CPU Load did not match", 37, deviceData.getCpuLoad());
		assertEquals("RAM Usage did not match", 43, deviceData.getRamUsage());

		assertEquals("GPS Status did not match", ENABLED, deviceData.getGpsStatus());
		assertEquals("Bluetooth Status did not match", DISABLED, deviceData.getBluetoothStatus());
		assertEquals("Wifi Status did not match", CONNECTED, deviceData.getWifiStatus());

		// TODO write assertions to verify the latitude and longitude fields
	}

	@Test
	public void testSorrento() throws IOException, ParseException {
		Event event = createEvent("2014-03-15:10:10:20,Sorrento F01L,myid,1,11,82,enabled,enabled,enabled,69,27,59,34.0948572303,-112.034915227");

		Event interceptedEvent = interceptor.intercept(event);
		DeviceData deviceData = getDeviceData(interceptedEvent);

		long inputTime = deviceStatusFormat.parse("2014-03-15:10:10:20").getTime();
		assertEquals("Time did not match", inputTime, deviceData.getTime());

		assertEquals("Device name did not match", "Sorrento F01L", deviceData.getName());
		assertEquals("Device id did not match", "myid", deviceData.getDeviceId());

		// TODO write assertions to verify the device temperature and ambient temperature fields
		assertEquals("Battery percent did not match", 82, deviceData.getBatteryPct());
		assertEquals("Signal percent did not match", 69, deviceData.getSignalPct());
		assertEquals("CPU Load did not match", 27, deviceData.getCpuLoad());
		assertEquals("RAM Usage did not match", 59, deviceData.getRamUsage());

		assertEquals("GPS Status did not match", ENABLED, deviceData.getGpsStatus());
		assertEquals("Bluetooth Status did not match", ENABLED, deviceData.getBluetoothStatus());
		assertEquals("Wifi Status did not match", ENABLED, deviceData.getWifiStatus());

		assertEquals("Latitude did not match", 34.0948572303f, deviceData.getLatitude(), 0);
		assertEquals("Longitude did not match", -112.034915227f, deviceData.getLongitude(), 0);
	}

	@Test
	public void testiFruit() throws IOException, ParseException {
		Event event = createEvent("2014-03-15:10:10:20,iFruit 3A,myid,1,23,12,disabled,disabled,disabled,75,33,45,0,0");

		Event interceptedEvent = interceptor.intercept(event);
		DeviceData deviceData = getDeviceData(interceptedEvent);

		long inputTime = deviceStatusFormat.parse("2014-03-15:10:10:20").getTime();
		assertEquals("Time did not match", inputTime, deviceData.getTime());

		assertEquals("Device name did not match", "iFruit 3A", deviceData.getName());
		assertEquals("Device id did not match", "myid", deviceData.getDeviceId());

		assertEquals("Device temp did not match", 24, deviceData.getDeviceTemp());
		assertEquals("Ambient temp did not match", 23, deviceData.getAmbientTemp());

		// TODO write assertions to verify the battery percent and signal percent fields
		assertEquals("CPU Load did not match", 33, deviceData.getCpuLoad());
		assertEquals("RAM Usage did not match", 45, deviceData.getRamUsage());

		assertEquals("GPS Status did not match", DISABLED, deviceData.getGpsStatus());
		assertEquals("Bluetooth Status did not match", DISABLED, deviceData.getBluetoothStatus());
		assertEquals("Wifi Status did not match", DISABLED, deviceData.getWifiStatus());

		assertEquals("Latitude did not match", 0f, deviceData.getLatitude(), 0);
		assertEquals("Longitude did not match", 0f, deviceData.getLongitude(), 0);
	}

	@Test
	public void testRonin() throws IOException, ParseException {
		Event event = createEvent("2014-03-15:10:10:20,Ronin S1,myid,-1,34,4,70,enabled,enabled,connected,59,50,36.1210390863,-119.098394626");

		Event interceptedEvent = interceptor.intercept(event);
		DeviceData deviceData = getDeviceData(interceptedEvent);

		long inputTime = deviceStatusFormat.parse("2014-03-15:10:10:20").getTime();
		assertEquals("Time did not match", inputTime, deviceData.getTime());

		assertEquals("Device name did not match", "Ronin S1", deviceData.getName());
		assertEquals("Device id did not match", "myid", deviceData.getDeviceId());

		assertEquals("Device temp did not match", 33, deviceData.getDeviceTemp());
		assertEquals("Ambient temp did not match", 34, deviceData.getAmbientTemp());
		assertEquals("Battery percent did not match", 4, deviceData.getBatteryPct());
		assertEquals("Signal percent did not match", 70, deviceData.getSignalPct());

		// TODO write assertions to verify the CPU load and RAM usage fields

		assertEquals("GPS Status did not match", ENABLED, deviceData.getGpsStatus());
		assertEquals("Bluetooth Status did not match", ENABLED, deviceData.getBluetoothStatus());
		assertEquals("Wifi Status did not match", CONNECTED, deviceData.getWifiStatus());

		assertEquals("Latitude did not match", 36.1210390863f, deviceData.getLatitude(), 0);
		assertEquals("Longitude did not match", -119.098394626f, deviceData.getLongitude(), 0);
	}

	@Test
	public void testTitanic() throws IOException, ParseException {
		Event event = createEvent("2014-03-15:10:10:20/Titanic 1000/myid/6/77/40/6/22/-2/enabled/enabled/enabled/41.2886752115/-117.021361217");

		Event interceptedEvent = interceptor.intercept(event);
		DeviceData deviceData = getDeviceData(interceptedEvent);

		long inputTime = deviceStatusFormat.parse("2014-03-15:10:10:20").getTime();
		assertEquals("Time did not match", inputTime, deviceData.getTime());

		assertEquals("Device name did not match", "Titanic 1000", deviceData.getName());
		assertEquals("Device id did not match", "myid", deviceData.getDeviceId());

		assertEquals("Device temp did not match", 20, deviceData.getDeviceTemp());
		assertEquals("Ambient temp did not match", 22, deviceData.getAmbientTemp());
		assertEquals("Battery percent did not match", 6, deviceData.getBatteryPct());
		assertEquals("Signal percent did not match", 77, deviceData.getSignalPct());
		assertEquals("CPU Load did not match", 40, deviceData.getCpuLoad());
		assertEquals("RAM Usage did not match", 6, deviceData.getRamUsage());

		// TODO write assertions to verify the GPS, Bluetooth, and WiFi statue fields

		assertEquals("Latitude did not match", 41.2886752115f, deviceData.getLatitude(), 0);
		assertEquals("Longitude did not match", -117.021361217f, deviceData.getLongitude(), 0);
	}
	
	@Test
	public void testDelimiter() throws IOException, ParseException {
		Event event = createEvent("2014-03-15:10:10:20/Titanic 1000/myid/6/77/40/6/22/-2/enabled/enabled/enabled/41.2886752115/-117.021361217");

		DeviceStatusInterceptor commaInterceptor = new DeviceStatusInterceptor(",", true);
		commaInterceptor.initialize();
		
		Event interceptedEvent = commaInterceptor.intercept(event);
		DeviceData deviceData = getDeviceData(interceptedEvent, ",");

		long inputTime = deviceStatusFormat.parse("2014-03-15:10:10:20").getTime();
		assertEquals("Time did not match", inputTime, deviceData.getTime());

		assertEquals("Device name did not match", "Titanic 1000", deviceData.getName());
		assertEquals("Device id did not match", "myid", deviceData.getDeviceId());

		assertEquals("Device temp did not match", 20, deviceData.getDeviceTemp());
		assertEquals("Ambient temp did not match", 22, deviceData.getAmbientTemp());
		assertEquals("Battery percent did not match", 6, deviceData.getBatteryPct());
		assertEquals("Signal percent did not match", 77, deviceData.getSignalPct());
		assertEquals("CPU Load did not match", 40, deviceData.getCpuLoad());
		assertEquals("RAM Usage did not match", 6, deviceData.getRamUsage());

		assertEquals("GPS Status did not match", ENABLED, deviceData.getGpsStatus());
		assertEquals("Bluetooth Status did not match", ENABLED, deviceData.getBluetoothStatus());
		assertEquals("Wifi Status did not match", ENABLED, deviceData.getWifiStatus());

		assertEquals("Latitude did not match", 41.2886752115f, deviceData.getLatitude(), 0);
		assertEquals("Longitude did not match", -117.021361217f, deviceData.getLongitude(), 0);
	}
	
	@Test
	public void testNoFixDelta() throws IOException, ParseException {
		Event event = createEvent("2014-03-15:10:10:20/Titanic 1000/myid/6/77/40/6/22/-2/enabled/enabled/enabled/41.2886752115/-117.021361217");

		DeviceStatusInterceptor commaInterceptor = new DeviceStatusInterceptor("\t", false);
		commaInterceptor.initialize();
		
		Event interceptedEvent = commaInterceptor.intercept(event);
		DeviceData deviceData = getDeviceData(interceptedEvent);

		long inputTime = deviceStatusFormat.parse("2014-03-15:10:10:20").getTime();
		assertEquals("Time did not match", inputTime, deviceData.getTime());

		assertEquals("Device name did not match", "Titanic 1000", deviceData.getName());
		assertEquals("Device id did not match", "myid", deviceData.getDeviceId());

		assertEquals("Device temp did not match", -2, deviceData.getDeviceTemp());
		assertEquals("Ambient temp did not match", 22, deviceData.getAmbientTemp());
		assertEquals("Battery percent did not match", 6, deviceData.getBatteryPct());
		assertEquals("Signal percent did not match", 77, deviceData.getSignalPct());
		assertEquals("CPU Load did not match", 40, deviceData.getCpuLoad());
		assertEquals("RAM Usage did not match", 6, deviceData.getRamUsage());

		assertEquals("GPS Status did not match", ENABLED, deviceData.getGpsStatus());
		assertEquals("Bluetooth Status did not match", ENABLED, deviceData.getBluetoothStatus());
		assertEquals("Wifi Status did not match", ENABLED, deviceData.getWifiStatus());

		assertEquals("Latitude did not match", 41.2886752115f, deviceData.getLatitude(), 0);
		assertEquals("Longitude did not match", -117.021361217f, deviceData.getLongitude(), 0);
	}
}

package com.loudacre.data;

/**
 * Class to represent a device's data snapshot
 */
public class DeviceData {
	private long time;
	private String name;
	private String deviceId;
	private int deviceTemp;
	private int ambientTemp;
	private int batteryPct;
	private int signalPct;
	private int cpuLoad;
	private int ramUsage;
	private String gpsStatus;
	private String bluetoothStatus;
	private String wifiStatus;
	private float longitude;
	private float latitude;

	public DeviceData(long time, String name, String deviceId, int deviceTemp, int ambientTemp, int batteryPct,
			int signalPct, int cpuLoad, int ramUsage, String gpsStatus, String bluetoothStatus, String wifiStatus,
			float longitude, float latitude) {
		super();
		this.time = time;
		this.name = name;
		this.deviceId = deviceId;
		this.deviceTemp = deviceTemp;
		this.ambientTemp = ambientTemp;
		this.batteryPct = batteryPct;
		this.signalPct = signalPct;
		this.cpuLoad = cpuLoad;
		this.ramUsage = ramUsage;
		this.gpsStatus = gpsStatus;
		this.bluetoothStatus = bluetoothStatus;
		this.wifiStatus = wifiStatus;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * Empty constructor
	 */
	public DeviceData() {

	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getDeviceTemp() {
		return deviceTemp;
	}

	public void setDeviceTemp(int deviceTemp) {
		this.deviceTemp = deviceTemp;
	}

	public int getAmbientTemp() {
		return ambientTemp;
	}

	public void setAmbientTemp(int ambientTemp) {
		this.ambientTemp = ambientTemp;
	}

	public int getBatteryPct() {
		return batteryPct;
	}

	public void setBatteryPct(int batteryPct) {
		this.batteryPct = batteryPct;
	}

	public int getSignalPct() {
		return signalPct;
	}

	public void setSignalPct(int signalPct) {
		this.signalPct = signalPct;
	}

	public int getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(int cpuLoad) {
		this.cpuLoad = cpuLoad;
	}

	public int getRamUsage() {
		return ramUsage;
	}

	public void setRamUsage(int ramUsage) {
		this.ramUsage = ramUsage;
	}

	public String getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public String getBluetoothStatus() {
		return bluetoothStatus;
	}

	public void setBluetoothStatus(String bluetoothStatus) {
		this.bluetoothStatus = bluetoothStatus;
	}

	public String getWifiStatus() {
		return wifiStatus;
	}

	public void setWifiStatus(String wifiStatus) {
		this.wifiStatus = wifiStatus;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * Creates a tab separated representation of all of the data
	 * 
	 * @return The data with tab separation
	 */
	public String toTabDelimitedString() {
		return toDelimitedString("\t");
	}

	/**
	 * Creates a delimited representation of all of the data
	 * 
	 * @param delimiter
	 *            The delimiter to separate the data
	 * @return The data with the delimiter separation
	 */
	public String toDelimitedString(String delimiter) {
		StringBuilder builder = new StringBuilder();

		builder.append(time).append(delimiter);
		builder.append(name).append(delimiter);
		builder.append(deviceId).append(delimiter);
		builder.append(deviceTemp).append(delimiter);
		builder.append(ambientTemp).append(delimiter);
		builder.append(batteryPct).append(delimiter);
		builder.append(signalPct).append(delimiter);
		builder.append(cpuLoad).append(delimiter);
		builder.append(ramUsage).append(delimiter);
		builder.append(gpsStatus).append(delimiter);
		builder.append(bluetoothStatus).append(delimiter);
		builder.append(wifiStatus).append(delimiter);
		builder.append(longitude).append(delimiter);
		builder.append(latitude);

		return builder.toString();
	}

	/**
	 * Initializes the DeviceData object from the tab delimited string
	 * 
	 * @param tabDelimitedString
	 *            The tab delimited string
	 */
	public void fromTabDelimitedString(String tabDelimitedString) {
		fromDelimitedString(tabDelimitedString, "\t");
	}

	/**
	 * Initializes the DeviceData object from the delimited string
	 * 
	 * @param tabDelimitedString
	 *            The tab delimited string
	 * @param delimiter
	 *            The delimiter to that separates the data
	 */
	public void fromDelimitedString(String tabDelimitedString, String delimiter) {
		String[] pieces = tabDelimitedString.split(delimiter);

		time = Long.parseLong(pieces[0]);
		name = pieces[1];
		deviceId = pieces[2];
		deviceTemp = Integer.parseInt(pieces[3]);
		ambientTemp = Integer.parseInt(pieces[4]);
		batteryPct = Integer.parseInt(pieces[5]);
		signalPct = Integer.parseInt(pieces[6]);
		cpuLoad = Integer.parseInt(pieces[7]);
		ramUsage = Integer.parseInt(pieces[8]);
		gpsStatus = pieces[9];
		bluetoothStatus = pieces[10];
		wifiStatus = pieces[11];
		longitude = Float.parseFloat(pieces[12]);
		latitude = Float.parseFloat(pieces[13]);
	}
}

package com.loudacre.mostactivestation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.loudacre.data.BaseStation;
import com.loudacre.data.DeviceData;
import com.loudacre.mostactivestation.distance.Haversine;

/**
 * Reorganizes the data by stem
 */
public class DistanceMapper extends Mapper<LongWritable, Text, LongWritable, BaseStation> {
	Logger logger = Logger.getLogger(DistanceMapper.class);

	/** The window of time for the output */
	int timewindow;

	/** The Calendar object used for time calculations */
	Calendar calendar;

	/** The list of all BaseStation objects */
	ArrayList<BaseStation> baseStations = new ArrayList<BaseStation>();

	/** Object used to calculate distance */
	Haversine haversine;

	/** Device data object that gets reused */
	DeviceData deviceData = new DeviceData();

	@Override
	public void setup(Context context) throws IOException {
		haversine = new Haversine();

		timewindow = Integer.parseInt(context.getConfiguration().get("timewindow", "60"));
		logger.info("timewindow set to " + timewindow);

		calendar = Calendar.getInstance();

		readBaseStations(context);
	}

	/**
	 * Read in the base stations list file
	 * 
	 * @param context
	 * @throws IOException
	 */
	private void readBaseStations(Context context) throws IOException {
		String baseStationList = context.getConfiguration().get("basestationlist");

		if (baseStationList == null) {
			logger.warn("basestationlist is not defined. This may cause the results to be empty.");
			return;
		}

		logger.info("basestationlist set to " + baseStationList);
		
		Path baseStationPath = new Path(baseStationList);
		FileSystem fs = FileSystem.get(context.getConfiguration());

		// Read the file or files in a directory
		if (fs.isFile(baseStationPath)) {
			processFile(baseStationPath, fs);
		} else {
			FileStatus[] files = fs.listStatus(baseStationPath);

			for (FileStatus file : files) {
				processFile(file.getPath(), fs);
			}
		}
	}

	/**
	 * Processes the actual file with the base stations
	 * 
	 * @param path
	 *            The path of the file
	 * @param fs
	 *            The FileSystem object to open the file
	 * @throws IOException
	 */
	public void processFile(Path path, FileSystem fs) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(fs.open(path)));

		String line = null;

		while ((line = input.readLine()) != null) {
			// Split each line and create the BaseStation objects
			BaseStation baseStation = new BaseStation();
			baseStation.fromTabDelimitedString(line);

			addBaseStation(baseStation);
		}

		input.close();
	}

	/**
	 * Adds a new BaseStation object to the list
	 * 
	 * @param baseStation
	 *            The base station to add
	 */
	public void addBaseStation(BaseStation baseStation) {
		baseStations.add(baseStation);
	}

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// Round down the time to the closest time window
		deviceData.fromTabDelimitedString(value.toString());
		long timestamp = deviceData.getTime();
		calendar.setTimeInMillis(timestamp);

		calendar.set(Calendar.MILLISECOND, 0);

		int seconds = calendar.get(Calendar.SECOND);
		seconds = seconds - (seconds % timewindow);
		calendar.set(Calendar.SECOND, seconds);

		BaseStation closestBaseStation = findClosestBaseStation(deviceData);

		// Emit the time and the closest BaseStation
		context.write(new LongWritable(calendar.getTimeInMillis()), closestBaseStation);
	}

	/**
	 * Finds the closest BaseStation to the DeviceData object
	 * 
	 * @param deviceData
	 *            The DeviceData object to work with
	 * @return The closest BaseStation to the DeviceData object
	 */
	public BaseStation findClosestBaseStation(DeviceData deviceData) {
		double deviceLatitude = deviceData.getLatitude();
		double deviceLongitude = deviceData.getLongitude();

		BaseStation closestBaseStation = new BaseStation();
		double closestDistance = Double.MAX_VALUE;

		for (BaseStation baseStation : baseStations) {
			double distance = haversine.distanceInMiles(deviceLatitude, deviceLongitude, baseStation.getLatitude(),
					baseStation.getLongitude());

			// Compare the values to check for a closer base station
			if (distance < closestDistance) {
				closestBaseStation = baseStation;
				closestDistance = distance;
			}
		}

		return closestBaseStation;
	}
}

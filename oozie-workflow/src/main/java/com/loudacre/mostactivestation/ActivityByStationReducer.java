package com.loudacre.mostactivestation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.loudacre.data.BaseStation;

public class ActivityByStationReducer extends Reducer<LongWritable, BaseStation, Text, Text> {
	/** The maximum number of stations to output */
	int maxStations;

	/** The format that the date should be output with */
	SimpleDateFormat dateFormat;

	@Override
	public void setup(Context context) throws IOException {
		maxStations = Integer.parseInt(context.getConfiguration().get("maxstations", "5"));

		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public void reduce(LongWritable key, Iterable<BaseStation> values, Context context) throws IOException,
			InterruptedException {
		// Go through every BaseStation and calculate the count
		HashMap<BaseStation, Integer> baseStationToCount = new HashMap<BaseStation, Integer>();

		for (BaseStation value : values) {
			Integer count = baseStationToCount.get(value);

			if (count == null) {
				count = new Integer(0);
			}

			baseStationToCount.put(value, new Integer(count.intValue() + 1));
		}
		
		// Note: This is doing an in memory sort of all BaseStations. This avoids a second
		// MapReduce pass and will finish quicker. This will not scale and a second MapReduce
		// pass should be used that uses a secondary sort.

		// Sort the entries in reverse order by the values
		List<Map.Entry<BaseStation, Integer>> entries = sortByValues(baseStationToCount);

		Text time = new Text(dateFormat.format(new Date(key.get())));

		// Output the max stations now that they are sorted
		for (int i = 0; i < Math.min(maxStations, entries.size()); i++) {
			BaseStation baseStation = entries.get(i).getKey();

			String output = entries.get(i).getValue() + "\t" + baseStation.getId();
			context.write(time, new Text(output));
		}
	}

	/**
	 * Creates a LinkedList that is sorted in reverse order by the values in the HashMap
	 * 
	 * @param baseStationToCount
	 *            The HashMap with the BaseStations and counts
	 * @return A LinkedList that is sorted in reverse order by the values in the HashMap
	 */
	public List<Map.Entry<BaseStation, Integer>> sortByValues(HashMap<BaseStation, Integer> baseStationToCount) {
		List<Map.Entry<BaseStation, Integer>> entries = new LinkedList<Map.Entry<BaseStation, Integer>>(
				baseStationToCount.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<BaseStation, Integer>>() {
			@Override
			public int compare(Entry<BaseStation, Integer> o1, Entry<BaseStation, Integer> o2) {
				// Sort in reverse order
				return -1 * o1.getValue().compareTo(o2.getValue());
			}
		});
		return entries;
	}
}

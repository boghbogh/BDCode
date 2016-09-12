package com.loudacre.dev.solution;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.kitesdk.data.DatasetReader;
import org.kitesdk.data.Datasets;
import org.kitesdk.data.View;

import com.loudacre.data.Activation;

/**
 * This class reads and prints data from the Kite dataset or view, similar to
 * the "show" command in the Kite CLI utility.
 */
public class ActivationDataSetReader extends Configured implements Tool {
	
	private static Logger logger = Logger.getLogger(ActivationDataSetReader.class);
	
	// Use this URI to read all records stored in the deviceactivations dataset
	public static final String VIEW_URI = "view:hive:deviceactivations";
	
	// Use this URI to filter the deviceactivations dataset by a specific phone number
	//public static final String VIEW_URI = "view:hive:deviceactivations?phone_number=8180789180";
	
	// Use this URI to filter the deviceactivations dataset by a specific device model
	//public static final String VIEW_URI = "view:hive:deviceactivations?deviceModel=Titanic+1000";
	
	public static void main(String... args) throws Exception {
		int rc = ToolRunner.run(new ActivationDataSetReader(), args);
	    System.exit(rc);	    
	}
	
	@Override
	public int run(String[] args) throws Exception {
		View<Activation> view = null;
		DatasetReader<Activation> reader = null;
		try {
			view = Datasets.load(VIEW_URI, Activation.class);			
			reader = view.newReader();

			for (Activation activation : reader) {
				System.out.println(activation);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return 0;
	}
}

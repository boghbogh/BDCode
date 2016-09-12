package com.loudacre.dev.stubs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.kitesdk.data.Dataset;
import org.kitesdk.data.DatasetDescriptor;
import org.kitesdk.data.DatasetWriter;
import org.kitesdk.data.Datasets;

import com.loudacre.data.Activation;
import com.loudacre.util.ActivationsXmlFileParser;

/**
 * This class reads device activation data (in XML format) from a 
 * specified directory in HDFS. It then loads these records into 
 * a Kite data set (backed by the Hive metastore, as an external table) 
 * according to an Avro schema in this project's <code>resources</code> folder. 
 */
public class ActivationDataSetLoader extends Configured implements Tool {
	
	private static Logger logger = Logger.getLogger(ActivationDataSetLoader.class);
	
	// TODO: Define a dataset URI below with the following characteristics:
	//
	//          1. The dataset is named "deviceactivations"
	//          2. It does not specify a namespace
	//          3. The metadata is stored using Hive's metastore
	//          4. The data is stored at the HDFS path /loudacre/deviceactivations
	private static final String DATASET_URI = "";
	
	public static void main(String... args) throws Exception {
		int rc = ToolRunner.run(new ActivationDataSetLoader(), args);
	    System.exit(rc);	    
	}
	
	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Path to input data directory must be specified!");
			return 1;
		}

		FileSystem fileSystem = FileSystem.get(new Configuration());
		ActivationsXmlFileParser activations = null;
		DatasetWriter<Activation> writer = null;
		try {			
			
			// TODO: Create a dataset descriptor based on the 'activation.avsc' 
			// Avro schema in the 'resources' folder of your project


			// TODO: Create the dataset with the descriptor you created in the 
			// previous step, and then open a new writer instance so that you
			// can write records to this dataset
			
			

			for (Path inputPath : getInputFiles(fileSystem, args[0])) {
				// iterate over all Activation objects in the XML file
				// at the path supplied to the reader and then write  
				// each one to the Kite data set
				activations = new ActivationsXmlFileParser(inputPath);
				for (Activation activation : activations) {
					
					// TODO: write the activation object to the dataset
					
				}

				activations.close();
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
			
			if (activations != null) {
				activations.close();
			}
			
			if (fileSystem != null) {
				fileSystem.close();
			}
		}

		return 0;
	}
	
	public List<Path> getInputFiles(FileSystem fileSystem, String inputDirectory) throws IOException {
		Path inputDirPath = new Path(inputDirectory);
		
		List<Path> filePaths = new ArrayList<Path>();
		
		if (! fileSystem.isDirectory(inputDirPath)) {
			String path = inputDirPath.toUri().toASCIIString();
			logger.error("Input path '" + path + "' is not a directory");
			return Collections.emptyList();
		}
		
		for (FileStatus inputItem : fileSystem.listStatus(inputDirPath)) {
			Path path = inputItem.getPath();
			if (fileSystem.isFile(path) && path.getName().endsWith(".xml")) {
				filePaths.add(path);
			}
		}
		
		return filePaths;
	}	
}

package com.loudacre.dev.solution;

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
	
	/** the URI includes the name of the dataset as well as a path for the external table */
	private static final String DATASET_URI = "dataset:hive:deviceactivations?location=hdfs:/loudacre/deviceactivations";
	
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
			if (Datasets.exists(DATASET_URI)) {
				// Although you would almost certainly NOT do this in production, 
				// in this exercise, we delete the repository if it already exists.
				// This will allow you to re-run the code easily after fixing any
				// errors you might have encountered.
				Datasets.delete(DATASET_URI);
			}
			
			// Create a dataset descriptor based on the 'activation.avsc' 
			// Avro schema in the 'resources' folder of your project
			DatasetDescriptor descriptor = new DatasetDescriptor.Builder()
				.schemaUri("resource:activation.avsc")
				.build();

			// create the dataset and open the writer
			Dataset<Activation> dataset = Datasets.create(DATASET_URI, descriptor, Activation.class);
			writer = dataset.newWriter();
			
			for (Path inputPath : getInputFiles(fileSystem, args[0])) {
				// iterate over all Activation objects in the XML file
				// at the path supplied to the reader and then write  
				// each one to the Kite data set
				activations = new ActivationsXmlFileParser(inputPath);
				for (Activation activation : activations) {
					writer.write(activation);
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

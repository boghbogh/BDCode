package com.loudacre.writers;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.log4j.Logger;

import com.loudacre.data.Device;

public class AvroDeviceWriter {
	private static Logger logger = Logger.getLogger(AvroDeviceWriter.class);

	public static void main(String[] args) {
		// Create the writers to write out the Device objects
		File file = new File("devices.avro");
		DatumWriter<Device> datumWriter = new SpecificDatumWriter<Device>(Device.getClassSchema());
		DataFileWriter<Device> dataFileWriter = new DataFileWriter<Device>(datumWriter);

		try {
			// Write out the Device objects
			dataFileWriter.create(Device.getClassSchema(), file);
			dataFileWriter.append(new Device("F00L", "Sorrento", 1, 1.2F));
			dataFileWriter.append(new Device("F01L", "Sorrento", 1, 1.2F));
			dataFileWriter.append(new Device("F10L", "Sorrento", 1, 1.4F));
			dataFileWriter.append(new Device("F11L", "Sorrento", 1, 1.6F));
			dataFileWriter.append(new Device("F20L", "Sorrento", 1, 1.8F));
			dataFileWriter.append(new Device("F21L", "Sorrento", 1, 1.8F));
			dataFileWriter.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
}

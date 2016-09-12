package com.loudacre.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import com.loudacre.data.Activation;
import com.loudacre.data.Activation.Builder;
import com.sun.xml.internal.stream.events.XMLEventAllocatorImpl;

/**
 * This class will parse the XML data file, which contains information about
 * the activation of mobile devices, given a filesystem path to that file. For 
 * each record parsed in that file, it will return an object that represents 
 * data from the parsed record.
 * 
 * @author Jason Reed-Ness
 */
public class ActivationsXmlFileParser implements Iterable<Activation>, Iterator<Activation> {

	private static Logger logger = Logger.getLogger(ActivationsXmlFileParser.class);

	/** The filesystem object to read files */
	protected FileSystem fs;

	/** The XMLStreamReader to read the activation file */
	protected XMLStreamReader xmlr;

	/** The current node's event type */
	protected int eventType;

	/**
	 * Constructor
	 * 
	 * @param path
	 *            The activation file to read
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public ActivationsXmlFileParser(Path path) throws IOException, XMLStreamException {
		fs = FileSystem.get(new Configuration());

		if (fs.isDirectory(path)) {
			throw new IOException("The path must be a file, not a directory!");
		}

		FSDataInputStream fsStream = fs.open(path);
		BufferedInputStream bufferedStream = new BufferedInputStream(fsStream);

		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		xmlif.setEventAllocator(new XMLEventAllocatorImpl());
		xmlr = xmlif.createXMLStreamReader(bufferedStream);
	}

	@Override
	public boolean hasNext() {
		try {
			// Keep reading until file is exhausted
			while (xmlr.hasNext()) {
				eventType = xmlr.next();

				// Only look at activation elements that are starting
				if (eventType == XMLStreamConstants.START_ELEMENT
						&& xmlr.getLocalName().equals("activation")) {
					return true;
				}
			}
		} catch (XMLStreamException e) {
			logger.error(e);

			return false;
		}

		return false;
	}

	@Override
	public Activation next() {
		try {
			while (xmlr.hasNext()) {
				Activation activation = processNode();

				if (activation != null) {
					return activation;
				}
			}
		} catch (XMLStreamException e) {
			logger.error(e);
		}

		return null;
	}

	/**
	 * Processes an activation element
	 * 
	 * @return The Activation object created from the XML
	 * @throws XMLStreamException
	 */
	private Activation processNode() throws XMLStreamException {		
		Builder builder = Activation.newBuilder();
		
		// Process the activation node's attributes
		for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			if (xmlr.getAttributeLocalName(i).equals("timestamp")) {
				builder = builder.setActivationTimestamp(Long.parseLong(xmlr
						.getAttributeValue(i)) * 1000);
			} else if (xmlr.getAttributeLocalName(i).equals("type")) {
				builder = builder.setDeviceType(xmlr.getAttributeValue(i));
			}
		}

		String elementName = "";

		// Process subnodes in the activation element
		while (xmlr.hasNext()) {
			eventType = xmlr.next();

			switch (eventType) {
			case XMLStreamConstants.START_ELEMENT:
				elementName = xmlr.getLocalName();

				break;
			case XMLStreamConstants.CHARACTERS:
				String text = xmlr.getText();
				if (elementName.equals("account-number")) {
					builder = builder.setAcctNum(Integer.parseInt(text));
				} else if (elementName.equals("device-id")) {
					builder = builder.setDeviceId(text);
				} else if (elementName.equals("phone-number")) {
					builder = builder.setPhoneNumber(text);
				} else if (elementName.equals("model")) {
					builder = builder.setDeviceModel(text);
				}

				elementName = "";

				break;
			case XMLStreamConstants.END_ELEMENT:
				// Hit the end of the activation element, create the POJO and return it
				if (xmlr.getLocalName().equals("activation")) {
					return builder.build();
				}
				break;
			}
		}

		return null;
	}

	@Override
	public void remove() {
		// Do nothing
	}

	@Override
	public Iterator<Activation> iterator() {
		return this;
	}

	/**
	 * Closes all resources used by this object
	 */
	public void close() {
		try {
			xmlr.close();
		} catch (XMLStreamException e) {
			logger.error(e);
		}
	}
}

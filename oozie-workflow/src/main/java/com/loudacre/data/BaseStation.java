package com.loudacre.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class BaseStation implements Writable {
	private long id;
	private int zipcode;
	private String city;
	private String state;
	private float latitude;
	private float longitude;

	public BaseStation(long id, int zipcode, String city, String state, float latitude, float longitude) {
		super();
		this.id = id;
		this.zipcode = zipcode;
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public BaseStation() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * Initializes the DeviceData object from the tab delimited string
	 * 
	 * @param tabDelimitedString
	 *            The tab delimited string
	 */
	public void fromTabDelimitedString(String tabDelimitedString) {
		String[] pieces = tabDelimitedString.split("\t");

		id = Long.parseLong(pieces[0]);
		zipcode = Integer.parseInt(pieces[1]);
		city = pieces[2];
		state = pieces[3];
		latitude = Float.parseFloat(pieces[4]);
		longitude = Float.parseFloat(pieces[5]);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(id);
		out.writeInt(zipcode);
		out.writeUTF(city);
		out.writeUTF(state);
		out.writeFloat(latitude);
		out.writeFloat(longitude);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id = in.readLong();
		zipcode = in.readInt();
		city = in.readUTF();
		state = in.readUTF();
		latitude = in.readFloat();
		longitude = in.readFloat();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof BaseStation) {
			return id == ((BaseStation) other).id;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int) id;
	}

	@Override
	public String toString() {
		return "BaseStation: " + id;
	}
}

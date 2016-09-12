package com.loudacre.mostactivestation.distance;

/*
 * Copyright 2010 LinkedIn Corp. and contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * Calculates distance (in miles) between two latitude/longitude pairs using the Haversine formula. This is based on
 * code from the LinkedIn DataFu HaversineDistInMiles UDF for Pig.
 * 
 * https://github.com/linkedin/datafu/blob/master/src/java/datafu/pig/geo/HaversineDistInMiles.java
 */
public class Haversine {

	private static final double EARTH_RADIUS = 3958.75;

	public double distanceInMiles(double lat1, double lng1, double lat2, double lng2) {
		double d_lat = Math.toRadians(lat2 - lat1);
		double d_long = Math.toRadians(lng2 - lng1);
		double a = Math.sin(d_lat / 2) * Math.sin(d_lat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(d_long / 2) * Math.sin(d_long / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return EARTH_RADIUS * c;
	}
}

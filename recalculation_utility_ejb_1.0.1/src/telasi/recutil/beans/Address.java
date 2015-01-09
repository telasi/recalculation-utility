/*
 * Copyright 2007, Dimitri Kurashvili (dimakura@gmail.com)
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
package telasi.recutil.beans;

import java.io.Serializable;

/**
 * Address.
 * @author dimakura
 */
public class Address implements Serializable {
	private static final long serialVersionUID = 5584521822047447561L;
	private int id;
	private int regionId;
	private int streetId;
	private String regionName;
	private String streetName;
	private String house;
	private String building;
	private String pourch;
	private String flate;
	private String postIndex;

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFlate() {
		return flate;
	}

	public void setFlate(String flate) {
		this.flate = flate;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getPostIndex() {
		return postIndex;
	}

	public void setPostIndex(String postIndex) {
		this.postIndex = postIndex;
	}

	public String getPorch() {
		return pourch;
	}

	public void setPorch(String pourch) {
		this.pourch = pourch;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getStreetId() {
		return streetId;
	}

	public void setStreetId(int streetId) {
		this.streetId = streetId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		if (streetName != null && streetName.trim().length() > 0) {
			b.append(streetName.trim());
			b.append(" ");
		}
		if (house != null && house.trim().length() > 0) {
			b.append("#");
			b.append(house.trim());
			b.append(" ");
		}
		return b.toString().trim();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

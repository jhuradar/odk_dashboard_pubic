/*
 * Copyright (C) 2016 John Hopkings Bloomberg School of Public Health
 *
 * Licensed under the MIT License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.jhsph.radar.dashboard.rs.model;


public class DuplicateData {
	private String 	addressId;
	private int		count;
	
	public DuplicateData(String addressId, int count) {
		super();
		this.addressId = addressId;
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getAddressId() {
		return addressId;
	}
}

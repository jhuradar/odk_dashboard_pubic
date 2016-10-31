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


public class CallbacksTotalsData {
	
	private String	number;
	private int		totalAny;
	private int		totalHouseholdOnly;
	private int		totalWoman;
	private int		totalChild;

	public CallbacksTotalsData(String number, int totalAny, int totalWoman, int totalChild, int totalHouseholdOnly) {
		super();
		this.number = number;
		this.totalAny = totalAny;
		this.totalWoman = totalWoman;
		this.totalChild = totalChild;
		this.totalHouseholdOnly = totalHouseholdOnly;
	}

	public String getNumber() {
		return number;
	}
	
	public int getTotalAny() {
		return totalAny;
	}
	
	public int getTotalChild() {
		return totalChild;
	}
	
	public int getTotalWoman() {
		return totalWoman;
	}

	public int getTotalHouseholdOnly() {
		return totalHouseholdOnly;
	}
}

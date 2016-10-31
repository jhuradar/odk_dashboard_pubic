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


public class RatioData {
	private String 	interviewerNumber;
	private int		count1;
	private int		count2;
	
	public RatioData(String interviewerNumber, int count1, int count2) {
		super();
		this.interviewerNumber = interviewerNumber;
		this.count1 = count1;
		this.count2 = count2;
	}
	
	public String getInterviewerNumber() {
		return interviewerNumber;
	}
	
	public int getCount1() {
		return count1;
	}

	public int getCount2() {
		return count2;
	}
}

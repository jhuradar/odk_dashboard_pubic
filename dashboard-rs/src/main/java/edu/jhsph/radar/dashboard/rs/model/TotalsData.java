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


public class TotalsData {
	
	private int		formsSubmitted;
	private int		totalWoman;
	private int		totalChild;
	private int		totalAnthropometry;
	private int		formsSubmittedTotal;
	private int		totalWomanTotal;
	private int		totalChildTotal;
	private int		totalAnthropometryTotal;
	
	public TotalsData(int formsSubmitted, int formsSubmittedTotal, int totalWoman, int totalWomanTotal, 
			int totalChild, int totalChildTotal, int totalAnthropometry, int totalAnthropometryTotal) {
		super();
		this.formsSubmitted = formsSubmitted;
		this.totalWoman = totalWoman;
		this.totalChild = totalChild;
		this.totalAnthropometry = totalAnthropometry;
		this.formsSubmittedTotal = formsSubmittedTotal;
		this.totalWomanTotal = totalWomanTotal;
		this.totalChildTotal = totalChildTotal;
		this.totalAnthropometryTotal = totalAnthropometryTotal;
	}

	public int getFormsSubmitted() {
		return formsSubmitted;
	}
	
	public int getTotalAnthropometry() {
		return totalAnthropometry;
	}
	
	public int getTotalChild() {
		return totalChild;
	}
	
	public int getTotalWoman() {
		return totalWoman;
	}

	public int getFormsSubmittedTotal() {
		return formsSubmittedTotal;
	}
	
	public int getTotalAnthropometryTotal() {
		return totalAnthropometryTotal;
	}
	
	public int getTotalChildTotal() {
		return totalChildTotal;
	}
	
	public int getTotalWomanTotal() {
		return totalWomanTotal;
	}
}

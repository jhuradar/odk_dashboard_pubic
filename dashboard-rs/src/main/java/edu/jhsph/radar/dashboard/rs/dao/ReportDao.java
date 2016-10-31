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
package edu.jhsph.radar.dashboard.rs.dao;

import java.util.List;

import edu.jhsph.radar.dashboard.rs.model.CallbackData;
import edu.jhsph.radar.dashboard.rs.model.CallbacksTotalsData;
import edu.jhsph.radar.dashboard.rs.model.DuplicateData;
import edu.jhsph.radar.dashboard.rs.model.RatioData;
import edu.jhsph.radar.dashboard.rs.model.ReportData;
import edu.jhsph.radar.dashboard.rs.model.TotalsData;


public interface ReportDao {
	List<ReportData> 			getHousehold(String idType);
	List<ReportData> 			getWoman(String idType);
	List<ReportData> 			getCaretaker(String idType);
	List<ReportData> 			getEligibleWoman(String idType);
	List<ReportData> 			getEligibleChild(String idType);
	TotalsData 					getTotals();
	List<RatioData> 			getChild_5_4(String idType);
	List<RatioData> 			getWoman_14_15(String idType);
	List<RatioData> 			getWoman_50_49(String idType);
	List<DuplicateData> 		getDuplicateHouseholds();
	List<CallbacksTotalsData>	getCallbacksTotals();
	List<CallbackData>			getHouseholdCallbacks();
	List<CallbackData>			getWomanCallbacks();
	List<CallbackData>			getChildCallbacks();
}

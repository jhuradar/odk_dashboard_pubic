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
package edu.jhsph.radar.dashboard.rs.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.jhsph.radar.dashboard.rs.dao.ReportDao;
import edu.jhsph.radar.dashboard.rs.model.CallbackData;
import edu.jhsph.radar.dashboard.rs.model.CallbacksTotalsData;
import edu.jhsph.radar.dashboard.rs.model.DuplicateData;
import edu.jhsph.radar.dashboard.rs.model.RatioData;
import edu.jhsph.radar.dashboard.rs.model.ReportData;
import edu.jhsph.radar.dashboard.rs.model.TotalsData;


@Named
@Singleton
public class SpringJdbcReportDao implements ReportDao {
	private final JdbcTemplate template;

	private static final Log LOG = LogFactory.getLog(SpringJdbcReportDao.class);

	@Inject
	public SpringJdbcReportDao(JdbcTemplate template) {
		this.template = template;
	}

	public List<ReportData> getHousehold(String idType) {
		LOG.debug("Entering getHousehold function for" + idType);
		try {
			/* Use following query to join on usernames to display 0 counts;
			 * This was done early in the project; to use it needs to be updated to use views
			 * 
			return template.query("select U.local_username name, " +
									       "C.NA_00_HH3B number, " +
									       "count(case when C.hh6 = '01' then 1 else null end) success, " +
									       "count(C._creator_uri_user) total " +
									  "from _registered_users U " +
									     "LEFT JOIN RD_CORE C on U._uri = C._creator_uri_user " +
									  "where U.IS_REMOVED='0'" +
									  "group by U._uri",
					new ReportRowMapper());
			 */
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
									       "count(case when c.INTERVIEW_RESULT = '01' then 1 else null end) success, " +
									       "count(c._creator_uri_user) total " +
									  "from VIEW_CORE c " +
									  "group by number",
					new ReportRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<ReportData>();
	}

	public List<ReportData> getWoman(String idType) {
		LOG.debug("Entering getWoman function for" + idType);
		try {
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
										"count(case when w.RESULT = '1' then 1 else null end) success, " +
										"count(w._URI) total " +
									"from VIEW_CORE c " +
									   "LEFT JOIN VIEW_WOMAN w ON c._URI = w._PARENT_AURI " +
									"group by number",
					new ReportRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<ReportData>();
	}

	public List<ReportData> getCaretaker(String idType) {
		LOG.debug("Entering getCaretaker function for" + idType);
		try {
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
										"count(case when ch.RESULT = '1' then 1 else null end) success, " +
										"count(ch._URI) total " +
									"from VIEW_CORE c " +
									   "LEFT JOIN VIEW_CHILD ch ON c._URI = ch._PARENT_AURI " +
									"group by number",
					new ReportRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<ReportData>();
	}

	public List<ReportData> getEligibleWoman(String idType) {
		LOG.debug("Entering getWoman function for" + idType);
		try {
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
				   						"count(case when h.ELIG_WOMAN = '1' then 1 else null end) success, " +
				   						"count(distinct c._uri) total " +
				       				"from VIEW_CORE c " +
									"LEFT OUTER JOIN VIEW_HOUSE_MEM h ON c._URI = h._PARENT_AURI " +
				       				"where c.INTERVIEW_RESULT = '01'" +
										"group by number",
					new ReportRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<ReportData>();
	}

	public List<ReportData> getEligibleChild(String idType) {
		LOG.debug("Entering getWoman function for" + idType);
		try {
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
				   						"count(case when h.ELIG_CHILD = '1' then 1 else null end) success, " +
				   						"count(distinct c._uri) total " +
				       				"from VIEW_CORE c " +
									"LEFT OUTER JOIN VIEW_HOUSE_MEM h ON c._URI = h._PARENT_AURI " +
				       				"where c.INTERVIEW_RESULT = '01'" +
										"group by number",
					new ReportRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<ReportData>();
	}

	public TotalsData getTotals() {
		LOG.debug("Entering getTotals function");
		return template.queryForObject("SELECT (SELECT COUNT(case when c.INTERVIEW_RESULT = '01' then 1 else null end) from VIEW_CORE c) as formsSubmitted, " +
												"(SELECT count(c._creator_uri_user) from VIEW_CORE c) as formsSubmittedTotal, " +
												"(SELECT COUNT(case when w.RESULT = '1' then 1 else null end) from VIEW_WOMAN w) as totalWoman, " +
												"(SELECT count(w._URI) from VIEW_WOMAN w) as totalWomanTotal, " +
												"(SELECT COUNT(case when ch.RESULT = '1' then 1 else null end) from VIEW_CHILD ch) as totalChild, " +
												"(SELECT COUNT(ch._URI) from VIEW_CHILD ch) as totalChildTotal, " +
												"(SELECT COUNT(a._URI) FROM VIEW_ANTHRO a WHERE a.AM5 = '1') as totalAnthropometry, " +
												"(SELECT COUNT(ch._URI) from VIEW_CHILD ch) as totalAnthropometryTotal " +
											"FROM dual",
					new TotalsRowMapper());
	}

	public List<RatioData> getChild_5_4(String idType) {
		LOG.debug("Entering getRatioChild54 function");
		try {
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
										"count(case when h.AGE_YEARS = '5' then 1 else null end) count1, " +
										"count(case when h.AGE_YEARS = '4' then 1 else null end) count2 " +
									"from VIEW_CORE c " +
									   "LEFT OUTER JOIN VIEW_HOUSE_MEM h ON c._URI = h._PARENT_AURI " +
									"group by number",
					new RatioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<RatioData>();
	}

	public List<RatioData> getWoman_14_15(String idType) {
		LOG.debug("Entering getWoman1415 function");
		try {
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
										"count(case when h.AGE_YEARS = '14' AND h.SEX = '2' then 1 else null end) count1, " +
										"count(case when h.AGE_YEARS = '15' AND h.SEX = '2' then 1 else null end) count2 " +
									"from VIEW_CORE c " +
									   "LEFT OUTER JOIN VIEW_HOUSE_MEM h ON c._URI = h._PARENT_AURI " +
									"group by number",
					new RatioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<RatioData>();
	}

	public List<RatioData> getWoman_50_49(String idType) {
		LOG.debug("Entering getWoman_50_49 function");
		try {
			String selector = "c.INTERVIEWER_ID";
			if (idType != null && idType.equalsIgnoreCase("Supervisor")) {
				selector = "c.SUPERVISOR_ID";
			} else if (idType != null && idType.equalsIgnoreCase("Cluster")) {
				selector = "c.HAMLET_ID";
			}
			return template.query("select " + selector + " number, " +
										"count(case when h.AGE_YEARS = '50' AND h.SEX = '2' then 1 else null end) count1, " +
										"count(case when h.AGE_YEARS = '49' AND h.SEX = '2' then 1 else null end) count2 " +
									"from VIEW_CORE c " +
									   "LEFT OUTER JOIN VIEW_HOUSE_MEM h ON c._URI = h._PARENT_AURI " +
									"group by number",
					new RatioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<RatioData>();
	}

	public List<DuplicateData> getDuplicateHouseholds() {
		LOG.debug("Entering getDuplicateHouseholds function");
		try {
			return template.query("select c.ADDRESS_ID ADDRESS_ID, " +
										"count(c._URI) count " +
									"from VIEW_CORE c " +
									   "WHERE c.ADDRESS_ID IS NOT NULL AND c.CALLBACK_REQUIRED = '0' " +
										"group by c.ADDRESS_ID " +
										"HAVING count(c._URI) > 1",
					new DuplicateRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("No users found");
		}
		return new ArrayList<DuplicateData>();
	}

	public List<CallbacksTotalsData> getCallbacksTotals() {
		LOG.debug("Entering getCallbacksTotals function");
/* changed to read from viewes
		return template.query("SELECT X.number number, " +
								      "count(case when X.house_callback = '1' then 1 else null end) totalHousehold, " +
								      "count(case when X.woman_callback = '1' then 1 else null end) totalWoman, " +
								      "count(case when X.child_callback = '1' then 1 else null end) totalChild " +
								"FROM (select distinct c.HAMLET_ID number, ADDRESS_ID, c.CALLBACK_REQUIRED as house_callback, w.CALLBACK_REQUIRED as woman_callback, ch.CALLBACK_REQUIRED as child_callback " +
								            "from VIEW_CORE c " +
								            "LEFT JOIN VIEW_WOMAN w ON c._URI = w._PARENT_AURI " +
								            "LEFT JOIN VIEW_CHILD ch ON c._URI = ch._PARENT_AURI  " +
								            "WHERE c.CALLBACK_REQUIRED = '1' " +
								            "OR  w.CALLBACK_REQUIRED = '1' " +
								            "OR ch.CALLBACK_REQUIRED = '1' " +
								        ")  X " +
								"GROUP BY X.number",
				new CallbacksTotalsRowMapper());
*/
		return template.query("SELECT c.HAMLET_ID number, " + 
										"count(case when c.CALLBACK_REQUIRED = '1' then 1 else null end) totalAny, " + 
										"count(case when c.CALLBACK_WM_REQUIRED = '1' then 1 else null end) totalWoman, " +
										"count(case when c.CALLBACK_CH_REQUIRED = '1' then 1 else null end) totalChild, " +
										"count(case when c.CALLBACK_HH_REQUIRED = '1' then 1 else null end) totalHouseholdOnly, " +
										"count(case when c.CALLBACK_AT_REQUIRED = '1' then 1 else null end) totalAnthro " +
								"from VIEW_CORE c " +
								"GROUP BY number",
				new CallbacksTotalsRowMapper());
	}
	
	public List<CallbackData> getHouseholdCallbacks() {
		LOG.debug("Entering getHouseholdCallbacks function");

		return template.query("select c.ADDRESS_ID id, h.PERSON_NAME name " +
									"from VIEW_CORE c " +
									   "INNER JOIN VIEW_HOUSE_MEM h ON c._URI = h._PARENT_AURI " +
									"where c.CALLBACK_REQUIRED = '1' AND h.RELATION = '01'",
							new CallbackRowMapper());
	}

	public List<CallbackData> getWomanCallbacks() {
		LOG.debug("Entering getHouseholdCallbacks function");

		return template.query("select w.WOMAN_ID id, w.WOMAN_NAME name " +
								"from VIEW_WOMAN w " +
								"where w.CALLBACK_REQUIRED = '1'",
						new CallbackRowMapper());
	}
	
	public List<CallbackData> getChildCallbacks() {
		LOG.debug("Entering getHouseholdCallbacks function");

		return template.query("select ch.CHILD_ID id, ch.CHILD_NAME name " +
									"from VIEW_CHILD ch " +
									"where ch.CALLBACK_REQUIRED = '1'",
					new CallbackRowMapper());
	}
	
	private class ReportRowMapper implements RowMapper<ReportData> {
		public ReportData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ReportData(rs.getString("number"), rs.getInt("success"), rs.getInt("total"));
		}
	}

	private class CallbacksTotalsRowMapper implements RowMapper<CallbacksTotalsData> {
		public CallbacksTotalsData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new CallbacksTotalsData(rs.getString("number"), rs.getInt("totalAny"),
						rs.getInt("totalWoman"), rs.getInt("totalChild"), rs.getInt("totalHouseholdOnly"));
		}
	}

	private class TotalsRowMapper implements RowMapper<TotalsData> {
		public TotalsData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TotalsData(rs.getInt("formsSubmitted"), rs.getInt("formsSubmittedTotal"), 
								  rs.getInt("totalWoman"), rs.getInt("totalWomanTotal"),
								  rs.getInt("totalChild"), rs.getInt("totalChildTotal"), 
								  rs.getInt("totalAnthropometry"), rs.getInt("totalAnthropometryTotal"));
		}
	}

	private class RatioRowMapper implements RowMapper<RatioData> {
		public RatioData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new RatioData(rs.getString("number"), rs.getInt("count1"), rs.getInt("count2"));
		}
	}

	private class DuplicateRowMapper implements RowMapper<DuplicateData> {
		public DuplicateData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new DuplicateData(rs.getString("ADDRESS_ID"), rs.getInt("count"));
		}
	}

	private class CallbackRowMapper implements RowMapper<CallbackData> {
		public CallbackData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new CallbackData(rs.getString("id"), rs.getString("name"));
		}
	}
}

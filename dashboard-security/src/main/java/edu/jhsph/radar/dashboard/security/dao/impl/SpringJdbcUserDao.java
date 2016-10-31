/*
 * Copyright (C) 2016 John Hopking Bloomberg School of Public Health
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
package edu.jhsph.radar.dashboard.security.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.jhsph.radar.dashboard.security.dao.UserDao;


@Named
@Singleton
public class SpringJdbcUserDao implements UserDao {
	private final JdbcTemplate template;

	private static final Log LOG = LogFactory.getLog(SpringJdbcUserDao.class);

	@Inject
	public SpringJdbcUserDao(JdbcTemplate template) {
		this.template = template;
	}

	public String getUserPassword(String username) {
		LOG.debug("Entering getPortalUser function. username = " + username);
		try {
			return template.queryForObject("select DIGEST_AUTH_PASSWORD from _registered_users where LOCAL_USERNAME=? and IS_REMOVED='0'",
					new Object[] { username }, String.class);
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("User not found");
		}
		return null;
	}

	public List<String> getUserRoles(String username) {
		LOG.debug("Entering getPortalUser function. username = " + username);
		try {
			return template.queryForList("select a.GRANTED_AUTHORITY as roles from _registered_users u, _user_granted_authority a where u._URI=a.USER and u.LOCAL_USERNAME=? and u.IS_REMOVED='0'",
					new Object[] { username }, String.class);
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("User not found");
		}
		return null;
	}
}

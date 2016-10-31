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
package edu.jhsph.radar.dashboard.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import edu.jhsph.radar.dashboard.security.dao.UserDao;


public class DashboardAuthenticationProvider implements AuthenticationProvider {
	private static final Log LOG = LogFactory.getLog(DashboardAuthenticationProvider.class);

	private static final String GROUP_DATA_VIEWERS 		= "GROUP_DATA_VIEWERS";
	private static final String GROUP_SITE_ADMINS 		= "GROUP_SITE_ADMINS";


	private String realmString;

	@Inject
	protected UserDao springJdbcUsersDao;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String encodedPassword = PasswordEncoder.encode(authentication.getName(), realmString, authentication.getCredentials().toString());

		String passwordFromDb = springJdbcUsersDao.getUserPassword(authentication.getName());

		LOG.info("name: " + authentication.getName() + "; pass: " + authentication.getCredentials() + "; encode:" + encodedPassword + "; from DB: " + passwordFromDb);

		if (encodedPassword.equals(passwordFromDb)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            List<String> roles = springJdbcUsersDao.getUserRoles(authentication.getName());
            boolean accessAllowed = false;
            if (roles != null) {
            	for (String role : roles) {
            		grantedAuths.add(new SimpleGrantedAuthority(role));
            		if (role.toUpperCase().contains(GROUP_SITE_ADMINS) || role.toUpperCase().contains(GROUP_DATA_VIEWERS)) {
            			accessAllowed = true;
            		}
            	}
            }
    		LOG.info("roles: " + roles);
    		if (accessAllowed) {
	            Authentication auth = new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), grantedAuths);
	            return auth;
    		}
		}

		return null;
	}

	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public String getRealmString() {
		return realmString;
	}
	public void setRealmString(String realmString) {
		this.realmString = realmString;
	}
}

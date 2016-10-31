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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordEncoder {
	private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

	public static String encode(String username, String realm, String rawPassword)  {
		String fullDigestAuth = username + ":" + realm + ":" + rawPassword;
		try {
			MessageDigest  md = MessageDigest.getInstance("MD5");
			return genHash(md, fullDigestAuth, 32);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	private static String genHash(MessageDigest md, String fullSourceString, int len )
							throws NoSuchAlgorithmException {
		byte[] asBytes;
		try {
			asBytes = fullSourceString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new NoSuchAlgorithmException("Cannot get UTF-8 encoding " + e.getMessage());
		}
		md.update(asBytes);

		byte[] messageDigest = md.digest();
		StringBuilder b = new StringBuilder();
		int i = len;
		while ( (2*messageDigest.length) < i ) {
			b.append("0");
			--i;
		}

		for ( int j = 0 ; j < messageDigest.length ; ++j ) {
			byte v = messageDigest[j];
			int hi = (v & 0xF0) >> 4;
			int lo = (v & 0x0F);
			b.append(hexDigits[hi]);
			b.append(hexDigits[lo]);
		}
		return b.toString();
	}
}

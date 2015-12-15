/*
 * #%L
 * =====================================================
 *   _____                _     ____  _   _       _   _
 *  |_   _|_ __ _   _ ___| |_  / __ \| | | | ___ | | | |
 *    | | | '__| | | / __| __|/ / _` | |_| |/ __|| |_| |
 *    | | | |  | |_| \__ \ |_| | (_| |  _  |\__ \|  _  |
 *    |_| |_|   \__,_|___/\__|\ \__,_|_| |_||___/|_| |_|
 *                             \____/
 * 
 * =====================================================
 * 
 * Hochschule Hannover
 * (University of Applied Sciences and Arts, Hannover)
 * Faculty IV, Dept. of Computer Science
 * Ricklinger Stadtweg 118, 30459 Hannover, Germany
 * 
 * Email: trust@f4-i.fh-hannover.de
 * Website: http://trust.f4.hs-hannover.de/
 * 
 * This file is part of ifmapj, version 2.3.1, implemented by the Trust@HsH
 * research group at the Hochschule Hannover.
 * %%
 * Copyright (C) 2010 - 2015 Trust@HsH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Helper class for string functions
 *
 * @author jk
 */
public final class StringHelpers {

	private StringHelpers() { }

	public static int getStringCharCount(String string, char pattern) {
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == pattern) {
				count++;
			}
		}
		return count;
	}

	public static String fromByte(byte[] value) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < value.length; i++) {
			b.append(String.format("%02x%s", value[i],
					i < value.length - 1 ? ":" : ""));
		}
		return b.toString();
	}

	public static String getRandomString(int length) {
		byte[] aesKey = new byte[length];
		new SecureRandom().nextBytes(aesKey);
		try {
			return MD5Provider.getMd5(new String(aesKey));
		} catch (NoSuchAlgorithmException e) {
			return Base64.encodeToString(aesKey, true);
		}
	}
}

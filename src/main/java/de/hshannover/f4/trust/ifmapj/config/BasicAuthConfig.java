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
package de.hshannover.f4.trust.ifmapj.config;

/**
 * Configuration for basic SSRC authentication.
 */
public class BasicAuthConfig {

	public final String url;
	public final String username;
	public final String password;
	public final String trustStorePath;
	public final String trustStorePassword;

	public final boolean threadSafe;

	/**
	 * Timeout for the initial connection attempt in milliseconds.
	 */
	public final int initialConnectionTimeout;

	/**
	 * Create a new {@link BasicAuthConfig} object with the given configuration
	 * parameter.
	 *
	 * @param url the URL to connect to
	 * @param username basic authentication user
	 * @param password basic authentication password
	 * @param trustStorePath path to trustStore
	 * @param trustStorePassword password for trustStore
	 * @param threadSafe true if the SSRC should be thread safe
	 * @param initialConnectionTimeout the initial connection timeout in milliseconds
	 */
	public BasicAuthConfig(
			String url,
			String username,
			String password,
			String trustStorePath,
			String trustStorePassword,
			boolean threadSafe,
			int initialConnectionTimeout) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.trustStorePath = trustStorePath;
		this.trustStorePassword = trustStorePassword;
		this.threadSafe = threadSafe;
		this.initialConnectionTimeout = initialConnectionTimeout;
	}

	/**
	 * Create a {@link BasicAuthConfig} for a non thread safe {@link SSRC}
	 * with initial connection timeout of 120 seconds.
	 *
	 * @param url the URL to connect to
	 * @param username basic authentication user
	 * @param password basic authentication password
	 * @param trustStorePath path to trustStore
	 * @param trustStorePassword password for trustStore
	 */
	public BasicAuthConfig(
			String url,
			String username,
			String password,
			String trustStorePath,
			String trustStorePassword) {
		this(url, username, password, trustStorePath, trustStorePassword, false, 120 * 1000);
	}
	
	@Override
	public String toString() {
		return "BasicAuthConfig [url:" + url
				+ ", username: " + username
				+ ", password: " + password
				+ ", trustStorePath: " + trustStorePath
				+ ", trustStorePassword: " + trustStorePassword
				+ ", threadSafe: " + threadSafe
				+ ", initialConnectionTimeout: " + initialConnectionTimeout + "]";
	}
}

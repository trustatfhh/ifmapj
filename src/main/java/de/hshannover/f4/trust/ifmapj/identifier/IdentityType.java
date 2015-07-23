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
 * This file is part of ifmapj, version 2.3.0, implemented by the Trust@HsH
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
package de.hshannover.f4.trust.ifmapj.identifier;

/**
 * This enum encapsulates all possible identity types as specified by IF-MAP.
 *
 * @author aw
 *
 */
public enum IdentityType {
	aikName { @Override
	public String toString() { return "aik-name"; } },
	distinguishedName { @Override
	public String toString() { return "distinguished-name"; } },
	dnsName { @Override
	public String toString() { return "dns-name"; } },
	emailAddress { @Override
	public String toString() { return "email-address"; } },
	hipHit { @Override
	public String toString() { return "hip-hit"; } },
	kerberosPrincipal { @Override
	public String toString() { return "kerberos-principal"; } },
	userName { @Override
	public String toString() { return "username"; } },
	sipUri { @Override
	public String toString() { return "sip-uri"; } },
	telUri { @Override
	public String toString() { return "tel-uri"; } },
	other { @Override
	public String toString() { return "other"; } },
}

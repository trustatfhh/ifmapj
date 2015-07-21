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
 * This file is part of ifmapj, version 2.2.2, implemented by the Trust@HsH
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
package de.hshannover.f4.trust.ifmapj.extendedIdentifiers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hshannover.f4.trust.ifmapj.binding.IfmapStrings;
import de.hshannover.f4.trust.ifmapj.exception.MarshalException;
import de.hshannover.f4.trust.ifmapj.identifier.IdentifierFactory;
import de.hshannover.f4.trust.ifmapj.identifier.Identifiers;
import de.hshannover.f4.trust.ifmapj.identifier.Identity;
import de.hshannover.f4.trust.ifmapj.log.IfmapJLog;

/**
 * Simple implementation of the {@link IcsIdentifiersFactory} interface.
 *
 * @author pe
 *
 */

@SuppressWarnings("deprecation")
public class IcsIdentifiersFactoryImpl implements IcsIdentifiersFactory {

	private DocumentBuilder mDocumentBuilder;
	private IdentifierFactory mIdentFact;

	/**
	 * Constructor
	 * Create a {@link IcsIdentifiersFactoryImpl}
	 */
	public IcsIdentifiersFactoryImpl() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			mDocumentBuilder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			IfmapJLog.error("Could not get DocumentBuilder instance [" + e.getMessage() + "]");
			throw new RuntimeException(e);
		}

		mIdentFact = Identifiers.getIdentifierFactory();
	}

	@Override
	public Identity createBackhaulInterface(String name) {
		return createExtendedIdentifier("backhaul-interface", name);
	}

	@Override
	public Identity createOverlayManagerGroup(String name) {
		return createExtendedIdentifier("overlay-manager-group", name);
	}

	@Override
	public Identity createOverlayNetworkGroup(String name) {
		return createExtendedIdentifier("overlay-network-group", name);
	}

	private Identity createExtendedIdentifier(String identifierName, String attrValue) {
		Document doc = mDocumentBuilder.newDocument();
		Element e = doc.createElementNS(IfmapStrings.ICS_METADATA_NS_URI,
				IfmapStrings.ICS_METADATA_PREFIX + ":" + identifierName);
		e.setAttribute("name", attrValue);
		e.setAttribute("administrative-domain", "");
		doc.appendChild(e);
		try {
			return mIdentFact.createIdentity(doc);
		} catch (MarshalException e1) {
			IfmapJLog.error("document that contains the extended identifier can't be handled");
			throw new RuntimeException(e1);
		}
	}

}

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
package de.hshannover.f4.trust.ifmapj.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.xml.parsers.DocumentBuilder;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.DomHelpers;
import de.hshannover.f4.trust.ifmapj.exception.IfmapErrorResult;
import de.hshannover.f4.trust.ifmapj.exception.MarshalException;
import de.hshannover.f4.trust.ifmapj.exception.UnmarshalException;

/**
 * We rely on the {@link SearchRequestHandlerTest} in order to make sure the
 * updates are correct... :-/
 */
public class SubscribeRequestHandlerTest {

	private static RequestHandler<? extends Request> sHandler = makeHandler();
	private static DocumentBuilder sDocBuilder = DomHelpers.newDocumentBuilder();
	private static final String REQ_EL_NAME = "subscribe";
	private static final String RES_EL_NAME = "subscribeReceived";
	private static final String IFMAP_URI = "http://www.trustedcomputinggroup.org/2010/IFMAP/2";

	private static SubscribeRequest makeRequest() {
		return new SubscribeRequestImpl();
	}

	private static SubscribeRequest makeSimpleRequest() {
		SubscribeRequest ret = makeRequest();
		ret.addSubscribeElement(
				TestHelpers.subscribeUpdate("mysub"));

		return ret;


	}

	private static RequestHandler<? extends Request> makeHandler() {
		return new SubscribeRequestHandler();
	}

	@Test
	public void testToElementGood() throws MarshalException {
		Request req = makeSimpleRequest();
		req.setSessionId("1234");
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNotNull(ret);
		assertEquals(REQ_EL_NAME, ret.getLocalName());
		assertEquals(IFMAP_URI, ret.getNamespaceURI());
		assertEquals(1, ret.getAttributes().getLength());
		assertEquals("1234", ret.getAttribute("session-id"));
		assertEquals(1, ret.getChildNodes().getLength());
	}

	@Test(expected = MarshalException.class)
	public void testToElementNullSessionId() throws MarshalException {
		Request req = makeSimpleRequest();
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test(expected = MarshalException.class)
	public void testToElementEmptySessionId() throws MarshalException {
		Request req = makeSimpleRequest();
		req.setSessionId("");
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test(expected = MarshalException.class)
	public void testToElementWrongType() throws MarshalException {
		Request req = new SubscribeRequestImpl();
		req.setSessionId("1234");
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test(expected = MarshalException.class)
	public void testToElementNoSubscribeElements() throws MarshalException {
		Request req = makeRequest();
		req.setSessionId("1234");
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test(expected = MarshalException.class)
	public void testToElementEmptyNameUpdate() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.addSubscribeElement(TestHelpers.subscribeUpdate(""));
		req.setSessionId("1234");
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test(expected = MarshalException.class)
	public void testToElementNullNameUpdate() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.addSubscribeElement(TestHelpers.subscribeUpdate(null));
		req.setSessionId("1234");
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test(expected = MarshalException.class)
	public void testToElementEmptyNameDelete() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.setSessionId("1234");
		req.addSubscribeElement(TestHelpers.subscribeDelete(""));
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test(expected = MarshalException.class)
	public void testToElementNullNameDelete() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.setSessionId("1234");
		req.addSubscribeElement(TestHelpers.subscribeDelete(null));
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNull(ret);
	}

	@Test
	public void testFromElementGood() throws UnmarshalException, IfmapErrorResult {
		Document doc = sDocBuilder.newDocument();
		Element response = doc.createElementNS(IFMAP_URI, "ifmap:response");
		Element result = doc.createElementNS(null, RES_EL_NAME);
		response.appendChild(result);
		Result res = sHandler.fromElement(response);
		assertNull(res);	// This handler returns null on success
	}

	@Test(expected = IfmapErrorResult.class)
	public void testFromElementWithErrorResult() throws IfmapErrorResult, UnmarshalException {
		Document doc = sDocBuilder.newDocument();
		Element response = doc.createElementNS(IFMAP_URI, "ifmap:response");
		Element result = doc.createElementNS(null, "errorResult");
		result.setAttribute("errorCode", "SystemError");
		response.appendChild(result);
		Result res = sHandler.fromElement(response);
		assertNull(res);
	}

	@Test(expected = UnmarshalException.class)
	public void testFromElementNoResult() throws IfmapErrorResult, UnmarshalException {
		Document doc = sDocBuilder.newDocument();
		Element response = doc.createElementNS(IFMAP_URI, "ifmap:response");
		Result res = sHandler.fromElement(response);
		assertNull(res);
	}

	@Test(expected = UnmarshalException.class)
	public void testFromElementWrongResult() throws IfmapErrorResult, UnmarshalException {
		Document doc = sDocBuilder.newDocument();
		Element response = doc.createElementNS(IFMAP_URI, "ifmap:response");
		Element result = doc.createElementNS(null, "publishReceived");
		response.appendChild(result);
		Result res = sHandler.fromElement(response);
		assertNull(res);
	}

	@Test
	public void testToElementSingleUpdate() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.setSessionId("1234");
		req.addSubscribeElement(
				TestHelpers.subscribeUpdate("mysub"));
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNotNull(ret);
		assertEquals(REQ_EL_NAME, ret.getLocalName());
		assertEquals(IFMAP_URI, ret.getNamespaceURI());
		assertEquals(1, ret.getAttributes().getLength());
		assertEquals("1234", ret.getAttribute("session-id"));
		assertEquals(1, ret.getChildNodes().getLength());
		Element upEl = (Element) ret.getChildNodes().item(0);
		assertEquals("update", upEl.getLocalName());
		assertNull(upEl.getNamespaceURI());
		assertNotNull(upEl.getAttributeNode("name"));
		assertEquals("mysub", upEl.getAttribute("name"));
	}

	@Test
	public void testToElementSingleDelete() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.setSessionId("1234");
		req.addSubscribeElement(
				TestHelpers.subscribeDelete("mysub"));
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNotNull(ret);
		assertEquals(REQ_EL_NAME, ret.getLocalName());
		assertEquals(IFMAP_URI, ret.getNamespaceURI());
		assertEquals(1, ret.getAttributes().getLength());
		assertEquals("1234", ret.getAttribute("session-id"));
		assertEquals(1, ret.getChildNodes().getLength());
		Element delEl = (Element) ret.getChildNodes().item(0);
		assertEquals("delete", delEl.getLocalName());
		assertNull(delEl.getNamespaceURI());
		assertNotNull(delEl.getAttributeNode("name"));
		assertEquals("mysub", delEl.getAttribute("name"));
	}

	@Test
	public void testToElementMix() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.setSessionId("1234");
		req.addSubscribeElement(
				TestHelpers.subscribeDelete("mysub"));
		req.addSubscribeElement(
				TestHelpers.subscribeUpdate("mysub"));

		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNotNull(ret);
		assertEquals(REQ_EL_NAME, ret.getLocalName());
		assertEquals(IFMAP_URI, ret.getNamespaceURI());
		assertEquals(1, ret.getAttributes().getLength());
		assertEquals("1234", ret.getAttribute("session-id"));
		assertEquals(2, ret.getChildNodes().getLength());
		Element delEl = (Element) ret.getChildNodes().item(0);
		Element upEl = (Element) ret.getChildNodes().item(1);
		assertEquals("delete", delEl.getLocalName());
		assertEquals("update", upEl.getLocalName());
		assertNull(upEl.getNamespaceURI());
		assertNull(delEl.getNamespaceURI());
		assertNotNull(upEl.getAttributeNode("name"));
		assertEquals("mysub", upEl.getAttribute("name"));
		assertNotNull(delEl.getAttributeNode("name"));
		assertEquals("mysub", delEl.getAttribute("name"));
	}

	@Test
	public void testToElementSingleUpdateWithFilter() throws MarshalException {
		SubscribeRequest req = makeRequest();
		req.setSessionId("1234");
		SubscribeUpdate up = (SubscribeUpdate) TestHelpers.subscribeUpdate("mysub");
		up.setResultFilter("myfilter");
		req.addSubscribeElement(up);
		Document doc = sDocBuilder.newDocument();
		Element ret = sHandler.toElement(req, doc);
		assertNotNull(ret);
		assertEquals(REQ_EL_NAME, ret.getLocalName());
		assertEquals(IFMAP_URI, ret.getNamespaceURI());
		assertEquals(1, ret.getAttributes().getLength());
		assertEquals("1234", ret.getAttribute("session-id"));
		assertEquals(1, ret.getChildNodes().getLength());
		Element upEl = (Element) ret.getChildNodes().item(0);
		assertEquals("update", upEl.getLocalName());
		assertNull(upEl.getNamespaceURI());
		assertNotNull(upEl.getAttributeNode("name"));
		assertEquals("mysub", upEl.getAttribute("name"));
		assertEquals("myfilter", upEl.getAttribute("result-filter"));
		assertNull(upEl.getAttributeNode("match-links"));
	}
}

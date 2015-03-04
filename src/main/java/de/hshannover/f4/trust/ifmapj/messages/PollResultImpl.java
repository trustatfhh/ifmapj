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
 * This file is part of ifmapj, version 2.2.0, implemented by the Trust@HsH
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.hshannover.f4.trust.ifmapj.exception.IfmapErrorResult;
import de.hshannover.f4.trust.ifmapj.messages.SearchResult.Type;

/**
 * Implementation of {@link PollResult}
 *
 * @author aw
 *
 */
class PollResultImpl implements PollResult {

	private final List<SearchResult> mResults;
	private final Collection<IfmapErrorResult> mErrorResults;

	PollResultImpl(List<SearchResult> results, Collection<IfmapErrorResult> eres) {

		if (results == null || eres == null) {
			throw new NullPointerException("result list is null");
		}

		mResults = new ArrayList<SearchResult>(results);
		mErrorResults = new ArrayList<IfmapErrorResult>(eres);
	}

	@Override
	public List<SearchResult> getResults() {
		return Collections.unmodifiableList(mResults);
	}

	@Override
	public Collection<SearchResult> getSearchResults() {
		return resultsOfType(SearchResult.Type.searchResult);
	}

	@Override
	public Collection<SearchResult> getUpdateResults() {
		return resultsOfType(SearchResult.Type.updateResult);
	}

	@Override
	public Collection<SearchResult> getDeleteResults() {
		return resultsOfType(SearchResult.Type.deleteResult);
	}

	@Override
	public Collection<SearchResult> getNotifyResults() {
		return resultsOfType(SearchResult.Type.notifyResult);
	}

	@Override
	public Collection<IfmapErrorResult> getErrorResults() {
		return Collections.unmodifiableCollection(mErrorResults);
	}

	private Collection<SearchResult> resultsOfType(Type type) {
		List<SearchResult> ret = new ArrayList<SearchResult>();

		for (SearchResult sr : mResults) {
			if (sr.getType() == type) {
				ret.add(sr);
			}
		}

		return Collections.unmodifiableCollection(ret);
	}
}

package uk.co.flax.luwak.intervals;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import uk.co.flax.luwak.Matches;

import static org.assertj.core.api.Fail.fail;


/**
 * Copyright (c) 2014 Lemur Consulting Ltd.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class IntervalMatchesAssert extends AbstractAssert<IntervalMatchesAssert, Matches<IntervalsQueryMatch>> {

    protected IntervalMatchesAssert(Matches<IntervalsQueryMatch> actual) {
        super(actual, IntervalMatchesAssert.class);
    }

    public IntervalsQueryMatchAssert matchesQuery(String queryId, String docId) {
        for (IntervalsQueryMatch match : actual.getMatches(docId)) {
            if (match.getQueryId().equals(queryId))
                return new IntervalsQueryMatchAssert(this, match);
        }
        fail("Document " + docId + " did not match query " + queryId);
        return null;
    }

    public static IntervalMatchesAssert assertThat(Matches<IntervalsQueryMatch> actual) {
        return new IntervalMatchesAssert(actual);
    }

    public IntervalMatchesAssert hasMatchCount(String docId, int count) {
        Assertions.assertThat(actual.getMatchCount(docId)).isEqualTo(count);
        return this;
    }

    public IntervalMatchesAssert hasErrorCount(int count) {
        Assertions.assertThat(actual.getErrors()).hasSize(count);
        return this;
    }

    public IntervalMatchesAssert hasQueriesRunCount(int count) {
        Assertions.assertThat(actual.getQueriesRun())
                .overridingErrorMessage("Expecting %d queries to be run, but was %d",
                        count, actual.getQueriesRun())
                .isEqualTo(count);
        return this;
    }

    public IntervalMatchesAssert doesNotMatchQuery(String queryId, String docId) {
        for (IntervalsQueryMatch match : actual.getMatches(docId)) {
            Assertions.assertThat(match.getQueryId()).isNotEqualTo(queryId);
        }
        return this;
    }
}

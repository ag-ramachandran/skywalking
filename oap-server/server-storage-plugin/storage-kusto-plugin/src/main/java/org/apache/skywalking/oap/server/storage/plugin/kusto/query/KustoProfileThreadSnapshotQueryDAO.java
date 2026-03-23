/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.storage.plugin.kusto.query;

import org.apache.skywalking.oap.server.storage.plugin.kusto.KustoClient;

import java.io.IOException;
import java.util.List;
import org.apache.skywalking.oap.server.core.profiling.trace.ProfileThreadSnapshotRecord;
import org.apache.skywalking.oap.server.core.storage.profiling.trace.IProfileThreadSnapshotQueryDAO;

public class KustoProfileThreadSnapshotQueryDAO implements IProfileThreadSnapshotQueryDAO {
    private final KustoClient kustoClient;

    public KustoProfileThreadSnapshotQueryDAO(final KustoClient kustoClient) {
        this.kustoClient = kustoClient;
    }

    @Override
    public List<ProfileThreadSnapshotRecord> queryRecords(String taskId) throws IOException {
        throw new UnsupportedOperationException(
            "TODO: Implement Kusto profile thread snapshot queryRecords by taskId");
    }

    @Override
    public int queryMinSequence(String segmentId, long start, long end) throws IOException {
        throw new UnsupportedOperationException(
            "TODO: Implement Kusto profile thread snapshot queryMinSequence");
    }

    @Override
    public int queryMaxSequence(String segmentId, long start, long end) throws IOException {
        throw new UnsupportedOperationException(
            "TODO: Implement Kusto profile thread snapshot queryMaxSequence");
    }

    @Override
    public List<ProfileThreadSnapshotRecord> queryRecords(String segmentId,
                                                          int minSequence,
                                                          int maxSequence) throws IOException {
        throw new UnsupportedOperationException(
            "TODO: Implement Kusto profile thread snapshot queryRecords by segment range");
    }
}

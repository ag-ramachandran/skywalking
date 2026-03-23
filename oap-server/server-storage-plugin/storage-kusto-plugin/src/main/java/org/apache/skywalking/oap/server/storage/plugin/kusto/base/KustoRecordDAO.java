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

package org.apache.skywalking.oap.server.storage.plugin.kusto.base;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.skywalking.oap.server.core.analysis.record.Record;
import org.apache.skywalking.oap.server.core.storage.IRecordDAO;
import org.apache.skywalking.oap.server.core.storage.model.Model;
import org.apache.skywalking.oap.server.core.storage.type.StorageBuilder;
import org.apache.skywalking.oap.server.library.client.request.InsertRequest;
import org.apache.skywalking.oap.server.storage.plugin.kusto.KustoClient;

@RequiredArgsConstructor
public class KustoRecordDAO implements IRecordDAO {
    private final KustoClient kustoClient;
    private final StorageBuilder storageBuilder;

    @Override
    public InsertRequest prepareBatchInsert(final Model model,
                                            final Record record) throws IOException {
        // TODO: Serialize record to a KustoInsertRequest using storageBuilder.entity2Storage(record).
        // Records are append-only (traces, logs, events) — a natural fit for Kusto.
        throw new UnsupportedOperationException("TODO: Implement Kusto record prepareBatchInsert");
    }
}

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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.skywalking.oap.server.core.analysis.metrics.Metrics;
import org.apache.skywalking.oap.server.core.storage.IMetricsDAO;
import org.apache.skywalking.oap.server.core.storage.SessionCacheCallback;
import org.apache.skywalking.oap.server.core.storage.model.Model;
import org.apache.skywalking.oap.server.core.storage.type.StorageBuilder;
import org.apache.skywalking.oap.server.library.client.request.InsertRequest;
import org.apache.skywalking.oap.server.library.client.request.UpdateRequest;
import org.apache.skywalking.oap.server.storage.plugin.kusto.KustoClient;

@RequiredArgsConstructor
public class KustoMetricsDAO implements IMetricsDAO {
    private final KustoClient kustoClient;
    private final StorageBuilder storageBuilder;

    @Override
    public List<Metrics> multiGet(final Model model,
                                  final List<Metrics> metrics) throws Exception {
        // TODO: Query Kusto for existing metrics by ID to support read-modify-write.
        // KQL: tableName | where id in ('id1','id2',...) | project *
        // Kusto is append-only; consider using materialized views for latest state.
        throw new UnsupportedOperationException("TODO: Implement Kusto metrics multiGet");
    }

    @Override
    public InsertRequest prepareBatchInsert(final Model model,
                                            final Metrics metrics,
                                            final SessionCacheCallback callback) throws IOException {
        // TODO: Serialize metrics to a KustoInsertRequest using storageBuilder.entity2Storage(metrics).
        // The request will be collected and flushed by KustoBatchDAO.flush().
        throw new UnsupportedOperationException("TODO: Implement Kusto metrics prepareBatchInsert");
    }

    @Override
    public UpdateRequest prepareBatchUpdate(final Model model,
                                            final Metrics metrics,
                                            final SessionCacheCallback callback) throws IOException {
        // TODO: In Kusto (append-only), "update" means inserting a new row with the merged value.
        // The latest row per ID can be resolved via a materialized view or arg_max() in queries.
        throw new UnsupportedOperationException("TODO: Implement Kusto metrics prepareBatchUpdate");
    }
}
